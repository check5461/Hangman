package com.hangman.illegaldisease.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonStart;
    Button buttonDictionary;
    Button buttonExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitializeButtons();
    }
    private void InitializeButtons(){
        buttonStart = findViewById(R.id.button_main_start);
        buttonDictionary = findViewById(R.id.button_main_dictionary);
        buttonExit = findViewById((R.id.button_main_exit));

        buttonStart.setOnClickListener(this);
        buttonDictionary.setOnClickListener(this);
        buttonExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case  R.id.button_main_start: {
                startActivity(new Intent(MainActivity.this, GameActivity.class));
                break;
            }

            case R.id.button_main_dictionary: {
                startActivity(new Intent(MainActivity.this, DictionaryActivity.class));
                break;
            }
            case R.id.button_main_exit: {
                finish();
                System.exit(0);
                break;
            }

        }
    }


}
