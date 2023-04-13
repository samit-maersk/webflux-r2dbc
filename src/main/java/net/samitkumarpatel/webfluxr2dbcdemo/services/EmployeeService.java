package net.samitkumarpatel.webfluxr2dbcdemo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.samitkumarpatel.webfluxr2dbcdemo.models.Employee;
import net.samitkumarpatel.webfluxr2dbcdemo.repositories.EmployeeRepository;
import net.samitkumarpatel.webfluxr2dbcdemo.utility.NotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.util.StringUtils.hasText;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    public Flux<Employee> getAll() {
        return employeeRepository.findAll();
    }
    public Mono<Employee> getById(long id) {
        return employeeRepository
                .findById(id)
                .flatMap(e -> isNull(e) ? Mono.empty(): Mono.just(e))
                .switchIfEmpty(Mono.error(new NotFoundException(String.format("data not found with id %s", id))));
    }

    public Mono<Employee> update(long id, Employee employee) {
        var  empFromDb = employeeRepository.findById(id);
        return Mono
                .zip(
                        (data) -> {
                            var eRequest = (Employee) data[0];
                            var eDb = (Employee) data[1];
                            var emp = Employee.builder();
                            //id
                            emp.id(id);

                            //name
                            if(nonNull(eRequest) && hasText(eRequest.name())) {
                                emp.name(eRequest.name());
                            } else {
                                emp.name(eDb.name());
                            }

                            //address
                            if(nonNull(eRequest) && hasText(eRequest.address())) {
                                emp.address(eRequest.address());
                            } else {
                                emp.address(eDb.address());
                            }

                            //designation
                            if(nonNull(eRequest) && hasText(eRequest.designation())) {
                                emp.designation(eRequest.designation());
                            } else {
                                emp.designation(eDb.designation());
                            }

                            //salary
                            if(nonNull(eRequest) && eRequest.salary() != 0.0) {
                                emp.salary(eRequest.salary());
                            } else {
                                emp.salary(eDb.salary());
                            }

                            //doj
                            if(nonNull(eRequest) && nonNull(eRequest.doj())) {
                                emp.doj(eRequest.doj());
                            } else {
                                emp.doj(eDb.doj());
                            }

                            //department
                            if(nonNull(eRequest) && hasText(eRequest.department())) {
                                emp.department(eRequest.department());
                            } else {
                                emp.department(eDb.department());
                            }

                            return emp.build();
                        },
                        Mono.just(employee),
                        empFromDb
                )
                //.cast(Employee.class)
                .flatMap(employeeRepository::save)
                .switchIfEmpty(Mono.error(new NotFoundException(String.format("data not found with id %s for update", id))));
    }

    public Mono<Void> delete(long id) {
        return employeeRepository.deleteById(id);
    }
    public Mono<Void> save(Employee employee) {
        return employeeRepository.save(employee).then();
    }
}
