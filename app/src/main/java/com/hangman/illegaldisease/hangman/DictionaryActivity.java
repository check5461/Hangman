package com.hangman.illegaldisease.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

class PostModel{
    public String word;
    public PostModel(){

    }
}
public class DictionaryActivity extends AppCompatActivity {
    String mac;
    FirebaseDatabase database;
    DatabaseReference parentRef;
    ArrayList<String> words;
    ArrayAdapter<String> adapter;
    Button buttonClear;

    ListView dictionaryListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        initializeValues();
        System.out.println("basdfasdfadsf");
    }
    private void initializeValues(){
        words = new ArrayList<>();
        mac = GameActivity.getMacAddress();
        database = FirebaseDatabase.getInstance();
        parentRef = database.getInstance().getReference("/Dictionary/");
        final DatabaseReference userRef = parentRef.child(GameActivity.getMacAddress());
        dictionaryListView = (ListView) findViewById(R.id.listView_dictionary);
        buttonClear = (Button) findViewById(R.id.button_dictionary_clear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRef.removeValue();
            }
        });
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String parsedValue;
                try{
                    parsedValue = dataSnapshot.getValue().toString(); //Just to avoid complexity
                }
                catch(NullPointerException e){
                    parsedValue = ""; //User attempts to clean without any data.
                }
                words.clear(); //Because we add a placeholder data, we need to delete it.
                words = GetListOfWords(separateWords(parsedValue));
                if(words == null || words.size() == 0){
                    words.add(""); //Add this empty value in order to prevent null values.
                }
                adapter = new ArrayAdapter<String>(DictionaryActivity.this,android.R.layout.simple_list_item_1,words);
                dictionaryListView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }
    private String separateTheWord(String keyValue){
        System.out.println(keyValue);
        String realValue = "";
        /*Our data is something like : -L0-4hrgsOEN9z_gfTLS=recall
          I want to parse only 'recall'. Use = as our favor
        */
        for(int chars = keyValue.length()-1;chars>=0;chars--){
            if(keyValue.charAt(chars) == '}'){
                continue; //Last character at last element will be }, skip it.
            }
            if(keyValue.charAt(chars) == '='){
                break;
            }
            realValue += keyValue.charAt(chars);
        }
        return new StringBuffer(realValue).reverse().toString();
    }

    private String[] separateWords(String wholeData){
        return wholeData.split(",");
    }

    private ArrayList<String> GetListOfWords(String[] separatedStrings){
        ArrayList<String> finalList = new ArrayList<>();
        for(String strings : separatedStrings){
            String separatedWord = separateTheWord(strings);
            finalList.add(separatedWord);
        }
        return finalList;
    }

}
