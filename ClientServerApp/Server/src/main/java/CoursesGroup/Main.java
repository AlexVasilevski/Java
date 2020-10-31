package CoursesGroup;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main( String[] args ) {
        try{
            // server socket creation
            ServerSocket EchoServerSocket = new ServerSocket(13);
            Socket Incoming = EchoServerSocket.accept();

            // server input output  streams creation
            InputStream InServerStream = Incoming.getInputStream();
            OutputStream OutServerStream = Incoming.getOutputStream();

            PrintWriter Write = new PrintWriter(OutServerStream, true);
            Scanner Read = new Scanner(InServerStream);


            boolean Connected = true;
            while(Connected && Read.hasNextLine()){
                String Line = Read.nextLine();

                // Connection termination
                if(Line.trim().equals("Bye")){
                    Connected = false;
                }

                // use EchoServer as proxy to get accurate time
                try{
                    Socket ProxySocket = new Socket();
                    ProxySocket.connect(new InetSocketAddress("time-A.timefreq.bldrdoc.gov", 13));

                    // create input from another server
                    InputStream ProxyStream = ProxySocket.getInputStream();
                    Scanner In = new Scanner(ProxyStream);
                    while(In.hasNextLine()){
                        Line += " [time: " + In.nextLine() + "]";
                    }
                }catch (IOException HandleException){
                    //TODO LOG exception
                }

                // send message
                Write.println("Echo: " + Line);

            }
        }
        catch (IOException HandleException){
            //TODO LOG exception
        }
    }
}
