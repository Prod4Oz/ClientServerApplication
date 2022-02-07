package com.skobelev.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {

    public static void main(String[] args) throws IOException {
        //  Занимаем порт, определяя серверный сокет
        ServerSocket servSocket = new ServerSocket(8080);
        ExecutorService poolExecutor = Executors.newFixedThreadPool(200);

        log("Server start");

        try {
            while (true) {
                //  Ждем подключения клиента и получаем потоки для дальнейшей работы
                Socket socket = servSocket.accept();
                handle(socket);
//                new Thread(null, () -> handle(socket)).start();
//                poolExecutor.submit(() -> handle(socket));
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
            servSocket.close();
        }
    }

    private static void handle(Socket socket) {
        log("client connected: " + socket.getRemoteSocketAddress());

        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                // Пишем ответ
                log("receive from " + socket.getRemoteSocketAddress() + " > " + line);

                out.println("Echo: " + line + " serverTime = " + System.currentTimeMillis());

                // Выход если от клиента получили end
                if (line.equals("end")) {
                    break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void log(String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] " + message);
    }
}
