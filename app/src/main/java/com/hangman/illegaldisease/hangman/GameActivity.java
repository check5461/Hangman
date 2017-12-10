package com.hangman.illegaldisease.hangman;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.NetworkInterface;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    private int phase = 0; //We will start at first phase, up to sixth
    ImageView phaseImage;
    CurrentGameStatus current_game = new CurrentGameStatus();
    int[] imageArray;

    FirebaseDatabase database;
    DatabaseReference parentRef;
    DatabaseReference userRef;
    DatabaseReference userPostRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initializeValues();
    }

    public void new_guess(View view) {

        TextView wordView = (TextView) findViewById(R.id.textView_game_word);
        TextView letter_guessed = (TextView) findViewById(R.id.editText_game);

        String new_guess = letter_guessed.getText().toString();
        if (!new_guess.equals("")) {
            if(current_game.try_to_insert_letter(new_guess) == false){
                phase++;
                phaseImage.setImageResource(imageArray[phase]);
            }
            if(phase == 6){ //Sixth phase, means you failed.
                showToastMessage(current_game.get_raw_word(),true);
                phase = 0;
                phaseImage.setImageResource(imageArray[phase]);
                reset(view);
            }
            wordView.setText(current_game.get_display_word());
            letter_guessed.setText("");

            if (current_game.word_completed()) {
                showToastMessage(current_game.get_raw_word(),false);
                phase = 0;
                phaseImage.setImageResource(imageArray[phase]);

                userPostRef = userRef.push();
                userPostRef.setValue(current_game.get_raw_word());

                reset(view);
            }
        }
    }
    public void reset(View view) {
        TextView wordView = (TextView) findViewById(R.id.textView_game_word);

        current_game = new CurrentGameStatus();
    }
    private void showToastMessage(String message, boolean failed){
        if(failed){
            Toast.makeText(getApplicationContext(), "You failed to guess " + message.toUpperCase(), Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Congrats!!!! \n You guessed " + message.toUpperCase() + " correctly", Toast.LENGTH_LONG).show();
        }

    }
    private void initializeValues(){
        TextView wordView = (TextView) findViewById(R.id.textView_game_word);
        phaseImage = (ImageView) findViewById(R.id.imageView_game);
        wordView.setText(current_game.get_display_word());
        imageArray = new int[] {R.drawable.first, R.drawable.second,R.drawable.third,R.drawable.fourth,R.drawable.fifth,R.drawable.sixth,R.drawable.seventh};
        //This is way too ugly, it needs to be in somewhere else. Arrays.xml somehow didn't work.
        database = FirebaseDatabase.getInstance();
        parentRef = database.getReference("Dictionary"); //Also this shouldnt be hard coded either.
        userRef = parentRef.child(getMacAddress());
        System.out.println(getMacAddress());
    }

    public static String getMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }


}
