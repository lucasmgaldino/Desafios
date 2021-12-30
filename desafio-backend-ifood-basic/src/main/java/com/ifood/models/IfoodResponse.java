package com.ifood.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ifood.infra.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.SneakyThrows;

import java.time.LocalDateTime;

@Getter
public class IfoodResponse {

    private String cidade;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dataHora;
    private String condicaoMeteorologica;
    private float temperaturaAtual;
    private float sensacaoTermica;
    private float temperaturaMaxima;
    private float temperaturaMinima;
    private float umidadeRelativa;
    private float pressaoAtmosferica;
    private float velocidadeDoVento;

    @JsonCreator
    public IfoodResponse(WeatherResponse weatherResponse) {
        this.dataHora = LocalDateTime.now();
        this.cidade = weatherResponse.getName();
        this.condicaoMeteorologica = weatherResponse.getWeather().get(0).getDescription();
        this.temperaturaAtual = weatherResponse.getMain().getTemp();
        this.sensacaoTermica = weatherResponse.getMain().getFeels_like();
        this.temperaturaMaxima = weatherResponse.getMain().getTemp_max();
        this.temperaturaMinima = weatherResponse.getMain().getTemp_min();
        this.umidadeRelativa = weatherResponse.getMain().getHumidity();
        this.pressaoAtmosferica = weatherResponse.getMain().getPressure();
        this.velocidadeDoVento = weatherResponse.getWind().getSpeed() * 3.6f; // convertendo de m/s para km/h
    }

    @SneakyThrows
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
    }
}
