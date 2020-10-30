package ru.job4j.server;

import ru.job4j.http.Request;

public interface ServerQueue {
    void put(Request.RequestBody requestBody);

    String get(String... resource);
}
