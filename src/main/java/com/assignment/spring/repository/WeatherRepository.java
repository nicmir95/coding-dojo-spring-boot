package com.assignment.spring.repository;

import com.assignment.spring.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherEntity, Integer> {

    WeatherEntity findAllByCityAndCreatedOnContaining(String city, Timestamp date);
}
