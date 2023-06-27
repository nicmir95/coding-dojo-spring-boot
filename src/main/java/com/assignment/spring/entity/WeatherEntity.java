package com.assignment.spring.entity;

import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "weather")
@AllArgsConstructor
public class WeatherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime createdOn;

    @NotNull
    private String city;

    @NotNull
    private String country;

    @NotNull
    private Double temperature;

    public WeatherEntity() {
        createdOn = LocalDateTime.now();
    }

}
