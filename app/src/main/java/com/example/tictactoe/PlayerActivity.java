package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class PlayerActivity extends AppCompatActivity {
    public static final String EXTRA_MODE="com.example.tictactoe.mode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Button btnSingle = findViewById(R.id.btnSingle);
        Button btnMulti = findViewById(R.id.btnMulti);

        btnSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayerActivity.this, DifficultyActivity.class);
                startActivity(intent);
            }
        });

        btnMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayerActivity.this, MainActivity.class);
                intent.putExtra(EXTRA_MODE, false);
                startActivity(intent);
            }
        });
    }

}
