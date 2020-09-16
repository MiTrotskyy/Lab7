package ru.server;

import ru.commands.Command;
import ru.data_base.DataBaseController;
import ru.general.ClientInfo;
import ru.general.human_being_controller.HumanBeingMap;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerSide {
    private static Logger LOGGER = Logger.getLogger(ServerSide.class.getName());
    private DatagramSocket serverSocket;
    private HumanBeingMap humanBeingMap;
    private BufferedReader bufferedReader;
    public ServerSide() throws SocketException {
        this(ClientInfo.getPort());
    }

    private ServerSide(int port) throws SocketException {
        serverSocket = new DatagramSocket(port);
        LOGGER.log(Level.INFO, "Server socket opened");
        humanBeingMap = new HumanBeingMap();
        LOGGER.log(Level.INFO, "Human being map object created");
    }

    class Receiver implements Runnable{
        private final ExecutorService executorRequests;
        private final ExecutorService executorSender;

        Receiver(ExecutorService executorRequests, ExecutorService executorSender) {
            this.executorRequests = executorRequests;
            this.executorSender = executorSender;
        }

        @Override
        public void run() {
            try {
                byte[] buffer = new byte[65536];
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
//                LOGGER.log(Level.INFO, "Datagram packet created");
                try {
                    serverSocket.receive(datagramPacket);
                    LOGGER.log(Level.INFO, "Datagram packet received");
                } catch (IOException e) {
                    if (bufferedReader.readLine().toLowerCase().equals("exit")) {
                        System.exit(0);
                    }
                }
                InetAddress address = datagramPacket.getAddress();
                int port = datagramPacket.getPort();
//                System.out.println(Arrays.toString(buffer));
                ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(buffer));
                Command command = (Command) objectInputStream.readObject();
                objectInputStream.close();

                Runnable task = new Handler(command, address, port, executorSender);
                executorRequests.submit(task);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    class Handler implements Runnable {
        private Command command;
        private InetAddress inetAddress;
        private int port;
        private ExecutorService executorService;

        Handler(Command command, InetAddress inetAddress, int port, ExecutorService executorService) {
            this.command = command;
            this.inetAddress = inetAddress;
            this.port = port;
            this.executorService = executorService;
        }

        @Override
        public void run() {
            if (command.getMessage().equals("register") || command.getMessage().equals("login")) {
                command.execute(humanBeingMap);
                LOGGER.log(Level.INFO, "Command executed");
            } else {
                humanBeingMap = DataBaseController.getDataByUser(command.getUser());
                command.execute(humanBeingMap);
                LOGGER.log(Level.INFO, "Command executed");
                DataBaseController.saveDataByUser(command.getUser(), humanBeingMap);
            }

            executorService.submit(new Sender(command, inetAddress, port));
        }
    }

    class Sender implements Runnable {
        private Command command;
        private InetAddress inetAddress;
        private int port;

        Sender(Command command, InetAddress inetAddress, int port) {
            this.command = command;
            this.inetAddress = inetAddress;
            this.port = port;
        }

        public void run() {
            try {
                byte[] sendBuf = command.toByteArray();

                DatagramPacket datagramPacket = new DatagramPacket(sendBuf, sendBuf.length, inetAddress, port);
                LOGGER.log(Level.INFO, "Datagram packet created");
                serverSocket.send(datagramPacket);
                LOGGER.log(Level.INFO, "Datagram packet send");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void run() {

        ExecutorService executorRequests = Executors.newFixedThreadPool(1);
        ExecutorService executorSender = Executors.newCachedThreadPool();
        ExecutorService executorReceiver = Executors.newCachedThreadPool();
        boolean running = true;
        while (running){
            try {
                Thread.sleep(5000);
                executorReceiver.submit(new Receiver(executorSender, executorRequests));
            } catch (InterruptedException e) {
                e.printStackTrace();
                running = false;
            }
        }
        serverSocket.close();
    }
    public static void runServer(int port) throws SocketException {
        ServerSide serverSide = new ServerSide(port);
        LOGGER.log(Level.INFO, "Server object created");
        serverSide.run();
    }

    public static void main(String[] args) throws SocketException {
        runServer();
    }
}
