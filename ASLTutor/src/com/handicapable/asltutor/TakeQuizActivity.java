package com.handicapable.asltutor;

import java.io.IOException;
import java.io.InputStream;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.handicapable.asltutor.helper.DictionaryOpenHelper;

public class TakeQuizActivity extends SherlockActivity {

	private SQLiteDatabase db;
	private Cursor queryResult;
	private String answer;
	private ImageView img;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_quiz);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		DictionaryOpenHelper dbHelper = new DictionaryOpenHelper(this);
		db = dbHelper.openReadableDatabase();

		img = (ImageView) findViewById(R.id.question_img);
		setupQuiz();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_take_quiz, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setupQuiz() {
		String where = "word = 'A' OR word = 'F' OR word = 'C' OR word = 'W' or word = 'O'";
		queryResult = db.query("dictionary", null, where, null, null, null, null);
		queryResult.moveToFirst();
		displayQuestion(queryResult.getString(1), queryResult.getString(2));
	}

	private void displayQuestion(String signName, String mediaPath) {
		// Set the answer
		answer = signName;

		InputStream imgStream = null;
		// Get the image stream
		try {
			imgStream = getAssets().open(mediaPath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		img.setImageBitmap(BitmapFactory.decodeStream(imgStream));
	}

	public void nextQuestion(View view) {
		if (!queryResult.isLast()) {
			queryResult.moveToNext();
			displayQuestion(queryResult.getString(1), queryResult.getString(2));
		} else {
			// Reach the end of quiz
			Toast toast = Toast.makeText(getApplicationContext(), "Congratulation! You have finished the quiz.",
					Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
			toast.show();
		}
	}

	public void showAnswer(View view) {
		Toast toast = Toast.makeText(getApplicationContext(), "The sign displayed was: '" + answer + "'",
				Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
		toast.show();
	}
}
