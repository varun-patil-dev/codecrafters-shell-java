import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        while (true) {
            System.out.print("$ ");
            System.out.flush();

            String input = reader.readLine();

            if (input == null) {
                break;
            }

            String[] parts = input.split(" ");

            if (parts[0].equals("exit")) {
                break;
            }

            if (parts[0].equals("echo")) {
                StringBuilder sb = new StringBuilder();

                for (int i = 1; i < parts.length; i++) {
                    if (i > 1) {
                        sb.append(" ");
                    }
                    sb.append(parts[i]);
                }

                System.out.println(sb);
                continue;
            }

            System.out.println(parts[0] + ": command not found");
        }
    }
}