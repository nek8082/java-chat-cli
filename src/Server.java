import java.io.*;
import java.net.ServerSocket;

public class Server {
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket, boolean flag) {
        this.serverSocket = serverSocket;
    }

    public void run() throws IOException {
        Client client = new Client(serverSocket.accept(), false);
        client.run();
    }
}
