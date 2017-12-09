package com.hangman.illegaldisease.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    CurrentGameStatus current_game = new CurrentGameStatus();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TextView wordView = (TextView) findViewById(R.id.textView_game_word);

        wordView.setText(current_game.get_display_word());
    }

    public void new_guess(View view) {

        TextView wordView = (TextView) findViewById(R.id.textView_game_word);
        TextView letter_guessed = (TextView) findViewById(R.id.editText_game);

        String new_guess = letter_guessed.getText().toString();
        if (!new_guess.equals("")) {
            current_game.try_to_insert_letter(new_guess);

            wordView.setText(current_game.get_display_word());
            letter_guessed.setText("");

            if (current_game.word_completed()) {
                Toast.makeText(getApplicationContext(), "Congrats!!!! \n You guessed " + current_game.get_raw_word().toUpperCase() + " correctly", Toast.LENGTH_LONG).show();
                reset(view);
            }
        }
    }


    public void reset(View view) {
        TextView wordView = (TextView) findViewById(R.id.textView_game_word);

        current_game = new CurrentGameStatus();
        wordView.setText(current_game.get_display_word());
    }
}
