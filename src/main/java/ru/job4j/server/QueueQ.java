package ru.job4j.server;

import ru.job4j.http.Request;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class QueueQ implements ServerQueue {
    private Map<String, String> queue = new ConcurrentHashMap<>();

    @Override
    public void put(Request.RequestBody requestBody) {
        queue.put(requestBody.getQueue(), requestBody.getText());
    }

    @Override
    public String get(String... resource) {
        String key = resource[resource.length - 1];
        String value = queue.get(key);
        String res = "Empty";

        if (value != null) {
            res = value;
            queue.remove(key);
        }

        return res;
    }
}
