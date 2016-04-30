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
    private Bar bars[]=new Bar[4];
    private Bar bar1;
    private Bar bar2;
    private Bar bar3;
    private Bar bar4;
    private int Size;
    private boolean freeze=false;
    private boolean ball_freeezed=false;
    public int [] score =new int[4];
    public static int ballRadius = 16;  //Radius

    private int ballX = 400; //X position
    private int ballY = 400; //Y position

    private int ballVelocityX = 13;  //X velocity
    private int ballVelocityY = 15;  //Y velocity
private int time=0;
    private int freeze_time=0;
    public static Board board;
    String currentDirectory;
    // private Timer timer; //timer associated with the ball
    // private int timerUpdate = 25;
Bar freeezed_bar;
    private Color ballColor; //color
public void update_score(int a,int id) {
    this.score[id] = a;
    System.out.println("Score:" + a + " id:" + id);
}
public void update_freeze(int id)
{
    bars[id].freezed=true;
}
    public void update_released(int id)
    {
        bars[id].freezed=false;
    }

    private void setBallSpeed()
    {
        switch (Main.speed)
        {
            case 10:ballVelocityX=10;ballVelocityY=12;break;
            case 15:ballVelocityX=15;ballVelocityY=18;break;
            case 20:ballVelocityX=25;ballVelocityY=30;break;
        }
        System.out.println("BallVelocity: "+ballVelocityX+"+j"+ballVelocityY);
    }
    public Board()
    {
        this(1000);
    }
    public Board(int s)
    {
        score[0]=Player.Lives;
        score[1]=Player.Lives;
        score[2]=Player.Lives;
        score[3]=Player.Lives;

        setBallSpeed();

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

                if(d > 0 && d < 1000) {
                    int mid =b.gettileY()+5*ballRadius;
                    if(d==mid)
                    {
                        return 0;
                    }
                    if (mid-d>0&&mid>6*ballRadius) {
                        //   System.out.println(d + " 1 move up " + b.gettileY() * 10);

                        //       new MessageSender("COMPUTER " + a + " " + bars[Main.playerId].getPos()).start();
                        if(mid-d>Main.speed)
                            return -Main.speed;
                        else
                            return -1*(mid-d);

                    }
                    if(d-mid>0&&mid<1000-6*ballRadius){

                        //  System.out.println(d + " 1 move down " + b.gettileY()*10);
                        if(d-mid>Main.speed)
                            return Main.speed;
                        else
                            return d-mid;
                    }

                }


            }
                break;
            case 2:
                if(ballVelocityY<0) {
                    int d = (ballY - ballRadius) * (ballVelocityX) / Math.abs(ballVelocityY) + ballX;

                    int mid = b.gettileX() + ballRadius * 5;

                    if (d > 0 && d < 1000) {
                        if (d == mid) {
                            return 0;
                        }
                        if (mid - d > 0 && mid > 6 * ballRadius) {
                            //   System.out.println(d + " 1 move up " + b.gettileY() * 10);

                            //       new MessageSender("COMPUTER " + a + " " + bars[Main.playerId].getPos()).start();
                            if (mid - d > Main.speed)
                                return -Main.speed;
                            else
                                return -1 * (mid - d);

                        }
                        if (d - mid > 0 && mid < 1000 - 6 * ballRadius) {

                            //  System.out.println(d + " 1 move down " + b.gettileY()*10);
                            if (d - mid > Main.speed)
                                return Main.speed;
                            else
                                return d - mid;
                        }
                    }
                }
                break;
            case 3:
                if (ballVelocityX >0) {
                    int d=(((1000-ballX-ballRadius)*(ballVelocityY))/(ballVelocityX))+ballY;
                    // System.out.println(d+" "+"case3 "+ b.gettileY()*10+" "+ballX+" "+ballY+" "+ballVelocityX+" "+ballVelocityY);
                    if(d > 0 && d < 100* 10) {
                        int mid =b.gettileY()+5*ballRadius;
                        if(d==mid)
                        {
                            return 0;
                        }
                        if (mid-d>0&&mid>6*ballRadius) {
                            //   System.out.println(d + " 1 move up " + b.gettileY() * 10);

                            //       new MessageSender("COMPUTER " + a + " " + bars[Main.playerId].getPos()).start();
                            if(mid-d>Main.speed)
                                return -Main.speed;
                            else
                                return -1*(mid-d);

                        }
                        if(d-mid>0&&mid<1000-6*ballRadius){

                            //  System.out.println(d + " 1 move down " + b.gettileY()*10);
                            if(d-mid>Main.speed)
                                return Main.speed;
                            else
                                return d-mid;
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

        //Winning condition
        if(Main.otherPlayerAlive==0) {
            swing._instance.dispose();
            new showStatus(1);
            timer.stop();
        }

        //Updating X and Y Coordinates
        if(Main.list.get((4+Main.playerId+2)%4).isbot()&&!bar1.freezed){
            if (ball_freeezed)
                bar1.move(-compu(bar1, 2), 0, 0, 0);
            else
                bar1.move(compu(bar1, 2), 0, 0, 0);
        }
        if(Main.list.get((4+Main.playerId+1)%4).isbot()&&!bar3.freezed) {
            if(!ball_freeezed)
            bar3.move(0, compu(bar3, 1), 0, 0);
            else
                bar3.move(0,-compu(bar3, 1), 0, 0);
        }
        if(Main.list.get((4+Main.playerId+3)%4).isbot()&&!bar4.freezed) {
            if(!ball_freeezed)
            bar4.move(0, compu(bar4, 3), 0, 0);
            else
             bar4.move(0,-compu(bar4, 3), 0, 0);
        }
        if(All_connected()) {
            time++;
            if(time==1000)
            {
                time=0;
            }
            if(time ==300)
            {
                freeze=true;
                System.out.println("freeze start");
            }
            if(Math.abs(time-freeze_time)==300&&freeezed_bar!=null)
            {

                if(freeezed_bar.freezed==true) {
                    freeezed_bar.freezed = false;
                    if(freeezed_bar==bar2)
                    {
                        new MessageSender("Released "+Main.playerId).start();

                    }
                    System.out.println("freeze release"+ " "+time+" "+freeze_time);
                }


            }
            //the perception of X-direction motion
            ballX += ballVelocityX;
//            System.out.println("Score_++:" + Main.list.get((4 + Main.playerId + 1) % 4).Lives+" "+(4 + Main.playerId + 1) % 4);
//            System.out.println("Score_++:" + Main.list.get((4 + Main.playerId + 2) % 4).Lives+" "+(4 + Main.playerId + 2) % 4);
//            System.out.println("Score_++:" + Main.list.get((4 + Main.playerId + 3) % 4).Lives+" "+(4 + Main.playerId + 3) % 4);

            // Left Wall
            if (ballX < ballRadius) {
                ballX = ballRadius; //reset the position
                ballVelocityX = -ballVelocityX; //reverse the velocity
                if(Main.list.get((4+Main.playerId+1)%4).isbot() && Main.list.get((4+Main.playerId+1)%4).isAlive) {
                    score[(4 + Main.playerId + 1) % 4]-= 1;
//                    System.out.println("Score_left:" + Main.list.get((4 + Main.playerId + 1) % 4).Lives+" "+(4 + Main.playerId + 1) % 4);

                    if(score[(4 + Main.playerId + 1) % 4]==0) {
                        Main.list.get((4+Main.playerId+1)%4).isAlive=false;
                        Main.otherPlayerAlive-=1;
                    }

                }
            }
            //Right Wall
            if (ballX > width - ballRadius) {
                ballX = width - ballRadius; //reset the position

                ballVelocityX = -ballVelocityX; //reverse the velocity

                if(Main.list.get((4+Main.playerId+3)%4).isbot() && Main.list.get((4+Main.playerId+3)%4).isAlive) {
                    score[(4 + Main.playerId + 3) % 4]-= 1;
                    if(score[(4 + Main.playerId + 3) % 4]==0)
                    {
                        Main.list.get((4+Main.playerId+3)%4).isAlive=false;
                        Main.otherPlayerAlive-=1;
                    }

                }
                //   System.out.println("right");
                //  new MessageSender("BALL "+ballX+" "+ballY+" "+ballVelocityX+" "+ballVelocityY).start();

            }

            ballY += ballVelocityY; //perception of Y-direction Motion
            // System.out.println(ballX+" "+ballY);
            if(freeze&&Math.sqrt(((ballX)-(500))*((ballX)-500)+(ballY-500)*(ballY-500))<100+ballRadius)
            {
                ballColor=Color.cyan;
                freeze=false;
                ball_freeezed=true;
            }
            // Top Wall
            if (ballY < ballRadius) {
                ballY = ballRadius;//reset the position
                ballVelocityY = -ballVelocityY; //reverse the velocity
                if(Main.list.get((4+Main.playerId+2)%4).isbot() && Main.list.get((4+Main.playerId+2)%4).isAlive){
                    score[(4 + Main.playerId + 2) % 4]-= 1;
                        getGraphics().setColor(Color.RED);
//getGraphics().drawRect(0,0,1000,8);
                    if(score[(4 + Main.playerId + 2) % 4]==0)
                    {
                        Main.list.get((4+Main.playerId+2)%4).isAlive=false;
                        Main.otherPlayerAlive-=1;
                    }

                }
                //  System.out.println("top");
                //  new MessageSender("BALL "+ballX+" "+ballY+" "+ballVelocityX+" "+ballVelocityY).start();

            }

            //Bottom Wall
            if (ballY > height - ballRadius) {
               // System.out.println(ballX+" bottom"+ballY+" "+bar2.gettileX()+" "+bar2.gettileY());
                ballY = height - ballRadius;//reset the position
                ballVelocityY = -ballVelocityY;//reverse the velocity
                if(Main.list.get((4+Main.playerId)%4).isAlive) {
                    score[Main.playerId] -= 1;
                    System.out.println("Score:" + score[Main.playerId]);
                    new MessageSender("BALL "+ballX+" "+ballY+" "+ballVelocityX+" "+ballVelocityY+" "+Main.playerId).start();
                    new MessageSender("SCORE " + Main.playerId + " " + score[Main.playerId]).start();
                   if (score[Main.playerId] == 0) {
                        Main.list.get(Main.playerId).isAlive = false;
                       swing._instance.dispose();
                       new showStatus(0);
                       timer.stop();
                    }
                }
            }


            //Math.abs(ballX- bar1.gettileX()*10)<ballRadius*2||
            //bar1
            if (ballY < bar1.gettileY()  + 2*ballRadius && ballX - bar1.gettileX()  < (ballRadius * 10)+1 && ballX+ballRadius - bar1.gettileX()  > 0&& Main.list.get((4+Main.playerId+2)%4).isbot()) {
                ballY = bar1.gettileY()  + 2*ballRadius;

               if(ball_freeezed&&Main.list.get((4+Main.playerId+2)%4).isbot) {
                   freeze_time=time;
                   bar1.freezed=true;
                   System.out.println(freeze_time+" freez time");
                freeezed_bar=bar1;
                ballColor=Color.BLACK;
                   ball_freeezed=false;
               }
                ballVelocityY = -ballVelocityY;//reverse the velocity
            }
            //bar2
            if ((ballY > bar2.gettileY()  - ballRadius && ballX - bar2.gettileX()  < (ballRadius * 10)+1 && ballX+ballRadius - bar2.gettileX() > 0)) {
                // System.out.println(ballX+" "+ballY+" "+bar2.gettileX()+" "+bar2.gettileY());
                ballY = bar2.gettileY()- ballRadius  ;//reset the position
                ballVelocityY = -ballVelocityY;//reverse the velocity
               if(ball_freeezed) {
                   freeze_time=time;
                   bar2.freezed = true;
                   System.out.println(freeze_time+" freez time");
                   freeezed_bar = bar2;
                   ballColor = Color.BLACK;
                   ball_freeezed=false;
                   new MessageSender("Freezed "+Main.playerId).start();
               }
                new MessageSender("BALL "+ballX+" "+ballY+" "+ballVelocityX+" "+ballVelocityY+" "+Main.playerId).start();
            }
            //bar3
            if (ballX < bar3.gettileX()  + ballRadius*2 && ballY - bar3.gettileY()  < (ballRadius * 10)+1 && ballY+ballRadius - bar3.gettileY()  > 0 && Main.list.get((4+Main.playerId+1)%4).isbot()) {
                ballX = bar3.gettileX()  + ballRadius*2; //reset the position
               if(ball_freeezed&&Main.list.get((4+Main.playerId+1)%4).isbot) {
                   freeze_time=time;
                   bar3.freezed = true;
                   freeezed_bar = bar3;
                   System.out.println(freeze_time+" freez time");
                   ballColor = Color.BLACK;
                   ball_freeezed=false;
               }
                ballVelocityX = -ballVelocityX; //reverse the velocity
            }
            //bar4
            if (ballX > bar4.gettileX()  - ballRadius  && ballY - bar4.gettileY()  <(ballRadius * 10)+1 && ballY +ballRadius- bar4.gettileY()  > 0 && Main.list.get((4+Main.playerId+3)%4).isbot()) {
                ballX = bar4.gettileX()  - ballRadius ; //reset the position
               if(ball_freeezed&&Main.list.get((4+Main.playerId+3)%4).isbot) {
                   freeze_time=time;
                   bar4.freezed = true;
                   freeezed_bar = bar4;
                   System.out.println(freeze_time+" freez time");
                   ballColor = Color.BLACK;
                  ball_freeezed=false;
               }
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

        g.drawImage(m.getGrass(),15*32,0,1000,1000,null);
        if(freeze)
        {
            g.setColor(Color.cyan);
            g.fillOval(500+15*32-100,500-100,200,200);
        }
        g.setColor(Color.black);
        g.fillRect(0+(15*32),0,ballRadius,ballRadius);
        g.fillRect(1000-ballRadius+(15*32),1000-ballRadius,ballRadius,ballRadius);
        g.fillRect(0+(15*32),1000-ballRadius,ballRadius,ballRadius);
        g.fillRect(1000-ballRadius+(15*32),0,ballRadius,ballRadius);
        g.setColor(ballColor);
        g.fillOval(ballX - ballRadius+(15*32), ballY -ballRadius, ballRadius*2, ballRadius*2);
        g.setColor(Color.magenta);
        //  if(Main.list.get(Main.playerId).isAlive)
        if(bar2.freezed)
            g.setColor(Color.cyan);
        if(Main.list.get(Main.playerId).isAlive)
        g.fillRoundRect(bar2.gettileX()+(15*32), 1000 - ballRadius, ballRadius*10, ballRadius,20,20);
        g.setColor(Color.magenta);
        if(bar1.freezed)
            g.setColor(Color.cyan);
         if(Main.list.get((Main.playerId+4+2)%4).isAlive)
        g.fillRoundRect(bar1.gettileX()+(15*32), 0 , ballRadius*10, ballRadius,20,20);
        g.setColor(Color.magenta);
        if(bar3.freezed)
            g.setColor(Color.cyan);
          if(Main.list.get((Main.playerId+4+1)%4).isAlive)
        g.fillRoundRect((15*32), bar3.gettileY(), ballRadius, ballRadius*10,20,20);
        g.setColor(Color.magenta);
        if(bar4.freezed)
            g.setColor(Color.cyan);
          if(Main.list.get((Main.playerId+4+3)%4).isAlive)
        g.fillRoundRect(1000-ballRadius+(15*32), bar4.gettileY(), ballRadius, ballRadius*10,20,20);
        g.setColor(Color.black);
        g.setFont(new Font("Serif",Font.PLAIN,30));
        if(Main.list.get(Main.playerId).isAlive)
        g.drawString(Main.list.get(Main.playerId).getName()+"-"+ Integer.toString(score[Main.playerId]),15*32+400,1000-50);
        if(Main.list.get((Main.playerId+2)%4).isAlive)
        g.drawString(Main.list.get((Main.playerId+2)%4).getName()+"-"+Integer.toString(score[(Main.playerId+2)%4]),15*32+400,50);
        if(Main.list.get((Main.playerId+1)%4).isAlive)
        g.drawString(Main.list.get((Main.playerId+1)%4).getName()+"-"+Integer.toString(score[(Main.playerId+1)%4]),15*32,400);
        if(Main.list.get((Main.playerId+3)%4).isAlive)
        g.drawString(Main.list.get((Main.playerId+3)%4).getName()+"-"+Integer.toString(score[(Main.playerId+3)%4]),1000+15*32-100,400);
if(300-time<100&&300-time>0)
{
    g.drawString("Freezing mode starting in "+Integer.toString(300-time),15*32+300,500);

}
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
            if(bar==bar4||bar==bar3&&!bar.freezed) {
                if(bar.gettileY()>ballRadius) {
                    if (KeyCode == KeyEvent.VK_W) {
                        if(bar.gettileY()-ballRadius>Main.speed)
                            bar.move(0, -Main.speed, 0, -1);
                        else
                            bar.move(0,-(bar.gettileY()-ballRadius),0,0);
                    }
                }
                if(bar.gettileY()<1000-11*ballRadius) {
                    if (KeyCode == KeyEvent.VK_S) {
                        if(bar.gettileY()<1000-Main.speed-11*ballRadius)
                        { bar.move(0, Main.speed, 0, 1);}
                        else
                        { bar.move(0,1000-bar.gettileY()-11*ballRadius,0,0);}
                    }
                }

//                if (KeyCode == KeyEvent.VK_A) {
//                    bar.move(-1, 0, -1, 0);
//                }
//                if (KeyCode == KeyEvent.VK_D) {
//                    bar.move(1, 0, 3, 0);
//                }
            }
            if(bar==bar1||bar==bar2&&!bar.freezed) {
//                if (KeyCode == KeyEvent.VK_W) {
//                    bar.move(0, -1, 0, -1);
//                }
//                if (KeyCode == KeyEvent.VK_S) {
//                    bar.move(0, 1, 0, 1);
//
//                }
                if(bar.gettileX()>ballRadius) {
                    if (KeyCode == KeyEvent.VK_A) {
                        if(bar.gettileX()>Main.speed+ballRadius)
                            bar.move(-Main.speed, 0, -1, 0);
                        else
                            bar.move(-1*(bar.gettileX()-ballRadius),0,0,0);
                        new MessageSender("PLAYER "+Main.playerId+" "+bars[Main.playerId].getPos()).start();
                    }
                }
                if(bar.gettileX()<1000-11*ballRadius) {
                    if (KeyCode == KeyEvent.VK_D) {
                        if(bar.gettileX()<1000-Main.speed-11*ballRadius)
                            bar.move(Main.speed, 0, 3, 0);
                        else
                            bar.move(1000-11*ballRadius-bar.gettileX(),0,0,0);
                        new MessageSender("PLAYER "+Main.playerId+" "+bars[Main.playerId].getPos()).start();
                    }
                }

            }
           // System.out.println(bar.gettileX()+" "+bar.gettileY()+" "+ballX+" "+ballY+" "+ballVelocityX+" "+ballVelocityY);

        }

        public  void keyReleased(KeyEvent e)
        {

        }
        public  void keyTyped(KeyEvent e)
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
                if(bar.gettileY()>25) {
                    if (KeyCode == KeyEvent.VK_W) {
                        bar.move(0, -Main.speed, 0, -1);
                    }
                }
                if(bar.gettileY()<835) {
                    if (KeyCode == KeyEvent.VK_S) {
                        bar.move(0, Main.speed, 0, 1);
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
                if(bar.gettileX()>25) {
                    if (KeyCode == KeyEvent.VK_A) {
                        bar.move(-Main.speed, 0, -1, 0);
                        new MessageSender("PLAYER "+Main.playerId+" "+bars[Main.playerId].getPos()).start();
                    }
                }
                if(bar.gettileX()<835) {
                    if (KeyCode == KeyEvent.VK_D) {
                        bar.move(Main.speed, 0, 3, 0);
                        new MessageSender("PLAYER "+Main.playerId+" "+bars[Main.playerId].getPos()).start();
                    }
                }

            }
            System.out.println(bar.gettileX()+" "+bar.gettileY()+" "+ballX+" "+ballY+" "+ballVelocityX+" "+ballVelocityY);

        }

    }
}


