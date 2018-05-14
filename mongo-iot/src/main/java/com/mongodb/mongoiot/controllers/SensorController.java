package com.mongodb.mongoiot.controllers;

import com.mongodb.mongoiot.models.Sensor;
import com.mongodb.mongoiot.repositories.SensorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SensorController {

    private final SensorRepository sensorRepository;

    public SensorController(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @PostMapping("sensor")
    public ResponseEntity<Sensor> postSensor(@RequestBody Sensor sensor) {
        Sensor saved = sensorRepository.save(sensor);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("sensors")
    public ResponseEntity<List<Sensor>> getSensors() {
        List<Sensor> sensors = sensorRepository.findAll();
        return ResponseEntity.ok(sensors);
    }
}
