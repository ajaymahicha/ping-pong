package game.GUI;

import javax.swing.*;
import java.awt.*;

public class Bar {
    private int tileX,tileY;
    private boolean horizontalControl ;
    private int playerId ;
public boolean freezed=false;
    public static int count=0;

    public Bar()
    {
        int id = Bar.count;
        playerId = id;
        Bar.count++;
        if(playerId == Main.playerId)
        {
            horizontalControl=true;
            initialise(400,1000-Board.ballRadius);
        }
        else
        {
            if( id-Main.playerId == 1 || id-Main.playerId == -3)
                initialise(0,400);
            if( id-Main.playerId == 2 || id-Main.playerId == -2)
                initialise(400,0);
            if( id-Main.playerId == 3 || id-Main.playerId == -1)
                initialise(1000-Board.ballRadius,400);
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
        switch ((4-Main.playerId + playerId)%4)
        {
            case 0:tileX=pos; break;
            case 3:tileY=1000-pos-10*Board.ballRadius;break;
            case 2:tileX=1000-pos-10*Board.ballRadius;break;
            case 1:tileY=pos;break;
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
        tileX+=dx;
        tileY+=dy;
    }
}

