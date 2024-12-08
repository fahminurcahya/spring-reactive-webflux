package com.middleearthdev.spring_reactive_webflux.dao;

import com.middleearthdev.spring_reactive_webflux.dto.ProductDto;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class ProductDao {

    private static void sleepExecution(int i) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
           throw new RuntimeException(e);
        }
    }

    public List<ProductDto> getProducts() {
        return IntStream.rangeClosed(1,10)
                .peek(ProductDao::sleepExecution)
                .peek(i->System.out.println("processing count: " + i))
                .mapToObj(i->new ProductDto(String.valueOf(i), "product" + i, "description" + i, 100 * i))
                .collect(Collectors.toList());
    }

    public Flux<ProductDto> getProductStream() {
        return Flux.range(1,10)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i-> System.out.println("proccessing count in stream flow :"+ i))
                .map(i->new ProductDto(String.valueOf(i), "product" + i, "description" + i, i));
    }

    public Flux<ProductDto> getProductList() {
        return Flux.range(1,10)
                .doOnNext(i -> System.out.println("proccesing count in stream flow :" + i))
                .map(i->new ProductDto(String.valueOf(i), "product" + i, "description" + i, i));
    }


}
