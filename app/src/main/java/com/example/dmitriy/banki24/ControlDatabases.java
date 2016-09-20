package com.example.dmitriy.banki24;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import model.SQLdatabase;

/**
 * Created by Dmitry on 18.09.2016.
 */
public class ControlDatabases {


        private SQLdatabase mydatabase;
        private Context mycontext;
        private SQLiteDatabase database;

        public ControlDatabases(Context c) {
            mycontext = c;
        }

        public ControlDatabases open() throws SQLException {
            mydatabase = new SQLdatabase(mycontext);
            database = mydatabase.getWritableDatabase();
            return this;

        }

        public void close() {
            mydatabase.close();
        }

        public void insert(String charcode, Boolean isselected, String nameTable) {
            ContentValues contentValue = new ContentValues();
            contentValue.put(mydatabase.KEY_CHAR_CODE, charcode);
            if(isselected){
                contentValue.put(mydatabase.KEY_IS_SELECTED, 1);

            } else { contentValue.put(mydatabase.KEY_IS_SELECTED, 0);}

            database.insert(nameTable, null, contentValue);


        }

        public Cursor query() {
            Cursor cursor = database.query(mydatabase.BEL_TABLE, null, null,
                    null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
            }
            return cursor;
        }

        public Cursor queryCharcodeSelected(String nameTable){
            String[] colums = new String[] {mydatabase.KEY_CHAR_CODE};
            Cursor cursor;
            if(nameTable.equals(mydatabase.BEL_TABLE)){
                cursor = database.query(mydatabase.BEL_TABLE, colums,
                        mydatabase.KEY_IS_SELECTED + " = " + 1 , null, null,null,null);
            } else{
                cursor = database.query(mydatabase.RUS_TABLE, colums,
                        mydatabase.KEY_IS_SELECTED + " = " + 1 , null, null,null,null);
            }
            if(cursor != null){
                cursor.moveToFirst();
            }
            return cursor;
        }

        public void clearBelTable(){
            database.execSQL("delete from "+ mydatabase.BEL_TABLE);
        }
        public void clearRusTable(){
            database.execSQL("delete from "+ mydatabase.RUS_TABLE);
        }

}
