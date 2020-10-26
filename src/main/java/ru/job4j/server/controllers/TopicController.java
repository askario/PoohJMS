package ru.job4j.server.controllers;

import ru.job4j.server.Storage;
import ru.job4j.http.HttpMethod;
import ru.job4j.http.Request;
import ru.job4j.http.Response;

public class TopicController extends AbstractController {
    private final String requestMapping = "/topic";
    private final Storage<String> queue;

    public TopicController(Storage<String> queue) {
        this.queue = queue;
        this.initMapping();
    }

    @Override
    protected void initMapping() {
        //POST /queue
        handlers.add(RequestHandler.builder()
                .requestMapping("")
                .method(HttpMethod.POST)
                .function(this::add)
                .build());
        //GET /topic/weather
        handlers.add(RequestHandler.builder()
                .requestMapping("/topic")
                .method(HttpMethod.GET)
                .function(this::getMessage)
                .build());
    }

    private Response add(Request request) {
        queue.offer(request.getBody().getText());
        return Response.builder()
                .text("Succefully added new message")
                .build();
    }

    private Response getMessage(Request request) {
        return Response.builder()
                .text(queue.poll())
                .build();
    }
}