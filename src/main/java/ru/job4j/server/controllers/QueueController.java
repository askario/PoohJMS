package ru.job4j.server.controllers;

import lombok.Getter;
import ru.job4j.server.Storage;
import ru.job4j.http.HttpMethod;
import ru.job4j.http.Request;
import ru.job4j.http.Response;

@Getter
public class QueueController extends AbstractController {
    private final String requestMapping = "/queue";
    private final Storage<String> queue;

    public QueueController(Storage<String> queue) {
        this.queue = queue;
        this.initMapping();
    }

    @Override
    protected void initMapping() {
        //POST /queue
        handlers.add(RequestHandler.builder()
                .requestMapping(addControllerMapping(""))
                .method(HttpMethod.POST)
                .function(this::add)
                .build());
        //GET /queue/weather
        handlers.add(RequestHandler.builder()
                .requestMapping(addControllerMapping("/weather"))
                .method(HttpMethod.GET)
                .function(this::getMessage)
                .build());
    }

    private String addControllerMapping(String suffix) {
        return getRequestMapping() + suffix;
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