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

        ;
        setContentPane(panel);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(new GridLayout(1, 1, 3, 3));


        JButton status = new JButton();
        status.setFont(new Font("Serif", Font.BOLD, 40));

        if (i == 1)
            status.setForeground(Color.GREEN);
        else
            status.setForeground(Color.RED);

        status.setText(s);

        status.setPreferredSize(new Dimension(280,90));
        status.setOpaque(false);
        status.setContentAreaFilled(false);
        status.setBorderPainted(false);

        panel.add(status);

        status.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swing._instance.setVisible(false);
                if(!Main.isSinglePlayer)
                    Menu.messageListener.interrupt();
                new Mainscreen();
            }
        });
        pack();

    }
        public static void main(String[] args)
        {
            new showStatus(1);
        }

}
