package archivator;

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

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("YOUR_FILE.zip"));

        int c=0;
        byte[] buff=new byte[6022386];

        String res;
        do {
            System.out.print("Write your directory path: ");
            String text = scanner.nextLine();
            pw.println(text);

            while((c=inputStream.read(buff))>0){
                bos.write(buff, 0, c);
            }
            bos.close();

//            res = br.readLine();
//            System.out.println(res);
//        } while (!res.equals("exit"));
        } while (true);

    }
}
