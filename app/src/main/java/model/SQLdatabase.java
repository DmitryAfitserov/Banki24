package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Dmitry on 17.09.2016.
 */
public class SQLdatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DATA_SETTINGS";
    public static final String BEL_TABLE = "BEL_TABLE";
    public static final String RUS_TABLE = "RUS_TABLE";
    public static final String KEY_ID = "_id";
    public static final String KEY_CHAR_CODE = "char_code";
    public static final String KEY_IS_SELECTED = "is_selected";
    public static final String CREATE_BEL_TABLE = "CREATE TABLE " + BEL_TABLE
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_CHAR_CODE + " TEXT, "
            + KEY_IS_SELECTED + " INTEGER)";
    public static final String CREATE_RUS_TABLE = "CREATE TABLE " + RUS_TABLE
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_CHAR_CODE + " TEXT, "
            + KEY_IS_SELECTED + " INTEGER)";




    public SQLdatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BEL_TABLE);
        db.execSQL(CREATE_RUS_TABLE);
        insert(db, "USD", true, "BEL");
        insert(db, "EUR", true, "BEL");
        insert(db, "RUB", true, "BEL");
        insert(db, "USD", true, "RUS");
        insert(db, "EUR", true, "RUS");




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BEL_TABLE);
        onCreate(db);
    }

    private void insert(SQLiteDatabase db, String charcode, Boolean isselected, String nameTable){
        ContentValues contentValue = new ContentValues();
        //  contentValue.put(mydatabase.KEY_ID, );
        contentValue.put(KEY_CHAR_CODE, charcode);
        if(isselected){
            contentValue.put(KEY_IS_SELECTED, 1);

        } else { contentValue.put(KEY_IS_SELECTED, 0);}

        if(nameTable.equals("BEL")){
            db.insert(BEL_TABLE, null, contentValue);

        } else db.insert(RUS_TABLE, null, contentValue);
    }
}
