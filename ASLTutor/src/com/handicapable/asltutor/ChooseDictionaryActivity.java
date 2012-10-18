package com.handicapable.asltutor;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

import com.handicapable.asltutor.helper.DictionaryOpenHelper;

@TargetApi(11)
public class ChooseDictionaryActivity extends Activity {

	private SQLiteDatabase db;
	private ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_dictionary);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		DictionaryOpenHelper dbHelper = new DictionaryOpenHelper(this);
		db = dbHelper.openReadableDatabase();
		Log.d("ChooseDictionaryActivity", "Database opened " + db.getPath());

		listView = (ListView) findViewById(R.id.dic_list);
		showDictionaries();
	}

	private void showDictionaries() {
		// Get a list of dictionaries
		Cursor queryResult = db.query("dictionaries", null, null, null, null, null, null);

		String[] from = new String[] { "dic_name" };
		int[] to = new int[] { R.id.word };

		@SuppressWarnings("deprecation")
		SimpleCursorAdapter dicts = new SimpleCursorAdapter(this, R.layout.search_result, queryResult, from, to);
		listView.setAdapter(dicts);

		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getApplicationContext(), ChooseWordActivity.class);
				Bundle bundle = new Bundle();
				TextView tv = (TextView) view.findViewById(R.id.word);

				bundle.putString("com.handicapable.asltutor.dic", tv.getText().toString());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
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
