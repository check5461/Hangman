package com.hangman.illegaldisease.hangman;

/**
 * Created by illegaldisease on 12/9/17.
 */

public class CurrentGameStatus {

    static WordToGuess wordToGuess;

    CurrentGameStatus(){
        wordToGuess = new WordToGuess(GenerateWord.generate());
    }


    String get_raw_word(){
        return wordToGuess.return_raw_word();
    }

    String get_display_word(){
        return wordToGuess.display_word();
    }

    boolean try_to_insert_letter(String guessed_letter) {
        if (wordToGuess.letter_belongs_to_word(guessed_letter) && !wordToGuess.letter_already_in_word(guessed_letter)) {
            wordToGuess.insert_letter(guessed_letter);
            return true;
        } else {
            return false;
        }
    }

    boolean word_completed(){
        return wordToGuess.word_completed();
    }
}
