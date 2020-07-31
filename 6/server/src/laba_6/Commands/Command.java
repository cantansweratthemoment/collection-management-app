package laba_6.Commands;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

/**
 * Абстрактная команда: класс-отец всех команд.
 */
public abstract class Command {
    /**
     * Метод - исполнение команды.
     *
     * @param o,channel - аругменты команды.
     */
    public abstract void execute(Object o, DatagramChannel channel, SocketAddress socketAddress) throws IOException;
}