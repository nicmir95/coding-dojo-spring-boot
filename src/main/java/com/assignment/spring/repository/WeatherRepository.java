package com.assignment.spring.repository;

import com.assignment.spring.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherEntity, Integer> {

    WeatherEntity findByCityAndCreatedOnContaining(String city, Timestamp date);
}
