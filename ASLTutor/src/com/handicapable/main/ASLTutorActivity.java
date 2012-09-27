package com.handicapable.main;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class ASLTutorActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getWindow().setBackgroundDrawable(Drawable.createFromPath(getString(R.string.bg_path)));
    }
}