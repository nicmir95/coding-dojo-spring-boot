package com.assignment.spring.service;

import com.assignment.spring.client.WeatherClient;
import com.assignment.spring.client.model.WeatherResponseDto;
import com.assignment.spring.entity.WeatherEntity;
import com.assignment.spring.repository.WeatherRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class WeatherService {

    private final WeatherClient weatherClient;
    private final WeatherRepository weatherRepository;
    private final ModelMapper mapper;

    public WeatherService(WeatherClient weatherClient, WeatherRepository weatherRepository, ModelMapper mapper) {
        this.weatherClient = weatherClient;
        this.weatherRepository = weatherRepository;
        this.mapper = mapper;
    }

    public WeatherResponseDto getWeather(String city) {
        return weatherClient.getWeather(city);
    }

    public WeatherEntity saveWeather(WeatherResponseDto weatherResponseDto) {
        log.info("Saving weather information for {}", weatherResponseDto.getName());

        WeatherEntity weather = mapper.map(weatherResponseDto, WeatherEntity.class);
        return weatherRepository.save(weather);
    }

    public WeatherEntity getLastWeatherSnapshotByCity(String city) {
        return weatherRepository.findFirstByCityOrderByCreatedOnDesc(city.trim());
    }

    public List<WeatherEntity> getWeatherSnapshotForTemperatureInterval(Double low, Double high) {
        return weatherRepository.findAllByTemperatureBetween(low, high);
    }


}