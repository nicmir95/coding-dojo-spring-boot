package com.assignment.spring.controller;

import com.assignment.spring.client.model.WeatherResponseDto;
import com.assignment.spring.entity.WeatherEntity;
import com.assignment.spring.model.WeatherRequestDTO;
import com.assignment.spring.model.WeatherSnapshotResponse;
import com.assignment.spring.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@Slf4j
public class WeatherController {

    private WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping(value = "/weather")
    public ResponseEntity<WeatherSnapshotResponse> createWeather(@Valid @RequestBody WeatherRequestDTO weatherRequestDTO) {
        log.info("Creating weather snapshot for {}", weatherRequestDTO.getCity());

        WeatherResponseDto weatherResponseDto = weatherService.getWeather(weatherRequestDTO.getCity());
        WeatherEntity weatherEntity = weatherService.saveWeather(weatherResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(WeatherSnapshotResponse.builder()
                .id(weatherEntity.getId())
                .createdOn(weatherEntity.getCreatedOn())
                .city(weatherEntity.getCity())
                .country(weatherEntity.getCountry())
                .temperature(weatherEntity.getTemperature())
                .build());
    }

    @GetMapping(value = "/weather/{city}", produces = "application/json")
    public WeatherSnapshotResponse getWeatherSnapshot(@NotBlank @Pattern(regexp = "[A-Za-z]") @PathVariable("city") String city) {
        log.info("Retrieving weather information for {}", city);

        WeatherEntity weatherEntity = weatherService.getLastWeatherSnapshotByCity(city);

        return WeatherSnapshotResponse.builder()
                .city(weatherEntity.getCity())
                .id(weatherEntity.getId())
                .country(weatherEntity.getCountry())
                .temperature(weatherEntity.getTemperature())
                .createdOn(weatherEntity.getCreatedOn())
                .build();
    }
    @GetMapping(value = "/weather", produces = "application/json")
    public List<WeatherSnapshotResponse> getWeatherSnapshotForPeriodForCity(
            @RequestParam("lowTemp") @NotNull Double lowTemp,
            @RequestParam("highTemp") @NotNull Double highTemp) {
        List<WeatherEntity> weatherEntityList = weatherService.getWeatherSnapshotForTemperatureInterval(lowTemp, highTemp);

        return weatherEntityList.stream().map(weatherEntity -> WeatherSnapshotResponse.builder()
                .city(weatherEntity.getCity())
                .id(weatherEntity.getId())
                .country(weatherEntity.getCountry())
                .temperature(weatherEntity.getTemperature())
                .createdOn(weatherEntity.getCreatedOn()).build()).collect(Collectors.toList());

    }

}
