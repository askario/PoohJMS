package ru.job4j.server.controllers;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractController {
    protected final String requestMapping = "";
    protected List<RequestHandler> handlers = new ArrayList<>();

    protected abstract void initMapping();

    public String getRequestMapping() {
        return requestMapping;
    }

    public List<RequestHandler> getHandlers() {
        return handlers;
    }
}
