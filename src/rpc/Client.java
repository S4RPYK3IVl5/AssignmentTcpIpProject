package rpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
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
            System.out.print("Write your command: ");
            String text = scanner.nextLine();
            pw.println(text);

            res = br.readLine();
            String[] splitedRes= res.split(";");
            Arrays.asList(splitedRes).forEach(System.out::println);
        } while (!res.equals("exit"));

    }
}
