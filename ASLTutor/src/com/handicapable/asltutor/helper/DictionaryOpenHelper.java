package com.handicapable.asltutor.helper;

import java.io.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DictionaryOpenHelper extends SQLiteOpenHelper {

	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "dictionary";
	private static final String DB_PATH = "/data/data/com.handicapable.asltutor/databases/";

	private final Context context;
	private SQLiteDatabase db;

	public DictionaryOpenHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;

		try {
			createDatabase();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createDatabase() throws IOException {
		if (!dbExists()) {
			SQLiteDatabase newDB = this.getReadableDatabase();
			try {
				copyDatabase();
			} catch (IOException e) {
				e.printStackTrace();
			}
			newDB.close();
		}
	}

	private boolean dbExists() {
		File dbFile = new File(DB_PATH + DB_NAME);
		return dbFile.exists();
	}

	public void copyDatabase() throws IOException {
		InputStream inDB = context.getAssets().open("dictionary.sqlite");
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

	public static SQLiteDatabase openReadableDatabase() {
		return SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READONLY);
	}

	public static SQLiteDatabase openWritableDatabase() {
		return SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {

	}
}
