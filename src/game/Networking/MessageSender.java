package game.Networking;

import game.GUI.Main;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class MessageSender extends Thread {
    String message;
    public MessageSender(String message)
    {

        if(Main.count==100)
        {
            Main.count=0;
        }
        this.message=message+" "+Main.count;
        Main.count++;
        System.out.println("SENT: "+message+" "+Main.count);
    }

    @Override
    public void run()
    {
        for(int i=0;i<Main.list.size();i++)
        {
            try {
                if(!Main.list.get(i).isbot() && Main.list.get(i).getId() != Main.playerId) {
                    Socket s = new Socket(Main.list.get(i).getHost(), Main.list.get(i).getPort());

                    PrintStream out = new PrintStream(s.getOutputStream());
                    out.print(message + "\n");
                    s.close();
                }

            } catch (UnknownHostException e) {
                Main.list.remove(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
