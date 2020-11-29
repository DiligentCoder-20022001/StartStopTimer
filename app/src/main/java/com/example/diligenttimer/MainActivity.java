package com.example.diligenttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekBar;
    TextView timerTextView;
    Button controllerButton;
    CountDownTimer countDownTimer;
    Boolean counterIsActive = false;//says if the counter button is tapped or not
    public void updateTimer(int secondsLeft)
    {
        int minutes = (int)secondsLeft/60; // rounds the number of minutes
        int seconds = secondsLeft - minutes*60;//seconds remaining
        String secondString = Integer.toString(seconds);
        if(seconds <= 9){
            secondString = "0" + secondString;
        }
        timerTextView.setText(Integer.toString(minutes) + ":" + secondString);

    }
    public void controlTimer(View view)
    {
        if(counterIsActive == false) {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);//makes it disappear
            controllerButton.setText("Stop");


            //Log.i("Button pressed", "Yes");
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long l) {
                    updateTimer((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    timerTextView.setText("0:00");
                    Log.i("finished", "Timer done");
                    //cant use this as it is inside a countdown timer
                    MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.car);
                    mPlayer.start();


                }
            }.start();
        }
        else
        {
            timerTextView.setText("0:30");
            timerSeekBar.setProgress(30);
            countDownTimer.cancel();
            controllerButton.setText("GO");
            timerSeekBar.setEnabled(true);
            counterIsActive = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controllerButton = (Button)findViewById(R.id.timerButton);
        timerSeekBar = (SeekBar)findViewById(R.id.seekBar);
        timerTextView = (TextView)findViewById(R.id.Timer);
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {


                updateTimer(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}