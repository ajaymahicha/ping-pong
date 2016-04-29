package game.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Scanner;
import java.util.concurrent.Exchanger;

public class Map {
    private Scanner m;
    private String Map[]=new String[31];
String currentDirectory;
    private Image grass,
            wall;
    public Map()
    {   currentDirectory= System.getProperty("user.dir");
        ImageIcon img=new ImageIcon(currentDirectory+"\\src\\game\\Images\\grass.png");
        grass= img.getImage();
        img=new ImageIcon(currentDirectory+"\\src\\game\\Images\\wall.png");
        wall=img.getImage();
        openFile();
        readFile();
        closeFile();
    }
    public Image getGrass()
    {
        return grass;

    }
    public Image getwall()
    {
        return wall;

    }
    public String getMap(int x,int y)
    {
        String index=Map[y].substring(x,x+1);
        return  index;
    }
    public void  openFile(){
        try {
            m = new Scanner(new File(currentDirectory+"\\src\\game\\GUI\\Map.txt"));
        }
        catch(Exception e)
        {
            System.out.println("error in Map file");
        }
    }
    public void  readFile(){
        while(m.hasNext())
        {
            for(int i=0;i<31;i++)
            {
Map[i]=m.next();
            }
        }

    }
    public void  closeFile(){
        m.close();

    }
}
