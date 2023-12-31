package com.assignment.spring.service;

import com.assignment.spring.client.WeatherClient;
import com.assignment.spring.client.model.WeatherResponseDto;
import com.assignment.spring.entity.WeatherEntity;
import com.assignment.spring.repository.WeatherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class WeatherServiceTest {

    @Mock
    private WeatherClient weatherClient;

    @Mock
    private WeatherRepository weatherRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private WeatherService weatherService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetWeather() {
        String city = "London";
        WeatherResponseDto weatherResponseDto = new WeatherResponseDto();
        when(weatherClient.getWeather(city)).thenReturn(weatherResponseDto);

        WeatherResponseDto result = weatherService.getWeatherByCity(city);

        assertEquals(weatherResponseDto, result);
    }

    @Test
    public void testSaveWeather() {
        WeatherResponseDto weatherResponseDto = new WeatherResponseDto();
        WeatherEntity weatherEntity = new WeatherEntity();
        when(mapper.map(weatherResponseDto, WeatherEntity.class)).thenReturn(weatherEntity);
        when(weatherRepository.save(weatherEntity)).thenReturn(weatherEntity);

        WeatherEntity result = weatherService.saveWeather(weatherResponseDto);

        assertEquals(weatherEntity, result);
    }


}
