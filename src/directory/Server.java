package directory;

import java.io.*;
import java.net.ServerSocket;
import java.util.regex.Pattern;

public class Server {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(6868);

        while (true) {

            var socket = serverSocket.accept();

            var outputStream = socket.getOutputStream();
            var pw = new PrintWriter(outputStream, true);

            var inputStream = socket.getInputStream();
            var isr = new BufferedReader(new InputStreamReader(inputStream));

            System.out.println("The client was accepted");
            System.out.println("Processing...");

            String inputText;
            while (true) {
                inputText = isr.readLine();
                if (inputText.equals("exit")) {
                    pw.println("exit");
                    break;
                }
                String output = readFromPath(inputText);
                pw.println("The output: '"+ output +  "'The name of directory is: " + inputText +
                        "' write 'exit' if you wanna disconnect form server");
//                else pw.println("Your path does't match the unix file system path");
            }
        }
    }

    private static String readFromPath(String inputText) throws IOException {

        String regexp = "(/[\\w]+)+|/"; // the custom regexp to match only Unix-like paths
        String[] commandAndArgs = inputText.split(" ");
        String path = null;
        for (String val: commandAndArgs) {
            if (Pattern.matches(regexp, val))
                path = val;
        }
        if (path == null)
            return "You did not provide any path or write it in incorrect way";

        ProcessBuilder pb = new ProcessBuilder(commandAndArgs);
        pb.directory(new File(path));
        Process process;
        try {
            process = pb.start();
        } catch (IOException ex) {
            return "This path does't exist : '" + inputText;
        }

        BufferedReader brStdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = brStdout.readLine()) != null) {
            System.out.println("cycle: " + line);
            sb.append(line.trim()).append(" ;");
        }
        return sb.toString();

    }

}
