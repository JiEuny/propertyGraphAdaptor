package com.semantic.graph_adaptor;

import com.semantic.graph_adaptor.controller.HttpController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GraphAdaptorApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphAdaptorApplication.class, args);

        HttpController httpController = new HttpController();
        httpController.getEntities();
//        httpController.createSubscription();
    }

}
