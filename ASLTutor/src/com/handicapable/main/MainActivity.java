package com.handicapable.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().setBackgroundDrawable(Drawable.createFromPath(getString(R.string.bg_path)));
	}

	public void startChooseQuizActivity(View view) {
		Intent intent = new Intent(this, ChooseQuizActivity.class);
		startActivity(intent);
	}

}
