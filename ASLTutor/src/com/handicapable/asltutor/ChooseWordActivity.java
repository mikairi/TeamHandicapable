package com.handicapable.asltutor;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.handicapable.asltutor.helper.DictionaryOpenHelper;
import com.handicapable.asltutor.helper.StringHelper;

public class ChooseWordActivity extends SherlockActivity {

	private SQLiteDatabase db;
	private ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_dictionary);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Bundle bundle = getIntent().getExtras();

		DictionaryOpenHelper dbHelper = new DictionaryOpenHelper(this);
		db = dbHelper.openReadableDatabase();

		listView = (ListView) findViewById(R.id.dic_list);
		showWords(bundle.getString("com.handicapable.asltutor.dic"));
	}

	private void showWords(String dic) {
		// Convert dic to table name
		final String dic_name = StringHelper.getTableFromName(dic);
		// Get a list of words
		Cursor queryResult = db.query(dic_name, new String[] { "_id", "word" }, null, null, null, null, "word");

		String[] from = new String[] { "word" };
		int[] to = new int[] { R.id.word };

		@SuppressWarnings("deprecation")
		SimpleCursorAdapter words = new SimpleCursorAdapter(this, R.layout.search_result, queryResult, from, to);
		listView.setAdapter(words);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getApplicationContext(), ViewSignActivity.class);
				Bundle bundle = new Bundle();
				TextView tv = (TextView) view.findViewById(R.id.word);

				bundle.putString("com.handicapable.asltutor.word", tv.getText().toString());
				bundle.putString("com.handicapable.asltutor.dic", dic_name);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_choose_dictionary, menu);
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
