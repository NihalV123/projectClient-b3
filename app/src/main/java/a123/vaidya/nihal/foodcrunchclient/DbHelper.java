package a123.vaidya.nihal.foodcrunchclient;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

<<<<<<< HEAD
    private static final String DB_NAME="EDMTDev";
    private static final int DB_VER = 1;
    public static final String DB_TABLE="Task";
    public static final String DB_COLUMN = "TaskName";
=======
    private static final String DB_NAME="FoodCrunchDB.db";
    private static final int DB_VER=2;
    public static final String DB_TABLE="TASK";
    public static final String DB_COLUMN ="TaskName";
>>>>>>> old/master

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
<<<<<<< HEAD
        String query = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT NOT NULL);",DB_TABLE,DB_COLUMN);
=======
        String query = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL)",DB_TABLE,DB_COLUMN);
>>>>>>> old/master
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
<<<<<<< HEAD
        String query = String.format("DELETE TABLE IF EXISTS %s",DB_TABLE);
        db.execSQL(query);
        onCreate(db);

    }

    public void insertNewTask(String task){
        SQLiteDatabase db= this.getWritableDatabase();
=======
        String query = String.format("DELETE TABLE IF EXISTS %s ",DB_TABLE);
        db.execSQL(query);
        onCreate(db);
    }

    public void insertNewTask(String task){
        SQLiteDatabase db = this.getWritableDatabase();
>>>>>>> old/master
        ContentValues values = new ContentValues();
        values.put(DB_COLUMN,task);
        db.insertWithOnConflict(DB_TABLE,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
<<<<<<< HEAD
    }

    public void deleteTask(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE,DB_COLUMN + " = ?",new String[]{task});
        db.close();
    }
    public void clearTask(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "DELETE FROM Task";
        db.execSQL(query);
    }
    public ArrayList<String> getTaskList(){
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE,new String[]{DB_COLUMN},null,null,null,null,null);
        while(cursor.moveToNext()){
=======


    }

    public void deletetask (String task)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE,DB_COLUMN + " - ? ",new String[]{task});
        db.close();


    }

    public ArrayList<String> getTaskList(){
        ArrayList<String>taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE,new String[]{DB_COLUMN},null,null,null,null,null);
        while (cursor.moveToNext())
        {
>>>>>>> old/master
            int index = cursor.getColumnIndex(DB_COLUMN);
            taskList.add(cursor.getString(index));
        }
        cursor.close();
        db.close();
        return taskList;
    }
<<<<<<< HEAD
=======

>>>>>>> old/master
}
