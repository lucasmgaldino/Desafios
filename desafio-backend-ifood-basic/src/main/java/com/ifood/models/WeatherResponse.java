package com.ifood.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.List;

@Data
public class WeatherResponse {

    private String name;
    private List<Weather> weather;
    private Main main;
    private Wind wind;

    @SneakyThrows
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
    }
}

@Data
class Main {
    private float temp;
    private float feels_like;
    private float temp_min;
    private float temp_max;
    private float pressure;
    private float humidity;
}

@Data
class Weather {
    private String main;
    private String description;
    private String icon;
}

@Data
class Wind {
    private float speed;
    private int deg;
}