package com.example.horizontial_animation;

import java.util.ArrayList;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 
 * @author cxphong
 * @version 0.1
 * 
 * 	Saturday, 10/5/2014
 * 
 * License: ???
 *
 */

/*
 * How does program work?
 * 
 * 1. Wait until stop scroll
 * 2. Find scrolled direction (left to right of vice versa)
 * 3. Depends on direction -> find nearest item  to center.
 * 4. Ok, smoothscrollto() to wanted position
 * 
 * */

public class MainActivity extends Activity {


	////////////////////////
	/* I like number 12 */
	private ImageView 	imageView_01, imageView_02, imageView_03,
						imageView_04, imageView_05, imageView_06, imageView_07, 
						imageView_08, imageView_09, imageView_10 ,imageView_11, 
						imageView_12;
	
	private ImageView centerImv;
	private int centerLeftEdge, centerRightEdge;
	
	/* In xml layout file  i set image width is 100dp*/
	private int itemWidth;
	private int screenWidth;
	private int itemPadding; //dp
	////////////////////////////////////////////
	HorizontalScrollView hrscrollView;
	ArrayList<xLocation> arrlocation;
	private int initialPosition;
	private int indexNereastCenter = 1;
	private int pointDownIndex;
	private int previousCenterIndex = 1;
    
    /* To get Scroll direction*/
    private float startPosition;
    private float stopPosition;
    private int scrollDirection;
    
    private int ANIMATION_DURATION = 200; // micro secon
	private int SCROLL_FROM_RIGHT_TO_LEFT = 0x01;
	private int SCROLL_FROM_LEFT_TO_RIGHT = 0x02;
	private int ONCLICK_THREADHOLD; //pixel
	private int STOP_DISTANCE_THREADHOLD; //pixel
	
	private ArrayList<ImageView> imvList;
	
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
		
