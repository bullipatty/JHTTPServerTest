/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bullipatty.game.server.http;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Patrick
 */
public class HTTPHandler extends Thread {
    
    private final HTTPServer _server;
    private final Socket _client;
    private final BufferedReader _in;
    private final PrintWriter _out;
    private final InputStream _inputStream;
    private final OutputStream _outputStream;
    private final Header _header;
    private final int _c;

    public HTTPHandler(HTTPServer server, Socket client, int c) {
        System.out.println("HTTPHandler is starting");
        _server = server;
        _client = client;
        BufferedReader in = null;
        PrintWriter out = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = client.getInputStream();
            outputStream = client.getOutputStream();
            in = new BufferedReader(new InputStreamReader(inputStream));
            out = new PrintWriter(outputStream);
        } catch (IOException ex) {
            System.exit(-1);
        }
        _header = new Header();
        _inputStream = inputStream;
        _outputStream = outputStream;
        _in = in;
        _out = out;
        _c = c;
    }
    
    public void readInput() {
        boolean run = true;
        String line = null;
        System.out.println("HTTPHandler is running");
        while(run) {
            try {
                line = _in.readLine();
                if(line == null) {
                    Thread.sleep(10);
                    /*
                    System.out.println("isClosed: " + _client.isClosed());
                    System.out.println("isConnected: " + _client.isConnected());
                    System.out.println("isInputShutdown: " + _client.isInputShutdown());
                    System.out.println("isOutputShutdown: " + _client.isOutputShutdown());
                    */
                } else if(line.equals("")) {
                    //System.out.println("\"httpinput: " + line + "\"");
                    run = false;
                } else {
                    _header.parse(line);
                    //System.out.println("\"httpinput: " + line + "\"");
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public void writeOutput() {
        _out.println("HTTP/1.1 200 OK");
        _out.println("Connection: Keep-Alive");
        _out.println("Content-Type: text/html");
        _out.println("");
        _out.println("<b>Hello" + _c + "</b>");
        _out.flush();
    }

    @Override
    public void run() {
        readInput();
        writeOutput();
        for (Map.Entry<String, String> entrySet : _header.getHeaders().entrySet()) {
            String key = entrySet.getKey();
            String value = entrySet.getValue();
            System.out.println("header input: m0: \"" + key + "\" m1: \"" + value + "\"");
        }
        try {
            _outputStream.close();
            //System.out
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
