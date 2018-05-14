package com.mongodb.mongoiot.repositories;

import com.mongodb.mongoiot.models.Sensor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository {

    Sensor save(Sensor sensor);

    List<Sensor> findAll();
}
