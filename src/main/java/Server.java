import java.io.*;
import java.net.ServerSocket;

public class Server {
    private ServerSocket serverSocket;
    private boolean flag;

    public Server(ServerSocket serverSocket, boolean flag) {
        this.serverSocket = serverSocket;
        this.flag = flag;
    }

    public void run() throws IOException {
        Client client = new Client(serverSocket.accept(), flag);
        client.run();
    }
}
