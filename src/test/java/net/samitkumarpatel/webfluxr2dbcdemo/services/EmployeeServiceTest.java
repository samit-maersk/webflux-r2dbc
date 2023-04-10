package net.samitkumarpatel.webfluxr2dbcdemo.services;

import net.samitkumarpatel.webfluxr2dbcdemo.handlers.EmployeeHandler;
import net.samitkumarpatel.webfluxr2dbcdemo.models.Employee;
import net.samitkumarpatel.webfluxr2dbcdemo.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest
public class EmployeeServiceTest {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    EmployeeRepository employeeRepository;

    @MockBean
    EmployeeHandler employeeHandler;

    @Container
    public static PostgreSQLContainer psql = new PostgreSQLContainer("postgres:14.1-alpine");

    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", psql::getJdbcUrl);
        registry.add("spring.r2dbc.username", psql::getUsername);
        registry.add("spring.r2dbc.password", psql::getPassword);
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    @Order(1)
    @DisplayName("db insert test")
    @Disabled
    void saveTest() {
        employeeService
                .save(new Employee(null,"a name","W DC","Engineer",0, LocalDate.now(),"IT"))
                .as(StepVerifier::create)
                .consumeNextWith(r -> assertEquals("", r))
                .verifyComplete();
    }
    @Test
    @Order(2)
    @DisplayName("db select with where test")
    void getOneTest() {

    }
    @Test
    @Order(3)
    @DisplayName("db select * test")
    void getAllTest() {

    }
    @Test
    @Order(4)
    @DisplayName("db update test")
    void updateTest() {

    }
    @Test
    @Order(5)
    @DisplayName("db delete test")
    void deleteTest() {

    }
}
