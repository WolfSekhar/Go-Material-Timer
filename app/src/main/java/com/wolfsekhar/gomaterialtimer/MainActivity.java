package com.wolfsekhar.gomaterialtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private CountDownTimer timer;
    private TextView textViewTimer;
    private EditText editTextMins,editTextSecs;
    private Button buttonStart,buttonStop;
    private long minutes =0, seconds = 0,milliseconds = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getViewsIds();

        editTextSecs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!editTextSecs.getText().toString().isEmpty()){
                    if ( Integer.parseInt(editTextSecs.getText().toString()) > 60){
                        editTextSecs.setText(String.valueOf(60));
                    }
                }
            }
        });

        buttonStart.setOnClickListener(l -> {
            try {
                minutes = Long.parseLong(editTextMins.getText().toString()) ;
                seconds = Long.parseLong(editTextSecs.getText().toString());
            } catch (NumberFormatException ignored) {}
            finally {
                if (minutes != 0 && seconds != 0){
                    countDownInitiate(convertToMillisecond(minutes,seconds));
                }
            }
        });


        buttonStop.setOnClickListener(l -> {
            timer.cancel();
            textViewTimer.setText("00:00");
            clear();
        });


    }

    private void getViewsIds(){
        textViewTimer = findViewById(R.id.textView_countdown);

        editTextMins = findViewById(R.id.edittext_minutes);
        editTextSecs = findViewById(R.id.edittext_seconds);

        buttonStart = findViewById(R.id.button_start_or_pause);
        buttonStop = findViewById(R.id.button_stop);
    }

    private void countDownInitiate(long totalTime){
        timer = new CountDownTimer(totalTime,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                milliseconds = millisUntilFinished;
                long[] l = convertToMinesAndSeconds(milliseconds);
                textViewTimer.setText(new StringBuilder().append(l[0]).append(" : ").append(l[1]).toString());
            }

            @Override
            public void onFinish() {
                textViewTimer.setText("Times Up");
            }
        }.start();
    }

    private long convertToMillisecond(long min,long sec ){
        return (((min * 60)+ sec) * 1000);
    }

    private long[] convertToMinesAndSeconds(long milliSeconds){
        long seconds = milliSeconds/1000;
        return new long[]{seconds / 60, seconds % 60};
    }

    private void clear(){
        timer.cancel();
        editTextMins.setText("");
        editTextSecs.setText("");
    }


}