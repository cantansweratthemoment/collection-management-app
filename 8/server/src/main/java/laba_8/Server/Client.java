package laba_8.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketAddress;
import java.sql.SQLException;

public class Client extends Thread {
    private ObjectInputStream in;
    private final SocketAddress socketAddress;
    private CommandDecoder decoder;

    public Client(ObjectInputStream objectInputStream, SocketAddress socketAddress, CommandDecoder commandDecoder) {
        this.in = objectInputStream;
        this.socketAddress = socketAddress;
        this.decoder = commandDecoder;
    }

    @Override
    public void run() {
        try {
            Object object = in.readObject();
            decoder.decode(object);
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
