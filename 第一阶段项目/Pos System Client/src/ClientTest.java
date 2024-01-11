import controller.ClientThread;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientTest {
    public static void main(String[] args) throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        ClientThread client=new ClientThread();
        pool.submit(client);
    }
}