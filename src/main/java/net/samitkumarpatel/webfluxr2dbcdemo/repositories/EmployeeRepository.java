package net.samitkumarpatel.webfluxr2dbcdemo.repositories;

import net.samitkumarpatel.webfluxr2dbcdemo.models.Employee;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface EmployeeRepository extends R2dbcRepository<Employee, Long> {
}
