package noc;

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
            do {
                inputText = isr.readLine();
                String[] numbersStr = inputText.split(",");
                Integer[] integersInt = {Integer.valueOf(numbersStr[0]), Integer.valueOf(numbersStr[1])};
                pw.println("The noc is: " + noc(integersInt[0], integersInt[1])
                        + " if you wanna to end with server, write 'exit'");
            } while (!inputText.equals("exit"));
            pw.println("exit");

        }

    }

    private static int noc(int x, int y){
        if(y == 0) return x;
        else return noc(y, x%y);
    }

}
