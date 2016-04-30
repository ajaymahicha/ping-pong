package game.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.Exchanger;

public class Map {
    private Scanner m;
    private String Map[]=new String[31];
String currentDirectory;
    private InputStream in;
    BufferedReader bufferedReader;
    private Image grass,
            wall;
    public Map()
    {   currentDirectory= System.getProperty("user.dir");
        ImageIcon img= null;
        try {
            img = new ImageIcon(ImageIO.read(getClass().getResource("/game/Images/grass.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        grass= img.getImage();
        try {
            img=new ImageIcon(ImageIO.read(getClass().getResource("/game/Images/wall.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            File mapfile=new File(currentDirectory+"\\src\\game\\GUI\\Map.txt");

            in = getClass().getResourceAsStream("/game/GUI/Map.txt");
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            m = new Scanner(getClass().getResource("/game/GUI/Map.txt").getFile());
        }
        catch(Exception e)
        {
            System.out.println("error in Map file");
        }
    }

    public void  readFile(){
        String s;
        try {
            for(int i=0;i<31;i++) {
                s = bufferedReader.readLine();
                if (s == null)
                    break;
                Map[i]=s;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void  closeFile(){
        m.close();

    }
}
