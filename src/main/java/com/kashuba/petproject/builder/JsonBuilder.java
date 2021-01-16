package com.kashuba.petproject.builder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kashuba.petproject.model.entity.Policy;

public class JsonBuilder {

public static String createJson(Policy policy) {

    Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    String json = GSON.toJson(policy);
    System.out.println(json);
    return json;
}
}
