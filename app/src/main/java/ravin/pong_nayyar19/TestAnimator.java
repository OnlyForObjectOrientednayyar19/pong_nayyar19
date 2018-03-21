package ravin.pong_nayyar19;
import android.graphics.*;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.SeekBar;

import java.util.Random;
/**
 * class that recreates the Pong Game
 * @author Nuxoll
 * @author Veghdal
 * @author Ravi Nayyar
 *          nayyar19
 *          nayyar19@up.edu
 * @version March 2018
 */
public class TestAnimator implements Animator {
    // instance variables
    private int count = 0; // counts the number of logical clock ticks
    private int paddleSize;
    private int numX = 50; //Current xCoor of the ball
    private int numY = 50; //Current yCoor of the ball
    private int wall_x; //Last X coordinate of the previous wall hit
    private int wall_y; //Last Y coordinate of the previous wall hit
    int select = 0;     //Used for the switch case
    private boolean left2right = true; //was the ball going left to right
    private boolean top2bottom = true; //was the ball going from top to bottom
    private int playerScore;
    //Determines if the ball has missed the paddle, the reset button has been pressed, and the ball
    //needs to be placed in its initial XY location
    private boolean initialXY = true;
    //Determines if the ball is still bouncing
    private boolean isBallInPlay = true;
    private int speed = 10;
    private int sizeOffset;
    Paint bluePaint = new Paint();
    int wallBoundry = 45;


    //Getters and Setters
    public int getPlayerScore(){return playerScore;}
    public void setPlayerScore(int pScore){this.playerScore = this.playerScore+pScore;}

    /**
     * Changes the speed of the ball
     * @param progressBar value of ballSpeedbar
     */
    public void setSpeed(int progressBar){this.speed = progressBar;}
    public int getSpeed(){return this.speed;}

    /**setSizeOffset
     * Determines the extent of the length of the paddle after the d3efault size
     * @param offset the number added to the top and bottom of the paddle's length
     */
    public void setSizeOffset(int offset){this.sizeOffset = offset;}
    public int getSizeOffset(){return this.sizeOffset;}

    /**setLastWallCoor
     * Sets the last known location of the ball hitting one of the walls
     * @param xCoor x coordinate of wall that the ball hit
     * @param yCoor y coordinate of wall that the ball hit
     */
    public void setLastWallCoor(int xCoor, int yCoor){
        this.wall_x = xCoor;
        this.wall_y = yCoor;
    }

    /**setLeft2Right
     * sets the value of the boolean variable left2right
     * if the 2nd x corrdiate is greater than the first, the ball is travelling from left -> right
     * @param x1 last wall touch x coordiate
     * @param x2 current wall touch x coordinate
     */
    public void setLeft2Right(int x1,int x2){
        if(x2>x1){this.left2right =  true;}
        else{this.left2right = false;}
    }

    /**setTop2bottom
     * sets the value of the boolean variable setTop2Bottom
     * if the 2nd y corrdiate is greater than the first, the ball is travelling from top -> bottom
     * @param y1 last wall touch y coordiate
     * @param y2 current wall touch y coordinate
     */
    public void setTop2botton(int y1, int y2){
        if(y2>y1){this.top2bottom = true;}
        else{this.top2bottom = false;}
    }



    public void setBallInPlay(boolean inPlay){this.isBallInPlay = inPlay;}
    public boolean getBallInPlay(){return  this.isBallInPlay;}

    public void setInitialXY(boolean initialXY){this.initialXY = initialXY;}
    public boolean getInitialXY(){return this.initialXY;}


    /**randomValue
     *
     * @param upperBound gives the upper bound of the random value
     * @return returns the random value
     */

    public int randomValue(int upperBound){
        Random r = new Random();
        if(initialXY){
            return (r.nextInt(upperBound) + 300);
        }
        else{
            return (r.nextInt(upperBound));
        }
    }
    /**
     * Interval between animation frames: .03 seconds (i.e., about 33 times
     * per second).
     * @return the time interval between frames, in milliseconds.
     */
    public int interval() {
        return 20;
    }

