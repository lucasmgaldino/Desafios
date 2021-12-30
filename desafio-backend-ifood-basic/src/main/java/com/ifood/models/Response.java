package com.ifood.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.SneakyThrows;

@Getter
public class Response {

    private String cidade;
    private String condicao_meteorologica;
    private float temperatura_atual;
    private float sensação_termica;
    private float temperatura_maxima;
    private float temperatura_minima;
    private float umidade_relativa;
    private float pressao_atmosferica;
    private float velocidade_vento;

    @JsonCreator
    public Response(WeatherResponse weatherResponse) {
        this.cidade = weatherResponse.getName();
        this.condicao_meteorologica = weatherResponse.getWeather().get(0).getDescription();
        this.temperatura_atual = weatherResponse.getMain().getTemp();
        this.sensação_termica = weatherResponse.getMain().getFeels_like();
        this.temperatura_maxima = weatherResponse.getMain().getTemp_max();
        this.temperatura_minima = weatherResponse.getMain().getTemp_min();
        this.umidade_relativa = weatherResponse.getMain().getHumidity();
        this.pressao_atmosferica = weatherResponse.getMain().getPressure();
        this.velocidade_vento = weatherResponse.getWind().getSpeed() * 3.6f; // convertendo de m/s para km/h
    }

    @SneakyThrows
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
    }
}
