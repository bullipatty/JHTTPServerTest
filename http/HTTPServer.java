/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bullipatty.game.server.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Patrick
 */
public class HTTPServer extends Thread {
    
    // private final HttpServer server;
    private final ServerSocket _socket;

    public HTTPServer(int port) {
        ServerSocket server = null;
        System.out.println("HTTPServer is starting on port: " + port);
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("port: " + port + " unavaible");
            System.exit(-1);
        }
        _socket = server;
    }

    @Override
    public void run() {
        System.out.println("HTTPServer is running");
        Socket client = null;
        int c = 0;
        while (true) {
            try {
            //    if(_acceptNext) {
                    System.out.println("wait: " + (++c));
                    client = _socket.accept();
                    (new HTTPHandler(this, client, c)).start();
            //    } else
                    Thread.sleep(1);
            } catch (IOException e) {
                System.exit(-1);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
}
