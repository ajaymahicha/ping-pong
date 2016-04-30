package game.Networking;

import game.GUI.Main;

import javax.net.ssl.SSLServerSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
//TODO Implement Security
public class MessageListener extends Thread {
    public int port;
    public ServerSocket serverSocket;
    public MessageListener()
    {
        Main.playerId=0;
        try {
            serverSocket = new ServerSocket(0);
            this.port = serverSocket.getLocalPort();
            System.out.println("Server Started "+ InetAddress.getLocalHost().getHostAddress()+":"+serverSocket.getLocalPort());
        } catch (IOException e) {
            System.err.println("no available ports");
        }
    }
    public MessageListener(int p)
    {
        try {
            serverSocket = new ServerSocket(p);
            this.port = serverSocket.getLocalPort();
            System.out.println("Server Started "+InetAddress.getLocalHost().getHostAddress()+":"+serverSocket.getLocalPort());
        } catch (IOException e) {
            System.err.println("no available ports");
        }
    }

    //TODO: edit interrupt
    @Override
    public void interrupt()
    {
        try {
            serverSocket.close();
            System.out.println("Server closed");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            super.interrupt();
        }
    }

    @Override
    public void run()
    {
        Socket socket;

        try {
            while(true)
            {
                socket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintStream out =new PrintStream(socket.getOutputStream());
                String data=in.readLine();
                System.out.println("RECIEVED: "+data);
                new Handler(data,out).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
