package com.handicapable.asltutor;

import java.io.*;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.handicapable.asltutor.helper.DictionaryOpenHelper;

public class ViewSignActivity extends SherlockActivity {

	private SQLiteDatabase db;
	private ImageView img;
	private TextView text;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_sign);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Bundle bundle = getIntent().getExtras();

		DictionaryOpenHelper dbHelper = new DictionaryOpenHelper(this);
		db = dbHelper.openReadableDatabase();

		img = (ImageView) findViewById(R.id.signImage);
		text = (TextView) findViewById(R.id.signMeaning);

		showSign(bundle.getString("com.handicapable.asltutor.word"));
	}

	private void showSign(String word) {
		String where = "word = '" + word + "'";
		Cursor queryResult = db.query("dictionary", null, where, null, null, null, null);
		queryResult.moveToFirst();
		String signName = queryResult.getString(1);
		String mediaPath = queryResult.getString(2);

		InputStream sign = null;
		// Show image
		try {
			if (mediaPath.contains("sdcard")) sign = new BufferedInputStream(new FileInputStream(mediaPath));
			else sign = getAssets().open(mediaPath);

		} catch (Exception e) {
			// TODO: handle exception

		}
		img.setImageBitmap(BitmapFactory.decodeStream(sign));
		img.setMinimumWidth(120);
		img.setMinimumHeight(120);
		// Show text
		text.setText(signName);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_view_sign, menu);
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
