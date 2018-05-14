package com.mongodb.mongoiot.repositories;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.mongoiot.models.Sensor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MongoDBSensorRepository implements SensorRepository {

    private final MongoCollection<Sensor> sensorCollection;

    @Autowired
    public MongoDBSensorRepository(MongoClient mongoClient) {
        MongoDatabase db = mongoClient.getDatabase("test");
        sensorCollection = db.getCollection("sensor", Sensor.class);
    }

    @Override
    public Sensor save(Sensor sensor) {
        sensor.setId(new ObjectId());
        sensorCollection.insertOne(sensor);
        return sensor;
    }

    @Override
    public List<Sensor> findAll() {
        return sensorCollection.find().into(new ArrayList<>());
    }
}
