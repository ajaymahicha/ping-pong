package game.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by Praveen on 30-Apr-16.
 */
public class selectLevel extends JFrame {


    public selectLevel()
    {
        JPanel panel=new JPanel()
        {
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

        setContentPane(panel);

        panel.setLayout(new GridLayout(5,1,3,3));

        JPanel jPanel[] = new JPanel[5];
        for (int i = 0; i < 5; i++) {
            jPanel[i] = new JPanel();
            jPanel[i].setOpaque(false);
            panel.add(jPanel[i]);
        }

        JLabel title = new JLabel();
        title.setFont(new Font("Serif",Font.ITALIC,90));
        title.setForeground(Color.BLACK);
        title.setText("Select Level");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        jPanel[0].add(title);

        JButton easy = new JButton(){
            @Override
            public void paint( Graphics g ) {
                super.paint( g );
                ImageIcon multi = null;
                try {
                    multi = new ImageIcon(ImageIO.read(getClass().getResource("/game/Images/easy.png")));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                g.drawImage(multi.getImage(),  0 , 0 , getWidth() , getHeight() , null);
            }
        };
        easy.setPreferredSize(new Dimension(280,90));
        easy.setOpaque(false);
        easy.setContentAreaFilled(false);
        easy.setBorderPainted(false);
        jPanel[1].add(easy);


        JButton medium = new JButton(){
            @Override
            public void paint( Graphics g ) {
                super.paint( g );
                ImageIcon multi = null;
                try {
                    multi = new ImageIcon(ImageIO.read(getClass().getResource("/game/Images/medium.png")));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                g.drawImage(multi.getImage(),  0 , 0 , getWidth() , getHeight() , null);
            }
        };
        medium.setPreferredSize(new Dimension(280,90));
        medium.setOpaque(false);
        medium.setContentAreaFilled(false);
        medium.setBorderPainted(false);
        jPanel[2].add(medium);

        JButton hard = new JButton(){
            @Override
            public void paint( Graphics g ) {
                super.paint( g );
                ImageIcon multi = null;
                try {
                    multi = new ImageIcon(ImageIO.read(getClass().getResource("/game/Images/hard.png")));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                g.drawImage(multi.getImage(),  0 , 0 , getWidth() , getHeight() , null);
            }
        };
        hard.setPreferredSize(new Dimension(280,90));
        hard.setOpaque(false);
        hard.setContentAreaFilled(false);
        hard.setBorderPainted(false);
        jPanel[3].add(hard);

        JButton back = new JButton(){
            @Override
            public void paint( Graphics g ) {
                super.paint( g );
                ImageIcon multi = null;
                try {
                    multi = new ImageIcon(ImageIO.read(getClass().getResource("/game/Images/back.png")));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                g.drawImage(multi.getImage(),  0 , 0 , getWidth() , getHeight() , null);
            }
        };
        back.setPreferredSize(new Dimension(280,90));
        back.setOpaque(false);
        back.setContentAreaFilled(false);
        back.setBorderPainted(false);
        jPanel[4].add(back);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        //pack();
        setVisible(true);

        easy.addActionListener(easyListener);
        medium.addActionListener(mediumListener);
        hard.addActionListener(hardListener);
        back.addActionListener(backListener);

    }

    private ActionListener easyListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Main.speed = 10;
            startSwing();
        }
    };

    private ActionListener mediumListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Main.speed = 15;
            startSwing();
        }
    };

    private ActionListener hardListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Main.speed = 20;
            startSwing();
        }
    };

    private ActionListener backListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new Mainscreen();
        }
    };

    private void startSwing()
    {

        this.setVisible(false);
        new swing();
    }

//    public static void main(String[] args){
//        new selectLevel();
//    }
}
