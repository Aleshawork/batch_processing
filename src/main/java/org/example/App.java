package org.example;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;


@PropertySource("classpath:application.properties")
@EnableBatchProcessing
@SpringBootApplication
public class App
{
    public static void main( String[] args ) throws RuntimeException {
        SpringApplication.run(App.class, args);
    }
}
