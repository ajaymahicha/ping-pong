package game.GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Ball extends JPanel implements ActionListener{

    private int ballRadius = 16;  //Radius

    private int ballX = ballRadius; //X position
    private int ballY = ballRadius; //Y position

    private int ballVelocityX = 13;  //X velocity
    private int ballVelocityY = 15;  //Y velocity

   // private Timer timer; //timer associated with the ball
   // private int timerUpdate = 25;

    private Color ballColor; //color


    //Initializes the bouncing ball panel parameters.
    //Constructor
    public Ball() {
        this.setBackground(Color.white); //bg color
        this.setForeground(Color.black); //fg color
        this.setPreferredSize(new Dimension(800,400)); //size of the ball
        ballColor = Color.black; //set color of the ball
     //   timer = new Timer(timerUpdate, this); // Calls actionPerformed every 25 milliseconds.
    }
    //end Constructor


    //Animation of the ball

    public void setAnimation(boolean motion) {

        if (motion) {
           // timer.start(); // start motion by starting the timer.
        }
        else {
          //  timer.stop(); // halt timer
        }

    }

    //function which sets the color of the ball and its variations

    public void setBallColor(Color color) {
        ballColor = color;
        this.repaint(); // Display the new color
    }



    //Action Performed
    //Every time the timer is called these set of actions are performed

    public void actionPerformed(ActionEvent e) {

        //Ball class extends Jpanel, this is the size of that JPanel
        int width = getWidth();
        int height = getHeight();

        //Updating X and Y Coordinates

        //the perception of X-direction motion
        ballX += ballVelocityX;

        //Left Wall
        if (ballX < ballRadius) {
            ballX = ballRadius; //reset the position
            ballVelocityX = - ballVelocityX; //reverse the velocity
        }
        //Right Wall
        else if (ballX > width-ballRadius) {
            ballX = width - ballRadius; //reset the position
            ballVelocityX = - ballVelocityX; //reverse the velocity
        }

        ballY += ballVelocityY; //perception of Y-direction Motion

        //Top Wall
        if (ballY < ballRadius) {
            ballY = ballRadius;//reset the position
            ballVelocityY = - ballVelocityY; //reverse the velocity
        }

        //Bottom Wall
        else if (ballY > height - ballRadius) {
            ballY = height- ballRadius;//reset the position
            ballVelocityY = - ballVelocityY;//reverse the velocity

        }

        //implement
        this.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(ballColor);
        g.fillOval(ballX - ballRadius, ballY - ballRadius, ballRadius*2, ballRadius*2);
    }

    public void setBall(int x,int y,int vx,int vy) {
        this.ballVelocityX = vx;
        ballVelocityY=vy;
        ballX=x;
        ballY=y;
    }
}// The code is inspired by "https://wiki.scn.sap.com"