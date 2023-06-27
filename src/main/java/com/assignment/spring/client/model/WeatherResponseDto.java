
package com.assignment.spring.client.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "coord",
    "weather",
    "base",
    "main",
    "visibility",
    "wind",
    "clouds",
    "dt",
    "sys",
    "id",
    "name",
    "cod"
})
public class WeatherResponseDto {

    @JsonProperty("coord")
    private CoordDto coordDto;
    @JsonProperty("weather")
    private List<WeatherDto> weatherDtos = null;
    @JsonProperty("base")
    private String base;
    @JsonProperty("main")
    private MainDto mainDto;
    @JsonProperty("visibility")
    private Integer visibility;
    @JsonProperty("wind")
    private WindDto windDto;
    @JsonProperty("clouds")
    private CloudsDto cloudsDto;
    @JsonProperty("dt")
    private Integer dt;
    @JsonProperty("sys")
    private SysDto sysDto;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("cod")
    private Integer cod;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("coord")
    public CoordDto getCoord() {
        return coordDto;
    }

    @JsonProperty("coord")
    public void setCoord(CoordDto coordDto) {
        this.coordDto = coordDto;
    }

    @JsonProperty("weather")
    public List<WeatherDto> getWeatherDtos() {
        return weatherDtos;
    }

    @JsonProperty("weather")
    public void setWeatherDtos(List<WeatherDto> weatherDtos) {
        this.weatherDtos = weatherDtos;
    }

    @JsonProperty("base")
    public String getBase() {
        return base;
    }

    @JsonProperty("base")
    public void setBase(String base) {
        this.base = base;
    }

    @JsonProperty("main")
    public MainDto getMain() {
        return mainDto;
    }

    @JsonProperty("main")
    public void setMain(MainDto mainDto) {
        this.mainDto = mainDto;
    }

    @JsonProperty("visibility")
    public Integer getVisibility() {
        return visibility;
    }

    @JsonProperty("visibility")
    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    @JsonProperty("wind")
    public WindDto getWind() {
        return windDto;
    }

    @JsonProperty("wind")
    public void setWind(WindDto windDto) {
        this.windDto = windDto;
    }

    @JsonProperty("clouds")
    public CloudsDto getClouds() {
        return cloudsDto;
    }

    @JsonProperty("clouds")
    public void setClouds(CloudsDto cloudsDto) {
        this.cloudsDto = cloudsDto;
    }

    @JsonProperty("dt")
    public Integer getDt() {
        return dt;
    }

    @JsonProperty("dt")
    public void setDt(Integer dt) {
        this.dt = dt;
    }

    @JsonProperty("sys")
    public SysDto getSys() {
        return sysDto;
    }

    @JsonProperty("sys")
    public void setSys(SysDto sysDto) {
        this.sysDto = sysDto;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("cod")
    public Integer getCod() {
        return cod;
    }

    @JsonProperty("cod")
    public void setCod(Integer cod) {
        this.cod = cod;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