		/* Convert 100dp [width in XML] to pixel */
		Resources r = getResources();
		itemWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics());
		//Log.d("TAG","itemWidth = " + itemWidth);
		
		/* Convert 5dp [width in XML] to pixel */
		itemPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics());
		ONCLICK_THREADHOLD = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, r.getDisplayMetrics());
		STOP_DISTANCE_THREADHOLD = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, r.getDisplayMetrics());
		
		/* Get Center location */
		Display display = getWindowManager().getDefaultDisplay(); 
		screenWidth = display.getWidth();  // deprecated but it works, cool
		centerLeftEdge = screenWidth/2 - itemWidth/2;
		centerRightEdge = screenWidth/2 + itemWidth/2;
		
		/* Set width of header & footer again depends on width of screen */
		LinearLayout headerLinearLayout = (LinearLayout) findViewById(R.id.header);
		LinearLayout footerLinearLayout = (LinearLayout) findViewById(R.id.footer);
		headerLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(centerLeftEdge, LayoutParams.MATCH_PARENT));
		footerLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(centerLeftEdge, LayoutParams.MATCH_PARENT));
		
		/* Highlight center item */
		imvList.get(0).setImageResource(R.drawable.balloon_light);
		//imvList.get(0).setPadding(0, 0, 0, 0);
		
		/* Scroll or Onclick */
		hrscrollView.setOnTouchListener(new OnTouchListener() 
		{
			
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				switch (event.getAction()) 
				{
					/* Just wait to ACTION_UP, we can find scrolled direction
					 * Don't need to wait scroll stop
					 * 
					 * Or event is onclick, not ontouch due to OnClick
					 * conflicts with onToch in action_down
					 * */
					case MotionEvent.ACTION_DOWN:
						startPosition = event.getX();
						break;
					
					case MotionEvent.ACTION_UP:
						stopPosition = event.getX();
						//Log.d("TAG", "diff = " + (stopPosition - startPosition));
						if (Math.abs(stopPosition - startPosition) <= ONCLICK_THREADHOLD)
						{
							onClickEvent();
						}
						else
						{
							onScrollEvent();
						}
						break;
						
					default:
						break;
				}
				
				return false;
			}
		});
	
	}
	
	private void onScrollEvent() 
	{
		if (stopPosition - startPosition >= 0)
			scrollDirection = SCROLL_FROM_LEFT_TO_RIGHT;
		else
			scrollDirection = SCROLL_FROM_RIGHT_TO_LEFT;
		
		initialPosition = hrscrollView.getScrollX();
		Thread checkStopScrollThread = new Thread(checkScrollStopEvent);
		checkStopScrollThread.start();
	}

	private void onClickEvent() 
	{
		int[] location = new int[2];
		pointDownIndex = -1;
		
		/* Find all items location */
		for (int i = 0; i < imvList.size(); i++)
		{
			imvList.get(i).getLocationOnScreen(location);
			if ((stopPosition >= location[0]) && (stopPosition <= (location[0] + itemWidth)))
			{
				pointDownIndex = i + 1;
				break;
			}
		}
					
		ObjectAnimator animator=ObjectAnimator.ofInt(hrscrollView, 
					"scrollX", (pointDownIndex-1)*itemWidth);
		
		animator.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				/* Un-Highlight previous center item */
		 		centerImv = imvList.get(previousCenterIndex - 1);
		 		centerImv.setImageResource(R.drawable.balloon_dark);
		 		//centerImv.setPadding(itemPadding, itemPadding, itemPadding, itemPadding);
		 		
		 		/* Highlight center item */
		 		centerImv = imvList.get(pointDownIndex-1);
		 		centerImv.setImageResource(R.drawable.balloon_light);
		 		//centerImv.setPadding(0, 0, 0, 0);
		 		previousCenterIndex = pointDownIndex;
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
			}
		});
		
 		animator.setDuration(ANIMATION_DURATION);
 		animator.start();
	}

	/*
	 * Wait until Horizontal stop scroll then 
	 * smooth scroll to wanted position
	 * */
	Runnable checkScrollStopEvent = new Runnable() 
	{
		
		@Override
		public void run() {
			while (true)
			{
				Delay(100);
				int newPosition = hrscrollView.getScrollX();
				
				/* Horizontal Stop scroll */
				if(initialPosition - newPosition == 0)
				{
					//Log.e("TAG", "Stop scroll");
					indexNereastCenter = FindItemNearestCenter();
					//Log.d("TAG", "Nearestindex = " + indexNereastCenter);
					
					/* 
					 * Smooth scroll to wanted position
					 * */
					runOnUiThread(new Runnable(){

						@Override
						public void run() {
							int value = 0;
							
							if(indexNereastCenter > 0)
								value = (indexNereastCenter-1)*itemWidth;
							
							/* Cool, I can change speed of scroll using this animation */
							ObjectAnimator animator=ObjectAnimator.ofInt(hrscrollView, 
										"scrollX", value);
							animator.addListener(new AnimatorListener() {
								
								@Override
								public void onAnimationStart(Animator animation) {
								}
								
								@Override
								public void onAnimationRepeat(Animator animation) {
								}
								
								@Override
								public void onAnimationEnd(Animator animation) {
									/* Un-Highlight previous center item */
							 		centerImv = imvList.get(previousCenterIndex - 1);
							 		centerImv.setImageResource(R.drawable.balloon_dark);
							 		//centerImv.setPadding(itemPadding, itemPadding, itemPadding, itemPadding);
							 		
							 		/* Highlight center item */
							 		centerImv = imvList.get(indexNereastCenter-1);
							 		centerImv.setImageResource(R.drawable.balloon_light);
							 		//centerImv.setPadding(0, 0, 0, 0);
							 		previousCenterIndex = indexNereastCenter;
								}
								
								@Override
								public void onAnimationCancel(Animator animation) {
								}
							});
							
					 		animator.setDuration(ANIMATION_DURATION);
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
	
	private int FindItemNearestCenter() 
	{
		
		int[] location = new int[2];
		arrlocation = new ArrayList<xLocation>();
		
		/* Scroll from left to right */
		if (scrollDirection == SCROLL_FROM_LEFT_TO_RIGHT)
		{
			/* Compare RightEdge of item with RightEdge of center */
			for (int i = 0; i < imvList.size(); i++)
			{
				imvList.get(i).getLocationOnScreen(location);
				if ( ((location[0] + itemWidth) <= centerRightEdge) && (location[0] + itemWidth >= 0))
				{
					arrlocation.add(new xLocation(i+1, (centerRightEdge - (location[0] + itemWidth))));
				}
			}
		}
		/* Scroll from right to left */
		else if (scrollDirection == SCROLL_FROM_RIGHT_TO_LEFT)
		{
			/* Compare LeftEdge of item with LeftEdge of center */
			for (int i = 0; i < imvList.size(); i++)
			{
				imvList.get(i).getLocationOnScreen(location);
				if ((location[0] >= centerLeftEdge) && (location[0] <= screenWidth))
				{
					arrlocation.add(new xLocation(i+1, location[0] - centerLeftEdge));
				}
			}
		}
		
		int nearestIndex;
		
		if(arrlocation.size() == 0)
		{
			return -1;
		}
		
		nearestIndex = FindMin(arrlocation);
		return nearestIndex;
	}
	
	/* Find minimum value */
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

/* Item location */
class ItemLocation 
{
	private int index;
	private int xLeftEdge;
	private int xRightEdge;
	
	public ItemLocation(int index, int xLeftEdge, int xRightEdge)
	{
		this.index = index;
		this.xLeftEdge = xLeftEdge;
		this.xRightEdge = xRightEdge;
	}
	
	public int getIndex()
	{
		return this.index;
	}
	
	public int getXLeftEdge()
	{
		return this.xLeftEdge;
	}
	
	public int getXRightEdge()
	{
		return this.xRightEdge;
	}
}