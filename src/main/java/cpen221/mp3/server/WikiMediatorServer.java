package cpen221.mp3.server;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import cpen221.mp3.wikimediator.WikiMediator;
import okhttp3.Request;

import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * WikiMediatorServer is a server that finds the n^th Fibonacci number given n. It
 * accepts requests of the correct form of queries and
 * for each request, returns a reply of the form: Reply ::= (Number | "err")
 * "\n" where a Number is the requested Fibonacci number, or "err" is used to
 * indicate a misformatted request. FibonacciServer can handle only one client
 * at a time.
 *
 * TODO: change the description to match that of WikiMediatorServer^
 */
public class WikiMediatorServer {
    /**
     * AF:
     *
     * RI:
     * serverSocket is not null
     */

    private ServerSocket serverSocket;
    private int limit;
    private WikiMediator backend;

    /**
     * Start a server at a given port number, with the ability to process
     * upto n requests concurrently.
     *
     * @param port the port number to bind the server to
     * @param n the number of concurrent requests the server can handle
     * requires: 0 <= port <= 65535
     */
    public WikiMediatorServer(int port, int n) throws IOException{
        serverSocket = new ServerSocket(port);
        limit = n;
        backend = new WikiMediator();
    }


    /**
     * Run the server, listening for connections and handling them.
     *
     * @throws IOException
     *             if the main server socket is broken
     */
    public void serve() throws IOException {
        while (true) {
            // block until a client connects
            final Socket socket = serverSocket.accept();
            // create a new thread to handle that client
            Thread handler = new Thread(new Runnable() {
                public void run() {
                    try {
                        try {
                            handle(socket);
                        } finally {
                            socket.close();
                        }
                    } catch (IOException ioe) {
                        // this exception wouldn't terminate serve(),
                        // since we're now on a different thread, but
                        // we still need to handle it
                        ioe.printStackTrace();
                    }
                }
            });
            // start the thread
            handler.start();
        }
    }

    /**
     * Handle one client connection. Returns when client disconnects.
     *
     * @param socket
     *            socket where client is connected
     * @throws IOException
     *             if connection encounters an error
     */
    private void handle(Socket socket) throws IOException {
        System.err.println("client connected");

        // get the socket's input stream, and wrap converters around it
        // that convert it from a byte stream to a character stream,
        // and that buffer it so that we can read a line at a time
        BufferedReader in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));

        // similarly, wrap character=>bytestream converter around the
        // socket output stream, and wrap a PrintWriter around that so
        // that we have more convenient ways to write Java primitive
        // types to it.
        PrintWriter out = new PrintWriter(new OutputStreamWriter(
                socket.getOutputStream()), true);

            for (String line = in.readLine(); line != null; line = in
                    .readLine()) {
                System.out.println("request: " + line);
                Request request = new Gson().fromJson(line, Request.class);
//                System.err.println("reply: " + y);
//                out.println(y);

            out.close();
            in.close();
        }

    }

    /**
     * this class holds the information parsed from json in the requests
     */
    public class Request{
        String id;
        String type;
        String query;
        String getPage;
        int limit;
        int timeout;
    }

    /**
     * this class holds the information parsed from response object
     */

    public static class Response <T> {
        String id;
        String status;
        T response;

        public Response(String id, String status, T response){
            this.id = id;
            this.status = status;
            this.response = response;
        }
    }

    //sample program
    public static void main(String[] args) {
        String jsonString = "{ \"id\": \"two\", \"type\": \"trending\", \"limit\": \"5\" }";
        Request request = new Gson().fromJson(jsonString, Request.class);
        System.out.println(request.limit);
        String[] seed = {"Barack Obama","Barack Obama in comics", "Barack Obama Sr.", "List of things named after Barack Obama", "Speeches of Barack Obama"};
        List<String> result = Arrays.asList(seed);
        Response response = new Response("2","success",5);
        String json = new Gson().toJson(response);
        System.out.println(json);
    }

}

