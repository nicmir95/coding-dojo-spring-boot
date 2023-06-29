package com.assignment.spring.configuration;

import com.assignment.spring.client.model.WeatherResponseDto;
import com.assignment.spring.entity.WeatherEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        return restTemplate;
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
            mapper.map(src -> src.getSysDto().getCountry(), WeatherEntity::setCountry);
            mapper.map(src -> src.getMainDto().getTemp(), WeatherEntity::setTemperature);
        });
        return modelMapper;
    }
    @Bean
    public HttpClient httpClient() {
       return HttpClient.newHttpClient();
    }

}
