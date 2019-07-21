import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;

    //safe history flag
    private boolean safe;

    public Client(Socket socket, boolean safe) {
        this.socket = socket;
        this.safe = safe;
    }

    public void run() throws IOException {

        //Get the input stream (sequence of bytes)
        InputStream inputStream = socket.getInputStream();

        //InputStreamReader takes a byte stream and converts it into a character stream
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        //Reads more than 1 char from the stream and safes them into a buffer
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        //Get the output stream (sequence of bytes)
        OutputStream outputStream = socket.getOutputStream();

        //Use PrintWriter to make the output stream buffered and work with characters (also add println method)
        PrintWriter writer = new PrintWriter(outputStream, true);

        System.out.println("Connected");

        Thread readingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String inputLine = null;
                try {
                    inputLine = bufferedReader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                while (inputLine != null) {
                    System.out.println("    " + inputLine);
                    try {
                        inputLine = bufferedReader.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread writingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNext()) {
                    writer.println(scanner.nextLine());
                }


            }
        });

        readingThread.start();
        writingThread.start();
    }
}
