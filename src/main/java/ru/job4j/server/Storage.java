package ru.job4j.server;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Storage<T> {
    private final Queue<T> queue = new ConcurrentLinkedQueue<>();

    public void offer(T t) {
        queue.offer(t);
    }

    public T poll() {
        return queue.poll();
    }

    public Storage<T> copy() {
        Storage<T> copy = new Storage<>();
        queue.stream().forEach(copy::offer);
        return copy;
    }
}
