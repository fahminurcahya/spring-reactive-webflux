package com.middleearthdev.spring_reactive_webflux.handler;

import com.middleearthdev.spring_reactive_webflux.dao.ProductDao;
import com.middleearthdev.spring_reactive_webflux.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductHandler {

    @Autowired
    private ProductDao productDao;

    public Mono<ServerResponse> getProduct(ServerRequest request) {
        Flux<ProductDto> dtoFlux = productDao.getProductList();
        return ServerResponse.ok().body(dtoFlux, ProductDto.class);
    }

    public Mono<ServerResponse> findProduct(ServerRequest request) {
        String productId = request.pathVariable("productId");
        Mono<ProductDto> dao = productDao.getProductList().filter(product -> product.getId().equals(productId)).next();
        return ServerResponse.ok().body(dao, ProductDto.class);

    }

    public Mono<ServerResponse> saveProduct(ServerRequest request) {
        Mono<ProductDto> productDtoMono = request.bodyToMono(ProductDto.class);
        Mono<String> map = productDtoMono.map(productDto -> productDto.getId() + ": " + productDto.getName() + " has been saved");
        return ServerResponse.ok().body(map, String.class);
    }
}
