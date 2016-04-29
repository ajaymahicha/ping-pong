package game.GUI;

import game.Networking.MessageSender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Board  extends JPanel implements ActionListener{
    private Timer timer;
    private Map m;
    private Bar bar;
    private Bar bars[]=new Bar[4];;
    private Bar bar1;
    private Bar bar2;
    private Bar bar3;
    private Bar bar4;
    private int Size;
    private int ballRadius = 16;  //Radius

    private int ballX = 400; //X position
    private int ballY = 400; //Y position

    private int ballVelocityX = 13;  //X velocity
    private int ballVelocityY = 15;  //Y velocity

    public static Board board;
    String currentDirectory;
    // private Timer timer; //timer associated with the ball
    // private int timerUpdate = 25;

    private Color ballColor; //color

    public Board()
    {
        this(1000);
    }
    public Board(int s)
    {
        Size=s;
        currentDirectory= System.getProperty("user.dir");
        setSize(s,s);
        int a=ballRadius*10;
        int b=ballRadius*10;
        int c=-13;
        int d=15;
        m=new Map();
        //-\- Create components
        switch(Main.playerId)
        {
            case 0:
                ballX = a;
                ballY = b;
                ballVelocityX = c;
                ballVelocityY = d;
                break;
            case 3:
                ballX = Size-b;
                ballY = a;
                ballVelocityX = -d;
                ballVelocityY = c;
                break;
            case 2:
                ballX = Size-a;
                ballY = Size-b;
                ballVelocityX = -c;
                ballVelocityY = -d;
                break;
            case 1:
                ballX = b;
                ballY = Size-a;
                ballVelocityX = d;
                ballVelocityY = -c;
                break;

        }
        //System.out.println("Board Created");

        for(int i=0;i<4;i++)
            bars[i]=new Bar();

        bar1=bars[(Main.playerId+2)%4];     //Top
        bar2=bars[Main.playerId];            //Mainplayer Bar bottom
        bar3=bars[(Main.playerId+1)%4];      //Left
        bar4=bars[(Main.playerId+3)%4];      //right

        bar=bars[Main.playerId];
        ballColor = Color.black; //set color of the ball
        addMouseListener(new MI());
        addKeyListener(new AI());
        setFocusable(true);
        timer =new Timer(25,this);
        timer.start();
        Board.board=this;


    }

    public void setBarPos(int id,int pos)
    {
        bars[id].setPos(pos);
    }

    public boolean All_connected()
    {
        for(int i=0;i<Main.list.size();i++)
        {
            if(Main.list.get(i).getStatus()!=2)
            {
                return false;
            }
        }
        return true;
    }

    public int compu(Bar b,int a)
    {
        switch(a) {
            case 1:  if (ballVelocityX < 0) {
                int d=(ballX-ballRadius)*(ballVelocityY)/Math.abs(ballVelocityX)+ballY;

                if(d > 3 * 10 && d < 97 * 10) {
                    int mid =b.gettileY()*10+5*ballRadius;
                    if(d>b.gettileY()*10&&d<b.gettileY()*10+10*ballRadius)
                    {
                        return 0;
                    }
                    if (mid-d>0&&b.gettileY()>3) {
                        //   System.out.println(d + " 1 move up " + b.gettileY() * 10);

                        //       new MessageSender("COMPUTER " + a + " " + bars[Main.playerId].getPos()).start();
                        return -1;
                    } else if(d-mid>0&&b.gettileY()<83){

                        //  System.out.println(d + " 1 move down " + b.gettileY()*10);
                        return 1;
                    }

                }


            }
                break;
            case 2:
                if(ballVelocityY<0)
                {
                    int d=(ballY-ballRadius)*(ballVelocityX)/Math.abs(ballVelocityY)+ballX;

                    if (d>3*10&&d<97*10) {
                        if(d>b.gettileX()*10&&d<b.gettileX()*10+10*ballRadius)
                        {
                            return 0;
                        }
                        int mid =b.gettileX()*10+ballRadius*5;
                        if (mid-d>0&&b.gettileX()>3) {
                            return -1;

                        }
                        else if(d-mid>0&&b.gettileX()<83) {
                            return 1;

                        }
                    }
                }

                break;
            case 3:
                if (ballVelocityX >0) {
                    int d=(((1000-ballX-ballRadius)*(ballVelocityY))/(ballVelocityX))+ballY;
                    // System.out.println(d+" "+"case3 "+ b.gettileY()*10+" "+ballX+" "+ballY+" "+ballVelocityX+" "+ballVelocityY);
                    if (d>0&&d<1000) {
                        if(d>b.gettileY()*10&&d<b.gettileY()*10+10*ballRadius)
                        {
                            return 0;
                        }
                        int mid=b.gettileY()*10+ballRadius*5;

                        if (mid-d>0&&b.gettileY()>3) {
                            return -1;

                        }
                        else if(d-mid>0&&b.gettileY()<83)
                        {   return 1;
                        }

                    }



                }

                break;

        }

        return 0;
    }

    public void actionPerformed(ActionEvent e)
    {
        int width = Size;
        int height = Size;


        int k=0;
        for(int i=0;i<4;i++)
        {
            if(i!=Main.playerId)
            if(Main.list.get(i).isAlive)
                k++;
        }

        if(k==0)
            swing._instance.updateCenter(1);

        //Updating X and Y Coordinates
        if(Main.list.get((4+Main.playerId+2)%4).isbot())
            bar1.move(2*compu(bar1,2),0,0,0);
        if(Main.list.get((4+Main.playerId+1)%4).isbot())
            bar3.move(0, 2*compu(bar3,1),0,0);
        if(Main.list.get((4+Main.playerId+3)%4).isbot())
            bar4.move( 0,2*compu(bar4,3),0,0);
        if(All_connected()) { //the perception of X-direction motion
          ballX += ballVelocityX;

            // Left Wall
            if (ballX < ballRadius) {
                ballX = ballRadius; //reset the position
                ballVelocityX = -ballVelocityX; //reverse the velocity
                if(Main.list.get((4+Main.playerId+1)%4).isbot() && Main.list.get((4+Main.playerId+1)%4).isAlive) {
                    Main.list.get((4 + Main.playerId + 1) % 4).Lives -= 1;
                    swing._instance.updateScore((4+Main.playerId+1)%4);

                    if(Main.list.get((4+Main.playerId+1)%4).Lives==0) {
                        Main.list.get((4+Main.playerId+1)%4).isAlive=false;
                    }

                }
                //   new MessageSender("BALL "+ballX+" "+ballY+" "+ballVelocityX+" "+ballVelocityY).start();
            }
            //Right Wall
            if (ballX > width - ballRadius) {
                ballX = width - ballRadius; //reset the position
                System.out.println("case3 "+ballX+" "+ballY+" "+ballVelocityX+" "+ballVelocityY);

                ballVelocityX = -ballVelocityX; //reverse the velocity

                if(Main.list.get((4+Main.playerId+3)%4).isbot() && Main.list.get((4+Main.playerId+3)%4).isAlive) {
                    Main.list.get((4 + Main.playerId + 3) % 4).Lives -= 1;
                    swing._instance.updateScore((4 + Main.playerId + 3) % 4);

                    if(Main.list.get((4+Main.playerId+3)%4).Lives==0) {
                        Main.list.get((4+Main.playerId+3)%4).isAlive=false;
                    }

                }
                //   System.out.println("right");
                //  new MessageSender("BALL "+ballX+" "+ballY+" "+ballVelocityX+" "+ballVelocityY).start();

            }

            ballY += ballVelocityY; //perception of Y-direction Motion
            // System.out.println(ballX+" "+ballY);
            // Top Wall
            if (ballY < ballRadius) {
                ballY = ballRadius;//reset the position
                ballVelocityY = -ballVelocityY; //reverse the velocity
                if(Main.list.get((4+Main.playerId+2)%4).isbot() && Main.list.get((4+Main.playerId+2)%4).isAlive){
                    Main.list.get((4+Main.playerId+2)%4).Lives-=1;
                    swing._instance.updateScore((4 + Main.playerId + 2) % 4);

                    if(Main.list.get((4+Main.playerId+2)%4).Lives==0) {
                        Main.list.get((4+Main.playerId+2)%4).isAlive=false;
                    }

                }
                //  System.out.println("top");
                //  new MessageSender("BALL "+ballX+" "+ballY+" "+ballVelocityX+" "+ballVelocityY).start();

            }

            //Bottom Wall
            if (ballY > height - ballRadius) {
                ballY = height - ballRadius;//reset the position
                ballVelocityY = -ballVelocityY;//reverse the velocity
                if(Main.list.get((4+Main.playerId)%4).isAlive) {
                    Main.list.get(Main.playerId).Lives -= 1;
                    System.out.println("Score:" + Main.list.get(Main.playerId).Lives);
                    new MessageSender("SCORE " + Main.playerId + " " + Main.list.get(Main.playerId % 4).Lives).start();
                    swing._instance.updateScore(Main.playerId);

                    if (Main.list.get(Main.playerId).Lives == 0) {
                        Main.list.get(Main.playerId).isAlive = false;
                        swing._instance.updateCenter(0);
                    }
                }
            }


            //Math.abs(ballX- bar1.gettileX()*10)<ballRadius*2||
            //bar1
            if (ballY < bar1.gettileY() * 10 + ballRadius && ballX - bar1.gettileX() * 10 < ballRadius * 10 && ballX - bar1.gettileX() * 10 > 0 && Main.list.get((4+Main.playerId+2)%4).isbot()) {
                ballY = bar1.gettileY() * 10 + ballRadius;
                ballVelocityY = -ballVelocityY;//reverse the velocity
            }
            //bar2
            if ((ballY > bar2.gettileY() * 10 - ballRadius*2  && ballX - bar2.gettileX() * 10 < ballRadius * 10 && ballX - bar2.gettileX() * 10 > 0)) {
//            System.out.println(ballX+" "+ballY+" "+bar2.gettileX()+" "+bar2.gettileY());
//            System.out.println("Boindo");
                ballY = bar2.gettileY() * 10 - ballRadius*2  ;//reset the position
                ballVelocityY = -ballVelocityY;//reverse the velocity
                new MessageSender("BALL "+ballX+" "+ballY+" "+ballVelocityX+" "+ballVelocityY+" "+Main.playerId).start();
            }
            //bar3
            if (ballX < bar3.gettileX() * 10 + ballRadius && ballY - bar3.gettileY() * 10 < ballRadius * 10 && ballY - bar3.gettileY() * 10 > 0 && Main.list.get((4+Main.playerId+1)%4).isbot()) {
                ballX = bar3.gettileX() * 10 + ballRadius; //reset the position
                ballVelocityX = -ballVelocityX; //reverse the velocity
            }
            //bar4
            if (ballX > bar4.gettileX() * 10 - ballRadius*2  && ballY - bar4.gettileY() * 10 < ballRadius * 10 && ballY - bar4.gettileY() * 10 > 0 && Main.list.get((4+Main.playerId+3)%4).isbot()) {
                ballX = bar4.gettileX() * 10 - ballRadius*2 ; //reset the position
                ballVelocityX = -ballVelocityX; //reverse the velocity
            }
        }


        repaint();
    }
    public void setBallPos(int a,int b,int c,int d,int e)
    {
        switch ((4+Main.playerId - e)%4) {
            case 0:
                ballX = a;
                ballY = b;
                ballVelocityX = c;
                ballVelocityY = d;
                break;
            case 3:
                ballX = Size-b;
                ballY = a;
                ballVelocityX = -d;
                ballVelocityY = c;
                break;
            case 2:
                ballX = Size-a;
                ballY = Size-b;
                ballVelocityX = -c;
                ballVelocityY = -d;
                break;
            case 1:
                ballX = b;
                ballY = Size-a;
                ballVelocityX = d;
                ballVelocityY = -c;
                break;
        }
    }


    public void paint(Graphics g) {
        super.paint(g);

        for(int y=0;y<31;y++)
        {
            for(int x=0;x<31;x++)
            {
                g.drawImage(m.getGrass(),(x+15)*32,(y)*32,null);
            }
        }
        g.setColor(ballColor);
        g.fillRect(0+(15*32),0,ballRadius,ballRadius);
        g.fillRect(980+(15*32),980,ballRadius,ballRadius);
        g.fillRect(0+(15*32),980,ballRadius,ballRadius);
        g.fillRect(980+(15*32),0,ballRadius,ballRadius);
        g.fillOval(ballX - ballRadius+(15*32), ballY - ballRadius, ballRadius*2, ballRadius*2);
        g.setColor(Color.magenta);

      //  if(Main.list.get(Main.playerId).isAlive)
            g.fillRoundRect(bar2.gettileX()*10 - ballRadius+(15*32), bar2.gettileY()*10 - ballRadius, ballRadius*10, ballRadius,20,20);
       // if(Main.list.get((Main.playerId+4+2)%4).isAlive)
            g.fillRoundRect(bar1.gettileX()*10 - ballRadius+(15*32), bar1.gettileY()*10 - ballRadius, ballRadius*10, ballRadius,20,20);
      //  if(Main.list.get((Main.playerId+4+1)%4).isAlive)
            g.fillRoundRect(bar3.gettileX()*10 - ballRadius+(15*32), bar3.gettileY()*10 - ballRadius, ballRadius, ballRadius*10,20,20);
      //  if(Main.list.get((Main.playerId+4+3)%4).isAlive)
            g.fillRoundRect(bar4.gettileX()*10 - ballRadius+(15*32), bar4.gettileY()*10 - ballRadius, ballRadius, ballRadius*10,20,20);


//todo:bar size ,sync bar size
//        g.drawImage(bar1.getBar(),bar1.gettileX()*10,bar1.gettileY()*10,null);
//        g.drawImage(bar2.getBar(),bar2.gettileX()*32,bar2.gettileY()*32,null);
//        g.drawImage(bar3.getBar(),bar3.gettileX()*32,bar3.gettileY()*32,null);
//        g.drawImage(bar4.getBar(),bar4.gettileX()*32,bar4.gettileY()*32,null);
    }
    public class MI extends MouseAdapter{
        @Override
        public void mouseMoved(MouseEvent e) {
            super.mouseMoved(e);
            int x=e.getX();
            if(x>3&&x<83)
            {
                bar.setPos(x);
            }
            else if(x<3)
            {
                bar.setPos(3);
            }
            else
                bar.setPos(83);

            new MessageSender("PLAYER "+Main.playerId+" "+bars[Main.playerId].getPos()).start();

        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            super.mouseWheelMoved(e);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
        }

    }
    public class AI extends KeyAdapter{
        public  void keyPressed(KeyEvent e)
        {
            int KeyCode=e.getKeyCode();
            if(KeyCode==KeyEvent.VK_1)
            {
                bar=bar1;
            }
            if(KeyCode==KeyEvent.VK_2)
            {
                bar=bar2;
            }
            if(KeyCode==KeyEvent.VK_3)
            {
                bar=bar3;
            }
            if(KeyCode==KeyEvent.VK_4)
            {
                bar=bar4;
            }
            if(KeyCode==KeyEvent.VK_L)
            {
                ballY += ballVelocityY;
            }
            if(KeyCode==KeyEvent.VK_O)
            {
                ballX += ballVelocityX;
            }
            if(KeyCode==KeyEvent.VK_K)
            {
                ballX -= ballVelocityX;
            }
            if(KeyCode==KeyEvent.VK_J)
            {
                ballY -= ballVelocityY;
            }
            if(bar==bar4||bar==bar3) {
                if(bar.gettileY()>3) {
                    if (KeyCode == KeyEvent.VK_W) {
                        bar.move(0, -1, 0, -1);
                    }
                }
                if(bar.gettileY()<83) {
                    if (KeyCode == KeyEvent.VK_S) {
                        bar.move(0, 1, 0, 1);
                    }
                }

//                if (KeyCode == KeyEvent.VK_A) {
//                    bar.move(-1, 0, -1, 0);
//                }
//                if (KeyCode == KeyEvent.VK_D) {
//                    bar.move(1, 0, 3, 0);
//                }
            }
            if(bar==bar1||bar==bar2) {
//                if (KeyCode == KeyEvent.VK_W) {
//                    bar.move(0, -1, 0, -1);
//                }
//                if (KeyCode == KeyEvent.VK_S) {
//                    bar.move(0, 1, 0, 1);
//
//                }
                if(bar.gettileX()>3) {
                    if (KeyCode == KeyEvent.VK_A) {
                        bar.move(-1, 0, -1, 0);
                        new MessageSender("PLAYER "+Main.playerId+" "+bars[Main.playerId].getPos()).start();
                    }
                }
                if(bar.gettileX()<83) {
                    if (KeyCode == KeyEvent.VK_D) {
                        bar.move(1, 0, 3, 0);
                        new MessageSender("PLAYER "+Main.playerId+" "+bars[Main.playerId].getPos()).start();
                    }
                }

            }
            System.out.println(bar.gettileX()+" "+bar.gettileY()+" "+ballX+" "+ballY);

        }

        public  void keyReleased(KeyEvent e)
        {

        }
        public  void keyTyped(KeyEvent e)
        {
            int KeyCode=e.getKeyCode();
//            if(KeyCode==KeyEvent.VK_1)
//            {
//
//               // System.out.println("cklci");
//                bar=bar1;
//            }
//            if(KeyCode==KeyEvent.VK_2)
//            {
//                bar=bar2;
//            }
//            if(KeyCode==KeyEvent.VK_3)
//            {
//                bar=bar3;
//            }
//            if(KeyCode==KeyEvent.VK_4)
//            {
//                bar=bar4;
//            }
            if(bar==bar4||bar==bar3) {
                if(bar.gettileY()>3) {
                    if (KeyCode == KeyEvent.VK_W) {
                        bar.move(0, -1, 0, -1);
                    }
                }
                if(bar.gettileY()<83) {
                    if (KeyCode == KeyEvent.VK_S) {
                        bar.move(0, 1, 0, 1);
                    }
                }

//                if (KeyCode == KeyEvent.VK_A) {
//                    bar.move(-1, 0, -1, 0);
//                }
//                if (KeyCode == KeyEvent.VK_D) {
//                    bar.move(1, 0, 3, 0);
//                }
            }
            if(bar==bar1||bar==bar2) {
//                if (KeyCode == KeyEvent.VK_W) {
//                    bar.move(0, -1, 0, -1);
//                }
//                if (KeyCode == KeyEvent.VK_S) {
//                    bar.move(0, 1, 0, 1);
//
//                }
                if(bar.gettileX()>3) {
                    if (KeyCode == KeyEvent.VK_A) {
                        bar.move(-1, 0, -3, 0);
                        new MessageSender("PLAYER "+Main.playerId+" "+bars[Main.playerId].getPos()).start();
                    }
                }
                if(bar.gettileX()<83) {
                    if (KeyCode == KeyEvent.VK_D) {
                        bar.move(1, 0, 3, 0);
                        new MessageSender("PLAYER "+Main.playerId+" "+bars[Main.playerId].getPos()).start();
                    }
                }

            }

        }
    }
}
