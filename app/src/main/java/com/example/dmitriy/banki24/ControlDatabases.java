package com.example.dmitriy.banki24;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

import model.SQLdatabase;

/**
 * Created by Dmitry on 18.09.2016.
 */
public class ControlDatabases {

    public class SQLController {

        private SQLdatabase mydatabase;
        private Context mycontext;
        private SQLiteDatabase database;

        public SQLController(Context c) {
            mycontext = c;
        }

        public SQLController open() throws SQLException {
            mydatabase = new SQLdatabase(mycontext);
            database = mydatabase.getWritableDatabase();
            return this;

        }

        public void close() {
            mydatabase.close();
        }

        public void insert() {
            ContentValues contentValue = new ContentValues();
            contentValue.put(mydatabase.KEY_ID, );
            contentValue.put(mydatabase.KEY_NAME, );
            contentValue.put(mydatabase.KEY_CHAR_CODE, );
            contentValue.put(mydatabase.KEY_IS_SELECTED, );
            database.insert(mydatabase.BEL_TABLE, null, contentValue);
        }

//        public Cursor fetch() {
//            String[] columns = new String[] { mydatabase._ID, mydatabase.TODO_SUBJECT,
//                    mydatabase.TODO_DESC };
//            Cursor cursor = database.query(mydatabase.TABLE_NAME, columns, null,
//                    null, null, null, null);
//            if (cursor != null) {
//                cursor.moveToFirst();
//            }
//            return cursor;
//        }

//        public int update(long _id, String name, String desc) {
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(mydatabase.TODO_SUBJECT, name);
//            contentValues.put(mydatabase.TODO_DESC, desc);
//            int i = database.update(mydatabase.TABLE_NAME, contentValues,
//                    mydatabase._ID + " = " + _id, null);
//            return i;
//        }

        public void delete(long _id) {
            database.delete(mydatabase.BEL_TABLE, mydatabase.KEY_ID + "=" + _id, null);
        }
    }
}
