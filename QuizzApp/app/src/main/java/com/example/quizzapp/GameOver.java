package com.example.quizzapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {

    TextView points, najlepszyWynik;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        int punkty = getIntent().getExtras().getInt("punkty");
        points = findViewById(R.id.points);
        najlepszyWynik = findViewById(R.id.najlepszyWynik);
        points.setText("" + punkty);
        sharedPreferences = getSharedPreferences("preferences", 0);
        int punktySP = sharedPreferences.getInt("punktySP",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(punkty > punktySP){
            punktySP = punkty;
            editor.putInt("punktySP", punktySP);
            editor.commit();
        }
        najlepszyWynik.setText("" + punktySP);
    }
    public void restart(View view) {
        Intent intent = new Intent(GameOver.this, StartQuiz.class);
        startActivity(intent);
        finish();
    }
    public void exit(View view) {
        finish();
    }
}
