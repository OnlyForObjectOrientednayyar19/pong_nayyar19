package ravin.pong_nayyar19;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * PongMainActivity
 *
 * This is the activity for the Pong game. It attaches a PongAnimator to
 * an AnimationSurface.
 *
 * @author Andrew Nuxoll
 * @author Steven R. Vegdahl
 * @version July 2013
 *
 */
public class MainActivity extends Activity implements View.OnClickListener{
    TextView displayScore1;
    TextView displayScore2;
    SeekBar paddleSizeBar;
    SeekBar paddle2SizeBar;
    SeekBar ballSpeedBar;
    Button resetButton;
    CheckBox checkButton;
    TestAnimator TA = new TestAnimator();
    public MainActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        paddleSizeBar = (SeekBar)findViewById(R.id.paddleSizeSB);
        paddle2SizeBar = (SeekBar)findViewById(R.id.paddle2SizeSB);
        ballSpeedBar = (SeekBar)findViewById(R.id.ballSpeedSB);
        displayScore1 = (TextView) findViewById(R.id.player1ScoreTV);
        displayScore2 = (TextView) findViewById(R.id.player2ScoreTV);

        checkButton = (CheckBox) findViewById(R.id.TwoDMode);
        checkButton.setOnClickListener(this);


        ballSpeedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TA.setSpeed(i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        paddleSizeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {TA.setSizeOffset1(i);}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        paddle2SizeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {TA.setSizeOffset2(i);}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        resetButton = (Button)findViewById(R.id.resetButton);
        resetButton.setOnClickListener(this);

        // Connect the animation surface with the animator
        AnimationSurface mySurface = (AnimationSurface) this.findViewById(R.id.animationSurface);
        mySurface.setAnimator(TA);
    }
    @Override
    //todo replace this with ontouch()
    public void onClick(View v){
        if(v.getId() == resetButton.getId()) {
            if (TA.getBallInPlay() == false) {
                TA.setBallInPlay(true);
                TA.setInitialXY(true);
                int Score1 = TA.getPlayerScore1();
                int Score2 = TA.getPlayerScore2();
                displayScore1.setText(""+2*Score1);
                displayScore2.setText(""+Score2);
            }
        }
        if(v.getId() == checkButton.getId()) {
            if (checkButton.isChecked()) {
                    TA.set2DMode(true);
                } else{
                    TA.set2DMode(false);
                }
            }
        }//onClick
}//Class MainActivity
