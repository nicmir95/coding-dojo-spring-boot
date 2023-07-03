package com.assignment.spring.entity;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Data
@Table(name = "weather")
@AllArgsConstructor
public class WeatherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private LocalDateTime createdOn;

    @NotNull
    private String city;

    @NotNull
    private String country;

    @NotNull
    private Double temperature;

    public WeatherEntity() {
        createdOn = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }
}
