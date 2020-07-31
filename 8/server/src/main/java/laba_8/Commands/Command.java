package laba_8.Commands;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.sql.SQLException;

public abstract class Command {
    public abstract void execute(Object o, DatagramChannel channel, SocketAddress socketAddress) throws IOException, SQLException;
}