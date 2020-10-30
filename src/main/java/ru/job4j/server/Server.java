package ru.job4j.server;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final WorkRegime workRegime;
    private final int port = 13500;
    private final String host = "localhost";
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public Server(WorkRegime workRegime) {
        this.workRegime = workRegime;
    }

    public void start() {
        System.out.println(String.format("Server started at host: %s, at port: %s", host, port));
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            Socket socket = null;

            while (true) {
                socket = serverSocket.accept();
                pool.submit(new ClientThread(socket, workRegime));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(serverSocket);
        }
    }

    public static void main(String[] args) {
        Server server = new Server(WorkRegime.TOPIC);
        server.start();
    }
}