package com.example.sqlite;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
/*
 * Description:
 * 
 */
public class SQLiteExampe extends Activity {

	 DatabaseImplement database;
	 ArrayList<Student> studentList;
	 Student student;
	 int i = 0;
	 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Open database if exist, esle create it --> initialize
        // Our purpose is to open exist database
        database = new DatabaseImplement(this);
        database.open();
        
        studentList = database.queryDatabase();
        
        for (int index = 0; index < studentList.size(); index++){
        	Log.i("TAG", "Order = "  + studentList.get(index).getOrder() + 
        				 ", ID = "   + studentList.get(index).getId() +
        				 ", Name = " + studentList.get(index).getName() + "\n");
        }	
        
    }

	@Override
	protected void onResume() {
		super.onResume();
		database.open();
	}

	@Override
	protected void onStop() {
		super.onStop();
		
		database.deleteData();
		student = new Student(i++, "0920083", "Cao Xuan Phong");
		database.addToDatabase(student);
		
		database.close();
	}
    
    


}
