package ru.client;

import org.postgresql.util.ByteBufferByteStreamWriter;
import ru.commands.*;
import ru.general.ClientInfo;
import ru.general.User;

import javax.xml.crypto.Data;
import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.regex.Pattern;

public class ClientSide {
    public static ClientCommandData CLIENT_COMMAND_DATA = new ClientCommandData(false);
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static boolean loginUser(User user) throws IOException, ClassNotFoundException {
        Command login = new Login();
        login.setUser(user);
        login = exchangeCommands(login);
//        login.execute(null);
        System.out.println(login.getMessage());
        return (login.isValid());
    }

    public static boolean registerUser(User user) throws IOException, ClassNotFoundException {
        Command register = new Register();
        register.setUser(user);
        register = exchangeCommands(register);
//        register.execute(null);
        System.out.println(register.getMessage());
        return (register.isValid());
    }

    public static Command exchangeCommands(Command command) throws IOException, ClassNotFoundException {
        return exchangeCommands(command, InetAddress.getByName(ClientInfo.getHost()), ClientInfo.getPort());
    }
    public static Command exchangeCommands(Command command, InetAddress address, int port) throws IOException, ClassNotFoundException {

        DatagramChannel clientDatagramChannel = DatagramChannel.open();
        clientDatagramChannel.bind(null);

        byte[] sendBuf;
        sendBuf = command.toByteArray();
        DatagramPacket datagramPacketSend = new DatagramPacket(sendBuf, sendBuf.length, address, port);
        clientDatagramChannel.socket().send(datagramPacketSend);

        byte[] receiveBuf = new byte[100000];
        DatagramPacket datagramPacket = new DatagramPacket(receiveBuf, receiveBuf.length);
        boolean received = false;
        try {
            clientDatagramChannel.socket().receive(datagramPacket);
            received = true;
        } catch (SocketTimeoutException e){
            System.out.println("No response from server");
        }

        if (received) {
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(receiveBuf));
            command = (Command) objectInputStream.readObject();
            objectInputStream.close();
        } else {
            command.setMessage("No response from server");
        }
        clientDatagramChannel.close();
        return command;
    }
    public static void run() throws IOException, ClassNotFoundException {
        System.out.println("You need to register or login\nEnter login or register to do it");
        String line = "";
        boolean inputCorrect = false;
        while (!inputCorrect) {
            line = bufferedReader.readLine();
            if (line.toLowerCase().equals("login") || line.toLowerCase().equals("register")) {
                inputCorrect = true;
            } else if (line.toLowerCase().equals("exit")){
                System.exit(0);
            } else {
                System.out.println("Invalid input. Enter login or register ");
            }
        }
        String action = line;
        inputCorrect = false;
        line = "";
        while (!inputCorrect) {
            System.out.println("Enter username, please use only english letters and numbers");
            line = bufferedReader.readLine();
            if (Pattern.matches("^[a-zA-Z0-9]{1,}$", line)){
                inputCorrect = true;
            }
        }
        String username = line;
        inputCorrect = false;
        line = "";
        while (!inputCorrect) {
            System.out.println("Enter password, please use only english letters and numbers");
            line = bufferedReader.readLine();
            if (Pattern.matches("^[a-zA-Z0-9]{1,}$", line)){
                inputCorrect = true;
            }
        }
        String password = encrypt(line);
        User user = new User(username, password);
        if (action.equals("register")) {
            if (!registerUser(user)) {
                System.exit(0);
            }
        }
        if (action.equals("login")) {
            if (!loginUser(user)) {
                System.exit(0);
            }
        }

        System.out.println("Hello " + username + "!");
        while (true) {
            System.out.println("Please, enter your command, to get full list of commands, use \"help\" command.");
            line = bufferedReader.readLine();
            if (!line.isEmpty()) {
                CLIENT_COMMAND_DATA.setCommand(line);
                if (CLIENT_COMMAND_DATA.getCommand() != null){
                    Command command = CLIENT_COMMAND_DATA.getCommand();
                command.setUser(user);
                if (command.isValid()) {
                    if (!command.getMessage().equals("script")) {
                        command = exchangeCommands(command);
                        System.out.println(command.getMessage());
                        if (command.getMessage().contains("Logging out")) {
                            System.exit(0);
                        }
                    }
                }
                }
            }
        }
    }

    private static String encrypt(String password){
        StringBuilder md5Hex = new StringBuilder("");
        try {
            MessageDigest messageDigest = null;
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(password.getBytes());

            byte[] digest = messageDigest.digest();
            BigInteger bigInteger = new BigInteger(1, digest);
            md5Hex = new StringBuilder(bigInteger.toString(16));

            while (md5Hex.length() < 32) {
                md5Hex.insert(0, "0");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5Hex.toString();
    }
}
