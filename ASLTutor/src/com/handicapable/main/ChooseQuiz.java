package com.handicapable.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class ChooseQuiz extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_quiz);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_choose_quiz, menu);
        return true;
    }

    
}
