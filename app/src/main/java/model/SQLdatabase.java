package model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Dmitry on 17.09.2016.
 */
public class SQLdatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "DATA_SETTINGS";
    public static final String BEL_TABLE = "BEL_TABLE";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name_currency";
    public static final String KEY_CHAR_CODE = "char_code";
    public static final String KEY_IS_SELECTED = "is_selected";
    public static final String CREATE_BEL_TABLE = "CREATE TABLE " + BEL_TABLE
            + "(" + KEY_ID + "key" + KEY_NAME + "name" + KEY_CHAR_CODE + "char code"
            + KEY_IS_SELECTED + "selected" + ")";




    public SQLdatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_BEL_TABLE);
        Log.d("EEE", "da sa");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BEL_TABLE);
        onCreate(db);
    }
}
