package cpen221.mp3.server;


import com.google.gson.Gson;
import cpen221.mp3.wikimediator.WikiMediator;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.*;

/**
 * WikiMediatorServer is a server that finds the n^th Fibonacci number given n. It
 * accepts requests of the correct form of queries and
 * for each request, returns a reply of the form: Reply ::= (Number | "err")
 * "\n" where a Number is the requested Fibonacci number, or "err" is used to
 * indicate a misformatted request. FibonacciServer can handle only one client
 * at a time.
 * <p>
 * TODO: change the description to match that of WikiMediatorServer^
 */
public class WikiMediatorServer {
    /**
     * AF:
     * <p>
     * RI:
     * serverSocket is not null
     */

    private final ServerSocket serverSocket;
    private final ExecutorService executor;

    /**
     * Start a server at a given port number, with the ability to process
     * upto n requests concurrently.
     *
     * @param port the port number to bind the server to
     * @param n    the number of concurrent requests the server can handle
     *             requires: 0 <= port <= 65535
     */
    public WikiMediatorServer(int port, int n) throws IOException {
        serverSocket = new ServerSocket(port);
        executor = Executors.newFixedThreadPool(n);
    }


    /**
     * Run the server, listening for connections and handling them.
     *
     * @throws IOException if the main server socket is broken
     */
    public void serve() throws IOException {
        while (true) {
            // block until a client connects
            final Socket socket = serverSocket.accept();
            // create a new thread to handle that client
            Thread handler = new Thread(() -> {
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
            });
            // start the thread
            handler.start();
        }
    }

    /**
     * Handle one client connection. Returns when client disconnects.
     *
     * @param socket socket where client is connected
     * @throws IOException if connection encounters an error
     */
    private void handle(Socket socket) throws IOException {
        System.err.println("client connected");
        WikiMediator wiki = new WikiMediator();


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
            Response<?> response;
            switch (request.type) {
                case "search":
                    Future<List<String>> future_s = executor.submit(() -> wiki.search(request.query, request.limit));
                    response = execute(request,future_s);
                    break;
                case "getPage":
                    Future<String> future_g = executor.submit(() -> wiki.getPage(request.getPage));
                    response = execute(request,future_g);
                    break;
                case "zeitgeist":
                    Future<List<String>> future_z = executor.submit(() -> wiki.zeitgeist(request.limit));
                    response = execute(request,future_z);
                    break;
                case "trending":
                    Future<List<String>> future_t = executor.submit(() -> wiki.trending(request.limit));
                    response = execute(request,future_t);
                    break;
                case "peakLoad30s":
                    Future<Integer> future_p = executor.submit(wiki::peakLoad30s);
                    response = execute(request,future_p);
                    break;
                case "stop":
                    executor.shutdown();
                    try {
                        // Wait a while for existing tasks to terminate
                        if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                            executor.shutdownNow(); // Cancel currently executing tasks
                            wiki.closeWiki();
                            // Wait a while for tasks to respond to being cancelled
                            if (!executor.awaitTermination(5, TimeUnit.SECONDS))
                                System.err.println("Server did not terminate");
                        }
                    } catch (InterruptedException ie) {
                        // (Re-)Cancel if current thread also interrupted
                        executor.shutdownNow();
                        // Preserve interrupt status
                        Thread.currentThread().interrupt();
                    }
                    response = new Response<>(request.id, "bye");
                    break;
                default:
                    response = new Response<>(request.id,"Invalid input, please try again!");
            }
            out.println(new Gson().toJson(response));

        }
        out.close();
        in.close();
    }


    private <T> Response<?> execute(Request request, Future<T> future) {
        T result;
        if (request.timeout != 0) {
            try {
                result = future.get();
            } catch (InterruptedException | ExecutionException e) {
                return new Response<>(request.id, "failure", "Operation interrupted");
            }
        } else {
            try {
                result = future.get(request.timeout, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException e) {
                return new Response<>(request.id, "failure", "Operation interrupted");
            } catch (TimeoutException e) {
                return new Response<>(request.id, "failure", "Operation timed out");
            }
        }
        return new Response<>(request.id, "success", result);
    }


    /**
     * this class holds the information parsed from json in the requests
     */
    class Request {
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

    class Response<T> {
        String id;
        String status;
        T response;

        public Response(String id, String status, T response) {
            this.id = id;
            this.status = status;
            this.response = response;
        }
        public Response(String id, T response) {
            this.id = id;
            this.response = response;
        }
    }

//        //sample program
//        public static void main (String[] args){
//            String jsonString = "{ \"id\": \"two\", \"type\": \"trending\", \"limit\": \"5\" }";
//            Request request = new Gson().fromJson(jsonString, Request.class);
//            System.out.println(request.limit);
//            String[] seed = {"Barack Obama", "Barack Obama in comics", "Barack Obama Sr.", "List of things named after Barack Obama", "Speeches of Barack Obama"};
//            List<String> result = Arrays.asList(seed);
//            Response response = new Response("2", "success", 5);
//            String json = new Gson().toJson(response);
//            System.out.println(json);
//        }
//
}


