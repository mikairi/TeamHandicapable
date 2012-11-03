package com.handicapable.asltutor;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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

		showSign(bundle.getString("com.handicapable.asltutor.dic"),
				bundle.getString("com.handicapable.asltutor.word"));
	}

	private void showSign(String dic, String word) {
		String where = "word = '" + word + "'";
		Cursor queryResult = db.query(dic, null, where, null, null, null, null);
		queryResult.moveToFirst();
		String signName = queryResult.getString(1);
		String mediaPath = queryResult.getString(2);
		InputStream sign = null;
		// Show image
		try {
			for (String S : getAssets().list("Alphabet"))
				System.out.println(S);

			if (mediaPath.contains("sdcard"))
				sign = new BufferedInputStream(new FileInputStream(mediaPath));
			else
				sign = getAssets().open(mediaPath);

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
