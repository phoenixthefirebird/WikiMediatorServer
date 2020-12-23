package cpen221.mp3;

import cpen221.mp3.server.WikiMediatorServer;
import org.junit.Test;

import java.io.IOException;

public class WikiMediatorServerTests {
    @Test
    public void test1() throws IOException {
        WikiMediatorServer server = new WikiMediatorServer(2, 5);
        server.serve();
    }
}
