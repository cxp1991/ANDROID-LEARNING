package com.example.horizontial_animation;

import java.util.ArrayList;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
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
	int num_image = 12;
	
	int centerLeftEdge, centerRightEdge;
	int itemWidth = 200;
	int screenWidth;
	////////////////////////////////////////////
	HorizontalScrollView hrscrollView;
	ArrayList<xLocation> arrlocation;
	private int initialPosition;
	int indexNereastCenter;
    
    /* To get Scroll direction*/
    float startPosition;
    float stopPosition;
    int scrollDirection;
	
	private int MAX_LEFT  = -1;
	private int MAX_RIGHT = -2;
	private int SCROLL_FROM_RIGHT_TO_LEFT = 0x01;
	private int SCROLL_FROM_LEFT_TO_RIGHT = 0x02;
	
	ArrayList<ImageView> imvList;
	
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
		
		/* Save to arraylist to calculate easily */
		imvList = new ArrayList<ImageView>();
		imvList.add(imageView_01);
		imvList.add(imageView_02);
		imvList.add(imageView_03);
		imvList.add(imageView_04);
		imvList.add(imageView_05);
		imvList.add(imageView_06);
		imvList.add(imageView_07);
		imvList.add(imageView_08);
		imvList.add(imageView_09);
		imvList.add(imageView_10);
		imvList.add(imageView_11);
		imvList.add(imageView_12);
		
		/* Get Center location */
		Display display = getWindowManager().getDefaultDisplay(); 
		screenWidth = display.getWidth();  // deprecated
		centerLeftEdge = screenWidth/2 - 100;
		centerRightEdge = screenWidth/2 + 100;
		
		/* Set 1st item at center at the beginning */
		ObjectAnimator animator = ObjectAnimator.ofInt(hrscrollView, "scrollX", 140);
		animator.setDuration(1); // Fast as fast possible
		animator.start();
		
		/* Scroll */
		hrscrollView.setOnTouchListener(new OnTouchListener() 
		{
			
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				switch (event.getAction()) 
				{
					/* Just wait to ACTION_UP, we can find scrolled direction
					 * Don't need to wait scroll stop
					 * */
					case MotionEvent.ACTION_DOWN:
						startPosition = event.getX();
						break;
					
					case MotionEvent.ACTION_UP:
						stopPosition = event.getX();
						if (stopPosition - startPosition >= 0)
							scrollDirection = SCROLL_FROM_LEFT_TO_RIGHT;
						else
							scrollDirection = SCROLL_FROM_RIGHT_TO_LEFT;

						initialPosition = hrscrollView.getScrollX();
						startCheckStopScroll();
						break;
						
					default:
						break;
				}
				
				return false;
			}
		});
	}
	
	private void startCheckStopScroll() 
	{
		Thread checkStopScrollThread = new Thread(startCheckRunnable);
		checkStopScrollThread.start();
	}
	
	/*
	 * Wait until Horizontal stop scroll then 
	 * smooth scroll to wanted position
	 * */
	
	Runnable startCheckRunnable = new Runnable() {
		
		@Override
		public void run() {
			while (true)
			{
				Delay(100);
				int newPosition = hrscrollView.getScrollX();
				
				/* Horizontal Stop scroll */
				if(initialPosition - newPosition == 0)
				{
					Log.e("TAG", "Stop scroll");
					indexNereastCenter = FindItemNearestCenter();
					Log.d("TAG", "Nearestindex = " + indexNereastCenter);
					
					/* 
					 * Smooth scroll to wanted position
					 * */
					runOnUiThread(new Runnable(){

						@Override
						public void run() {
							int value = 0;
							
							if(indexNereastCenter > 0)
								value = indexNereastCenter*200-60;
							else if (indexNereastCenter == MAX_LEFT)
								value = 1*200-60;
							else if (indexNereastCenter == MAX_RIGHT)
								value = 12*200-60;
							
							ObjectAnimator animator=ObjectAnimator.ofInt(hrscrollView, 
										"scrollX", value);
							
					 		animator.setDuration(300);
					 		animator.start();
						}
						
					});
					
					break;
				}
				else
				{
					Delay(100);
					initialPosition = hrscrollView.getScrollX();
				}
			}
		}

	};
	
	/* Find item is nearest center,
	 * Also depend on scrolling direction
	 * */
	
	private int FindItemNearestCenter() {
		
		int[] location = new int[2];
		arrlocation = new ArrayList<xLocation>();
		
		/* Scroll from left to right */
		if (scrollDirection == SCROLL_FROM_LEFT_TO_RIGHT)
		{
			/* Compare RightEdge of item with RightEdge of center */
			for (int i = 0; i < imvList.size(); i++)
			{
				imvList.get(i).getLocationOnScreen(location);
				if ( ((location[0] + 200) <= centerRightEdge) && (location[0] >= 0))
				{
					arrlocation.add(new xLocation(i+1, (location[0] + 200 - centerRightEdge)));
				}
			}

			/* No item satisfied -> minimum position*/
			if(arrlocation.size() == 0)
				return MAX_LEFT;
		}
		
		/* Scroll from right to left */
		else if (scrollDirection == SCROLL_FROM_RIGHT_TO_LEFT)
		{
			/* Compare LeftEdge of item with LeftEdge of center */
			for (int i = 0; i < imvList.size(); i++)
			{
				imvList.get(i).getLocationOnScreen(location);
				if (location[0] >= centerLeftEdge)
				{
					arrlocation.add(new xLocation(i+1, location[0] - centerLeftEdge));
				}
			}

			/* No item satisfied -> maximum position*/
			if(arrlocation.size() == 0)
				return MAX_RIGHT;
		}
		
		int nearestIndex;
		nearestIndex = FindMin(arrlocation);
		return nearestIndex;
	}
	
	/* Find min value */
	private int FindMin(ArrayList<xLocation> arrLocation) {
		
		 int minIndex =arrLocation.get(0).getIndex();
		 int minValue = arrLocation.get(0).getdistanceFromCenter();  
		  for(int i=1; i < arrLocation.size() ;i++){  
		    if(arrLocation.get(i).getdistanceFromCenter() < minValue){  
		      minValue = arrLocation.get(i).getdistanceFromCenter();
		      minIndex = arrLocation.get(i).getIndex();
		    }  
		  }  
		  
		  return minIndex; 
	}
	
	/*
	 * Delay using Thread
	 * */
	private void Delay(int time) 
	{
		try 
		{
			Thread.sleep(time);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}			
	}

}

/* Object save index & distance from center position of each item */
class xLocation 
{
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