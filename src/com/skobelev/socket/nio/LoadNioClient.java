package com.skobelev.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Дудос атака
 */
public class LoadNioClient {

    public static void main(String[] args) {
        var sockets = new ArrayList<SocketChannel>();
        System.out.println("Opening sockets");
        for (int i = 0; i < 1000; i++) {
            try {
                // Определяем сокет сервера
                InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 8081);
                final SocketChannel socketChannel = SocketChannel.open();

                //  подключаемся к серверу
                socketChannel.connect(socketAddress);
                sockets.add(socketChannel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.print("Print any string to exit");
        new Scanner(System.in).next();

        System.out.print("Closing connections");
        for (SocketChannel socket : sockets) {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("error closing socket " + e.getMessage());
            }
        }
    }
}
