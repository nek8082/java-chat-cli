import java.io.*;
import java.net.ServerSocket;

class Server {
    private ServerSocket serverSocket;
    private boolean flag;

    Server(ServerSocket serverSocket, boolean flag) {
        this.serverSocket = serverSocket;
        this.flag = flag;
    }

    void run() {
        try {
            System.out.println("Listening");
            Client client = new Client(serverSocket.accept(), flag);
            client.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
