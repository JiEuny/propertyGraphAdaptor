package com.semantic.graph_adaptor.adaptor;

import com.arangodb.ArangoDB;
import com.arangodb.springframework.annotation.EnableArangoRepositories;
import com.arangodb.springframework.config.ArangoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableArangoRepositories(basePackages = {"com.semantic.graph_adaptor.adaptor"})
public class DBConfiguration implements ArangoConfiguration {

    @Override
    public ArangoDB.Builder arango() {
        ArangoDB.Builder arango = new ArangoDB.Builder()
                .host("localhost", 8529)
                .user("root")
                .password("0000");
        return arango;
    }

    @Override
    public String database() {
        return "_system";
    }
}
