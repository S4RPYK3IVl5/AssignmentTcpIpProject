package directory;

import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Server {

    private static List<String> fileList = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(6868);
        String regexp = "(/[\\w]+)+|/";

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
                if (Pattern.matches(regexp, inputText)) {
                    String output = readFromPath(inputText);
                    pw.println("The output: '"+ output +  "'The name of directory is: " + inputText +
                            "' write 'exit' if you wanna disconnect form server" + " " + Pattern.matches(regexp, inputText));
                }
                else pw.println("Your path does't match the unix file system path");
            }
        }
    }

    private static String readFromPath(String inputText) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("ls");
        pb.directory(new File(inputText));
        Process process;
        try {
            process = pb.start();
        } catch (IOException ex) {
            return "This path does't exist : " + inputText;
        }
        BufferedReader brStdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = brStdout.readLine()) != null) {
            System.out.println("cycle: " + line);
            sb.append(line.trim()).append(" ");
        }
        return sb.toString();
    }

}
