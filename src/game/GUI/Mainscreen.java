package game.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
public class Mainscreen extends JFrame{
    JTextField name;
    public Mainscreen()
    {
        //JPanel gameName = new JPanel();
        JPanel panel1=new JPanel()
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

        panel1.setLayout(new GridLayout(7,1,3,3));

        JPanel[] panels = new JPanel[7];

        for(int i=0;i<7;i++)
        {
            panels[i]=new JPanel();
            panels[i].setOpaque(false);
            panel1.add(panels[i]);
        }


        JLabel label = new JLabel("Any fancy line here",SwingConstants.CENTER);
        label.setFont(new Font("Serif",Font.BOLD,100));
        label.setForeground(Color.BLUE);
        JPanel emptyPanel = new JPanel();
        emptyPanel.setOpaque(false);
        label.setVisible(true);
        panels[1].add(label);

        JPanel emptyPanel1 = new JPanel();
        emptyPanel1.setOpaque(false);

        panels[3].setLayout(new GridLayout(1,3,3,3));
        panels[3].add(emptyPanel);
        name=new JTextField();
        name.setFont(new Font("Serif",Font.BOLD,70));
        name.setText("Player");
        name.setOpaque(false);
        panels[3].add(name);
        Font font1 = new Font("Serif", Font.BOLD, 80);
        name.setFont(font1);
        panels[3].add(emptyPanel1);
        panels[5].setLayout(new GridLayout(1,5,3,3));
        JPanel emptyPanel2 = new JPanel();
        emptyPanel2.setOpaque(false);
        panels[5].add(emptyPanel2);
        JButton button1 = new JButton(){

            @Override
            public void paint( Graphics g ) {
                super.paint( g );
                ImageIcon single = null;
                try {
                    single = new ImageIcon(ImageIO.read(getClass().getResource("/game/Images/single.png")));
//                    single=new ImageIcon(ImageIO.read(getClass().getResource("Images/single.png")));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                g.drawImage(single.getImage(),  0 , 0 , getWidth() , getHeight() , null);
            }
        };
        button1.setOpaque(false);
        button1.setContentAreaFilled(false);
        button1.setBorderPainted(false);
        panels[5].add(button1);
        JPanel emptyPanel4 = new JPanel();
        emptyPanel4.setOpaque(false);
        panels[5].add(emptyPanel4);


        JButton button2 = new JButton(){
            @Override
            public void paint( Graphics g ) {
                super.paint( g );
                ImageIcon multi = null;
                try {
                    multi = new ImageIcon(ImageIO.read(getClass().getResource("/game/Images/multi.png")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                g.drawImage(multi.getImage(),  0 , 0 , getWidth() , getHeight() , null);
            }
        };
        button2.setOpaque(false);
        button2.setContentAreaFilled(false);
        button2.setBorderPainted(false);
        panels[5].add(button2);
        JPanel emptyPanel3 = new JPanel();
        emptyPanel3.setOpaque(false);
        panels[5].add(emptyPanel3);


        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(name.getText().equals(""))
                    JOptionPane.showMessageDialog(null,"Please Enter Your Name");
                else {

                    Main.playername=name.getText();
                    Main.list.add(new Player(0, null, 0, 2, false, name.getText()));

                    for(int i=Main.list.size();i<4;i++)
                        Main.list.add(new Player(Main.list.size(), null, 0, 2, true, "Bot "+i));

                    Main.isSinglePlayer=true;
                    dispose();
                    setVisible(false);
                    new selectLevel();
                }
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(name.getText().equals(""))
                    JOptionPane.showMessageDialog(null,"Please Enter Your Name");
                else {
                    dispose();
                    setVisible(false);
                    Main.playername=name.getText();
                    new Menu();
                }
            }
        });

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setContentPane(panel1);




        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


}
