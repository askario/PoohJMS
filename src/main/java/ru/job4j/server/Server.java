package ru.job4j.server;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final int port;
    private final String host;
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );
    private final Storage<String> queue = new Storage<>();
    private final Dispatcher dispatcher = new Dispatcher(queue);

    public Server() {
        this.port = 13500;
        this.host = "localhost";
    }

    public Server(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            Socket socket = null;

            while (true) {
                socket = serverSocket.accept();
                pool.submit(new ClientThread(socket, dispatcher));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(serverSocket);
        }
    }
}