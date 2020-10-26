package ru.job4j.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import ru.job4j.http.Request;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

@AllArgsConstructor
public class ClientThread extends Thread {
    private final Socket socket;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Dispatcher dispatcher;

    @Override
    public void run() {
        try (InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream()) {

            byte[] buf = new byte[32 * 1024];
            int readBytes = in.read(buf);
            String line = new String(buf, 0, readBytes);
            Request request = mapper.readValue(line, Request.class);

            out.write(dispatcher.dispatch(request).toString().getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}