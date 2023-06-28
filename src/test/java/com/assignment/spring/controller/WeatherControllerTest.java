package com.assignment.spring.controller;

import com.assignment.spring.client.model.MainDto;
import com.assignment.spring.client.model.SysDto;
import com.assignment.spring.client.model.WeatherResponseDto;
import com.assignment.spring.entity.WeatherEntity;
import com.assignment.spring.model.WeatherRequestDTO;
import com.assignment.spring.service.WeatherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void createWeather_ReturnsWeatherSnapshotResponse() throws Exception {
        WeatherRequestDTO requestDTO = new WeatherRequestDTO();
        requestDTO.setCity("London");

        WeatherResponseDto responseDto = new WeatherResponseDto();
        responseDto.setName("London");
        responseDto.setMain(MainDto.builder().temp(20.5).build());
        responseDto.setSys(SysDto.builder().country("UK").build());

        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setId(1);
        weatherEntity.setCountry("UK");
        weatherEntity.setCity("London");
        weatherEntity.setTemperature(20.5);
        weatherEntity.setCreatedOn(LocalDateTime.now());

        when(weatherService.getWeather("London")).thenReturn(responseDto);
        when(weatherService.saveWeather(any(WeatherResponseDto.class))).thenReturn(weatherEntity);

        mockMvc.perform(MockMvcRequestBuilders.post("/weather")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("London"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.temperature").value(20.5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdOn").exists());
    }

    @Test
    public void createWeather_InvalidRequest_ReturnsBadRequest() throws Exception {
        WeatherRequestDTO requestDTO = new WeatherRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.post("/weather")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getWeatherSnapshot_ReturnsWeatherSnapshotResponse() throws Exception {
        String city = "London";

        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setId(1);
        weatherEntity.setCity("London");
        weatherEntity.setTemperature(20.5);
        weatherEntity.setCreatedOn(LocalDateTime.now());

        when(weatherService.getLastWeatherSnapshotByCity(city)).thenReturn(weatherEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/weather/{city}", city)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("London"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.temperature").value(20.5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdOn").exists());
    }

    @Test
    public void getWeatherSnapshotForPeriodForCity_ReturnsWeatherSnapshotResponseList() throws Exception {
        double lowTemp = 10.0;
        double highTemp = 30.0;

        WeatherEntity weatherEntity1 = new WeatherEntity();
        weatherEntity1.setId(1);
        weatherEntity1.setCity("London");
        weatherEntity1.setTemperature(20.0);
        weatherEntity1.setCreatedOn(LocalDateTime.now());

        WeatherEntity weatherEntity2 = new WeatherEntity();
        weatherEntity2.setId(2);
        weatherEntity2.setCity("Paris");
        weatherEntity2.setTemperature(25.0);
        weatherEntity2.setCreatedOn(LocalDateTime.now());

        List<WeatherEntity> weatherEntityList = new ArrayList<>();
        weatherEntityList.add(weatherEntity1);
        weatherEntityList.add(weatherEntity2);

        when(weatherService.getWeatherSnapshotForTemperatureInterval(lowTemp, highTemp)).thenReturn(weatherEntityList);

        mockMvc.perform(MockMvcRequestBuilders.get("/weather")
                        .param("lowTemp", String.valueOf(lowTemp))
                        .param("highTemp", String.valueOf(highTemp))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].city").value("London"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].temperature").value(20.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].createdOn").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].city").value("Paris"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].temperature").value(25.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].createdOn").exists());
    }

    @Test
    public void createWeather_ServiceThrowsException_ReturnsInternalServerError() throws Exception {
        WeatherRequestDTO requestDTO = new WeatherRequestDTO();
        requestDTO.setCity("London");

        when(weatherService.getWeather("London")).thenThrow(new RuntimeException("Failed to get weather data."));

        mockMvc.perform(MockMvcRequestBuilders.post("/weather")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void getWeatherSnapshot_ServiceThrowsException_ReturnsInternalServerError() throws Exception {
        String city = "London";

        when(weatherService.getLastWeatherSnapshotByCity(city)).thenThrow(new RuntimeException("Failed to retrieve weather data."));

        mockMvc.perform(MockMvcRequestBuilders.get("/weather/{city}", city)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void getWeatherSnapshotForPeriodForCity_ServiceThrowsException_ReturnsInternalServerError() throws Exception {
        double lowTemp = 10.0;
        double highTemp = 30.0;

        when(weatherService.getWeatherSnapshotForTemperatureInterval(lowTemp, highTemp))
                .thenThrow(new RuntimeException("Failed to retrieve weather data."));

        mockMvc.perform(MockMvcRequestBuilders.get("/weather")
                        .param("lowTemp", String.valueOf(lowTemp))
                        .param("highTemp", String.valueOf(highTemp))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void createWeather_InvalidRequest_ThrowsConstraintViolationException() throws Exception {
        WeatherRequestDTO requestDTO = new WeatherRequestDTO();

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

}
