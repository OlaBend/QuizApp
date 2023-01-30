package com.example.quizzapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class StartQuiz extends AppCompatActivity {

    TextView timer;
    TextView result;
    ImageView showImage;
    HashMap<String, Integer> map = new HashMap<>();
    ArrayList<String> techList = new ArrayList<>();
    int index;
    Button btn1, btn2, btn3, btn4;
    TextView points;
    int punkty;
    CountDownTimer countDownTimer;
    long time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_quiz);
        timer = findViewById(R.id.timer);
        result = findViewById(R.id.result);
        showImage = findViewById(R.id.showImage);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        points = findViewById(R.id.points);
        index = 0;
        techList.add("Beagle");
        techList.add("Golden Retriver");
        techList.add("Labrador");
        techList.add("Jamnik krótkowłosy");
        techList.add("Corgi");
        techList.add("King Charles Spaniel");
        //techList.add("Jamnik długowłosy");
        map.put(techList.get(0), R.drawable.beagle_pies);
        map.put(techList.get(1), R.drawable.pies_golden_retriever);
        map.put(techList.get(2), R.drawable.labrador);
        map.put(techList.get(3), R.drawable.jamnik);
        map.put(techList.get(4), R.drawable.corgi);
        map.put(techList.get(5), R.drawable.cavalier);
        Collections.shuffle(techList);
        time = 10000;
        punkty = 0;
        start_quiz();

    }

    private void start_quiz() {
        time = 10000;
        timer.setText("" + (time / 1000)+ "s");
        points.setText(punkty + " / " + techList.size());
        wygenerujPytanie(index);
        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long time) {
                timer.setText("" + (time / 1000) + "s");
            }

            @Override
            public void onFinish() {
                index++;
                if(index > techList.size() - 1){
                    showImage.setVisibility(View.GONE);
                    btn1.setVisibility(View.GONE);
                    btn2.setVisibility(View.GONE);
                    btn3.setVisibility(View.GONE);
                    btn4.setVisibility(View.GONE);
                    Intent intent = new Intent(StartQuiz.this, GameOver.class);
                    intent.putExtra("punkty", punkty);
                    startActivity(intent);
                    finish();
                } else {
                    start_quiz();
                }
            }
        }.start();
    }

    private void wygenerujPytanie(int index) {
        ArrayList<String> techListTemp = (ArrayList<String>) techList.clone();
        String dobraOdp = techList.get(index);
        techListTemp.remove(dobraOdp);
        Collections.shuffle(techListTemp);
        ArrayList<String> newList = new ArrayList<>();
        newList.add(techListTemp.get(0));
        newList.add(techListTemp.get(1));
        newList.add(techListTemp.get(2));
        newList.add(dobraOdp);
        Collections.shuffle(newList);
        btn1.setText(newList.get(0));
        btn2.setText(newList.get(1));
        btn3.setText(newList.get(2));
        btn4.setText(newList.get(3));
        showImage.setImageResource(map.get(techList.get(index)));
    }

    public void nastepnePytanie(View view) {
        countDownTimer.cancel();
        index++;
        if(index > techList.size() - 1){
            showImage.setVisibility(View.GONE);
            btn1.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
            btn3.setVisibility(View.GONE);
            btn4.setVisibility(View.GONE);
            Intent intent = new Intent(StartQuiz.this, GameOver.class);
            intent.putExtra("punkty", punkty);
            startActivity(intent);
            finish();
        }else {
            start_quiz();
        }
    }
    public void selectedAnswer(View view) {
        countDownTimer.cancel();
        String odp = ((Button) view).getText().toString().trim();
        String dobraOdp = techList.get(index);
        if(odp.equals(dobraOdp)){
            punkty++;
            points.setText(punkty + " / " + techList.size());
            result.setText("Dobra odpowiedź!");
        }else {
            result.setText("Zła odpowiedź.");
        }
    }
}
