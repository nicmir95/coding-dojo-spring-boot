package com.assignment.spring.service;

import com.assignment.spring.client.WeatherClient;
import com.assignment.spring.client.model.WeatherResponseDto;
import com.assignment.spring.entity.WeatherEntity;
import com.assignment.spring.repository.WeatherRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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

    public WeatherResponseDto getWeatherByCity(String city) {
        return weatherClient.getWeather(city);
    }

    public WeatherEntity getWeatherSnapshotById(Integer id) {
        Optional<WeatherEntity> optionalWeatherEntity = weatherRepository.findById(id);
        return optionalWeatherEntity.isPresent() ? optionalWeatherEntity.get() : null;
    }
    public WeatherEntity saveWeather(WeatherResponseDto weatherResponseDto) {
        log.info("Saving weather information for {}", weatherResponseDto.getName());

        WeatherEntity weather = mapper.map(weatherResponseDto, WeatherEntity.class);
        return weatherRepository.save(weather);
    }
    public WeatherEntity getWeatherSnapshotByCityAndDate(String city, LocalDateTime date) {
        return weatherRepository.findByCityAndCreatedOn(city, date);
    }



}
