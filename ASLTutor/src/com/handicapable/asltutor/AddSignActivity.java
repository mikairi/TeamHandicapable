package com.handicapable.asltutor;

import java.io.*;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
	private Bitmap bmp;
	private String newImageFilePath;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_sign);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mImageView = (ImageView) findViewById(R.id.newImage);
		newImageFilePath = (Environment.getExternalStorageDirectory().toURI().toString() + "\\temp.png");
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
		} else if (resultCode == RESULT_OK && requestCode == 55) {
			bmp = BitmapFactory.decodeFile(newImageFilePath);
			mImageView.setImageBitmap(bmp);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void addSign(View view) {
		TextView meaning = (TextView) findViewById(R.id.newWord);

		Toast toast;
		if (bmp != null) saveCapturedImage(meaning);
		try {
			addToDictionary(meaning);
			toast = Toast.makeText(getApplicationContext(), "You have added a new sign to your dictionary!",
					Toast.LENGTH_SHORT);
			toast.show();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			toast = Toast.makeText(getApplicationContext(), "Unable to add sign to dictionary", Toast.LENGTH_SHORT);
			toast.show();
		}

		finish();
	}

	public void selectImage(View view) {
		Intent pickImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
		pickImageIntent.setType("image/*");

		startActivityForResult(pickImageIntent, SELECT_IMAGE);
	}

	public void takePicture(View view) {
		File imageFile = new File(newImageFilePath);
		Uri imageFileUri = Uri.fromFile(imageFile);
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);
		startActivityForResult(takePictureIntent, 55);
	}

	private void saveCapturedImage(TextView meaning) {
		try {
			FileOutputStream fos = openFileOutput(meaning.getText().toString(), Context.MODE_PRIVATE);
			bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addToDictionary(TextView meaning) {
		DictionaryOpenHelper dbHelper = new DictionaryOpenHelper(this);
		db = dbHelper.openWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("word", meaning.getText().toString());
		values.put("media_path", imageuri.toString());
		db.insert("dictionary", null, values);
	}
}
