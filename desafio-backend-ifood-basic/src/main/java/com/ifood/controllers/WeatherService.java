package com.ifood.controllers;

import com.ifood.models.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weatherService", url = "http://api.openweathermap.org/data/2" +
        ".5/weather?lang=pt_br&units=metric&appid=${api.key}")
public interface WeatherService {

    @GetMapping
    Response getWeather(@RequestParam(name = "q") String city);

}
