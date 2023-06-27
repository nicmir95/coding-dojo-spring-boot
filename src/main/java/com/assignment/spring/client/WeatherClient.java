package com.assignment.spring.client;

import com.assignment.spring.client.model.WeatherResponseDto;

public interface WeatherClient{
    WeatherResponseDto getWeather(String city);
}
