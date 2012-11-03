package com.handicapable.asltutor;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.handicapable.asltutor.helper.DictionaryOpenHelper;

public class AddSignActivity extends SherlockActivity {

	public static final int SELECT_IMAGE = 1;

	private ImageView mImageView;
	private String imagePath;
	private SQLiteDatabase db;
	private Uri imageuri;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_sign);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		DictionaryOpenHelper dbHelper = new DictionaryOpenHelper(this);
		db = dbHelper.openReadableDatabase();

		mImageView = (ImageView) findViewById(R.id.newImage);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_add_sign, menu);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == SELECT_IMAGE) {
			Uri resultUri = data.getData();
			Log.d("AddSign", data.resolveType(this));
			if (resultUri != null) {
				Cursor cursor = getContentResolver().query(resultUri,
						new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null);
				cursor.moveToFirst();

				imagePath = cursor.getString(0);
				imageuri = Uri.parse(imagePath);
				mImageView.setImageURI(Uri.parse(imagePath));
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void addSign(View view) {
		TextView meaning = (TextView) findViewById(R.id.newWord);

		ContentValues values = new ContentValues();
		values.put("word", meaning.getText().toString());
		values.put("media_path", imageuri.toString());
		db.insert("user_dictionary", null, values);

		db.close();
		Toast toast = Toast.makeText(getApplicationContext(), "You have added a new sign to your dictionary!",
				Toast.LENGTH_SHORT);
		toast.show();
		finish();
	}

	public void selectImage(View view) {
		Intent pickImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
		pickImageIntent.setType("image/*");

		// Code for taking pictures, which does not work atm
		// Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		//
		// String pickTitle = "Select or Take a new Picture";
		// Intent chooseIntent = Intent.createChooser(pickImageIntent, pickTitle);
		// chooseIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { takePhotoIntent });

		startActivityForResult(pickImageIntent, SELECT_IMAGE);
	}

}
