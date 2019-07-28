package com.example.tictactoe;



import android.content.Context;

import android.content.DialogInterface;

import android.content.Intent;

import android.content.SharedPreferences;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;

import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import static com.example.tictactoe.R.id.board;



public class MainActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;

    public static int difficulty;

    public static boolean mode;

    private Button viewbutton;

    private BoardView boardView;

    private GameEngine gameEngine;

    private long startTime, runtime, bestTime;

    private SharedPreferences sharedPreferences;

    private TextView tvBestTime;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mDatabaseHelper =new DatabaseHelper( this );
        startTime= System.currentTimeMillis();

        runtime= 0;

        boardView = findViewById(board);

        tvBestTime= findViewById(R.id.tvBestTime);

        gameEngine = new GameEngine();

        boardView.setGameEngine(gameEngine);

        boardView.setMainActivity(this);

        viewbutton = findViewById(R.id.button );
        viewbutton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.tictactoe.ListDataActivity.class);
                startActivity(intent);


            }
        } );


        Intent intent= getIntent();

        mode= intent.getBooleanExtra(PlayerActivity.EXTRA_MODE, true);

        difficulty= intent.getIntExtra(DifficultyActivity.EXTRA_DIFFICULTY, 0);



        sharedPreferences=getPreferences(Context.MODE_PRIVATE);

        bestTime=sharedPreferences.getLong(getString(R.string.time_Key), 1000000);

        if(bestTime!=1000000)

            tvBestTime.setText(getString(R.string.am_tvBestTime)+bestTime/1000F + getString(R.string.am_Seconds));

    }



    @Override

    protected void onPause() {

        super.onPause();

        runtime+= System.currentTimeMillis()-startTime;

    }



    @Override

    protected void onResume() {

        super.onResume();

        startTime=System.currentTimeMillis();

    }



    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.am_menu, menu);

        return super.onCreateOptionsMenu(menu);

    }



    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.amMiNewGame) {

            newGame();

        }



        return super.onOptionsItemSelected(item);

    }



    public void gameEnded(char c) {

        String msg = (c == 'T') ? "Game Ended. Tie" : "GameEnded. " + c + " win";

        runtime=runtime+System.currentTimeMillis()-startTime;

        if(runtime<bestTime && c!='T') {

            bestTime=runtime;

            tvBestTime.setText(getString(R.string.am_tvBestTime)+bestTime/1000F + getString(R.string.am_Seconds));

            SharedPreferences.Editor editor= sharedPreferences.edit();

            editor.putLong(getString(R.string.time_Key), bestTime);

            editor.apply();

        }
        String newEntry =("RunTime = "+ (float)runtime/1000);
        AddData(newEntry);

        runtime=0;



        new AlertDialog.Builder(this).setTitle("Tic Tac Toe").

                setMessage(msg).

                setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override

                    public void onDismiss(DialogInterface dialogInterface) {

                        newGame();

                    }

                }).show();

    }




    private void newGame() {

        gameEngine.newGame();

        boardView.invalidate();

        runtime=0;

        startTime=System.currentTimeMillis();

    }

    public void AddData(String newEntry) {
        boolean insertData = mDatabaseHelper.addData(newEntry);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        }

      }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}