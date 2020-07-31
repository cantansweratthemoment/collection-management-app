package laba_8.Server;

import laba_8.Database.DatabaseCommunicator;
import laba_8.Utils.CollectionManager;

import java.io.*;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.sql.SQLException;

import static java.nio.ByteBuffer.wrap;

public class Communicator {
    private static ObjectInputStream in;
    public static SocketAddress socketAddress;

    public void run(String port_s) throws InterruptedException {
        try {
            checkForSaveCommand();
            int port = Integer.parseInt(port_s);
            try {
                CollectionManager.initializeCollection();
                DatabaseCommunicator.getOrganizations().loadCollection(CollectionManager.getCollection());
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
                            Client client = new Client(new ObjectInputStream(new ByteArrayInputStream(ib)), socketAddress, new CommandDecoder(datagramChannel, socketAddress));
                            client.start();
                            Thread.currentThread().sleep(50);
                        } catch (EOFException | SocketException e) {
                            System.out.println("Проблемы с клиентом.");
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
            } catch (SQLException e) {
                System.out.println("Проблемы с базой данных.");
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
                            /*OrganizationsBase organizationsBase= DataBase.getHumansDataBase();
                            humansDataBase.loadCollection(humans.getArray());
                            System.out.println("Коллекция сохранена в базу данных.");*/
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