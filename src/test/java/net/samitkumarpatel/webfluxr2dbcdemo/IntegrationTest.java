package net.samitkumarpatel.webfluxr2dbcdemo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

@Testcontainers
@SpringBootTest
public class IntegrationTest {

    WebTestClient webTestClient;
    @Autowired
    ApplicationContext applicationContext;
    @Container
    public static PostgreSQLContainer psql = new PostgreSQLContainer<>("postgres:14.1-alpine")
            .withCopyFileToContainer(MountableFile.forClasspathResource("schema.sql"), "/docker-entrypoint-initdb.d/schema.sql");

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
