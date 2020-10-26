package ru.job4j.client;

import ru.job4j.http.HttpMethod;
import ru.job4j.http.Request;

public class ClientApplication {
    public static void main(String[] args) {
        Client publisher = new Client();
        Client subscriber = new Client();

        publisher.startWithRequest(Request.builder()
                .method(HttpMethod.POST)
                .resource("/queue")
                .body(Request.RequestBody.builder()
                        .queue("weather")
                        .text("temperature + 18 C")
                        .build())
                .build());

        subscriber.startWithRequest(Request.builder()
                .method(HttpMethod.GET)
                .resource("/queue/weather")
                .build());

    }
}
