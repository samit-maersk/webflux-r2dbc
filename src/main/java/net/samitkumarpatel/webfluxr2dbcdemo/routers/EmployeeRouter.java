package net.samitkumarpatel.webfluxr2dbcdemo.routers;

import lombok.RequiredArgsConstructor;
import net.samitkumarpatel.webfluxr2dbcdemo.models.Employee;
import net.samitkumarpatel.webfluxr2dbcdemo.services.EmployeeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class EmployeeRouter {
    private final EmployeeService employeeService;
    @Bean
    public RouterFunction route() {
        return RouterFunctions.route()
                .path("/employee", builder -> builder
                        .GET("", this::all)
                        .POST("", this::save)
                        .GET("/{id}", this::getById)
                        .PUT("/{id}", this::update)
                        .DELETE("/{id}", this::delete))
                .build();
    }


    private Mono<ServerResponse> save(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Employee.class)
                .flatMap(employeeService::save)
                .then(ServerResponse.ok().build());
    }

    private Mono<ServerResponse> all(ServerRequest serverRequest) {
        return ServerResponse.ok().body(employeeService.getAll(), Employee.class);
    }

    private Mono<ServerResponse> getById(ServerRequest serverRequest) {
        var id = serverRequest.pathVariable("id");
        return ServerResponse.ok().body(employeeService.getById(Long.parseLong(id)), Employee.class);
    }

    private Mono<ServerResponse> update(ServerRequest serverRequest) {
        var id = serverRequest.pathVariable("id");
        return serverRequest.bodyToMono(Employee.class)
                .flatMap(employee -> employeeService.update(Long.parseLong(id), employee))
                .flatMap(e -> ServerResponse.ok().body(Mono.just(e), Employee.class));
    }

    private Mono<ServerResponse> delete(ServerRequest serverRequest) {
        var id = serverRequest.pathVariable("id");
        return employeeService
                .delete(Long.parseLong(id))
                .then(ServerResponse.ok().build());
    }
}
