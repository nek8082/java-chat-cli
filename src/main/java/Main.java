import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to connect or listen (c/l): ");
        String answer = scanner.next();
        System.out.print("Do you want to safe the chat history or continue from an existing chat history (if there" +
                " already exists one for the ip) (y/n): ");
        String answer2 = scanner.next();
        boolean flag = false;

        switch (answer2) {
            case "y":
                flag = true;
                DBManager.createDb();
                break;
            case "n":
                flag = false;
                break;
        }

        switch (answer) {
            case "c":
                //Scanner to read user input
                System.out.print("Enter server ip and press enter: ");
                String ip = scanner.next();
                System.out.print("Enter server port and press enter: ");
                int port = scanner.nextInt();
                Socket socket = new Socket(ip, port);
                Client client = new Client(socket, flag);
                client.run();
                break;
            case "l":
                System.out.print("What port do you want to listen on (0 to 65535): ");
                ServerSocket serverSocket = new ServerSocket(scanner.nextInt());
                Server server = new Server(serverSocket, flag);
                server.run();
                break;

        }

    }
}
