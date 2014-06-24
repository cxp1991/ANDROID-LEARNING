package com.example.horizontial_animation;

import java.util.ArrayList;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.res.Resources;
import android.media.MediaPlayer;
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
 * FIXME: No waiting stop scroll to scroll
 * */

public class MainActivity extends Activity 
{
	////////////// RINGTONE /////////////////////////////
	private ImageView 	ringtoneImgView_01, ringtoneImgView_02, ringtoneImgView_03,
	ringtoneImgView_04, ringtoneImgView_05, ringtoneImgView_06, ringtoneImgView_07, 
	ringtoneImgView_08, ringtoneImgView_09, ringtoneImgView_10 ,ringtoneImgView_11, 
	ringtoneImgView_12;
	
	private ImageView ringtoneCenterImv;
	
	HorizontalScrollView ringtoneHrscrollview;
	ArrayList<xLocation> ringtoneArrlocation;
	private int ringtoneInitialPosition;
	private int ringtoneIndexNereastCenter = 1;
	private int ringtonePointDownIndex;
	private int ringtonePreviousCenterIndex = 1;
	private ArrayList<ImageView> ringtoneImgList;
	
	private float ringtoneStartPosition;
    private float ringtoneStopPosition;
    private int   ringtoneScrollDirection;
    private ArrayList<RingToneData> ringtoneDataArraylist;
    MediaPlayer ringtoneMPlayer = null; 
    private int previousRingtoneIndex = -1;
    //////////////////////////////////////////////////////
    
	//////////// MUSIC ///////////////////
	private ImageView 	musicImgView_01, musicImgView_02, musicImgView_03,
						musicImgView_04, musicImgView_05, musicImgView_06, musicImgView_07, 
						musicImgView_08, musicImgView_09, musicImgView_10 ,musicImgView_11, 
						musicImgView_12;
	
	private ImageView musicCenterImv;
	HorizontalScrollView musicHrscrollView;
	ArrayList<xLocation> musicArrlocation;
	private int musicInitialPosition;
	private int musicIndexNereastCenter = 1;
	private int musicPointDownIndex;
	private int musicPreviousCenterIndex = 1;
	private ArrayList<ImageView> musicImgList;
	
    /* To get Scroll direction */
    private float musicStartPosition;
    private float musicStopPosition;
    private int   musicScrollDirection;
    //////////////////////////////////////////
    
    ////////////// GLOBAL /////////////////////
    private int centerLeftEdge, centerRightEdge;
	private int itemWidth;
	private int screenWidth;
	private int itemPadding; 
	private ImageView backgroundImv;
    
