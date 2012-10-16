package com.handicapable.asltutor;

import android.annotation.TargetApi;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.handicapable.asltutor.helper.DictionaryOpenHelper;
import com.handicapable.asltutor.helper.StringHelper;

@TargetApi(11)
public class ChooseWordActivity extends Activity {

	private SQLiteDatabase db;
	private ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_dictionary);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		Bundle bundle = getIntent().getExtras();

		DictionaryOpenHelper dbHelper = new DictionaryOpenHelper(this);
		db = dbHelper.openReadableDatabase();
		Log.d("ChooseDictionaryActivity", "Database opened " + db.getPath());

		listView = (ListView) findViewById(R.id.dic_list);
		showWords(bundle.getString("com.handicapable.asltutor.word"));
	}

	private void showWords(String dic) {
		// Convert dic to table name
		String dic_name = StringHelper.getTableFromName(dic);
		// Get a list of words
		Cursor queryResult = db.query(dic_name, new String[] { "_id", "word" }, null, null, null, null, "word");

		String[] from = new String[] { "word" };
		int[] to = new int[] { R.id.word };

		@SuppressWarnings("deprecation")
		SimpleCursorAdapter words = new SimpleCursorAdapter(this, R.layout.search_result, queryResult, from, to);
		listView.setAdapter(words);
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
