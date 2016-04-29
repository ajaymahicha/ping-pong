package game.GUI;

import javax.swing.*;
import java.awt.*;

public class Bar {
   private int tileX,tileY;
    private boolean horizontalControl ;
    private int playerId ;
    private Image bar;

    public static int count=0;

    public Bar()
    {
        int id = Bar.count;
        //System.out.println("Created bar "+count);
        playerId = id;
        Bar.count++;

        if(playerId == Main.playerId)
        {
            horizontalControl=true;
            initialise(40,99);
        }
        else
        {
            if( id-Main.playerId == 1 || id-Main.playerId == -3)
                initialise(2,40);
            if( id-Main.playerId == 2 || id-Main.playerId == -2)
                initialise(40,2);
            if( id-Main.playerId == 3 || id-Main.playerId == -1)
                initialise(99,40);
        }
    }
    public void initialise(int a,int b)
    {  //TODO
         tileX=a;
        tileY=b;

//        x=a;
//        y=b;
    }

    public int gettileX()
    {
        return tileX;
    }
    public int gettileY()
    {
        return tileY;
    }

    public void setPos(int pos)
    {
        switch ((4+Main.playerId - playerId)%4)
        {
            case 0:tileX=pos; break;
            case 1:tileY=83-pos;break;
            case 2:tileX=83-pos;break;
            case 3:tileY=pos;break;
        }
    }

    public int getPos()
    {
        if(Math.abs(Main.playerId - playerId)%2==0)
            return tileX;
        else
            return tileY;
    }

    public void move(int dx,int dy,int tx,int ty)
    {
//        x+=dx;
//        y+=dy;
        tileX+=dx;
        tileY+=dy;
       // System.out.println(x+" "+y+" "+tileX+" "+tileY);
    }
}

