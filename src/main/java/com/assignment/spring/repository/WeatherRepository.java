package com.assignment.spring.repository;

import com.assignment.spring.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherEntity, Integer> {

    WeatherEntity findFirstByCityOrderByCreatedOnDesc(String city);

    WeatherEntity findAllByCityAndCreatedOnContaining(String city, LocalDateTime date);
}
