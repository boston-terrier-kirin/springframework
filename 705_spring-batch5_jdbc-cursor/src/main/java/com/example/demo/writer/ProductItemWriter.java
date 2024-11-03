package com.example.demo.writer;

import com.example.demo.model.Product;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class ProductItemWriter implements ItemWriter<Product> {

    @Override
    public void write(Chunk<? extends Product> chunk) throws Exception {
        System.out.println("■FirstItemWriter: " + chunk);
        chunk.getItems().stream().forEach(System.out::println);
    }
}
