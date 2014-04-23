package com.example.sqlite;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseImplement {
	private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    
    public DatabaseImplement (Context context){
    	dbHelper = new DatabaseHelper(context);
    }
    
    // Open/create database
    public void open () throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    
    // Close database
    public void close () {
    	dbHelper.close();
    }
    
    // Insert item into database
    public void addToDatabase (Student student) {//edit 

	    ContentValues values = new ContentValues();
	
	    values.put(DatabaseHelper.COLUMN_ORDER, student.getOrder());
	    values.put(DatabaseHelper.COLUMN_ID   , student.getId());
	    values.put(DatabaseHelper.COLUMN_NAME , student.getName());
	    
	    database.insert(DatabaseHelper.TABLE_NAME, null, values);
    }
    
    public ArrayList<Student> queryDatabase(){//edit
        ArrayList<Student> list = new ArrayList<Student>();
        
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME,
                                 DatabaseHelper.ALL_DATABASE_COLUMN, null, null, null, null, null);
        cursor.moveToFirst();
		
        while (!cursor.isAfterLast()) {
			 Student item = parseStudent(cursor);
			 list.add(item);
			 cursor.moveToNext();
		}
		
		 cursor.close();
		 return list;
	}

	private Student parseStudent(Cursor cursor) {
		Student student = new Student( cursor.getInt(0), cursor.getString(1), cursor.getString(2));
		return student;
	}
	
	//Delete table
	public void deleteData (){
		database.execSQL("DELETE FROM " + DatabaseHelper.TABLE_NAME);
	}


}
