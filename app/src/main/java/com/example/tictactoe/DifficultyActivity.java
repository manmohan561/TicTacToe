package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DifficultyActivity extends AppCompatActivity {
    public static final String EXTRA_DIFFICULTY= "com.example.tictactoe.difficulty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);

        Button btnEasy= findViewById(R.id.btnEasy);
        Button btnMedium= findViewById(R.id.btnMedium);
        Button btnUnbeatable= findViewById(R.id.btnUnbeatable);

        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callIntent(1);
            }
        });

        btnMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callIntent(2);
            }
        });

        btnUnbeatable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callIntent(3);
            }
        });
    }

    private void callIntent(int diff) {
        Intent intent= new Intent(DifficultyActivity.this, MainActivity.class);
        intent.putExtra(EXTRA_DIFFICULTY, diff);
        startActivity(intent);
    }
}
