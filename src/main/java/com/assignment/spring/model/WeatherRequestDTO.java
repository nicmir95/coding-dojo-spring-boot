package com.assignment.spring.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class WeatherRequestDTO {

    @NotNull
    @Pattern(regexp = "[A-Za-z]")
    private String city;
}
