package com.handicapable.asltutor.helper;

import java.io.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DictionaryOpenHelper extends SQLiteOpenHelper {

	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "dictionary";
	private static final String DB_PATH = "/data/data/com.handicapable.asltutor/databases/";

	private final Context context;

	public DictionaryOpenHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
	}

	private void copyDatabase() throws IOException {
		InputStream inDB = context.getAssets().open(DB_NAME + ".sqlite");
		String outFilePath = DB_PATH + DB_NAME;
		OutputStream outDB = new FileOutputStream(outFilePath);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inDB.read(buffer)) > 0) {
			outDB.write(buffer, 0, length);
		}

		// Close the streams
		outDB.flush();
		outDB.close();
		inDB.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			copyDatabase();
			Log.d("DictionaryOpenHelper", "Succesfully copied database");
		} catch (IOException e) {
			throw new Error("Error copying database");
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {

	}
}
