package com.skobelev.socket;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoadClient {

    public static void main(String[] args) {
        List<Socket> sockets = new ArrayList<Socket>();
        System.out.println("Opening sockets");
        for (int i = 0; i < 1000; i++) {
            try {
                sockets.add(new Socket("localhost", 8080));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.print("Print any string to exit");
        new Scanner(System.in).next();

        System.out.print("Closing connections");
        for (Socket socket : sockets) {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("error closing socket " + e.getMessage());
            }
        }
    }
}
