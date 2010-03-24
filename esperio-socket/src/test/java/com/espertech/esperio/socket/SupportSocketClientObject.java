package com.espertech.esperio.socket;

import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SupportSocketClientObject {

    private final Socket requestSocket;
    private final ObjectOutputStream out;

    public SupportSocketClientObject(int port) throws UnknownHostException, IOException {
        requestSocket = new Socket("localhost", port);
        out = new ObjectOutputStream(requestSocket.getOutputStream());
    }

    public void send(Object object) throws IOException
    {
        out.writeObject(object);
        out.flush();
    }

    public void close() throws IOException {
        out.close();
        requestSocket.close();
    }
}
