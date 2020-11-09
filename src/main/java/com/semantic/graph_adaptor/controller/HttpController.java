package com.semantic.graph_adaptor.controller;

import com.google.gson.*;
import com.semantic.graph_adaptor.adaptor.EntityAdaptor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

@Controller
public class HttpController {

    RestTemplate restTemplate = new RestTemplate();
    String brokerUrl = "http://172.20.0.129:8080/entities?type=Sensor";
    HttpHeaders headers = new HttpHeaders();
    EntityAdaptor adaptor = new EntityAdaptor();

    public void getEntities() {

        headers.set("accept", "application/ld+json");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String result = restTemplate.exchange(brokerUrl, HttpMethod.GET, entity, String.class).getBody();

        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(result);
        JsonArray jsonArray = jsonElement.getAsJsonArray();

//        System.out.println(jsonArray.get(0));
//        System.out.println(jsonArray.get(0));
//        JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
//        System.out.println(jsonObject.get("createdAt"));


        for(int i = 0; i < jsonArray.size(); i++) {

            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            adaptor.storeEntity(jsonObject);
        }
    }

    public void createSubscription() {

        headers.set("Accept", "application/json");
        headers.set("Content-Type", "application/ld+json");

        String subscriptionFile = "/src/main/java/com/semantic/graph_adaptor/controller/SubscriptionList.json";

        FileReader reader = null;

        try {
            reader = new FileReader(System.getProperty("user.dir") + subscriptionFile);
        } catch (FileNotFoundException e) {

        }

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(reader);
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        for (int i = 0; i<jsonArray.size(); i++) {
            HttpEntity<String> entity = new HttpEntity<String>(jsonArray.get(i).toString(), headers);
            String result = restTemplate.exchange(brokerUrl+"/subscriptions", HttpMethod.POST, entity, String.class).getBody();
            System.out.println("create"+result);
        }

    }

    @RequestMapping(value = "/notify")
    public String notification(@RequestBody String requestbody) {

        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(requestbody).getAsJsonObject();
        JsonObject jsonObject = (JsonObject)jsonElement;
        JsonArray jsonArray = jsonObject.get("data").getAsJsonArray();

        if(jsonObject.get("subscriptionId").toString().split(":")[3].equals("Sensor\"")) {

        }

        return "";
    }
}
