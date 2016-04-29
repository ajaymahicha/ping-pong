package game.GUI;

import game.Networking.MessageListener;
import game.Networking.MessageSender;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class server extends JFrame{
    private JTextField host;
    private JTextField port;
    private JButton connectButton;
    private JButton startServerButton;
    private JButton startButton;
    private JTextArea message;
    private JPanel panel1;
    private JTextField playerName;

    public static server servergui;

    public server() {

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        server.servergui = this;
        setContentPane(panel1);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

        startServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MessageListener messageListener = new MessageListener(1234);
                messageListener.start();
                setTitle(Main.playerId+":"+Integer.toString(messageListener.serverSocket.getLocalPort()));

                try {
                    Main.list.add(
                            new Player(Main.list.size(),
                                    messageListener.serverSocket.getInetAddress().getLocalHost().getHostAddress(),
                                    messageListener.serverSocket.getLocalPort(),
                                    1,
                                    false,
                                    Main.playername
                            )
                    );
                } catch (UnknownHostException e1) {
                    e1.printStackTrace();
                }

                updateUsers();

                startServerButton.setVisible(false);
                connectButton.setVisible(false);
            }
        });

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MessageListener messageListener = new MessageListener();
                    messageListener.start();
                    setTitle(Integer.toString(messageListener.serverSocket.getLocalPort()));
                    Socket socket;
                    if(host.getText().equals("")) {
                        socket = new Socket(InetAddress.getByName("localhost"), 1234);
                        host.setText("localhost");
                        port.setText("1234");
                    }
                    else
                        socket= new Socket(InetAddress.getByName(host.getText()), Integer.parseInt(port.getText()));

                    PrintStream out = new PrintStream(socket.getOutputStream());
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


                    out.print("ADDME " + InetAddress.getLocalHost().getHostAddress() + " " + messageListener.port +" "+Main.playername+"\n");
                    System.out.println("SENT: "+"ADDME " + InetAddress.getLocalHost().getHostAddress() + " " + messageListener.port +" "+Main.playername+ "\n");
                    String listString = in.readLine();
                    String listarray[] = listString.split(";");

                    for (int i = 0; i < listarray.length; i++) {
                        String playerInfo[] = listarray[i].split("&");
                        int id = Integer.parseInt(playerInfo[0]);
                        String host = playerInfo[1];
                        int port = Integer.parseInt(playerInfo[2]);
                        int status = Integer.parseInt(playerInfo[3]);;     //0:NOT PLAYING 1:CONNECTED 2:PLAYING 3:DISCONNECTED
                        boolean isbot = Boolean.parseBoolean(playerInfo[4]);
                        String name = playerInfo[5];
                        Player p = new Player(id,host,port,status,isbot,name);
                        System.out.println("Player Added: "+p.toString());
                        Main.list.add(p);

                    }


                    Main.playerId=Main.list.size();

                    Main.list.add(new Player(Main.list.size(),host.getText(),Integer.parseInt(port.getText()),1,false,Main.playername));

                    updateUsers();
                    System.out.println("Added to list client side: " + host.getText() + ":" + port.getText());

                    setTitle(Main.playerId+":"+Integer.toString(messageListener.serverSocket.getLocalPort()));
                    socket.close();

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                swing sw=new swing();

                String message="STARTED "+Main.playerId;
                Main.list.get(Main.playerId).updateStatus(2);
                for(int i=0;i<Main.list.size();i++)
                {
                    try {
                        if(!Main.list.get(i).isbot() && Main.list.get(i).getId() != Main.playerId) {
                            Socket s = new Socket(Main.list.get(i).getHost(), Main.list.get(i).getPort());

                            PrintStream out = new PrintStream(s.getOutputStream());
                            out.print(message + "\n");
                            s.close();
                        }

                    } catch (UnknownHostException ex) {
                        Main.list.remove(i);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });
    }

    public void updateUsers(){
        String msg="";

        for(int i=0;i<Main.list.size();i++)
        {
            msg+=Main.list.get(i).getId()+":"+Main.list.get(i).name+"\n";
        }

        message.setText(msg);
    }


}