    private int ANIMATION_DURATION = 200; // micro seconds
	private int SCROLL_FROM_RIGHT_TO_LEFT = 0x01;
	private int SCROLL_FROM_LEFT_TO_RIGHT = 0x02;
	private int ONCLICK_THREADHOLD; //pixel
	private int STOP_DISTANCE_THREADHOLD = 0; //pixel
	////////////////////////////////////////////
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		////////////////// GLOBAL ///////////////////
		/* Convert 100dp [width in XML] to pixel */
		Resources r = getResources();
		itemWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, r.getDisplayMetrics());
		//Log.d("TAG","itemWidth = " + itemWidth);
		
		/* Convert 5dp [width in XML] to pixel */
		itemPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics());
		ONCLICK_THREADHOLD = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, r.getDisplayMetrics());
		//STOP_DISTANCE_THREADHOLD = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, r.getDisplayMetrics());
		
		/* Get Center location */
		Display display = getWindowManager().getDefaultDisplay(); 
		screenWidth = display.getWidth();  // deprecated but it works, cool
		centerLeftEdge = screenWidth/2 - itemWidth/2;
		centerRightEdge = screenWidth/2 + itemWidth/2;
		
		backgroundImv = (ImageView) findViewById(R.id.bgrimg);
		//////////////////////////////////////////////
		
		///////////////////// MUSIC ////////////////////
		musicHrscrollView = (HorizontalScrollView)findViewById(R.id.hrScrollviewMusic);
		// Get the image view
		musicImgView_01 = (ImageView)findViewById(R.id.image_01);
		musicImgView_02 = (ImageView)findViewById(R.id.image_02);
		musicImgView_03 = (ImageView)findViewById(R.id.image_03);
		musicImgView_04 = (ImageView)findViewById(R.id.image_04);
		musicImgView_05 = (ImageView)findViewById(R.id.image_05);
		musicImgView_06 = (ImageView)findViewById(R.id.image_06);
		musicImgView_07 = (ImageView)findViewById(R.id.image_07);
		musicImgView_08 = (ImageView)findViewById(R.id.image_08);
		musicImgView_09 = (ImageView)findViewById(R.id.image_09);
		musicImgView_10 = (ImageView)findViewById(R.id.image_10);
		musicImgView_11 = (ImageView)findViewById(R.id.image_11);
		musicImgView_12 = (ImageView)findViewById(R.id.image_12);
		
		/* Save to arraylist to calculate easily */
		musicImgList = new ArrayList<ImageView>();
		musicImgList.add(musicImgView_01);
		musicImgList.add(musicImgView_02);
		musicImgList.add(musicImgView_03);
		musicImgList.add(musicImgView_04);
		musicImgList.add(musicImgView_05);
		musicImgList.add(musicImgView_06);
		musicImgList.add(musicImgView_07);
		musicImgList.add(musicImgView_08);
		musicImgList.add(musicImgView_09);
		musicImgList.add(musicImgView_10);
		musicImgList.add(musicImgView_11);
		musicImgList.add(musicImgView_12);
		
		/* Set width of header & footer again depends on width of screen */
		LinearLayout musicHeaderLinearLayout = (LinearLayout) findViewById(R.id.header);
		LinearLayout musicFooterLinearLayout = (LinearLayout) findViewById(R.id.footer);
		musicHeaderLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(centerLeftEdge, LayoutParams.MATCH_PARENT));
		musicFooterLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(centerLeftEdge, LayoutParams.MATCH_PARENT));
		
		/* Highlight center item */
		musicImgList.get(0).setImageResource(R.drawable.balloon_light);
		
		///////////////////// RINGTONE ////////////////////
		ringtoneDataArraylist = new ArrayList<MainActivity.RingToneData>();
		ringtoneDataArraylist.add(new RingToneData(0, R.raw.whistle));//09
		ringtoneDataArraylist.add(new RingToneData(0, R.raw.flute));//08
		ringtoneDataArraylist.add(new RingToneData(0, R.raw.river)); //01
		ringtoneDataArraylist.add(new RingToneData(0, R.raw.rain));//02
		ringtoneDataArraylist.add(new RingToneData(0, R.raw.thunder));//03
		ringtoneDataArraylist.add(new RingToneData(0, R.raw.wind));//04
		ringtoneDataArraylist.add(new RingToneData(0, R.raw.frog));//05
		ringtoneDataArraylist.add(new RingToneData(0, R.raw.cicada));//06
		ringtoneDataArraylist.add(new RingToneData(0, R.raw.nighttime));//07
		ringtoneDataArraylist.add(new RingToneData(0, R.raw.nighttime));//07
		ringtoneDataArraylist.add(new RingToneData(0, R.raw.nighttime));//07
		ringtoneDataArraylist.add(new RingToneData(0, R.raw.nighttime));//07
		
	
		
		
		ringtoneHrscrollview = (HorizontalScrollView)findViewById(R.id.hrScrollviewringtone);
		
		// Get the image view
		ringtoneImgView_01 = (ImageView)findViewById(R.id.ringtoneimage_01);
		ringtoneImgView_02 = (ImageView)findViewById(R.id.ringtoneimage_02);
		ringtoneImgView_03 = (ImageView)findViewById(R.id.ringtoneimage_03);
		ringtoneImgView_04 = (ImageView)findViewById(R.id.ringtoneimage_04);
		ringtoneImgView_05 = (ImageView)findViewById(R.id.ringtoneimage_05);
		ringtoneImgView_06 = (ImageView)findViewById(R.id.ringtoneimage_06);
		ringtoneImgView_07 = (ImageView)findViewById(R.id.ringtoneimage_07);
		ringtoneImgView_08 = (ImageView)findViewById(R.id.ringtoneimage_08);
		ringtoneImgView_09 = (ImageView)findViewById(R.id.ringtoneimage_09);
		ringtoneImgView_10 = (ImageView)findViewById(R.id.ringtoneimage_10);
		ringtoneImgView_11 = (ImageView)findViewById(R.id.ringtoneimage_11);
		ringtoneImgView_12 = (ImageView)findViewById(R.id.ringtoneimage_12);
		
		/* Save to arraylist to calculate easily */
		ringtoneImgList = new ArrayList<ImageView>();
		ringtoneImgList.add(ringtoneImgView_01);
		ringtoneImgList.add(ringtoneImgView_02);
		ringtoneImgList.add(ringtoneImgView_03);
		ringtoneImgList.add(ringtoneImgView_04);
		ringtoneImgList.add(ringtoneImgView_05);
		ringtoneImgList.add(ringtoneImgView_06);
		ringtoneImgList.add(ringtoneImgView_07);
		ringtoneImgList.add(ringtoneImgView_08);
		ringtoneImgList.add(ringtoneImgView_09);
		ringtoneImgList.add(ringtoneImgView_10);
		ringtoneImgList.add(ringtoneImgView_11);
		ringtoneImgList.add(ringtoneImgView_12);
		
		
		/* Set width of header & footer again depends on width of screen */
		LinearLayout ringtoneHeaderLinearLayout = (LinearLayout) findViewById(R.id.ringtoneheader);
		LinearLayout ringtoneFooterLinearLayout = (LinearLayout) findViewById(R.id.ringtonefooter);
		ringtoneHeaderLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(centerLeftEdge, LayoutParams.MATCH_PARENT));
		ringtoneFooterLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(centerLeftEdge, LayoutParams.MATCH_PARENT));
		
		/* Highlight center item */
		ringtoneImgList.get(0).setImageResource(R.drawable.balloon_light);
		//////////////////////////////////////////////////////////////////
		
		/* Scroll or OnClick */ 
		musicHrscrollView.setOnTouchListener(new OnTouchListener() 
		{
			
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				switch (event.getAction()) 
				{
					 /*
					  * Just wait to ACTION_UP, we can find scrolled direction
					  * Don't need to wait scroll stop
					  * 
					  * Or event is onclick, not ontouch due to OnClick
					  * conflicts with onToch in action_down
					  */
				
					case MotionEvent.ACTION_DOWN:
						musicStartPosition = event.getX();
						break;
					
					case MotionEvent.ACTION_UP:
						musicStopPosition = event.getX();
						//Log.d("TAG", "diff = " + (stopPosition - startPosition));
						if (Math.abs(musicStopPosition - musicStartPosition) <= ONCLICK_THREADHOLD)
						{
							musicOnClickEvent();
						}
						else
						{
							musicOnScrollEvent();
						}
						break;
						
					default:
						break;
				}
				
				return false;
			}
		});
	
		/* Scroll or Onclick */ 
		ringtoneHrscrollview.setOnTouchListener(new OnTouchListener() 
		{
			
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				switch (event.getAction()) 
				{
					 /*
					  * Just wait to ACTION_UP, we can find scrolled direction
					  * Don't need to wait scroll stop
					  * 
					  * Or event is OnClick, not OnTouch due to OnClick
					  * conflicts with onToch in action_down
					  */
				
					case MotionEvent.ACTION_DOWN:
						ringtoneStartPosition = event.getX();
						break;
					
					case MotionEvent.ACTION_UP:
						ringtoneStopPosition = event.getX();
						//Log.d("TAG", "diff = " + (stopPosition - startPosition));
						if (Math.abs(ringtoneStopPosition - ringtoneStartPosition) <= ONCLICK_THREADHOLD)
						{
							ringtoneOnClickEvent();
						}
						else
						{
							ringtoneOnScrollEvent();
						}
						break;
						
					default:
						break;
				}
				
				return false;
			}
		});
	
}

	private void musicOnScrollEvent() 
	{
		if (musicStopPosition - musicStartPosition >= 0)
			musicScrollDirection = SCROLL_FROM_LEFT_TO_RIGHT;
		else
			musicScrollDirection = SCROLL_FROM_RIGHT_TO_LEFT;
		
		musicInitialPosition = musicHrscrollView.getScrollX();
		Thread checkStopScrollThread = new Thread(MusicCheckScrollStop);
		checkStopScrollThread.start();
	}

	private void musicOnClickEvent() 
	{
		int[] location = new int[2];
		musicPointDownIndex = -1;
		
		// Find all items location 
		for (int i = 0; i < musicImgList.size(); i++)
		{
			musicImgList.get(i).getLocationOnScreen(location);
			if ((musicStopPosition >= location[0]) && (musicStopPosition <= (location[0] + itemWidth)))
			{
				musicPointDownIndex = i + 1;
				break;
			}
		}
					
		ObjectAnimator animator=ObjectAnimator.ofInt(musicHrscrollView, 
					"scrollX", (musicPointDownIndex-1)*itemWidth);
		
		animator.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				// Un-Highlight previous center item 
		 		musicCenterImv = musicImgList.get(musicPreviousCenterIndex - 1);
		 		musicCenterImv.setImageResource(R.drawable.balloon_dark);
		 		
		 		// Highlight center item 
		 		musicCenterImv = musicImgList.get(musicPointDownIndex-1);
		 		musicCenterImv.setImageResource(R.drawable.balloon_light);
		 		musicPreviousCenterIndex = musicPointDownIndex;
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
	 */
	
	Runnable MusicCheckScrollStop = new Runnable() 
	{
		@Override
		public void run() {
			while (true)
			{
				Delay(100);
				int newPosition = musicHrscrollView.getScrollX();
				
				/* Horizontal Stop scroll */ 
				if(musicInitialPosition - newPosition == 0)
				{
					//Log.e("TAG", "Stop scroll");
					musicIndexNereastCenter = MusicFindItemNearestCenter();
					//Log.d("TAG", "Nearestindex = " + musicIndexNereastCenter);
					
					 
					 /*
					  *  Smooth scroll to wanted position
					  */
					runOnUiThread(new Runnable(){

						@Override
						public void run() {
							int value = 0;
							
							if(musicIndexNereastCenter > 0)
								value = (musicIndexNereastCenter-1)*itemWidth;
							
							/* Cool, I can change speed of scroll using this animation */ 
							ObjectAnimator animator=ObjectAnimator.ofInt(musicHrscrollView, 
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
									//Un-Highlight previous center item 
							 		musicCenterImv = musicImgList.get(musicPreviousCenterIndex - 1);
							 		musicCenterImv.setImageResource(R.drawable.balloon_dark);
							 		//musicCenterImv.setPadding(itemPadding, itemPadding, itemPadding, itemPadding);
							 		
							 		//Highlight center item 
							 		musicCenterImv = musicImgList.get(musicIndexNereastCenter-1);
							 		musicCenterImv.setImageResource(R.drawable.balloon_light);
							 		//musicCenterImv.setPadding(0, 0, 0, 0);
							 		musicPreviousCenterIndex = musicIndexNereastCenter;
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
					musicInitialPosition = musicHrscrollView.getScrollX();
				}
			}
		}
		
	};
	
	/*
	 Find item is nearest center,
	 * Also depend on scrolling direction
	 */
	private int MusicFindItemNearestCenter() 
	{
		
		int[] location = new int[2];
		musicArrlocation = new ArrayList<xLocation>();
		
		// Scroll from left to right 
		if (musicScrollDirection == SCROLL_FROM_LEFT_TO_RIGHT)
		{
			// Compare RightEdge of item with RightEdge of center 
			for (int i = 0; i < musicImgList.size(); i++)
			{
				musicImgList.get(i).getLocationOnScreen(location);
				if ( ((location[0] + itemWidth) <= centerRightEdge) && (location[0] + itemWidth >= 0))
				{
					musicArrlocation.add(new xLocation(i+1, (centerRightEdge - (location[0] + itemWidth))));
				}
			}
		}
		// Scroll from right to left 
		else if (musicScrollDirection == SCROLL_FROM_RIGHT_TO_LEFT)
		{
			// Compare LeftEdge of item with LeftEdge of center 
			for (int i = 0; i < musicImgList.size(); i++)
			{
				musicImgList.get(i).getLocationOnScreen(location);
				if ((location[0] >= centerLeftEdge) && (location[0] <= screenWidth))
				{
					musicArrlocation.add(new xLocation(i+1, location[0] - centerLeftEdge));
				}
			}
		}
		
		int nearestIndex;
		
		if (musicArrlocation.size() == 0)
		{
			return -1;
		}
		
		nearestIndex = FindMin(musicArrlocation);
		return nearestIndex;
	}
	
	///////////////////// RINGTONE //////////////////////
	private void ringtoneOnScrollEvent() 
	{
		if (ringtoneStopPosition - ringtoneStartPosition >= 0)
			ringtoneScrollDirection = SCROLL_FROM_LEFT_TO_RIGHT;
		else
			ringtoneScrollDirection = SCROLL_FROM_RIGHT_TO_LEFT;
		
		ringtoneInitialPosition = ringtoneHrscrollview.getScrollX();
		Thread checkStopScrollThread = new Thread(RingtoneCheckScrollStop);
		checkStopScrollThread.start();
	}

	private void ringtoneOnClickEvent() 
	{
		int[] location = new int[2];
		ringtonePointDownIndex = -1;
		
		// Find all items location 
		for (int i = 0; i < ringtoneImgList.size(); i++)
		{
			ringtoneImgList.get(i).getLocationOnScreen(location);
			if ((ringtoneStopPosition >= location[0]) && (ringtoneStopPosition <= (location[0] + itemWidth)))
			{
				ringtonePointDownIndex = i + 1;
				break;
			}
		}
					
		ObjectAnimator animator=ObjectAnimator.ofInt(ringtoneHrscrollview, 
					"scrollX", (ringtonePointDownIndex-1)*itemWidth);
		
		animator.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				// Un-Highlight previous center item 
		 		ringtoneCenterImv = ringtoneImgList.get(ringtonePreviousCenterIndex - 1);
		 		ringtoneCenterImv.setImageResource(R.drawable.balloon_dark);
		 		
		 		// Highlight center item 
		 		ringtoneCenterImv = ringtoneImgList.get(ringtonePointDownIndex-1);
		 		ringtoneCenterImv.setImageResource(R.drawable.balloon_light);
		 		ringtonePreviousCenterIndex = ringtonePointDownIndex;
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
			}
		});
		
 		animator.setDuration(ANIMATION_DURATION);
 		animator.start();
		
		if (ringtoneDataArraylist.get(ringtonePointDownIndex-1).getStatus() == 0)
		{
			if (ringtoneMPlayer != null)
			{
				ringtoneMPlayer.stop();
				ringtoneDataArraylist.get(previousRingtoneIndex).setStatus(0);
			}
			
			ringtoneMPlayer = MediaPlayer.create(this, ringtoneDataArraylist.get(ringtonePointDownIndex-1).getRingtoneId());
			ringtoneMPlayer.start();
			ringtoneMPlayer.setLooping(true);
			ringtoneDataArraylist.get(ringtonePointDownIndex-1).setStatus(1);
			previousRingtoneIndex = ringtonePointDownIndex-1;
			//backgroundImv.setImageResource(R.drawable.whistle);
		}
		else
		{
			ringtoneMPlayer.stop();
			ringtoneDataArraylist.get(ringtonePointDownIndex-1).setStatus(0);
		}
		
	}

	/*
	 * Wait until Horizontal stop scroll then 
	 * smooth scroll to wanted position
	 */
	
	Runnable RingtoneCheckScrollStop = new Runnable()
	{
		
		@Override
		public void run() {
			while (true)
			{
				Delay(100);
				int newPosition = ringtoneHrscrollview.getScrollX();
				
				/* Horizontal Stop scroll */ 
				if(ringtoneInitialPosition - newPosition == 0)
				{
					//Log.e("TAG", "Stop scroll");
					ringtoneIndexNereastCenter = RingtoneFindItemNearestCenter();
					//Log.d("TAG", "Nearestindex = " + ringtoneIndexNereastCenter);
					
					 
					 /*
					  *  Smooth scroll to wanted position
					  */
					runOnUiThread(new Runnable(){

						@Override
						public void run() {
							int value = 0;
							
							if(ringtoneIndexNereastCenter > 0)
								value = (ringtoneIndexNereastCenter-1)*itemWidth;
							
							/* Cool, I can change speed of scroll using this animation */ 
							ObjectAnimator animator=ObjectAnimator.ofInt(ringtoneHrscrollview, 
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
									//Un-Highlight previous center item 
							 		ringtoneCenterImv = ringtoneImgList.get(ringtonePreviousCenterIndex - 1);
							 		ringtoneCenterImv.setImageResource(R.drawable.balloon_dark);
							 		//ringtoneCenterImv.setPadding(itemPadding, itemPadding, itemPadding, itemPadding);
							 		
							 		//Highlight center item 
							 		ringtoneCenterImv = ringtoneImgList.get(ringtoneIndexNereastCenter-1);
							 		ringtoneCenterImv.setImageResource(R.drawable.balloon_light);
							 		//ringtoneCenterImv.setPadding(0, 0, 0, 0);
							 		ringtonePreviousCenterIndex = ringtoneIndexNereastCenter;
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
					ringtoneInitialPosition = ringtoneHrscrollview.getScrollX();
				}
			}
		}
		
	};
	
	/*
	 Find item is nearest center,
	 * Also depend on scrolling direction
	 */
	private int RingtoneFindItemNearestCenter() 
	{
		
		int[] location = new int[2];
		ringtoneArrlocation = new ArrayList<xLocation>();
		
		// Scroll from left to right 
		if (ringtoneScrollDirection == SCROLL_FROM_LEFT_TO_RIGHT)
		{
			// Compare RightEdge of item with RightEdge of center 
			for (int i = 0; i < ringtoneImgList.size(); i++)
			{
				ringtoneImgList.get(i).getLocationOnScreen(location);
				if ( ((location[0] + itemWidth) <= centerRightEdge) && (location[0] + itemWidth >= 0))
				{
					ringtoneArrlocation.add(new xLocation(i+1, (centerRightEdge - (location[0] + itemWidth))));
				}
			}
		}
		// Scroll from right to left 
		else if (ringtoneScrollDirection == SCROLL_FROM_RIGHT_TO_LEFT)
		{
			// Compare LeftEdge of item with LeftEdge of center 
			for (int i = 0; i < ringtoneImgList.size(); i++)
			{
				ringtoneImgList.get(i).getLocationOnScreen(location);
				if ((location[0] >= centerLeftEdge) && (location[0] <= screenWidth))
				{
					ringtoneArrlocation.add(new xLocation(i+1, location[0] - centerLeftEdge));
				}
			}
		}
		
		int nearestIndex;
		
		if (ringtoneArrlocation.size() == 0)
		{
			return -1;
		}
		
		nearestIndex = FindMin(ringtoneArrlocation);
		return nearestIndex;
	}
	////////////////////////////////////////////////////
	
	 //Find minimum value 
	private int FindMin(ArrayList<xLocation> musicArrlocation) 
	{
		
		 int minIndex =musicArrlocation.get(0).getIndex();
		 int minValue = musicArrlocation.get(0).getdistanceFromCenter();  
		  for(int i=1; i < musicArrlocation.size() ;i++){  
		    if(musicArrlocation.get(i).getdistanceFromCenter() < minValue){  
		      minValue = musicArrlocation.get(i).getdistanceFromCenter();
		      minIndex = musicArrlocation.get(i).getIndex();
		    }  
		  }  
		  
		  return minIndex; 
	}
	
	 /* Delay using Thread
	 */ 
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
	
	@Override
	protected void onStop() 
	{
		super.onStop();
		if (ringtoneMPlayer != null)
			ringtoneMPlayer.stop();
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

	class RingToneData
	{
		private int isPlaying = 0;
		private int index;
		private int ringtoneId;
		
		public RingToneData (int index, int ringtoneId)
		{
			this.index = index;
			this.ringtoneId = ringtoneId;
		}
		
		public int getIndex()
		{
			return this.index;
		}
		
		public int getRingtoneId()
		{
			return this.ringtoneId;
		}
		
		public void setStatus(int status)
		{
			this.isPlaying = status;
		}
		
		public int getStatus()
		{
			return this.isPlaying;
		}
		
	}
}

