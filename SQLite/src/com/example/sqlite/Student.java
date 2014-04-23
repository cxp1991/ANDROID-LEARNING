package com.example.sqlite;

public class Student {
		
	/*
	 * 1 0920083 cao xuan phong
	 * 2 0920084 nguyenhoaiphong
	 */
	
	private int    _order;
	private String id;
	private String name;
	
	public Student(int _order, String id, String name){
		this._order = _order;
		this.id = id;
		this.name = name;
	}
	
	public int getOrder(){
		return this._order;
	}
	
	public String getId (){
		return this.id;
	}
	
	public String getName (){
		return this.name;
	}

}
