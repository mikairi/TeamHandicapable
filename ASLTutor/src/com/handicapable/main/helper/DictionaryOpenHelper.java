package com.handicapable.main.helper;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public DictionaryOpenHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "dictionary";
    private static final String DB_PATH = "/data/data/com.handicapable.main/databases/";
    
    private final Context context;
    
    public DictionaryOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }
    
    private void copyDatabase() {
        InputStream inDB = context.getAssets().open(DB_NAME);
        String outFilePath = DB_PATH + DB_NAME;
        OutputStream outDB = new FileOutputStream(outFilePath);
        
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0){
          myOutput.write(buffer, 0, length);
        }
        
        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        copyDatabase();
    }
}