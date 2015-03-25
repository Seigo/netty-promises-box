package com.osawaseigo.netty.promises;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.netty.util.concurrent.Future;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {    	
        System.out.println( "Netty promises!" );
        
        if (args[0].equals("server")) {
        	System.out.println("Server listening..");
            IoServer server = new IoServer(Integer.parseInt(args[1]));
            server.run();
            System.exit(0);
        } else {
            IoClient client = new IoClient(args[0], Integer.parseInt(args[1]));

            ResponseFuture future = client.send("Hello server!");
            
            System.out.println("Doing some stuff in the meantime...");
            
            try {
            	String replyMsg = future.get(10, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
            	System.out.println("Timed out..");
            }
        }
    }
}
