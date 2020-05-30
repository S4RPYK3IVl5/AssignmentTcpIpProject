package rpc;

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
                pw.println(output);
//                else pw.println("Your path does't match the unix file system path");
            }
        }
    }

    private static String readFromPath(String inputText) throws IOException {

        String[] commandAndArgs = inputText.split(" ");

        ProcessBuilder pb = new ProcessBuilder(commandAndArgs);
        Process process;
        try {
            process = pb.start();
        } catch (IOException ex) {
            return "The error during execution command: '" + inputText;
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
