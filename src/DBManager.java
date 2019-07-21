import java.io.FileWriter;
import java.io.IOException;

public class DBManager {
    public static void main(String[] args) throws IOException {
        //Create chat database
        FileWriter fileWriter = new FileWriter("chat.db");
        fileWriter.write("Hello World");
        fileWriter.close();
    }
}
