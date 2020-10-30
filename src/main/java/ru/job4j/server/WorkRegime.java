package ru.job4j.server;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WorkRegime {
    QUEUE(new QueueQ()),
    TOPIC(new TopicQ());

    private ServerQueue serverQueue;
}
