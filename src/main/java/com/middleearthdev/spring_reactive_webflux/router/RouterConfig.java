package com.middleearthdev.spring_reactive_webflux.router;

import com.middleearthdev.spring_reactive_webflux.handler.ProductHandler;
import com.middleearthdev.spring_reactive_webflux.handler.ProductStreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Autowired
    private ProductHandler productHandler;

    @Autowired
    private ProductStreamHandler productStreamHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("/router/products", productHandler::getProduct)
                .GET("/router/products/stream", productStreamHandler::getProducts)
                .GET("/router/products/{productId}", productHandler::findProduct)
                .POST("/router/products/save",productHandler::saveProduct)
                .build();
    }
}
