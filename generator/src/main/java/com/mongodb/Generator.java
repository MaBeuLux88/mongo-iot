package com.mongodb;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static jdk.incubator.http.HttpResponse.BodyHandler.asString;

public class Generator {

    private static int dataCenterId;
    private static String connectionString;
    private Random random = new Random();
    private HttpClient httpClient = HttpClient.newHttpClient();

    public static void main(String[] args) {
        connectionString = args[0];
        dataCenterId = Integer.parseInt(args[1]);
        new Generator().generateData();
    }

    private void generateData() {
        List<Integer> range = IntStream.range(1, 101).boxed().collect(Collectors.toList());
        Collections.shuffle(range);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                generateData(range);
            }
        }, 0, 1000);

    }

    private void generateData(List<Integer> range) {
        long start = System.currentTimeMillis();
        int maxTemp = random.nextInt(150) + 101;
        range.stream().parallel().map(id -> generateNextRandomSensor(id, maxTemp)).forEach(this::sendPost);
        long end = System.currentTimeMillis();
        System.out.println("Processing time : " + (end - start));
    }

    private String generateNextRandomSensor(int serverId, int maxTemp) {
        int randomTemp = random.nextInt(maxTemp) + 1;
        return "{\n" + "  \"dataCenterId\":" + dataCenterId + ",\n" + "  \"serverId\":" + serverId + ",\n" + "  \"sensorType\":\"hard drive\",\n" + "  \"temperature\" : " + randomTemp + ",\n" + "  \"spaceAvailableGB\": 256,\n" + "  \"spaceUsedGB\":200\n" + "}";
    }

    private void sendPost(String sensor) {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                                                 .uri(new URI(connectionString))
                                                 .headers("Content-Type", "application/json;charset=UTF-8")
                                                 .POST(HttpRequest.BodyPublisher.fromString(sensor))
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
