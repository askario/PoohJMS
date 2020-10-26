package ru.job4j.server;

public class ServerApplication {
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
        System.out.println("Server application started");
    }
}
