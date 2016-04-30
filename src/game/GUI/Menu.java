package game.GUI;

import game.Networking.MessageListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Menu extends JFrame {

    JTextArea connection;
    JButton button;
    JButton button1;
    JButton button2;
    JButton button3;
    JTextArea msgbox;
    String currentDirectory;
    public static MessageListener messageListener;

    public static Menu gui;

    public Menu()
    {
        currentDirectory= System.getProperty("user.dir");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        JPanel jPanel=new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image image= null;
                try {
                    image = ImageIO.read(getClass().getResource("/game/Images/bg.jpg"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                g.drawImage(image, 0, 0,this.getWidth(),this.getHeight(), null);
            }
        };

        setContentPane(jPanel);

        jPanel.setLayout(new GridLayout(1,4,3,3));
        JPanel subSection[]=new JPanel[4];
        for(int i=0;i<subSection.length;i++)
        {
            subSection[i]=new JPanel();
            subSection[i].setOpaque(false);
            jPanel.add(subSection[i]);
        }

        //subSection 1
        setupsubSection1(subSection);


        //subSection 3
        setupsubSection3(subSection);
        gui = this;
    }

    private void setupsubSection1(JPanel[] subSection) {


        subSection[1].setLayout(new GridLayout(8,1,3,30));

        JPanel[] sections = new JPanel[8];
        for(int i=0;i<sections.length;i++)
        {
            sections[i]=new JPanel();
            sections[i].setOpaque(false);
            subSection[1].add(sections[i]);
        }
        //start button
        button = new customButton("/game/Images/start.png");
        sections[2].add(button);

        //create server button
        button1 = new customButton("/game/Images/createserver.png");
        sections[3].add(button1);

        //join server button
        button2 = new customButton("/game/Images/joinserver.png");
        sections[4].add(button2);

        //exit
        button3 = new customButton("/game/Images/exit.png");
        sections[5].add(button3);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);

                for(int i=Main.list.size();i<4;i++)
                    Main.list.add(new Player(Main.list.size(), null, 0, 2, true, "Bot "+i));

                swing sw=new swing();

                String message="STARTED "+Main.playerId;
                Main.list.get(Main.playerId).status=2;
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

        button1.addActionListener(startServer);
        button2.addActionListener(joinServer);
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }

    private void setupsubSection3(JPanel[] subSection){
        subSection[3].setLayout(new GridLayout(2,1,3,3));
        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(20,20,20,20),BorderFactory.createBevelBorder(1)));
                JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        subSection[3].add(top);
        subSection[3].add(bottom);

        top.setLayout(new GridBagLayout());
        GridBagConstraints c =new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx=0;
        c.gridy=0;
        JLabel connected = new JLabel("Connected");
        connected.setFont(new Font("Serif",Font.ITALIC,50));
        connected.setForeground(Color.blue);
        top.add(connected,c);

        c.gridx=0;
        c.gridy=1;


        connection = new JTextArea();
        connection.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(0),new EmptyBorder(10,10,10,10)));
      //todo wtf is this psr
        connection.setText("-> "+Main.playername);
        connection.setOpaque(false);
        connection.setEditable(false);
        connection.setFont(new Font("Serif",Font.BOLD,30));
        top.add(connection,c);
        //connection.setEditable;

        bottom.setLayout(new GridBagLayout());
        bottom.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(20,20,20,20),BorderFactory.createBevelBorder(1)));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx=0;
        c.gridy=0;
        JLabel Messages = new JLabel("Message");
        Messages.setFont(new Font("Serif",Font.ITALIC,50));
        Messages.setForeground(Color.blue);
        bottom.add(Messages,c);


        c.gridx=0;
        c.gridy=1;
        c.gridwidth=4;
        msgbox = new JTextArea();
        msgbox.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(0),new EmptyBorder(10,10,10,10)));
        msgbox.setText("");
        msgbox.setOpaque(false);
        msgbox.setEditable(false);
        msgbox.setForeground(Color.CYAN);
        msgbox.setFont(new Font("Serif",Font.BOLD,25));
        bottom.add(msgbox,c);

    }

    public void updateUsers(){
        String msg="";

        for(int i=0;i<Main.list.size();i++)
        {
            msg+="-> "+Main.list.get(i).getId()+":"+Main.list.get(i).name+"\n";
        }
        connection.setText(msg);
    }

    public void updateMessage(String s){
        String msg=msgbox.getText();

        msg+=s+"\n";
        msgbox.setText(msg);
    }

    ActionListener startServer = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            messageListener = new MessageListener(1234);
            messageListener.start();
            setTitle(Main.playerId+":"+Integer.toString(messageListener.serverSocket.getLocalPort()));

            try {
                Main.list.add(
                        new Player(Main.list.size(),
                                InetAddress.getLocalHost().getHostAddress(),
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
            try {
                updateMessage("Server started at "+InetAddress.getLocalHost().getHostAddress()+":"+messageListener.port);
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            }

            button1.setVisible(false);
            button2.setVisible(false);
        }
    };

    ActionListener joinServer = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            new enterHost();
            button1.setVisible(false);
            button2.setVisible(false);
        }
    };



}
