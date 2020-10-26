package ru.job4j.server.controllers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import ru.job4j.http.HttpMethod;
import ru.job4j.http.Request;
import ru.job4j.http.Response;

import java.util.function.Function;

@AllArgsConstructor
@Builder
@Getter
public class RequestHandler {
    private final String requestMapping;
    private final HttpMethod method;
    private final Function<Request, Response> function;


    public Response call(Request request) {
        return function.apply(request);
    }
}
