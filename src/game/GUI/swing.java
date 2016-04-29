package game.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

class velocity{
    public  float dx;
    public float dy;
}
class position{
    public float x;
    public float y;
}
class equation {
    public float a;
    public float b;
    public float c;
}
class velocities{
    velocity v_a;
    velocity v_b;
}

public class swing extends JFrame {
    public static final float radius=(float)1.2;
    private JLabel player[]= new JLabel[4];
    JPanel centerPanel = new JPanel();

    private String s="                  ";

    public static swing _instance;

    //UPdate Status
    public void updateCenter(int i)
    {
        String s;
        final int j=i;
        if(i==1)
            s="YOU WIN";
        else
            s="YOU LOSE";
        centerPanel.setLayout(new GridLayout(1,1,3,3));
        JButton status = new JButton();
        status.setText(s);
        status.setFont(new Font("Serif",Font.BOLD,50));
        if(i==1)
            status.setForeground(Color.GREEN);
        else
            status.setForeground(Color.RED);
        centerPanel.add(status);

        status.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new showStatus(j);
            }
        });
    }

    //To update score of player whose id is "a"
    public void updateScore(int a)
    {
        if(a%3==0)
            player[(4+a+Main.playerId)%4].setText(Main.list.get(a).getName()+":Left "+Integer.toString(Main.list.get((4+Main.playerId+a)%4).getLives())+s);
        else
            player[(4+a+Main.playerId)%4].setText(s+Main.list.get(a).getName()+": Lives Left "+Integer.toString(Main.list.get((4+Main.playerId+a)%4).getLives()));
    }

    public swing()
    {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Swing");
        JPanel panel=new Board(1000);
        setContentPane(panel);
        panel.setLayout(new BorderLayout(3,3));
        for(int i=0;i<4;i++)
        {
            //player[i] = new JLabel(Main.list.get((4+Main.playerId+i)%4).getName());
            if(i%3==0)
                player[i] = new JLabel(Main.list.get((4+Main.playerId+i)%4).getName()+": Lives Left "+Integer.toString(Main.list.get((4+Main.playerId+i)%4).Lives)+s);
            else
                player[i] = new JLabel(s+Main.list.get((4+Main.playerId+i)%4).getName()+": Lives Left "+Integer.toString(Main.list.get((4+Main.playerId+i)%4).Lives));

            player[i].setFont(new Font("Serif",Font.BOLD,28));

        }

        panel.add(player[0], BorderLayout.SOUTH);
        panel.add(player[1], BorderLayout.WEST);
        panel.add(player[2], BorderLayout.NORTH);
        panel.add(player[3], BorderLayout.EAST);




        panel.add(centerPanel,BorderLayout.CENTER);


        player[0].setHorizontalAlignment(JTextField.RIGHT);
        player[1].setHorizontalAlignment(JTextField.RIGHT);
//        JLabel player0 = new JLabel("Player 0"+s);
//        JLabel player1 = new JLabel(s+"Player 1");
//        JLabel player2 = new JLabel(s+"Player 2");
//        JLabel player3 = new JLabel("Player 3"+s);
//
//        panel.add(player0, BorderLayout.SOUTH);
//        panel.add(player1, BorderLayout.WEST);
//        panel.add(player2, BorderLayout.NORTH);
//        panel.add(player3, BorderLayout.EAST);
//
//        player0.setFont(new Font("Serif",Font.BOLD,28));
//        player1.setFont(new Font("Serif",Font.BOLD,28));
//        player2.setFont(new Font("Serif",Font.BOLD,28));
//        player3.setFont(new Font("Serif",Font.BOLD,28));
//
//        JPanel centerPanel = new JPanel();
//
//        panel.add(centerPanel,BorderLayout.CENTER);
//
//        player0.setHorizontalAlignment(JTextField.RIGHT);
//        player1.setHorizontalAlignment(JTextField.RIGHT);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _instance=this;
    }
    public void showFrame()
    {
        setVisible(true);
    }
    public void hideFrame()
    {
        setVisible(false);
    }
    public boolean Is_near_wall(position p,equation e)
    {
        if((Math.abs(e.a*p.x+e.b*p.y+e.c)/Math.sqrt(e.a*e.a+e.b*e.b))<=radius)
        {
            return true;
        }
        return false;
    }
    public boolean IS_near_ball(position p1 ,position p2)
    {
        if(Math.sqrt(((p1.x-p2.x)*(p1.x-p2.x))+((p1.y-p2.y)*(p1.y-p2.y)))<=2*radius)
        {
            return true;
        }
        return false;
    }
    public velocity coll_wall(position p,velocity v,equation e)
    {

        //collision with horizontal wall
        if(e.a==0)
        {
            v.dy=-v.dy;

        }
        // collision with vertical wall
        if(e.b==0)
        {
            v.dx=-v.dx;
        }
        return v;
    }
    public velocities coll_ball(velocities v)
    {
        velocity temp =new velocity();
        temp=v.v_a;
        v.v_a=v.v_b;
        v.v_b=temp;
        return v;
    }

    public static void main(String[] args)
    {
        new swing();
    }
}

