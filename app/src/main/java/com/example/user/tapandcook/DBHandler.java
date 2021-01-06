package com.example.user.tapandcook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    //info of database
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="UserDB.db";
    private static final String TABLE_NAME="User";
    private static final String COLUMN_ID="UserID";
    private static final String COLUMN_NAME="Username";
    private static final String COLUMN_PASSWORD="Password";
    private static final String COLUMN_EMAIL="Email";

    //initialize database
    public DBHandler(Context context) {
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
    }

    //create table to store user's information
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COLUMN_NAME+" TEXT, "+COLUMN_PASSWORD+" TEXT, "+COLUMN_EMAIL+" TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    //insert value to database
    public void insertHandler(User user) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(COLUMN_NAME,user.getUsername());
        values.put(COLUMN_PASSWORD,user.getPassword());
        values.put(COLUMN_EMAIL,user.getEmail());

        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public boolean loginHandler(String username, String password){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_NAME+" =?"+" AND "+ COLUMN_PASSWORD+"=?",new String[]{username,password});

        //if username and password exist
        if(cursor!=null&& cursor.moveToFirst()){
            if(cursor.getCount()>0){
                return true;
            }
        }
        return false;
    }

    public boolean checkHandler(String email){

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_EMAIL+" =?",new String[]{email});

        //email already exists
        if(cursor!=null&& cursor.moveToFirst()){
            if(cursor.getCount()>0){

                return true;
            }
        }

            return false;
    }

    public Integer checkuserID(String name){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_NAME+" =?",new String[]{name});

        if(cursor!=null&& cursor.moveToFirst()){
            if(cursor.getCount()>0){
                int ID=cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                return ID;
            }
        }

        return 0;
    }


}
