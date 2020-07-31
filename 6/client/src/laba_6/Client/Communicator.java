package laba_6.Client;

import java.io.IOException;
import java.net.*;
import java.nio.channels.DatagramChannel;

public class Communicator {
    private DatagramSocket datagramSocket;
    private DatagramPacket dp;
    private DatagramPacket ip;
    private String host;
    private int port;

    public Communicator(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void startCommunication() throws IOException {
            try{
                datagramSocket = new DatagramSocket();
                datagramSocket.connect(InetAddress.getByName(host), port);
                System.out.println("Подключаемся...");
            }catch (SocketException e){
                System.out.println("Не удалось подключиться.");
                e.printStackTrace();
            }catch (UnknownHostException e){
                System.out.println("Что-то не так с хостом.");
                System.exit(0);
            }
        }

    public void endCommunication(){
        if (datagramSocket!=null){
            datagramSocket.close();
        }
    }

    public DatagramSocket getDatagramSocket() {
        return datagramSocket;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
