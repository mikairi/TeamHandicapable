package com.handicapable.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class QuizSettings extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_settings);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_quiz_settings, menu);
        return true;
    }

    
}
