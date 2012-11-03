package com.handicapable.asltutor;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.handicapable.asltutor.helper.DictionaryOpenHelper;

public class ChooseWordActivity extends SherlockListActivity {

	private SQLiteDatabase db;
	private ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchable);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Bundle bundle = getIntent().getExtras();

		DictionaryOpenHelper dbHelper = new DictionaryOpenHelper(this);
		db = dbHelper.openReadableDatabase();

		showWords(bundle.getString("com.handicapable.asltutor.dic"));
	}

	private void showWords(final String dic_name) {
		String query = "SELECT dictionary._id, dictionary.word FROM dictionary, "
				+ "dictionaries WHERE dictionaries.name = ? AND dictionaries._id = dictionary.dictionary_id "
				+ "ORDER BY word";
		// Get a list of words
		Cursor queryResult = db.rawQuery(query, new String[] { dic_name });

		String[] from = new String[] { "word" };
		int[] to = new int[] { R.id.word };

		@SuppressWarnings("deprecation")
		SimpleCursorAdapter words = new SimpleCursorAdapter(this, R.layout.search_result, queryResult, from, to);
		setListAdapter(words);

		listView = getListView();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getApplicationContext(), ViewSignActivity.class);
				Bundle bundle = new Bundle();
				TextView tv = (TextView) view.findViewById(R.id.word);

				// Bundle up the word
				bundle.putString("com.handicapable.asltutor.word", tv.getText().toString());
				bundle.putString("com.handicapable.asltutor.dic", dic_name);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate menu
		getSupportMenuInflater().inflate(R.menu.activity_choose_dictionary, menu);

		// Search view
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		searchView.setSubmitButtonEnabled(true);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			db.close();
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
