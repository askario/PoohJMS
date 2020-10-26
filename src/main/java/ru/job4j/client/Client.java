package ru.job4j.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import ru.job4j.http.Request;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    private final int port = 13500;
    private final String host = "localhost";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void startWithRequest(Request request) {
        Socket socket = null;
        try {
            socket = new Socket(host, port);

            try (InputStream in = socket.getInputStream();
                 OutputStream out = socket.getOutputStream()) {

                String line = objectMapper.writeValueAsString(request);

                out.write(line.getBytes());
                out.flush();

                byte[] data = new byte[32 * 1024];
                int readBytes = in.read(data);

                System.out.printf("Server > %s", new String(data, 0, readBytes));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(socket);
        }
    }
}
