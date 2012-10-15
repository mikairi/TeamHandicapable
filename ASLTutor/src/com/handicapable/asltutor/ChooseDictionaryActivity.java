package com.handicapable.asltutor;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.handicapable.asltutor.helper.DictionaryOpenHelper;

public class ChooseDictionaryActivity extends Activity {

	private SQLiteDatabase db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_dictionary);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		DictionaryOpenHelper dbHelper = new DictionaryOpenHelper(this);
		db = dbHelper.getReadableDatabase();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_choose_dictionary, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
