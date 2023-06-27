package com.assignment.spring.configuration;

import com.assignment.spring.client.model.WeatherResponseDto;
import com.assignment.spring.entity.WeatherEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<WeatherResponseDto, WeatherEntity> typeMap = modelMapper.createTypeMap(WeatherResponseDto.class, WeatherEntity.class);
        typeMap.addMappings(mapper -> {
            mapper.map(src -> src.getName(), WeatherEntity::setCity);
            mapper.map(src -> src.getId(),   WeatherEntity::setId);
            mapper.map(src -> src.getSys().getCountry(), WeatherEntity::setCountry);
            mapper.map(src -> src.getMain().getTemp(), WeatherEntity::setTemperature);
        });
        return modelMapper;
    }
    @Bean
    public HttpClient httpClient() {
       return HttpClient.newHttpClient();
    }

}
