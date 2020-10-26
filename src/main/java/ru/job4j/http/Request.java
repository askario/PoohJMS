package ru.job4j.http;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Request {
    private final HttpMethod method;
    private final String resource;
    private final RequestBody body;

    public Request(HttpMethod method, String resource, RequestBody body) {
        this.method = method;
        this.resource = resource;
        this.body = body;
    }

    public Request() {
        this.method = null;
        this.resource = null;
        this.body = null;
    }

    @Builder
    @Getter
    @ToString
    public static class RequestBody {
        private final String queue;
        private final String topic;
        private final String text;

        public RequestBody(String queue, String topic, String text) {
            this.queue = queue;
            this.topic = topic;
            this.text = text;
        }

        public RequestBody() {
            this.queue = null;
            this.topic = null;
            this.text = null;
        }
    }
}
