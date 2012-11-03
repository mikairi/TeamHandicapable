package com.handicapable.asltutor;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;

public class MainActivity extends SherlockActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().setBackgroundDrawable(Drawable.createFromPath(getString(R.string.bg_path)));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void startChooseDictionaryActivity(View view) {
		Intent intent = new Intent(this, ChooseDictionaryActivity.class);
		startActivity(intent);
	}

	public void startChooseQuizActivity(View view) {
		Intent intent = new Intent(this, ChooseQuizActivity.class);
		startActivity(intent);
	}

	public void startMakeQuizActivity(View view) {
		Intent intent = new Intent(this, MakeQuizActivity.class);
		startActivity(intent);
	}

	public void startAddSignActivity(View view) {
		Intent intent = new Intent(this, AddSignActivity.class);
		startActivity(intent);
	}

}
