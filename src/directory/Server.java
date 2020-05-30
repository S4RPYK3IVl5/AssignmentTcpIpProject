package directory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

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
            inputText = isr.readLine();
            if (inputText.equals("exit")){ pw.println("exit"); break; }
            else pw.println("The name of directory is: " + inputText +
                    "\nwrite 'exit' if you wanna disconnect form server");
        }

    }

}
