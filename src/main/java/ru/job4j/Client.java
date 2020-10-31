package ru.job4j;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.apache.commons.io.IOUtils;
import ru.job4j.http.HttpMethod;
import ru.job4j.http.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private final int port = 13500;
    private final String host = "localhost";

    public void start(Request request) {
        Socket socket = null;
        val objectMapper = new ObjectMapper();

        try {
            socket = new Socket(host, port);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                String req = objectMapper.writeValueAsString(request);
                out.println(req);
                out.flush();

                String res = reader.readLine();
                System.out.println("Server response > " + res);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(socket);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable producer = () -> {
            Request request = Request.builder()
                    .method(HttpMethod.POST)
                    .resource("/topic")
                    .body(Request.RequestBody.builder()
                            .topic("weather")
                            .text("temparature + 18C")
                            .build())
                    .build();
            new Client().start(request);
        };

        Runnable consumer = () -> {
            Request request = Request.builder()
                    .method(HttpMethod.GET)
                    .resource("/topic/weather")
                    .build();
            new Client().start(request);
        };

        Thread clientProducer = new Thread(producer);
        clientProducer.start();
        clientProducer.join();

        Thread clientConsumer1 = new Thread(consumer);
        Thread clientConsumer2 = new Thread(consumer);
        clientConsumer1.start();
        clientConsumer2.start();
    }
}
