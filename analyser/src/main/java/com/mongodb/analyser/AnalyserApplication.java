package com.mongodb.analyser;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Level;

import static com.mongodb.client.model.Filters.and;
import static java.util.logging.Logger.getLogger;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class AnalyserApplication {

    private final static List<Sensor> sensors = new ArrayList<>();
    private static String mongodbURI;
    private static int myDataCenterId;
    private MongoCollection<Sensor> collSensor;
    private MongoCollection<Report> collReport;

    public static void main(String[] args) {
        mongodbURI = args[0];
        myDataCenterId = Integer.parseInt(args[1]);
        new AnalyserApplication().runtime();
    }

    private void runtime() {
        initMongoDB();

        Thread t1 = new Thread(this::processingChangeList);
        t1.start();

        Timer timer = new Timer();
        TimerTask reportTask = new TimerTask() {
            @Override
            public void run() {
                analyseAndWriteToMongoDB();
            }
        };
        timer.schedule(reportTask, 0, 10000);
    }

    private void processingChangeList() {
        Bson filterInsert = Filters.in("operationType", "insert");
        Bson filterDataCenterId = Filters.eq("fullDocument.dataCenterId", myDataCenterId);
        List<Bson> pipeline = Collections.singletonList(Aggregates.match(and(filterInsert, filterDataCenterId)));
        collSensor.watch(pipeline).forEach((Consumer<ChangeStreamDocument<Sensor>>) this::pushToSensorsList);
    }

    private void pushToSensorsList(ChangeStreamDocument<Sensor> sensorChangeStreamDocument) {
        Sensor sensor = sensorChangeStreamDocument.getFullDocument();
        sensors.add(sensor);
    }

    private void initMongoDB() {
        getLogger("org.mongodb.driver").setLevel(Level.SEVERE);

        CodecRegistry codecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(), fromProviders(
                PojoCodecProvider.builder().register(Sensor.class).register(Report.class).build()));

        MongoClientOptions.Builder options = new MongoClientOptions.Builder().codecRegistry(codecRegistry);
        MongoClientURI uri = new MongoClientURI(mongodbURI, options);
        MongoClient client = new MongoClient(uri);

        MongoDatabase db = client.getDatabase("test");
        collSensor = db.getCollection("sensor", Sensor.class);
        collReport = db.getCollection("report", Report.class);
    }

    private void analyseAndWriteToMongoDB() {
        int nbSensors = sensors.size();
        if (nbSensors == 0)
            return;
        System.out.println("Analysing " + nbSensors + " sensors.");
        double avgTemperature = sensors.stream()
                                       .map(Sensor::getTemperature)
                                       .mapToDouble(Float::doubleValue)
                                       .average()
                                       .orElse(0.0);
        sensors.clear();
        Report myReport = new Report(myDataCenterId, avgTemperature);
        System.out.println("Report : " + myReport);
        collReport.insertOne(myReport);
    }

}
