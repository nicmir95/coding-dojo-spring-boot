package com.assignment.spring.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WeatherSnapshotRequest {

    @NotNull
    private String city;


}
