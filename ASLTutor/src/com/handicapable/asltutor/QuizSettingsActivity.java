package com.handicapable.asltutor;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;

public class QuizSettingsActivity extends SherlockActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz_settings);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_quiz_settings, menu);
		return true;
	}

	public void showSign(View view) {
		Toast toast = Toast.makeText(getApplicationContext(), "The sign displayed was: 'A'", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
		toast.show();
	}
}