    /**
     * The background color: black.
     *@return the background color onto which we will draw the image.
     */
    public int backgroundColor() {return Color.rgb(0, 0, 0);}

    /**
     * Action to perform on clock tick
     * The tick method contains the code for everything that is shown on the screen
     * It determines the bounce direction, bounce angle, paddle size, the physical drawing of the ball
     * @param g the graphics object on which to draw
     */
    public void tick(Canvas g) {

        bluePaint.setColor(Color.rgb(0,0,255));
        int upperYpaddle = (int)(g.getHeight()/2-g.getHeight()/10-4.5*sizeOffset);
        int lowerYpaddle = (int)(600+4.5*sizeOffset);
        g.drawRect((int)(g.getHeight()/4-g.getHeight()/5),upperYpaddle,0,lowerYpaddle,bluePaint);

        if(isBallInPlay) {
            //Only active if it is the the first time starting the app or after the reset button
            //has been pressed
            if (initialXY) {
                numX = randomValue((g.getHeight() - 100));
                numY = randomValue((g.getHeight() - 100));
                select = 0;
                initialXY = false;
                int initialspeed = randomValue(30);
                if (initialspeed < 10) {
                    setSpeed(10);
                 }
             }
             //if the ball hits the bottom wall
            if (numY > (g.getHeight() - wallBoundry)) {
                setLeft2Right(wall_x, numX);
                if (left2right) {
                    select = 1;
                } else {
                    select = 2;
                }
                setLastWallCoor(numX, numY);
            }
            //if the ball hits the top wall
            if (numY < wallBoundry) {
                setLeft2Right(wall_x, numX);
                if (left2right) {
                    select = 0;
                } else {
                    select = 3;
                }
                setLastWallCoor(numX, numY);
            }
            //if the ball hits the far right wall
            if (numX > (g.getWidth() - wallBoundry)) {
                setTop2botton(wall_y, numY);
                if (top2bottom) {
                    select = 3;
                } else {
                    select = 2;
                }
                setLastWallCoor(numX, numY);
            }
            //if the ball hits the paddle
            int paddleWidth = (int) (g.getHeight() / 4 - g.getHeight() / 5);
            if (numX < (paddleWidth + wallBoundry)) {
                if (numY < lowerYpaddle) {
                    if (numY > upperYpaddle) {
                        setTop2botton(wall_y, numY);
                        if (top2bottom) {
                            select = 0;
                        } else {
                            select = 1;
                        }
                        setLastWallCoor(numX, numY);
                    }
                }
                setPlayerScore(1);
             }
            //if the ball misses the paddle
            if (numX < -31) {
                //numY = 500;
                //numX = 500;
                setPlayerScore(-5);
                select = 4;
                isBallInPlay = false;
            }
        }//if isBallisInPlay

        //Switch statement determines the the slope of the ball's trajectory
        int rAngle = randomValue(10);
        if(rAngle>getSpeed()){rAngle =0;}
        switch (select){
              case 0:
                numX+=(getSpeed()+rAngle);
                numY+=(getSpeed()+rAngle);
            break;
            case 1:
                numX+=(getSpeed()+rAngle);
                numY-=(getSpeed()-rAngle);
                break;
            case 2:
                numX-=(getSpeed()-rAngle);
                numY-=(getSpeed()-rAngle);
                break;
            case 3:
                numX-=(getSpeed()-rAngle);
                numY+=(getSpeed()+rAngle);
                break;
            case 4:
         }
        // Draw the ball in the correct position.
        Paint redPaint = new Paint();
        redPaint.setColor(Color.RED);
        g.drawCircle(numX, numY, 30,redPaint);
    }

    /**
     * Tells that we never pause.
     * @return indication of whether to pause
     */
    public boolean doPause() {return false;}

    /**Tells that we never stop the animation.
     * @return indication of whether to quit.
     */
    public boolean doQuit() {return false;}

    public void onTouch(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {}
    }
}//class TextAnimator
