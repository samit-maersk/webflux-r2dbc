package net.samitkumarpatel.webfluxr2dbcdemo.routers;

import net.samitkumarpatel.webfluxr2dbcdemo.handlers.EmployeeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Configuration
public class EmployeeRouter {
    @Bean
    public RouterFunction route(EmployeeHandler employeeHandler) {
        return RouterFunctions.route()
                .path("/employee", builder -> builder
                        .GET("", employeeHandler::all)
                        .GET("/{id}", employeeHandler::getById)
                        .POST("", employeeHandler::save)
                        .PUT("/{id}", employeeHandler::update)
                        .DELETE("/{id}", employeeHandler::delete))
                .build();
    }
}
