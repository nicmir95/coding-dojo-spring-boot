package com.assignment.spring.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Data
@Table(name = "weather")
@AllArgsConstructor
public class WeatherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Timestamp createdOn;

    @NotNull
    private String city;

    @NotNull
    private String country;

    @NotNull
    private Double temperature;

    public WeatherEntity() {
        createdOn = Timestamp.from(Instant.now());
    }

}
