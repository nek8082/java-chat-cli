import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String msg1 = "Do you want to connect or listen (c/l): ";
        String msg2 = "Do you want to safe the chat history or continue from an existing chat history (if there" +
                " already exists one for the ip) (y/n): ";
        String errMsg = "Wrong input please try again";

        String answer = checkInput(errMsg, "c", "l", msg1, scanner);
        String answer2 = checkInput(errMsg, "y", "n", msg2, scanner);

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

    private static String checkInput(String errMsg, String input1, String input2, String msg, Scanner scanner) {
        System.out.print(msg);
        String answer = scanner.next();
        while (!answer.equals(input1) && !answer.equals(input2)) {
            System.out.println(errMsg);
            System.out.print(msg);
            answer = scanner.next();
        }
        return answer;
    }
}
