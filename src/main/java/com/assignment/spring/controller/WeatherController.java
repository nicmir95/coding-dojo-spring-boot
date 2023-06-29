package com.assignment.spring.controller;

import com.assignment.spring.client.model.WeatherResponseDto;
import com.assignment.spring.entity.WeatherEntity;
import com.assignment.spring.model.WeatherSnapshotRequest;
import com.assignment.spring.model.WeatherSnapshotResponse;
import com.assignment.spring.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@RestController
@Slf4j
@Validated
public class WeatherController {

    final private WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping(value = "/weather")
    public WeatherSnapshotResponse createWeather(@Valid @RequestBody WeatherSnapshotRequest weatherSnapshotRequest) {
        log.info("Creating weather snapshot for city {}", weatherSnapshotRequest.getCity());

        WeatherResponseDto weatherResponseDto = weatherService.getWeatherByCity(weatherSnapshotRequest.getCity());
        WeatherEntity weatherEntity = weatherService.saveWeather(weatherResponseDto);

        return WeatherSnapshotResponse.builder().id(weatherEntity.getId()).createdOn(weatherEntity.getCreatedOn().toLocalDateTime()).city(weatherEntity.getCity()).country(weatherEntity.getCountry()).temperature(weatherEntity.getTemperature()).build();
    }

    @GetMapping(value = "/weather/{id}", produces = "application/json")
    public WeatherSnapshotResponse getWeatherSnapshotById(@PathVariable("id") @NotNull Integer id) {
        log.info("Retrieving weather snapshot with id {}", id);

        WeatherEntity weatherEntity = weatherService.getWeatherSnapshotById(id);

        return WeatherSnapshotResponse.builder().city(weatherEntity.getCity()).id(weatherEntity.getId()).country(weatherEntity.getCountry()).temperature(weatherEntity.getTemperature()).createdOn(weatherEntity.getCreatedOn().toLocalDateTime()).build();
    }

    @GetMapping(value = "/weather", produces = "application/json")
    public WeatherSnapshotResponse getWeatherSnapshotByCityAndDate(
            @RequestParam("city") @NotNull String city,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd'T'HH:mm:ss") @RequestParam("date") @NotNull LocalDateTime date) {
        log.info("Retrieving weather snapshot for city {} for date {}", city, date);
        WeatherEntity weatherEntity = weatherService.getWeatherSnapshotByCityAndDate(city, date);

        return WeatherSnapshotResponse.builder().city(weatherEntity.getCity()).id(weatherEntity.getId()).country(weatherEntity.getCountry()).temperature(weatherEntity.getTemperature()).createdOn(weatherEntity.getCreatedOn().toLocalDateTime()).build();
    }

}
