import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        Set<String> builtins = Set.of("echo", "exit", "type");

        while (true) {
            System.out.print("$ ");
            System.out.flush();

            String input = reader.readLine();

            if (input == null) {
                break;
            }

            String[] parts = input.split(" ");

            String command = parts[0];

            if (command.equals("exit")) {
                break;
            }

            if (command.equals("echo")) {
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

            if (command.equals("type")) {
                String target = parts[1];

                if (builtins.contains(target)) {
                    System.out.println(target + " is a shell builtin");
                } else {
                    System.out.println(target + ": not found");
                }

                continue;
            }

            System.out.println(command + ": command not found");
        }
    }
}