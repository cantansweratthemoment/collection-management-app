package laba_7.Commands.SpecificCommands;

import laba_7.Commands.Command;
import laba_7.Commands.SerializedCommands.SerializedCombinedCommand;
import laba_7.Server.Receiver;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

public class Update extends Command implements Serializable {
    private static final long serialVersionUID = 32L;

    @Override
    public void execute(Object o, DatagramChannel channel, SocketAddress socketAddress) throws IOException {
        SerializedCombinedCommand combinedCommand = (SerializedCombinedCommand) o;
        Object obj = combinedCommand.getObject();
        String arg = combinedCommand.getArg();
        Receiver commandReceiver = new Receiver(channel);
        commandReceiver.update(arg, obj, socketAddress);
    }
}