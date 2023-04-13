package net.samitkumarpatel.webfluxr2dbcdemo;

import net.samitkumarpatel.webfluxr2dbcdemo.models.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

@Testcontainers
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTest {

    WebTestClient webTestClient;
    @Autowired
    ApplicationContext applicationContext;
    @Container
    public static PostgreSQLContainer psql = new PostgreSQLContainer<>("postgres:14.1-alpine")
            .withCopyFileToContainer(MountableFile.forClasspathResource("schema.sql"), "/docker-entrypoint-initdb.d/schema.sql")
            .waitingFor(Wait.forListeningPort());

    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        //TODO It has to be implement like this - https://www.testcontainers.org/modules/databases/r2dbc/ can be applied below
        var r2dbcHost = String.format("r2dbc:postgresql://%s:%s/%s", /*psql.getHost()*/"127.0.0.1", psql.getFirstMappedPort(), psql.getDatabaseName());
        System.out.println(r2dbcHost);

        registry.add("spring.r2dbc.url", () -> r2dbcHost);
        registry.add("spring.r2dbc.username", psql::getUsername);
        registry.add("spring.r2dbc.password", psql::getPassword);
    }

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(applicationContext).build();
    }

    @Test
    @DisplayName("/employee POST")
    @Order(1)
    void postTest() {
        webTestClient
                .post()
                .uri("/employee")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue("""
                        {
                            "name" : "User One",
                            "address" : "WD",
                            "designation" : "Manager",
                            "salary" : 5400.60,
                            "doj": "2023-06-04",
                            "department" : "IT"
                        }
                        """)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("/employee GET/all")
    @Order(2)
    void fetchAllTest() {
        webTestClient
                .get()
                .uri("/employee")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Employee.class)
                .hasSize(1);
    }

    @Test
    @DisplayName("/employee GET/one")
    @Order(3)
    void fetchOneTest() {
        webTestClient
                .get()
                .uri("/employee/1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json("""
                    {
                        "id": 1,
                        "name" : "User One",
                        "address" : "WD",
                        "designation" : "Manager",
                        "salary" : 5400.60,
                        "doj": "2023-06-04",
                        "department" : "IT"
                    }
                """);
    }

    @Test
    @DisplayName("/employee PUT")
    @Order(4)
    void putTest() {
        webTestClient
                .put()
                .uri("/employee/1")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue("""
                        {
                            "name" : "Changed"
                        }
                        """)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json("""
                    {
                        "id": 1,
                        "name" : "Changed",
                        "address" : "WD",
                        "designation" : "Manager",
                        "salary" : 5400.60,
                        "doj": "2023-06-04",
                        "department" : "IT"
                    }
                """);
    }

    @Test
    @DisplayName("/employee DELETE")
    @Order(5)
    void deleteTest() {
        webTestClient
                .get()
                .uri("/employee/1")
                .exchange()
                .expectStatus()
                .isOk();
    }
}
