package CoursesGroup;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main( String[] args ) {

        try{
            // read user console input
            Scanner UserScanner = new Scanner(System.in);

            // socket, input, output creation
            Socket ClientSocket = new Socket();
            ClientSocket.connect(new InetSocketAddress("192.168.0.102", 13));
            if(ClientSocket.isConnected()){
                System.out.println("Connection established");
            }

            OutputStream Output = ClientSocket.getOutputStream();
            InputStream Input = ClientSocket.getInputStream();

            // sending InputString to EchoServer
            PrintWriter OutputWriter = new PrintWriter(Output, true);
            Scanner InputScanner = new Scanner(Input);

            while(true){

                String InputString = UserScanner.nextLine();
                OutputWriter.println(InputString);

                // reading EchoServer response
                while(InputScanner.hasNextLine()){
                    System.out.println(InputScanner.nextLine());
                }

            }

        }
        catch(IOException HandleException){
            //TODO LOG exception
        }
    }
}
