package com.example.horizontial_animation;

import java.util.ArrayList;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

public class MainActivity extends Activity {


	////////////////////////
	ImageView imageView_01;
	ImageView imageView_02;
	ImageView imageView_03;
	ImageView imageView_04;
	ImageView imageView_05;
	ImageView imageView_06;
	ImageView imageView_07;
	ImageView imageView_08;
	ImageView imageView_09;
	ImageView imageView_10;
	ImageView imageView_11;
	ImageView imageView_12;
	ImageView previous_image = null;
	int num_image = 12;
	
	int centerLeftEdge;
	int rangeToScrollToCenter;
	int itemWidth = 200;
	int currentPosition = 0;
	int screenWidth;
	////////////////////////////////////////////
	HorizontalScrollView hrscrollView;
	ArrayList<xLocation> arrlocation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	
	hrscrollView = (HorizontalScrollView)findViewById(R.id.horizontalscrollview);
	// Get the image view
	imageView_01 = (ImageView)findViewById(R.id.image_01);
	imageView_02 = (ImageView)findViewById(R.id.image_02);
	imageView_03 = (ImageView)findViewById(R.id.image_03);
	imageView_04 = (ImageView)findViewById(R.id.image_04);
	imageView_05 = (ImageView)findViewById(R.id.image_05);
	imageView_06 = (ImageView)findViewById(R.id.image_06);
	imageView_07 = (ImageView)findViewById(R.id.image_07);
	imageView_08 = (ImageView)findViewById(R.id.image_08);
	imageView_09 = (ImageView)findViewById(R.id.image_09);
	imageView_10 = (ImageView)findViewById(R.id.image_10);
	imageView_11 = (ImageView)findViewById(R.id.image_11);
	imageView_12 = (ImageView)findViewById(R.id.image_12);
	
	Display display = getWindowManager().getDefaultDisplay(); 
	screenWidth = display.getWidth();  // deprecated
	centerLeftEdge = screenWidth/2 - 100;

	hrscrollView.setOnTouchListener(new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {

			switch (event.getAction()) 
			{
				case MotionEvent.ACTION_UP:
					
					int itemIndex = FindItemNearestCenter();
					break;
	
				default:
					break;
			}
			
			return false;
		}
		
		
		private int FindItemNearestCenter() {
			
			int[] location = new int[2];
			arrlocation = new ArrayList<xLocation>();
			
			imageView_01.getLocationOnScreen(location);
			arrlocation.add(new xLocation(1, Math.abs(location[0] - centerLeftEdge)));
			
			imageView_02.getLocationOnScreen(location);
			arrlocation.add(new xLocation(2, Math.abs(location[0] - centerLeftEdge)));
			
			imageView_03.getLocationOnScreen(location);
			arrlocation.add(new xLocation(3, Math.abs(location[0] - centerLeftEdge)));
			
			imageView_04.getLocationOnScreen(location);
			arrlocation.add(new xLocation(4, Math.abs(location[0] - centerLeftEdge)));
			
			imageView_05.getLocationOnScreen(location);
			arrlocation.add(new xLocation(5, Math.abs(location[0] - centerLeftEdge)));
			
			imageView_06.getLocationOnScreen(location);
			arrlocation.add(new xLocation(6, Math.abs(location[0] - centerLeftEdge)));
			
			imageView_07.getLocationOnScreen(location);
			arrlocation.add(new xLocation(7, Math.abs(location[0] - centerLeftEdge)));
			
			imageView_08.getLocationOnScreen(location);
			arrlocation.add(new xLocation(8, Math.abs(location[0] - centerLeftEdge)));
			
			imageView_09.getLocationOnScreen(location);
			arrlocation.add(new xLocation(9, Math.abs(location[0] - centerLeftEdge)));
			
			imageView_10.getLocationOnScreen(location);
			arrlocation.add(new xLocation(10, Math.abs(location[0] - centerLeftEdge)));
			
			imageView_11.getLocationOnScreen(location);
			arrlocation.add(new xLocation(11, Math.abs(location[0] - centerLeftEdge)));
			
			imageView_12.getLocationOnScreen(location);
			arrlocation.add(new xLocation(12, Math.abs(location[0] - centerLeftEdge)));
			
			int nearestIndex = FindMin(arrlocation);
			Log.d("TAG", "nearestIndex = " + nearestIndex);
			
			return 0;
		}

	});
	
	}
	
	private int FindMin(ArrayList<xLocation> arrLocation) {
		 int minIndex = 0;
		 int minValue = arrLocation.get(0).getdistanceFromCenter();  
		  for(int i=1; i < arrLocation.size() ;i++){  
		    if(arrLocation.get(i).getdistanceFromCenter() < minValue){  
		      minValue = arrLocation.get(i).getdistanceFromCenter();
		      minIndex = arrLocation.get(i).getIndex();
		    }  
		  }  
		  
		  return minIndex; 
	}
	
	public void OnclickListener(View view)
	{
		switch (view.getId()) {
		case R.id.image_01:
			hrscrollView.smoothScrollBy(400 - centerLeftEdge - currentPosition, 0);
			currentPosition = 400 -centerLeftEdge;
			
			break;
		case R.id.image_02:
			hrscrollView.smoothScrollBy(600 - centerLeftEdge - currentPosition, 0);
			currentPosition = 600 -centerLeftEdge;
			break;
		case R.id.image_03:
			hrscrollView.smoothScrollBy(800 - centerLeftEdge - currentPosition, 0);
			currentPosition = 800 -centerLeftEdge;
		
			break;
		case R.id.image_04:
			hrscrollView.smoothScrollBy(1000 - centerLeftEdge - currentPosition, 0);
			currentPosition = 1000 -centerLeftEdge;
			
			break;
		case R.id.image_05:
			hrscrollView.smoothScrollBy(1200 - centerLeftEdge - currentPosition, 0);
			currentPosition = 1200 -centerLeftEdge;
			break;
		case R.id.image_06:
			hrscrollView.smoothScrollBy(1400 - centerLeftEdge - currentPosition, 0);
			currentPosition = 1400 -centerLeftEdge;
			break;
		case R.id.image_07:
			hrscrollView.smoothScrollBy(1600 - centerLeftEdge - currentPosition, 0);
			currentPosition = 1600 -centerLeftEdge;
			break;
		case R.id.image_08:
			hrscrollView.smoothScrollBy(1800 - centerLeftEdge - currentPosition, 0);
			currentPosition = 1800 -centerLeftEdge;
			break;
		case R.id.image_09:
			hrscrollView.smoothScrollBy(2000 - centerLeftEdge - currentPosition, 0);
			currentPosition = 2000 -centerLeftEdge;
			break;
		case R.id.image_10:
			hrscrollView.smoothScrollBy(2200 - centerLeftEdge - currentPosition, 0);
			currentPosition = 2200 -centerLeftEdge;
			break;
		case R.id.image_11:
			hrscrollView.smoothScrollBy(2400 - centerLeftEdge - currentPosition, 0);
			currentPosition = 2400 -centerLeftEdge;
			break;
		case R.id.image_12:
			hrscrollView.smoothScrollBy(2600 - centerLeftEdge - currentPosition, 0);
			currentPosition = 2600 -centerLeftEdge;
			break;

		default:
			break;
		}
	}
}

class xLocation {
	private int index;
	private int distanceFromCenter;
	
	public xLocation(int index, int distanceFromCenter)
	{
		this.index = index;
		this.distanceFromCenter = distanceFromCenter;
	}
	
	public int getIndex()
	{
		return this.index;
	}
	
	public int getdistanceFromCenter()
	{
		return this.distanceFromCenter;
	}
}