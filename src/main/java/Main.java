import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        while (true) {
            System.out.print("$ ");
            System.out.flush();

            String command = reader.readLine();

            if (command == null) {
                break; // EOF
            }

            System.out.println(command + ": command not found");
        }
    }
}