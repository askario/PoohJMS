package ru.job4j.http;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class Response {
    private final String status;
    private final String text;
}
