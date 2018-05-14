package com.riviera;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;

import static com.mongodb.client.model.Filters.and;
import static java.util.logging.Logger.getLogger;
import static jdk.incubator.http.HttpResponse.BodyHandler.asString;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class AlertManager {

    private static String slackHookURL;
    private static String mongodbURI;
    private static int temperatureThreshold;
    private MongoCollection<Report> collReport;
    private HttpClient httpClient = HttpClient.newHttpClient();

    public static void main(String[] args) {
        mongodbURI = args[0];
        slackHookURL = args[1];
        temperatureThreshold = Integer.parseInt(args[2]);
        new AlertManager().process();
    }

    private void initMongoDB() {
        getLogger("org.mongodb.driver").setLevel(Level.SEVERE);

        CodecRegistry codecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(), fromProviders(
                PojoCodecProvider.builder().register(Report.class).build()));

        MongoClientOptions.Builder options = new MongoClientOptions.Builder().codecRegistry(codecRegistry);
        MongoClientURI uri = new MongoClientURI(mongodbURI, options);
        MongoClient client = new MongoClient(uri);

        MongoDatabase db = client.getDatabase("test");
        collReport = db.getCollection("report", Report.class);
    }

    private void process() {
        initMongoDB();

        Bson filterInsert = Filters.in("operationType", "insert");
        Bson filterMinimalTemperature = Filters.gt("fullDocument.averageTemperature", temperatureThreshold);
        List<Bson> pipeline = Collections.singletonList(Aggregates.match(and(filterInsert, filterMinimalTemperature)));
        collReport.watch(pipeline).forEach((Consumer<? super ChangeStreamDocument<Report>>) this::sendAlertToSlack);
    }

    private void sendAlertToSlack(ChangeStreamDocument<Report> report) {
        sendToSlack(report.getFullDocument());
        System.out.println(report.getFullDocument());
    }

    private void sendToSlack(Report report) {
        try {
            String rawData = "{\"text\":\"" + report + "\"}";
            HttpRequest httpRequest = HttpRequest.newBuilder()
                                                 .uri(new URI(slackHookURL))
                                                 .headers("Content-Type", "application/json;charset=UTF-8")
                                                 .POST(HttpRequest.BodyPublisher.fromString(rawData))
                                                 .build();
            HttpResponse<String> send = httpClient.send(httpRequest, asString());
            int code = send.statusCode();
            if (code != 200)
                System.err.println("ERROR: Received error code : " + code);
        } catch (URISyntaxException | InterruptedException | IOException e) {
            System.err.println("Error while trying to send the POST request!");
            e.printStackTrace();
        }
    }

}
