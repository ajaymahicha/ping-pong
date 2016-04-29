package game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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

public class swing {
    JFrame f;
    public static final float radius=(float)1.2;
    public swing()
    {

        f=new JFrame();
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setTitle("Swing");
        Board board=new Board();
       f.add(board);
        f.setSize(1015,1045);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void showFrame()
    {
        f.setVisible(true);
    }
    public void hideFrame()
    {
        f.setVisible(false);
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

}

