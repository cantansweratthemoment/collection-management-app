package laba_6.Commands.SpecificCommands;

import laba_6.Commands.Command;
import laba_6.Server.Receiver;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

/**
 * Команда print_field_descending_annual_turnover.
 */
public class PrintFieldDescendingAnnualTurnover extends Command implements Serializable {
    private static final long serialVersionUID = 32L;

    @Override
    public void execute(Object o, DatagramChannel channel, SocketAddress socketAddress) throws IOException {
        Receiver commandReceiver = new Receiver(channel);
        commandReceiver.print_field_descending_annual_turnover(socketAddress);
    }
}
