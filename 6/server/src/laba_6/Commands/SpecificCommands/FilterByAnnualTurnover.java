package laba_6.Commands.SpecificCommands;

import laba_6.Commands.Command;
import laba_6.Server.Receiver;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

/**
 * Команда filter_by_annual_turnover.
 */
public class FilterByAnnualTurnover extends Command implements Serializable {
    private static final long serialVersionUID = 32L;

    @Override
    public void execute(Object o, DatagramChannel channel, SocketAddress socketAddress) throws IOException {
        Receiver commandReceiver = new Receiver(channel);
        commandReceiver.filter_by_annual_turnover((String) o,socketAddress);
    }
}
