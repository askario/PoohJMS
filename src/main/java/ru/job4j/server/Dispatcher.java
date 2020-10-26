package ru.job4j.server;

import lombok.val;
import ru.job4j.http.Request;
import ru.job4j.http.Response;
import ru.job4j.server.controllers.AbstractController;
import ru.job4j.server.controllers.QueueController;
import ru.job4j.server.controllers.TopicController;

import java.util.ArrayList;
import java.util.List;

public class Dispatcher {
    private final Storage<String> queue;

    private final List<AbstractController> controllers = new ArrayList<>();

    public Dispatcher(Storage<String> queue) {
        this.queue = queue;
    }

    private void initControllers() {
        controllers.clear();
        controllers.add(new QueueController(queue));
        controllers.add(new TopicController(queue.copy()));
    }

    public Response dispatch(Request request) {
        initControllers();
        val handlerOpt = controllers.stream()
                .map(controller -> controller.getHandlers())
                .flatMap(requestHandlers -> requestHandlers.stream())
                .filter(requestHandler -> requestHandler.getMethod().equals(request.getMethod())
                        && requestHandler.getRequestMapping().equals(request.getResource()))
                .findFirst();

        if (!handlerOpt.isPresent()) {
            throw new RuntimeException("No controller for given url");
        }

        return handlerOpt.get().call(request);
    }
}