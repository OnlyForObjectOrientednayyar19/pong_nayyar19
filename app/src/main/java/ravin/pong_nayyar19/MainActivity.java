package ravin.pong_nayyar19;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    TextView displayScore;
    SeekBar paddleSizeBar;
    SeekBar ballSpeedBar;
    Button resetButton;
    TestAnimator TA = new TestAnimator();
    public MainActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        paddleSizeBar = (SeekBar)findViewById(R.id.paddleSizeSB);
        ballSpeedBar = (SeekBar)findViewById(R.id.ballSpeedSB);
        displayScore = (TextView) findViewById(R.id.playerScoreTV);
        displayScore.setText(" "+TA.getPlayerScore());


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
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {TA.setSizeOffset(i);}
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
        if(v.getId() == resetButton.getId()){
            if(TA.getBallInPlay()==false){
                TA.setBallInPlay(true);
                TA. setInitialXY(true);
            }
        }
    }
    public void update(){
        displayScore.setText(" "+TA.getPlayerScore());
    }
}//Class MainActivity
