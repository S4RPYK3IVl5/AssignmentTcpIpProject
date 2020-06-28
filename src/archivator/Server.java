package archivator;

import org.zeroturnaround.zip.ZipUtil;

import java.io.*;
import java.net.ServerSocket;
import java.util.Date;
import java.util.UUID;

public class Server {

    private static String rootPath = "/Users/asaprykin/opt/files/";

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
                var pathToZip = rootPath + UUID.randomUUID() + ".zip";
                ZipUtil.pack(new File(inputText), new File(pathToZip));

                File myFile = new File(pathToZip);
                byte [] mybytearray  = new byte [(int)myFile.length()];
                var fis = new FileInputStream(myFile);
                var bis = new BufferedInputStream(fis);
                bis.read(mybytearray, 0, mybytearray.length);
                System.out.println(new Date() + "Sending " + pathToZip + "(" + mybytearray.length + " bytes)");
                outputStream.write(mybytearray,0,mybytearray.length);
                outputStream.flush();
                System.out.println(new Date() + "Sending complited");

//                pw.println(inputText);
            } while (!inputText.equals("exit"));
            pw.println("exit");
        }
    }

}
