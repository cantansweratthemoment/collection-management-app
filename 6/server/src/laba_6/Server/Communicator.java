package laba_6.Server;

import laba_6.Utils.CollectionUtils.CollectionManager;
import laba_6.Utils.FileUtils.ParserJson;

import java.io.*;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;


/**
 * Серверный класс, принимающий соединения.
 */
public class Communicator {
    private static ObjectInputStream in;
    public static SocketAddress socketAddress;

    public void run(String port_s) {
        try {
            checkForSaveCommand();
            int port = Integer.parseInt(port_s);
            try {
                CollectionManager.initializeCollection();
                ParserJson.fromJsonToCollection();
                SocketAddress addr = new InetSocketAddress(port);
                DatagramChannel datagramChannel = DatagramChannel.open();
                datagramChannel.configureBlocking(false);
                datagramChannel.socket().bind(addr);
                System.out.println("Сервер запущен!");
                byte[] ib = new byte[4096];
                while (true) {
                    socketAddress = datagramChannel.receive(ByteBuffer.wrap(ib));
                    if (socketAddress != null) {
                        try {
                            in = new ObjectInputStream(new ByteArrayInputStream(ib));
                            CommandDecoder commandDecoder = new CommandDecoder(datagramChannel, socketAddress);
                            Object object = in.readObject();
                            commandDecoder.decode(object);
                        } catch (EOFException | SocketException e) {
                            System.out.println("Проблемы с клиентом.");
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } finally {
                            if (in != null) {
                                in.close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Что-то пошло не так.");
                e.printStackTrace();
            }
        } catch (NumberFormatException | IOException ex) {
            System.out.println("Ошибка! Некорректный формат порта.");
            System.exit(0);
        }
    }
    public void checkForSaveCommand() throws IOException {
        Thread backgroundReaderThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
                    while (!Thread.interrupted()) {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        if (line.equalsIgnoreCase("save")) {
                            ParserJson.collectionToJson();
                            System.out.println("Коллекция сохранена в файл.");
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        backgroundReaderThread.setDaemon(true);
        backgroundReaderThread.start();
    }
}