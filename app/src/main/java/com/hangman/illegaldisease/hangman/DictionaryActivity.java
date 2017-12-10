package com.hangman.illegaldisease.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DictionaryActivity extends AppCompatActivity {
    //This will read data from firebase and fill the listview
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
    }
}
