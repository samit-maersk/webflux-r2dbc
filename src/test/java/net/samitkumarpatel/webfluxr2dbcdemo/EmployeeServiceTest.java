package net.samitkumarpatel.webfluxr2dbcdemo;

import net.samitkumarpatel.webfluxr2dbcdemo.models.Employee;
import net.samitkumarpatel.webfluxr2dbcdemo.repositories.EmployeeRepository;
import net.samitkumarpatel.webfluxr2dbcdemo.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @MockBean
    EmployeeRepository employeeRepository;
    EmployeeService employeeService;


    @BeforeEach
    void beforeEach() {
        employeeService = new EmployeeService(employeeRepository);
    }

    @Test
    void allTest() {
        when(employeeRepository.findAll()).thenReturn(Flux.empty());
        employeeService
                .getAll()
                .as(StepVerifier::create).expectNextCount(0).verifyComplete();
    }

    @Test
    void updateTest() {
        var db = Employee.builder().id(1l).address("Washington DC").department("IT").designation("Denmark").doj(LocalDate.now()).name("user1").salary(200l).build();
        when(employeeRepository.findById(anyLong())).thenReturn(Mono.just(db));
        when(employeeRepository.save(any())).thenReturn(Mono.just(Employee.builder().name("CHANGED").salary(200l).build()));

        employeeService
                .update(anyLong(), Employee.builder().name("CHANGED").build())
                .as(StepVerifier::create)
                .consumeNextWith(employee -> {
                    assertEquals("CHANGED", employee.name(), "name has to change");
                    assertEquals(200l, employee.salary(), "salary should be similar to db");
                })
                .verifyComplete();
    }
}
