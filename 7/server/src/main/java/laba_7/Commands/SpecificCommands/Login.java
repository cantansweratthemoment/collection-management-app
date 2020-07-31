package laba_7.Commands.SpecificCommands;

import laba_7.Commands.Command;
import laba_7.Server.Receiver;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.sql.SQLException;

public class Login extends Command implements Serializable {
    private static final long serialVersionUID = 32L;

    @Override
    public void execute(Object o, DatagramChannel channel, SocketAddress socketAddress) throws IOException, SQLException {
        Receiver commandReceiver = new Receiver(channel);
        commandReceiver.login((String) o,socketAddress);
    }
}
