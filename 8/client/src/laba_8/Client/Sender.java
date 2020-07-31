package laba_8.Client;

import laba_8.Commands.SerializedCommands.SerializedArgumentCommand;
import laba_8.Commands.SerializedCommands.SerializedCombinedCommand;
import laba_8.Commands.SerializedCommands.SerializedObjectCommand;
import laba_8.Commands.SerializedCommands.SerializedSimplyCommand;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Sender {
    private final Communicator communicator;
    private final DatagramSocket datagramSocket;

    public Sender(Communicator communicator) {
        this.communicator = communicator;
        this.datagramSocket = communicator.getDatagramSocket();
    }

    public void sendObject(SerializedArgumentCommand serializedCommand) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(4096);
        ObjectOutputStream out = new ObjectOutputStream(byteStream);
        out.writeObject(serializedCommand);
        byte[] data = byteStream.toByteArray();
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length, datagramSocket.getInetAddress(), communicator.getPort());
        datagramSocket.send(datagramPacket);
    }

    public void sendObject(SerializedCombinedCommand serializedCommand) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(4096);
        ObjectOutputStream out = new ObjectOutputStream(byteStream);
        out.writeObject(serializedCommand);
        byte[] data = byteStream.toByteArray();
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length, datagramSocket.getInetAddress(), communicator.getPort());
        datagramSocket.send(datagramPacket);
    }

    public void sendObject(SerializedObjectCommand serializedCommand) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(4096);
        ObjectOutputStream out = new ObjectOutputStream(byteStream);
        out.writeObject(serializedCommand);
        byte[] data = byteStream.toByteArray();
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length, datagramSocket.getInetAddress(), communicator.getPort());
        datagramSocket.send(datagramPacket);
    }

    public void sendObject(SerializedSimplyCommand serializedCommand) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(4096);
        ObjectOutputStream out = new ObjectOutputStream(byteStream);
        out.writeObject(serializedCommand);
        byte[] data = byteStream.toByteArray();
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length, InetAddress.getByName(communicator.getHost()), communicator.getPort());
        datagramSocket.send(datagramPacket);
    }

    public void sendMessage(String message) throws IOException {
        byte[] data = message.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length, InetAddress.getByName(communicator.getHost()), communicator.getPort());
        datagramSocket.send(datagramPacket);
    }

    public DatagramSocket getDatagramSocket() {
        return datagramSocket;
    }
}