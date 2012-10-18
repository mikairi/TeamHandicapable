package com.handicapable.asltutor;

import android.annotation.TargetApi;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.handicapable.asltutor.helper.DictionaryOpenHelper;

@TargetApi(11)
public class ViewSignActivity extends Activity {

	private SQLiteDatabase db;
	private ImageView img;
	private TextView text;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_sign);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		Bundle bundle = getIntent().getExtras();

		DictionaryOpenHelper dbHelper = new DictionaryOpenHelper(this);
		db = dbHelper.openReadableDatabase();

		img = (ImageView) findViewById(R.id.signImage);
		text = (TextView) findViewById(R.id.signMeaning);

		showSign(bundle.getString("com.handicapable.asltutor.dic"), bundle.getString("com.handicapable.asltutor.word"));
	}

	private void showSign(String dic, String word) {
		String where = "word = '" + word + "'";
		Cursor queryResult = db.query(dic, null, where, null, null, null, null);

		String signName = queryResult.getString(1);
		String mediaPath = queryResult.getString(2);

		img.setImageURI(Uri.parse(mediaPath));
		text.setText(signName);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_view_sign, menu);
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
