package com.hangman.illegaldisease.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {
    private int phase = 0; //We will start at first phase, up to sixth
    ImageView phaseImage;
    CurrentGameStatus current_game = new CurrentGameStatus();
    int[] imageArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TextView wordView = (TextView) findViewById(R.id.textView_game_word);
        phaseImage = (ImageView) findViewById(R.id.imageView_game);
        wordView.setText(current_game.get_display_word());
        imageArray = new int[] {R.drawable.first, R.drawable.second,R.drawable.third,R.drawable.fourth,R.drawable.fifth,R.drawable.sixth,R.drawable.seventh};
        //This is way too ugly, it needs to be in somewhere else. Arrays.xml somehow didnt work.
    }

    public void new_guess(View view) {

        TextView wordView = (TextView) findViewById(R.id.textView_game_word);
        TextView letter_guessed = (TextView) findViewById(R.id.editText_game);

        String new_guess = letter_guessed.getText().toString();
        if (!new_guess.equals("")) {
            if(current_game.try_to_insert_letter(new_guess)){

            }
            else{
                //TODO : Load the next image here
                phase++;
                phaseImage.setImageResource(imageArray[phase]);
            }
            if(phase == 6){ //Sixth phase
                phase = 0;
                //Game over stuff
            }
            wordView.setText(current_game.get_display_word());
            letter_guessed.setText("");

            if (current_game.word_completed()) {
                Toast.makeText(getApplicationContext(), "Congrats!!!! \n You guessed " + current_game.get_raw_word().toUpperCase() + " correctly", Toast.LENGTH_LONG).show();
                //TODO : add word here to the dictionary
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
