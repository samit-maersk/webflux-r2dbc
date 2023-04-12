package net.samitkumarpatel.webfluxr2dbcdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
public class IntegrationTest {

    WebTestClient webTestClient;
    @Autowired
    ApplicationContext applicationContext;
    @Container
    public static PostgreSQLContainer psql = new PostgreSQLContainer("postgres:14.1-alpine" );

    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        //TODO a tweak https://www.testcontainers.org/modules/databases/r2dbc/ can be applied below
        //spring.r2dbc.url=r2dbc:tc:mysql:///databasename?TC_IMAGE_TAG=8
        var r2dbcHost = String.format("r2dbc:postgresql://%s:%s/postgres", psql.getHost(), psql.getFirstMappedPort());
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
    @DisplayName("/employee test")
    void fetchAll() {
        webTestClient
                .get()
                .uri("/employee")
                .exchange()
                .expectStatus()
                .isOk();
    }
}
