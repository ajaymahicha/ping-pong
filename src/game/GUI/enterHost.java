package game.GUI;

import game.Networking.MessageListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class enterHost extends JFrame {
    private JPanel panel,panel1,panel2;
    private JTextField textField1;
    private JTextField textField2;
    private JButton button1;
    JTextField host;
    JLabel PORT;
    JTextField port;
    public enterHost()
    {
        panel = new JPanel(){
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

        ;
        setContentPane(panel);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(new GridLayout(2,1,3,3));

        panel1=new JPanel();
        panel2=new JPanel();
        panel.add(panel1);
        panel.add(panel2);

        panel1.setLayout(new FlowLayout());
        JLabel HOST = new JLabel("HOST");
        HOST.setFont(new Font("Serif",Font.PLAIN,45));
      host = new JTextField();
        host.setPreferredSize(new Dimension(330,80));
        host.setFont(new Font("Serif",Font.PLAIN,45));
        PORT=new JLabel("PORT");
        PORT.setFont(new Font("Serif",Font.PLAIN,45));
        port=new JTextField();
        port.setPreferredSize(new Dimension(150,80));
        port.setFont(new Font("Serif",Font.PLAIN,45));
        panel1.setOpaque(false);
        panel1.add(HOST);
        panel1.add(host);
        panel1.add(PORT);
        panel1.add(port);

        JButton button = new JButton("JOIN"){
            @Override
            public void paint( Graphics g ) {
                super.paint( g );
                ImageIcon multi = null;
                try {
                    multi = new ImageIcon(ImageIO.read(getClass().getResource("/game/Images/join.png")));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                g.drawImage(multi.getImage(),  0 , 0 , getWidth() , getHeight() , null);
            }
        };
        button.setPreferredSize(new Dimension(240,80));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        button.addActionListener(new ActionListener() {
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


                    out.print("ADDME " + InetAddress.getLocalHost().getHostAddress() + " " + messageListener.port +" "+ Main.playername+"\n");
                    System.out.println("SENT: "+"ADDME " + InetAddress.getLocalHost().getHostAddress() + " " + messageListener.port +" "+ Main.playername+ "\n");
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

                    Menu.gui.updateUsers();
                    System.out.println("Added to list client side: " + host.getText() + ":" + port.getText());

                    setTitle(Main.playerId+":"+Integer.toString(messageListener.serverSocket.getLocalPort()));
                    socket.close();

                    setVisible(false);

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        panel2.add(button);
        panel2.setOpaque(false);

        pack();
    }
}
