package cpen221.mp3;

import cpen221.mp3.client.WikiMediatorClient;
import cpen221.mp3.client.*;
import cpen221.mp3.server.WikiMediatorServer;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;
import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.*;

import java.io.IOException;

public class WikiMediatorServerTests {
    //one client connecting to server
    @Test
    public void test1() throws IOException, InterruptedException {
        WikiMediatorServer server = new WikiMediatorServer(999, 1);
        WikiMediatorClient client= new WikiMediatorClient("127.0.0.1", 999 );


        // send the requests to find the first N Fibonacci numbers
        WikiMediatorClient.Request x = new WikiMediatorClient.Request("id","search","UBC", 30);
        client.sendRequest(x);
        System.out.println("sending request");
        System.out.println(client.getReply());
        client.close();

    }
    //two clients connecting to server
    @Test
    public void test2() throws IOException {
        WikiMediatorServer server = new WikiMediatorServer(999, 1);
        WikiMediatorClient client= new WikiMediatorClient("127.0.0.1",
                999);
        // Request x = new Request("id","search", 30);
        server.serve();

        // serverThread.join();

    }

//    //one client tryin
//    @Test
//    public void test3() throws IOException {
//        WikiMediatorServer server = new WikiMediatorServer(8090, 1);
//        WikiMediatorClient client= new WikiMediatorClient("35.236.3.212",
//                8090);)
//        server.serve();
//
//    }



}
