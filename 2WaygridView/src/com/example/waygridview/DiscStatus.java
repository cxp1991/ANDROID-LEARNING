package com.example.waygridview;

public class DiscStatus {

	private int id;
	private int position;
	private boolean status;
	
	
	public DiscStatus (int id, int position, boolean status) {
		setPosition(position);
		setStatus(status);
		setId(id);
	}
	
	public DiscStatus (int position, boolean status) {
		setPosition(position);
		setStatus(status);
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setPosition(int position){
		this.position = position;
	}
	
	public void setStatus(boolean status){
		this.status = status;
	}
	
	public int getId(){
		return this.id;
	}

	public int getPosition(){
		return this.position;
	}
	
	public boolean getStatus(){
		return this.status;
	}

}
