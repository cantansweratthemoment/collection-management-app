package laba_7.Server;

import laba_7.Commands.*;
import laba_7.Commands.SerializedCommands.*;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadPoolExecutor;

public class CommandDecoder {
    private final DatagramChannel channel;
    private final SocketAddress socketAddress;
    private final ForkJoinPool forkJoinPool = new ForkJoinPool(2);
    private final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);

    public CommandDecoder(DatagramChannel channel, SocketAddress socketAddress) {
        this.channel = channel;
        this.socketAddress = socketAddress;
    }

    public void decode(Object o) throws IOException, SQLException {
        forkJoinPool.submit(() -> {
            if (o instanceof SerializedSimplyCommand) {
                SerializedSimplyCommand simplyCommand = (SerializedSimplyCommand) o;
                Command command = simplyCommand.getCommand();
                String arg = "";
                try {
                    command.execute(arg, channel, socketAddress);
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }

            if (o instanceof SerializedArgumentCommand) {
                SerializedArgumentCommand argumentCommand = (SerializedArgumentCommand) o;
                Command command = argumentCommand.getCommand();
                String arg = argumentCommand.getArg();
                try {
                    command.execute(arg, channel, socketAddress);
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }

            if (o instanceof SerializedObjectCommand) {
                SerializedObjectCommand objectCommand = (SerializedObjectCommand) o;
                Command command = objectCommand.getCommand();
                Object arg = objectCommand.getObject();
                try {
                    command.execute(arg, channel, socketAddress);
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }

            if (o instanceof SerializedCombinedCommand) {
                SerializedCombinedCommand combinedCommand = (SerializedCombinedCommand) o;
                Command command = combinedCommand.getCommand();
                try {
                    command.execute(combinedCommand, channel, socketAddress);
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}