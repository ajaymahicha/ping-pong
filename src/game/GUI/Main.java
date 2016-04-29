package game.GUI;

import javafx.util.Pair;

import java.util.ArrayList;

public class Main {

    public static ArrayList<Player> list=new ArrayList<Player>();
    public static int playerId=0;
  public static  String playername="";
    public static int count=0;
    public static int previousCount=0;
    public static boolean isSinglePlayer=false;
    public static void main(String[] args)
    {
        new Mainscreen();

        //swing game = new swing();

    }
}
