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
    public showStatus(int i) {
        String s;
        System.out.println(System.getProperty("user.dir")+"\\src\\game\\Images\\mainmenu.png");
        if (i == 1)
            s = "YOU WIN";
        else
            s = "YOU LOSE";
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image image = null;
                try {
                    image = ImageIO.read(new File(System.getProperty("user.dir") + "\\src\\game\\Images\\bg.jpg"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
            }
        };

        panel=new JPanel();
        setContentPane(panel);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Label showing status
        JLabel status = new JLabel();
        status.setText(s);
        status.setFont(new Font("Serif",Font.BOLD,50));
        if(i==1)
            status.setForeground(Color.BLUE);
        else
            status.setForeground(Color.RED);

        //Main Menu Button
        JButton mainMenu = new JButton(){

            @Override
            public void paint( Graphics g ) {
                super.paint( g );
                ImageIcon single = null;
                try {
                    single = new ImageIcon(ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\game\\Images\\mainmenu.png")));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                g.drawImage(single.getImage(),  0 , 0 , getWidth() , getHeight() , null);
            }
        };

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
                    single = new ImageIcon(ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\game\\Images\\exit.png")));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                g.drawImage(single.getImage(),  0 , 0 , getWidth() , getHeight() , null);
            }
        };
        exit.setOpaque(false);
        exit.setContentAreaFilled(false);
        exit.setBorderPainted(false);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.anchor = GridBagConstraints.CENTER;
        c.gridx=0;
        c.gridy=0;
        c.gridwidth=2;
        panel.add(status,c);

        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy=4;
        c.gridwidth=1;
        panel.add(mainMenu,c);

        c.gridx=1;
        panel.add(exit,c);


        pack();

    }
        public static void main(String[] args)
        {
            new showStatus(1);
        }

}
