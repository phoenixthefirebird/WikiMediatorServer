package cpen221.mp3.server;


import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

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
//        System.err.println("client connected");
//
//        // get the socket's input stream, and wrap converters around it
//        // that convert it from a byte stream to a character stream,
//        // and that buffer it so that we can read a line at a time
//        BufferedReader in = new BufferedReader(new InputStreamReader(
//                socket.getInputStream()));
//
//        // similarly, wrap character=>bytestream converter around the
//        // socket output stream, and wrap a PrintWriter around that so
//        // that we have more convenient ways to write Java primitive
//        // types to it.
//        PrintWriter out = new PrintWriter(new OutputStreamWriter(
//                socket.getOutputStream()), true);
//
//        try {
//            // each request is a single line containing a number
//            for (String line = in.readLine(); line != null; line = in
//                    .readLine()) {
//                System.err.println("request: " + line);
//                try {
//                    int x = Integer.valueOf(line);
//                    // compute answer and send back to client
//                    BigInteger y = fibonacci(x);
//                    System.err.println("reply: " + y);
//                    out.println(y);
//                } catch (NumberFormatException e) {
//                    // complain about ill-formatted request
//                    System.err.println("reply: err");
//                    out.print("err\n");
//                }
//                //TODO: to read request
//
//                // important! our PrintWriter is auto-flushing, but if it were
//                // not:
//                // out.flush();
//            }
//        } finally {
//            out.close();
//            in.close();
//        }

//    }
//
//}

    }

}

