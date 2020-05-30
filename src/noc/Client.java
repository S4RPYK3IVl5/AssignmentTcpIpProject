package noc;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {

        var socket = new Socket("localhost", 6868);

        var outputStream = socket.getOutputStream();

        var pw = new PrintWriter(outputStream, true);
        var scanner = new Scanner(System.in);

        var inputStream = socket.getInputStream();
        var br = new BufferedReader(new InputStreamReader(inputStream));

        String res;
        do {
            System.out.print("Write your numbers by comma : ");
            String text = scanner.nextLine();
            pw.println(text);

            res = br.readLine();
            System.out.println(res);
        } while (!res.equals("exit"));

    }
}
