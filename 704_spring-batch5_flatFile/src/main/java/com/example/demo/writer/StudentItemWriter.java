package com.example.demo.writer;

import com.example.demo.model.StudentCsv;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class StudentItemWriter implements ItemWriter<StudentCsv> {

    @Override
    public void write(Chunk<? extends StudentCsv> chunk) throws Exception {
        System.out.println("■FirstItemWriter: " + chunk);
        chunk.getItems().stream().forEach(System.out::println);
    }
}
