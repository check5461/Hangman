package com.hangman.illegaldisease.hangman;
import java.util.Random;

public class WordToGuess {
    static String raw_text = "";    // Stores the main body of the word to guess
    String current_state = "";    // Current state of the word. "_" for un-guessed letters

    WordToGuess(String word)
    {
        raw_text = word;
        for (int i=0;i<raw_text.length();i++)
            current_state = current_state + "_";
        Random r = new Random();
        int i1 = r.nextInt(raw_text.length()-1);
        insert_letter(raw_text.substring(i1,i1+1));
    }

    void insert_letter(String letter){
        for (int i = 0; i < raw_text.length(); i++)
        {
            char c = raw_text.toLowerCase().charAt(i);
            if (c == letter.toLowerCase().toCharArray()[0])
            {
                current_state = current_state.substring(0,i) + c + current_state.substring(i+1,current_state.length());
            }
        }
    }

    boolean letter_belongs_to_word(String letter)
    {
        char c;
        c = letter.toLowerCase().toCharArray()[0];

        for (int i=0;i < raw_text.length(); i++)
        {
            char w = raw_text.toLowerCase().charAt(i);
            if (w == c)
            {
                return true;
            }
        }
        return false;
    }

    boolean letter_already_in_word(String letter)
    {
        char c;
        c = letter.toLowerCase().toCharArray()[0];

        for (int i=0;i < current_state.length(); i++)
        {
            char w = current_state.toLowerCase().charAt(i);
            System.out.println("Current char: " + c + " word char: " + w);
            if (w == c)
            {
                return true;
            }
        }
        return false;
    }

    String display_word()
    {
        String temp = "";
        // Append and pre append a space over
        for (int i = 0; i < current_state.length(); i++)
        {
            temp = temp + " " + current_state.charAt(i) + " ";
        }
        // Remove the leading and trailing blank space
        temp = temp.substring(1,temp.length()-1);
        return temp;
    }

    String return_raw_word()
    {
        return raw_text;
    }

    boolean word_completed(){
        return current_state.equals(raw_text);
    }

}
