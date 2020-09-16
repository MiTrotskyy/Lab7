package ru;

import ru.client.ClientSide;
import ru.data_base.DataBaseController;
import ru.server.ServerSide;

import java.net.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter running mode, server or client");
        String mode = bufferedReader.readLine().trim().toLowerCase();
        if (mode.equals("newdb")) {
            DataBaseController.createTable();
            System.exit(0);
        }
        if (mode.equals("cleardb")) {
            DataBaseController.clear();
            System.exit(0);
        }
        if (mode.equals("server")) {
            System.out.println("Enter server port");
            try {
                int port = Integer.parseInt(bufferedReader.readLine().trim());
//              Запуск сервера с указанным портом
                ServerSide.runServer();
            } catch (NumberFormatException e) {
                System.out.println("Invalid port");
                System.exit(0);
            }  /* catch (IOException e) {
                e.printStackTrace();
                System.out.println("Can't start server");
                System.exit(0);
            } */
        } else if (mode.equals("client")) {
            try {
                System.out.println("Enter server port");
                int port = Integer.parseInt(bufferedReader.readLine().trim());
                System.out.println("Enter hostname");
                InetAddress inetAddress = InetAddress.getByName(bufferedReader.readLine().trim());
                ClientSide.run();
            }catch (NumberFormatException e){
                System.out.println("Invalid port");
                System.exit(0);
            } catch(UnknownHostException e){
                System.out.println("IP for this host not found");
                System.exit(0);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
