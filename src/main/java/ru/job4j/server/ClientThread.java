package ru.job4j.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.val;
import ru.job4j.http.HttpMethod;
import ru.job4j.http.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@AllArgsConstructor
public class ClientThread extends Thread {
    private final Socket socket;
    private final WorkRegime workRegime;

    @Override
    public void run() {
        val mapper = new ObjectMapper();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter writer = new PrintWriter(socket.getOutputStream())) {

            String line = reader.readLine();
            Request request = mapper.readValue(line, Request.class);

            ServerQueue serverQueue = workRegime.getServerQueue();

            if (request.getMethod().equals(HttpMethod.POST)) {
                serverQueue.put(request.getBody());
                writer.println("Message added");
            } else if (request.getMethod().equals(HttpMethod.GET))
                writer.println(serverQueue.get(request.getResource().split("/")));

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}