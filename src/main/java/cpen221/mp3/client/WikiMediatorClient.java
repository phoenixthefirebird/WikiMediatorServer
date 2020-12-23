package cpen221.mp3.client;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;

//TODO: change this class from FibonacciClient to WikiMediatorClient to test WikiMediatorServer
public class WikiMediatorClient {
    private static final int N = 100;
    private Socket socket;
    private BufferedReader in;
    // Rep invariant: socket, in, out != null
    private PrintWriter out;

    /**
     * Make a WikiMediatorClient and connect it to a server running on
     * hostname at the specified port.
     *
     * @throws IOException if can't connect
     */
    public WikiMediatorClient(String hostname, int port) throws IOException {
        socket = new Socket(hostname, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    /**
     * Use the WikiMediatorClient
     */
    public static void main(String[] args) {
        try {
            WikiMediatorClient client = new WikiMediatorClient("35.236.3.212",
                    8090);

            // send the requests to find the first N Fibonacci numbers
            for (int x = 1; x <= N; ++x) {
                client.sendRequest(x);
                System.out.println("fibonacci(" + x + ") = ?");
            }

            // collect the replies
            for (int x = 1; x <= N; ++x) {
                BigInteger y = client.getReply();
                System.out.println("fibonacci(" + x + ") = " + y);
            }

            client.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Send a request to the server. Requires this is "open".
     *
     * @param x to find Fibonacci(x)
     * @throws IOException if network or server failure
     */
    public void sendRequest(int x) throws IOException {
        out.print(x + "\n");
        out.flush(); // important! make sure x actually gets sent
    }

    /**
     * Get a reply from the next request that was submitted.
     * Requires this is "open".
     *
     * @return the requested Fibonacci number
     * @throws IOException if network or server failure
     */
    public BigInteger getReply() throws IOException {
        String reply = in.readLine();
        if (reply == null) {
            throw new IOException("connection terminated unexpectedly");
        }

        try {
            return new BigInteger(reply);
        }
        catch (NumberFormatException nfe) {
            throw new IOException("misformatted reply: " + reply);
        }
    }

    /**
     * Closes the client's connection to the server.
     * This client is now "closed". Requires this is "open".
     *
     * @throws IOException if close fails
     */
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}