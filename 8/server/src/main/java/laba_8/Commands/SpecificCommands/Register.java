package laba_8.Commands.SpecificCommands;

import laba_8.Commands.Command;
import laba_8.Server.Receiver;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.sql.SQLException;

public class Register extends Command implements Serializable {
    private static final long serialVersionUID = 32L;

    @Override
    public void execute(Object o, DatagramChannel channel, SocketAddress socketAddress) throws IOException, SQLException {
        Receiver commandReceiver = new Receiver(channel);
        commandReceiver.register((String) o, socketAddress);
    }
}
