/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bullipatty.game.server.http;

import java.util.HashMap;

/**
 *
 * @author Patrick
 */
public class Header {
    
    private HashMap<String, String> _hearders = null;
    private int c = 0;

    public Header() {
        _hearders = new HashMap<>();
    }
    
    public HashMap<String, String> getHeaders() {
        return _hearders;
    }
    
    public void parse(String line) {
        if(c == 0) {
            String[] m = line.split(" ");
            _hearders.put("Method", m[0]);
            _hearders.put("Query", m[1]);
            _hearders.put("Protocol", m[2]);
        } else {
            String[] m = line.split(":|\\s", 2);
            if(m.length > 1) {
                _hearders.put(m[0], m[1]);
            }
        }
        c++;
    }
    
    
}
