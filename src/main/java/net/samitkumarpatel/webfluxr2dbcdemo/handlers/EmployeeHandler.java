package net.samitkumarpatel.webfluxr2dbcdemo.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.samitkumarpatel.webfluxr2dbcdemo.models.Employee;
import net.samitkumarpatel.webfluxr2dbcdemo.services.EmployeeService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class EmployeeHandler {
    private final EmployeeService employeeService;
    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Employee.class)
                .flatMap(employee -> employeeService.save(employee))
                .flatMap(e -> ServerResponse.ok().build());
    }

    public Mono<ServerResponse> all(ServerRequest serverRequest) {
        return ServerResponse.ok().body(employeeService.getAll(), Employee.class);
    }

    public Mono<ServerResponse> getById(ServerRequest serverRequest) {
        var id = serverRequest.pathVariable("id");
        return ServerResponse.ok().body(employeeService.getById(Long.valueOf(id)), Employee.class);
    }

    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        var id = serverRequest.pathVariable("id");
        return serverRequest.bodyToMono(Employee.class)
                .flatMap(employee -> employeeService.update(Long.valueOf(id), employee))
                .flatMap(e -> ServerResponse.ok().body(Mono.just(e), Employee.class));
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        var id = serverRequest.pathVariable("id");
        return employeeService.delete(Long.valueOf(id)).flatMap(v -> ServerResponse.ok().body(v, Void.class));
    }

}
