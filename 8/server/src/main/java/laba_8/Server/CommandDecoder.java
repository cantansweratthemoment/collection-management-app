package laba_8.Server;

import laba_8.Commands.*;
import laba_8.Commands.SerializedCommands.*;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.sql.SQLException;

public class CommandDecoder {
    private final DatagramChannel channel;
    private final SocketAddress socketAddress;

    public CommandDecoder(DatagramChannel channel, SocketAddress socketAddress) {
        this.channel = channel;
        this.socketAddress = socketAddress;
    }

    public void decode(Object o) throws IOException, SQLException {
        if (o instanceof SerializedSimplyCommand) {
            SerializedSimplyCommand simplyCommand = (SerializedSimplyCommand) o;
            Command command = simplyCommand.getCommand();
            String arg = "";
            command.execute(arg, channel, socketAddress);
        }

        if (o instanceof SerializedArgumentCommand) {
            SerializedArgumentCommand argumentCommand = (SerializedArgumentCommand) o;
            Command command = argumentCommand.getCommand();
            String arg = argumentCommand.getArg();
            command.execute(arg, channel, socketAddress);
        }

        if (o instanceof SerializedObjectCommand) {
            SerializedObjectCommand objectCommand = (SerializedObjectCommand) o;
            Command command = objectCommand.getCommand();
            Object arg = objectCommand.getObject();
            command.execute(arg, channel, socketAddress);
        }

        if (o instanceof SerializedCombinedCommand) {
            SerializedCombinedCommand combinedCommand = (SerializedCombinedCommand) o;
            Command command = combinedCommand.getCommand();
            command.execute(combinedCommand, channel, socketAddress);
        }
    }
}