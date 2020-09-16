package ru.general;

public class ClientInfo {
    static String host = "localhost";
    static int port = 8000;

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        ClientInfo.host = host;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        ClientInfo.port = port;
    }
}
