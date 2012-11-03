package com.handicapable.asltutor;

import android.app.SearchManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.handicapable.asltutor.helper.DictionaryOpenHelper;

public class SearchableActivity extends SherlockListActivity {

	private String searchQuery;
	private SQLiteDatabase db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchable);

		DictionaryOpenHelper dbHelper = new DictionaryOpenHelper(this);
		SQLiteDatabase db = dbHelper.openReadableDatabase();

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

	private void search(String query) {

	}
}
