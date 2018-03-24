package ravin.pong_nayyar19;
import android.graphics.*;
import android.view.MotionEvent;

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
    private int numX = 50; //Current xCoor of the ball
    private int numY = 50; //Current yCoor of the ball
    private int wall_x; //Last X coordinate of the previous wall hit
    private int wall_y; //Last Y coordinate of the previous wall hit
    private int select = 0;     //Used for the switch case
    private boolean left2right = true; //was the ball going left to right
    private boolean top2bottom = true; //was the ball going from top to bottom
    private int player1Score;
    private int player2Score;
    //Determines if the ball has missed the paddle, the reset button has been pressed, and the ball
    //needs to be placed in its initial XY location
    private boolean initialXY = true;
    //Determines if the ball is still bouncing
    private boolean isBallInPlay = true;
    private boolean Two_D_ModeActive;
    private int speed = 10;
    private int sizeOffset1;
    private int sizeOffset2;
    private int paddle_x1;//X center paddle1 coordinate
    private int paddle_y1;//Y center paddle1 coordinate
    private int paddle_x2;//X center paddle2 coordinate
    private int paddle_y2;//Y center paddle2 coordinate
    private int height;
    private int width;
    private Paint bluePaint = new Paint();
    private Paint redPaint = new Paint();
    private Paint whitePaint = new Paint();
    private Paint paintSelect = new Paint();
    private int wallBoundry = 45;


    //Getters and Setters

    /**
     * Get Player Score Getters and Setters
     * Get player's one and two's Scores
     */
    public int getPlayerScore1(){return player1Score;}
    public void setPlayerScore1(int pScore){this.player1Score = this.player1Score+pScore;}

    public int getPlayerScore2(){return player2Score;}
    public void setPlayerScore2(int pScore){this.player2Score = this.player2Score+pScore;}

    /**
     * Changes the speed of the ball
     * @param progressBar value of ballSpeedbar
     */
    public void setSpeed(int progressBar){this.speed = progressBar;}
    public int getSpeed(){return this.speed;}

    /**setSizeOffset1
     * Determines the extent of the length of the paddle 1 after the d3efault size
     * @param offset the number added to the top and bottom of the paddle's length
     */
    public void setSizeOffset1(int offset){this.sizeOffset1 = offset;}
    public int getSizeOffset1(){return this.sizeOffset1;}

    /**setSizeOffset2
     * Determines the extent of the length of the paddle after the d3efault size
     * @param offset the number added to the top and bottom of the paddle's length
     */
    public void setSizeOffset2(int offset){this.sizeOffset2 = offset;}
    public int getSizeOffset2(){return this.sizeOffset2;}


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

    public void setCenterPaddle1(int x, int y){
        this.paddle_x1 = x;
        this.paddle_y1 = y;
    }
    public void setCenterPaddle2(int x, int y){
        this.paddle_x2 = x;
        this.paddle_y2 = y;
    }


    public void setBallInPlay(boolean inPlay){this.isBallInPlay = inPlay;}
    public boolean getBallInPlay(){return  this.isBallInPlay;}

    public void setInitialXY(boolean initialXY){this.initialXY = initialXY;}
    public boolean getInitialXY(){return this.initialXY;}

    /**
     * These getters and setters for the 2Dmode determines if the game can be
     * played in 1 or 2 dimensions
     * @return
     */
    public boolean get2DMode(){return this.Two_D_ModeActive;}
    public void set2DMode(boolean mode){this.Two_D_ModeActive = mode;}

    public int getHalfHeight(){return this.height/2;}
    public int getHalfWidth(){return this.width/2;}

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
        return 10;
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
        redPaint.setColor(Color.rgb(255,0,0));
        whitePaint.setColor(Color.rgb(255,255,255));
        this.width = g.getWidth();
        this.height = g.getHeight();
        //Defining the first paddle
        int XpaddleP1 = paddle_x1;
        int YpaddleP1 = paddle_y1;
        int upperYpaddleP1 = (int)(YpaddleP1-75-(sizeOffset1 *5));
        int lowerYpaddleP1 = (int)(YpaddleP1+75+(sizeOffset1 *5));

        //Defining the second paddle
        int XpaddleP2 = paddle_x2;
        int YpaddleP2 = paddle_y2;
        int upperYpaddleP2 = (int)(YpaddleP2-75-(sizeOffset2 *5));
        int lowerYpaddleP2 = (int)(YpaddleP2+75+(sizeOffset2 *5));

        //Differentiating between fist dimension mode, or second dimension mode
        if(Two_D_ModeActive){
        //If two D mode is active, restrict paddle1's x coordinate to the first third of the screen
        //and restrict paddle2's x coordinate to the last third of the screen
            if (XpaddleP1 > (int) (g.getWidth() / 3)){
                XpaddleP1 = (int) (g.getWidth() / 3);}
                g.drawRect(XpaddleP1, upperYpaddleP1, XpaddleP1 + 50, lowerYpaddleP1, bluePaint);


            if (XpaddleP2 < (int) ((2/3)*g.getWidth())){
                XpaddleP2 = (int) (g.getWidth()*(3/4));}
            g.drawRect(XpaddleP2, upperYpaddleP2, XpaddleP2+50, lowerYpaddleP2, redPaint);
        //Single dimension mode
        }else{
            XpaddleP1 = 0;
            g.drawRect(XpaddleP1, upperYpaddleP1, XpaddleP1 + 50, lowerYpaddleP1, bluePaint);
            XpaddleP2 = g.getWidth()-50;
            g.drawRect(XpaddleP2, upperYpaddleP2, XpaddleP2+50, lowerYpaddleP2, redPaint);
        }
        if(isBallInPlay) {
            //Only active if it is the the first time starting the app or after the reset button
            //has been pressed
            if (initialXY) {
                setCenterPaddle1(0,(int)g.getHeight()/2);
                setCenterPaddle2(g.getWidth()-50, g.getHeight()/2);
                numX = 1004;
                numY = randomValue((g.getHeight() - 100));
                setLastWallCoor(numX-1,numY-1);
                initialXY = false;
                paintSelect = whitePaint;
                select = randomValue(4);
                 int initialspeed = randomValue(30);
                if (initialspeed < 10) {setSpeed(10);}
             }
             if(numX>670 && numX<1338){paintSelect = whitePaint;}

             //if the ball hits the bottom wall
            if (numY > (g.getHeight() - wallBoundry)) {
                setLeft2Right(wall_x, numX);
                if (left2right) {select = 1;}
                else {select = 2;}
                setLastWallCoor(numX, numY);
            }
            //if the ball hits the top wall
            if (numY < wallBoundry) {
                setLeft2Right(wall_x, numX);
                if (left2right) {select = 0;}
                else {select = 3;}
                setLastWallCoor(numX, numY);
            }

            //if the ball hits paddle 2
             if ((numX > XpaddleP2-25)&&(numX <XpaddleP2+25)){
                if (numY < lowerYpaddleP2) {
                    if (numY > upperYpaddleP2) {
                        setTop2botton(wall_y, numY);
                        if (top2bottom) {
                            select = 3;
                        } else {
                            select = 2;
                        }
                        setLastWallCoor(numX, numY);
                    }
                }
             paintSelect = redPaint;
             setPlayerScore2(1);
             }

            //if the ball hits paddle 1
            int paddleWidth = (int) (g.getHeight() / 4 - g.getHeight() / 5);
            if (    (numX > XpaddleP1) && (numX < (XpaddleP1 + 100))){
                if (numY < lowerYpaddleP1) {
                    if (numY > upperYpaddleP1) {
                        setTop2botton(wall_y, numY);
                        if (top2bottom) {
                            select = 0;
                        } else {
                            select = 1;
                        }
                        setLastWallCoor(numX, numY);
                    }
                }
                paintSelect = bluePaint;
                setPlayerScore1(1);
            }
            //if the ball misses paddle 1
            if (numX < -31) {
                select = 4;
                isBallInPlay = false;
                setPlayerScore1(-10);
            }

            //if the ball misses paddle 2
            if (numX > g.getWidth()+31) {
                select = 4;
                isBallInPlay = false;
                setPlayerScore2(-10);

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
        g.drawCircle(numX, numY, 30,paintSelect);
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

        int xPos = (int)event.getX();
        int yPos = (int)event.getY();
        if(xPos < 670) {
            this.setCenterPaddle1(xPos, yPos);
        }
        else if (xPos > 1338){
            this.setCenterPaddle2(xPos, yPos);
        }
     }
}//class TextAnimator
