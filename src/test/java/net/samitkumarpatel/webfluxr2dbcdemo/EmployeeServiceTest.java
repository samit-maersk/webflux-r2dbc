package net.samitkumarpatel.webfluxr2dbcdemo;

import net.samitkumarpatel.webfluxr2dbcdemo.repositories.EmployeeRepository;
import net.samitkumarpatel.webfluxr2dbcdemo.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

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
        Mockito.when(employeeRepository.findAll()).thenReturn(Flux.empty());
        employeeService
                .getAll()
                .as(StepVerifier::create).expectNextCount(0).verifyComplete();
    }
}
