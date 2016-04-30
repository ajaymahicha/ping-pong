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

/**
 * Created by Praveen on 29-Apr-16.
 */
public class showStatus extends JFrame {
    JPanel panel;
    JFrame _instance;
    public showStatus(int k) {
        String s;
        if (k == 1)
            s = "YOU WIN";
        else
            s = "YOU LOSE";
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image image = null;
                try {
                    image = ImageIO.read(getClass().getResource("/game/Images/bg.jpg"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
            }
        };

        setContentPane(panel);
        panel.setLayout(new GridLayout(2,1,3,3));

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jPanel1 = new JPanel();
        jPanel1.setOpaque(false);
        panel.add(jPanel1);

        JPanel jPanel2 = new JPanel();
        jPanel2.setOpaque(false);
        panel.add(jPanel2);
        jPanel2.setLayout(new GridLayout(1,2,3,3));


        //Label showing status
        JLabel status = new JLabel();
        status.setText(s);
        status.setFont(new Font("Serif",Font.BOLD,150));
        if(k==1)
            status.setForeground(Color.BLUE);
        else
            status.setForeground(Color.RED);
        jPanel1.add(status);


        //Main Menu Button
        JButton mainMenu = new JButton(){

            @Override
            public void paint( Graphics g ) {
                super.paint( g );
                ImageIcon single = null;
                try {
                    single = new ImageIcon(ImageIO.read(getClass().getResource("/game/Images/mainmenu.png")));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                g.drawImage(single.getImage(),  0 , 0 , getWidth() , getHeight() , null);
            }
        };
        mainMenu.setPreferredSize(new Dimension(280,90));
        mainMenu.setOpaque(false);
        mainMenu.setContentAreaFilled(false);
        mainMenu.setBorderPainted(false);


        //Exit Button
        JButton exit = new JButton(){

            @Override
            public void paint( Graphics g ) {
                super.paint( g );
                ImageIcon single = null;
                try {
                    single = new ImageIcon(ImageIO.read(getClass().getResource("/game/Images/exit.png")));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                g.drawImage(single.getImage(),  0 , 0 , getWidth() , getHeight() , null);
            }
        };
        exit.setPreferredSize(new Dimension(280,90));
        exit.setOpaque(false);
        exit.setContentAreaFilled(false);
        exit.setBorderPainted(false);

        JPanel jpanel21 =new JPanel();
        jpanel21.setOpaque(false);
        jPanel2.add(jpanel21);
        jpanel21.add(mainMenu);

        JPanel jpanel22 = new JPanel();
        jpanel22.setOpaque(false);
        jPanel2.add(jpanel22);
        jpanel22.add(exit);

        mainMenu.addActionListener(mainmenuListener);
        exit.addActionListener(exitListener);

        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _instance = this;
    }

    ActionListener mainmenuListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            _instance.dispose();
            if(!Main.isSinglePlayer)
            Menu.messageListener.interrupt();
            new Mainscreen();
        }
    };

    ActionListener exitListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!Main.isSinglePlayer)
            Menu.messageListener.interrupt();
            _instance.dispose();
            System.exit(0);
        }
    };

        public static void main(String[] args)
        {
            new showStatus(1);
        }

}
