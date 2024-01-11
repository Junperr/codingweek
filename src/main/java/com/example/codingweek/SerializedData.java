package com.example.codingweek;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.util.HashMap;

public abstract class SerializedData {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public String serialize() throws JsonProcessingException {
        return objectMapper.writeValueAsString(this);
    }

    public HashMap<String, Object> toMap() throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(this);

        TypeFactory typeFactory = objectMapper.getTypeFactory();
        MapType mapType = typeFactory.constructMapType(HashMap.class, String.class, Object.class);

        return objectMapper.readValue(json, mapType);
    }

}
