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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import java.io.IOException;

public class WikiMediatorServerTests {

    //one client connecting to server
    @Test
    public void test1() throws IOException, InterruptedException {

        Runnable task = () -> {
            WikiMediatorServer server = null;
            try {
                server = new WikiMediatorServer(999, 1);
                server.serve();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Started serving");

        };

        Thread t1 = new Thread(task);
        t1.start();

        WikiMediatorClient client= new WikiMediatorClient("127.0.0.1", 999 );
        WikiMediatorClient.Request x = new WikiMediatorClient.Request("id","peakLoad30s");
        client.sendRequest(x);

        System.out.println("sending request");
        System.out.println(client.getReply());
        client.close();

    }

    //two clients connecting to server
    @Test
    public void test2() throws IOException {
        Runnable task = () -> {
            WikiMediatorServer server = null;
            try {
                server = new WikiMediatorServer(999, 2);
                server.serve();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Started serving");

        };

        Thread t1 = new Thread(task);
        t1.start();

         WikiMediatorClient client1= new WikiMediatorClient("127.0.0.1", 999 );
         WikiMediatorClient.Request x1 = new WikiMediatorClient.Request("id","search","UBC", 5, 30);
         Runnable task1 = () -> {
             try {
                 client1.sendRequest(x1);
             } catch (IOException e) {
                 e.printStackTrace();
             }
         };
        WikiMediatorClient client2 = new WikiMediatorClient("127.0.0.1", 999 );
        WikiMediatorClient.Request x2 = new WikiMediatorClient.Request("id","getPage","university", 5, 30);

        Runnable task2 = () -> {
            try {
                 client2.sendRequest(x2);
             } catch (IOException e) {
                 e.printStackTrace();
             }
         };
         Thread t2 = new Thread(task1);
         Thread t3 = new Thread(task2);
         t2.start();
         t3.start();
         System.out.println("sending request");
         System.out.println(client1.getReply());

         System.out.println("sending request");
         System.out.println(client2.getReply());
         client1.close();
         client2.close();
    }

    //ten clients connecting to server
    @Test
    public void test3() throws IOException {
        Runnable task = () -> {
            WikiMediatorServer server = null;
            try {
                server = new WikiMediatorServer(1, 10);
                server.serve();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Started serving");

        };

        Thread t1 = new Thread(task);
        t1.start();

        WikiMediatorClient client1= new WikiMediatorClient("127.0.0.1", 1 );
        WikiMediatorClient.Request x1 = new WikiMediatorClient.Request("id","search","UBC", 5, 30);
        Runnable task1 = () -> {
            try {
                client1.sendRequest(x1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        WikiMediatorClient client2 = new WikiMediatorClient("127.0.0.1", 1 );
        WikiMediatorClient.Request x2 = new WikiMediatorClient.Request("id","search","animal", 5, 30);

        Runnable task2 = () -> {
            try {
                client2.sendRequest(x2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        WikiMediatorClient client3 = new WikiMediatorClient("127.0.0.1", 1 );
        WikiMediatorClient.Request x3 = new WikiMediatorClient.Request("3","zeitgeist",5);

        Runnable task3 = () -> {
            try {
                client3.sendRequest(x3);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        WikiMediatorClient client4 = new WikiMediatorClient("127.0.0.1", 1 );
        WikiMediatorClient.Request x4 = new WikiMediatorClient.Request("4","search","birds", 5, 30);

        Runnable task4 = () -> {
            try {
                client4.sendRequest(x4);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        WikiMediatorClient client5 = new WikiMediatorClient("127.0.0.1", 1 );
        WikiMediatorClient.Request x5 = new WikiMediatorClient.Request("45","trending", 3);

        Runnable task5 = () -> {
            try {
                client5.sendRequest(x5);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        WikiMediatorClient client6 = new WikiMediatorClient("127.0.0.1", 1 );
        WikiMediatorClient.Request x6 = new WikiMediatorClient.Request("2","peakLoad30s");

        Runnable task6 = () -> {
            try {
                client6.sendRequest(x6);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        WikiMediatorClient client7 = new WikiMediatorClient("127.0.0.1", 1 );
        WikiMediatorClient.Request x7 = new WikiMediatorClient.Request("id","stop");

        Runnable task7 = () -> {
            try {
                client7.sendRequest(x7);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        WikiMediatorClient client8 = new WikiMediatorClient("127.0.0.1", 1 );
        WikiMediatorClient.Request x8 = new WikiMediatorClient.Request("fast","search","Fibonacci number", 5, 30);

        Runnable task8 = () -> {
            try {
                client8.sendRequest(x8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        WikiMediatorClient client9 = new WikiMediatorClient("127.0.0.1", 1 );
        WikiMediatorClient.Request x9 = new WikiMediatorClient.Request("33","search","Christmas gift idea", 5, 30);

        Runnable task9 = () -> {
            try {
                client9.sendRequest(x9);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        WikiMediatorClient client10 = new WikiMediatorClient("127.0.0.1", 1 );
        WikiMediatorClient.Request x10 = new WikiMediatorClient.Request("2","peakLoad30s");

        Runnable task10 = () -> {
            try {
                client10.sendRequest(x10);

            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Thread t2 = new Thread(task1);
        Thread t3 = new Thread(task2);
        Thread t4 = new Thread(task3);
        Thread t5 = new Thread(task4);
        Thread t6 = new Thread(task5);
        Thread t7 = new Thread(task6);
        Thread t8 = new Thread(task7);
        Thread t9 = new Thread(task8);
        Thread t10 = new Thread(task9);
        Thread t11 = new Thread(task10);
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();
        t11.start();
        System.out.println("sending request");
        System.out.println(client1.getReply() + System.lineSeparator());
        System.out.println("sending request" + System.lineSeparator());
        System.out.println(client2.getReply() + System.lineSeparator());
        System.out.println("sending request" + System.lineSeparator());
        System.out.println(client3.getReply() + System.lineSeparator());
        System.out.println("sending request" + System.lineSeparator());
        System.out.println(client4.getReply()+ System.lineSeparator());
        System.out.println("sending request");
        System.out.println(client5.getReply()+ System.lineSeparator());
        System.out.println("sending request");
        System.out.println(client6.getReply()+ System.lineSeparator());
        System.out.println("sending request");
        System.out.println(client7.getReply()+ System.lineSeparator());
        System.out.println("sending request");
        System.out.println(client8.getReply()+ System.lineSeparator());
        System.out.println("sending request");
        System.out.println(client9.getReply()+ System.lineSeparator());
        System.out.println("sending request");
        System.out.println(client10.getReply() + System.lineSeparator());
        System.out.println("sending request");
        System.out.println(client10.getReply() + System.lineSeparator());

        client1.close();
        client2.close();
        client3.close();
        client4.close();
        client5.close();
        client6.close();
//        client7.close();
        client8.close();
        client9.close();
        client10.close();
//        WikiMediatorClient client11 = new WikiMediatorClient("127.0.0.1", 9 );
//        WikiMediatorClient.Request x11 = new WikiMediatorClient.Request("2","peakLoad30s");
//        client11.sendRequest(x11);
//        System.out.println(client11.getReply() + System.lineSeparator());
//        client11.close();


    }
    @Test
    public void test4() throws IOException, InterruptedException {

        Runnable task = () -> {
            WikiMediatorServer server = null;
            try {
                server = new WikiMediatorServer(9, 2);
                server.serve();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Started serving");

        };

        Thread t1 = new Thread(task);
        t1.start();

        WikiMediatorClient client1= new WikiMediatorClient("127.0.0.1", 9 );
        WikiMediatorClient.Request x1 = new WikiMediatorClient.Request("id","peakLoad30s");
        Runnable task1 = () -> {
            try {
                client1.sendRequest(x1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        WikiMediatorClient client2 = new WikiMediatorClient("127.0.0.1", 9 );
        WikiMediatorClient.Request x2 = new WikiMediatorClient.Request("id","peakLoad30s");

        Runnable task2 = () -> {
            try {
                client2.sendRequest(x2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Thread t2 = new Thread(task1);
        Thread t3 = new Thread(task2);
        t2.start();
        t3.start();
        System.out.println("sending request");
        System.out.println(client1.getReply());

        System.out.println("sending request");
        System.out.println(client2.getReply());
        client1.close();
        client2.close();

    }



}
