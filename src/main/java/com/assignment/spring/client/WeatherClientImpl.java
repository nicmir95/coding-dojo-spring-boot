package com.assignment.spring.client;

import com.assignment.spring.client.model.WeatherResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
public class WeatherClientImpl implements WeatherClient {

    private final RestTemplate restTemplate;

    private String apikey;

    private String baseUrl;

    public WeatherClientImpl(@Value("${weatherService.apikey}") String apikey, RestTemplate restTemplate, @Value("${weatherService.baseUrl}") String baseUrl) {
        this.apikey = apikey;
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    @Override
    public WeatherResponseDto getWeather(String city) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json;charset=utf-8");
        headers.set("Content-Type", "application/json;charset=utf-8");

        String weatherURL = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("q", "{q}")
                .queryParam("appid", "{appid}")
                .encode()
                .toUriString();
        Map<String, String> params = Map.of(
                "q", city,
                "appid", apikey
        );

        ResponseEntity<WeatherResponseDto> responseEntity = restTemplate
                .exchange(weatherURL, HttpMethod.GET, new HttpEntity<>(headers), WeatherResponseDto.class, params);

        return responseEntity.getBody();
    }
}
