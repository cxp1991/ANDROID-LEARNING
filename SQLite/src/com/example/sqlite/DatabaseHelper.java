package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{
	
	 // without Environment.getExternalStorageDirectory()
	 // database will saved in internal storage
	 public static final String DATABASE_NAME = "SRUDENT.db"; //edit
     public static final int    DATABASE_VERSION = 1;
     
     // Table name and all column
     public static final String TABLE_NAME       = "STUDENT";	//edit  		
     public static final String COLUMN_ORDER     = "_order";	//edit			
     public static final String COLUMN_ID        = "_id";		//edit
     public static final String COLUMN_NAME      = "_name";		//edit
     public static final String ALL_DATABASE_COLUMN[] = {			
    	 COLUMN_ORDER, COLUMN_ID, COLUMN_NAME 
     };
     
     /*
      * CREATE TABLE table_name
	  * (
	  *		column_name1 data_type(size),
	  *		column_name2 data_type(size),
	  *		column_name3 data_type(size),
	  *		....
	  *	);
      */
     private static final String TABLE_CREATE =      		
		 "CREATE TABLE " + TABLE_NAME
		+ "("
    			+ COLUMN_ORDER + " integer, "
    			+ COLUMN_ID    + " text, "
    			+ COLUMN_NAME  + " text"
    		
		+ ");" ;
	
	/*
	 * Create a helper object to create, open, and/or manage a database. 
	 * This method always returns very quickly. The database is not actually 
	 * created or opened until one of getWritableDatabase() or getReadableDatabase() is called.
	 * 
	 *	Parameters:
     *	context: to use to open or create the database
     *	name:    of the database file, or null for an in-memory database
     *	factory: to use for creating cursor objects, or null for the default
     *	version: number of the database (starting at 1); if the database is older, 
     *	onUpgrade(android.database.sqlite.SQLiteDatabase,int,int) will be used to upgrade the database;
     *	if the database is newer, onDowngrade(android.database.sqlite.SQLiteDatabase,int,int)
     * 	will be used to downgrade the database
	 */
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/*
	 *  Called when the database is created for the first time. 
	 *  This is where the creation of tables and the initial population of the tables should happen.
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
		
	}

	
	/*
	 *  Called when the database needs to be upgraded. The implementation should use this method to drop tables,
	 *  add tables, or do anything else it needs to upgrade to the new schema version.
	 *	The SQLite ALTER TABLE documentation can be found here. If you add new columns you 
	 *	can use ALTER TABLE to insert them into a live table. If you rename or remove columns you can 
	 *	use ALTER TABLE to rename the old table, then create the new table and then populate the new table 
	 *	with the contents of the old table.
	 *	This method executes within a transaction. If an exception is thrown, all changes will automatically be rolled back
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
