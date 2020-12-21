package cpen221.mp3.server;


/**
 * WikiMediatorServer is a server that finds the n^th Fibonacci number given n. It
 * accepts requests of the correct form of queries and
 * for each request, returns a reply of the form: Reply ::= (Number | "err")
 * "\n" where a Number is the requested Fibonacci number, or "err" is used to
 * indicate a misformatted request. FibonacciServer can handle only one client
 * at a time.
 *
 * TODO: change the description to match that of WikiMediatorServer
 */
public class WikiMediatorServer {

    /**
     * Start a server at a given port number, with the ability to process
     * upto n requests concurrently.
     *
     * @param port the port number to bind the server to
     * @param n the number of concurrent requests the server can handle
     */
    public WikiMediatorServer(int port, int n) {
        /* TODO: Implement this method */
    }

}
