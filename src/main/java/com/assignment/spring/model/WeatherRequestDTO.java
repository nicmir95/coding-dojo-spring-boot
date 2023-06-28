package com.assignment.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class WeatherRequestDTO {

    @NotNull
    private String city;


}
