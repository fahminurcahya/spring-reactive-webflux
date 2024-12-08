package com.middleearthdev.spring_reactive_webflux.handler;

import com.middleearthdev.spring_reactive_webflux.dao.ProductDao;
import com.middleearthdev.spring_reactive_webflux.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductStreamHandler {
    @Autowired
    private ProductDao productDao;

    public Mono<ServerResponse> getProducts(ServerRequest request) {
        Flux<ProductDto> productStream = productDao.getProductStream();
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(productStream, ProductDto.class);
    }
}
