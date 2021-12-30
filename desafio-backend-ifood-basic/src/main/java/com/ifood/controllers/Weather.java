package com.ifood.controllers;

import com.ifood.models.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Weather {

    private final WeatherService weatherService;

    public Weather(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather/{city}")
    public ResponseEntity execute(@PathVariable String city) {
        // Optei por simplesmente validar a entrada, mas poderia ter adotado outra estratégia.
        if (!validateCity(city)) {
            return ResponseEntity.badRequest().body("Cidade Inválida");
        }
        Response response = this.weatherService.getWeather(city);
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

    private boolean validateCity(final String input) {
        return input.matches("[a-zA-Zà-úÀ-Ú\\s]*");
    }

}
