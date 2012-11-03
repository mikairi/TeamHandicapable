package com.handicapable.asltutor;

import android.app.SearchManager;
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
import com.handicapable.asltutor.helper.DictionaryOpenHelper;

public class SearchableActivity extends SherlockListActivity {

	private String searchQuery;
	private SQLiteDatabase db;
	private ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchable);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		DictionaryOpenHelper dbHelper = new DictionaryOpenHelper(this);
		db = dbHelper.openReadableDatabase();

		Intent searchIntent = getIntent();
		if (Intent.ACTION_SEARCH.equals(searchIntent.getAction())) {
			searchQuery = searchIntent.getStringExtra(SearchManager.QUERY);
			search(searchQuery);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_searchable, menu);
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

	private void search(String query) {
		String where = "word LIKE '" + query + "%'";
		Cursor queryResult = db.query("dictionary", null, where, null, null, null, "word");
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
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
}
