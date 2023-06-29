
package com.assignment.spring.client.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class WeatherResponseDto implements Serializable {

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

}
