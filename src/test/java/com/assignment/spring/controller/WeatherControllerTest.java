package com.assignment.spring.controller;

import com.assignment.spring.client.model.MainDto;
import com.assignment.spring.client.model.SysDto;
import com.assignment.spring.client.model.WeatherResponseDto;
import com.assignment.spring.entity.WeatherEntity;
import com.assignment.spring.model.WeatherSnapshotRequest;
import com.assignment.spring.service.WeatherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc()
public class WeatherControllerTest {


    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WeatherService weatherService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeAll
    static void before() {
        postgresContainer.start();
        postgresContainer.withReuse(true);
        System.setProperty("spring.datasource.url", postgresContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgresContainer.getUsername());
        System.setProperty("spring.datasource.password", postgresContainer.getPassword());
    }

    @AfterAll
    static void after() {
        postgresContainer.stop();
    }
    @Test
    public void createWeather_ReturnsWeatherSnapshotResponse() throws Exception {
        WeatherSnapshotRequest requestDTO = new WeatherSnapshotRequest();
        requestDTO.setCity("London");

        WeatherResponseDto responseDto = new WeatherResponseDto();
        responseDto.setName("London");
        MainDto mainDto = new MainDto();
        mainDto.setTemp(20.5);
        responseDto.setMainDto(mainDto);
        SysDto sysDto = new SysDto();
        sysDto.setCountry("UK");
        responseDto.setSysDto(sysDto);

        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setId(1);
        weatherEntity.setCountry("UK");
        weatherEntity.setCity("London");
        weatherEntity.setTemperature(20.5);
        weatherEntity.setCreatedOn(LocalDateTime.now());

        when(weatherService.saveWeather(any(WeatherResponseDto.class))).thenReturn(weatherEntity);
        when(weatherService.getWeatherByCity("London")).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/weather")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("London"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.temperature").value(20.5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdOn").exists());
    }

    @Test
    public void createWeather_InvalidRequest_ReturnsBadRequest() throws Exception {
        WeatherSnapshotRequest requestDTO = new WeatherSnapshotRequest();

        mockMvc.perform(MockMvcRequestBuilders.post("/weather")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getWeatherSnapshotById_ReturnsWeatherSnapshotResponse() throws Exception {
        Integer id = 1;

        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setId(1);
        weatherEntity.setCity("London");
        weatherEntity.setTemperature(20.5);
        weatherEntity.setCreatedOn(LocalDateTime.now());

        when(weatherService.getWeatherSnapshotById(id)).thenReturn(weatherEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/weather/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("London"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.temperature").value(20.5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdOn").exists());
    }

    @Test
    public void createWeather_ServiceThrowsException_ReturnsInternalServerError() throws Exception {
        WeatherSnapshotRequest requestDTO = new WeatherSnapshotRequest();
        requestDTO.setCity("London");

        when(weatherService.getWeatherByCity("London")).thenThrow(new RuntimeException("Failed to get weather data."));

        mockMvc.perform(MockMvcRequestBuilders.post("/weather")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void createWeather_InvalidRequest_ThrowsConstraintViolationException() throws Exception {
        WeatherSnapshotRequest requestDTO = new WeatherSnapshotRequest();

        mockMvc.perform(MockMvcRequestBuilders.post("/weather")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getWeatherSnapshot_InvalidCity_ThrowsConstraintViolationException() throws Exception {
        String city = "";

        mockMvc.perform(MockMvcRequestBuilders.get("/weather/{city}", city)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getWeatherSnapshotByCityAndDate_ShouldReturnWeatherSnapshotResponse() throws Exception {
        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setId(1);
        weatherEntity.setCity("Paris");
        weatherEntity.setTemperature(25.5);
        weatherEntity.setCountry("FR");

        when(weatherService.getWeatherSnapshotByCityAndDate(anyString(), any(LocalDateTime.class))).thenReturn(weatherEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/weather")
                        .param("city", "Paris")
                        .param("date", "2023-06-26T10:00:00.000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Paris"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.temperature").value(25.5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.country").value("FR"))
                .andReturn();
    }
}
