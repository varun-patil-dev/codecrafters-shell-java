import java.io.*;
import java.util.*;

public class Main {

    private static String findExecutable(String command) {
        String pathEnv = System.getenv("PATH");

        if (pathEnv == null) {
            return null;
        }

        String[] paths = pathEnv.split(File.pathSeparator);

        for (String dir : paths) {
            File file = new File(dir, command);

            if (file.exists() && file.isFile() && file.canExecute()) {
                return file.getAbsolutePath();
            }
        }

        return null;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        Set<String> builtins = Set.of("echo", "exit", "type");

        while (true) {
            System.out.print("$ ");
            System.out.flush();

            String input = reader.readLine();

            if (input == null) {
                break;
            }

            if (input.isBlank()) {
                continue;
            }

            String[] parts = input.trim().split("\\s+");
            String command = parts[0];

            // exit
            if (command.equals("exit")) {
                break;
            }

            // echo
            if (command.equals("echo")) {
                System.out.println(
                        input.length() > 5 ? input.substring(5) : "");
                continue;
            }

            // type
            if (command.equals("type")) {
                if (parts.length < 2) {
                    continue;
                }

                String target = parts[1];

                if (builtins.contains(target)) {
                    System.out.println(target + " is a shell builtin");
                } else {
                    String path = findExecutable(target);

                    if (path != null) {
                        System.out.println(target + " is " + path);
                    } else {
                        System.out.println(target + ": not found");
                    }
                }

                continue;
            }

            // external executable
            String executable = findExecutable(command);

            if (executable != null) {
                List<String> cmd = new ArrayList<>();

                // IMPORTANT: use command name, not full path
                cmd.add(command);

                for (int i = 1; i < parts.length; i++) {
                    cmd.add(parts[i]);
                }

                ProcessBuilder pb = new ProcessBuilder(cmd);

                File exeFile = new File(executable);
                String parentDir = exeFile.getParent();

                Map<String, String> env = pb.environment();
                env.put(
                        "PATH",
                        parentDir + File.pathSeparator
                                + env.getOrDefault("PATH", "")
                );

                pb.inheritIO();

                Process process = pb.start();
                process.waitFor();

                continue;
            }

            System.out.println(command + ": command not found");
        }
    }
}