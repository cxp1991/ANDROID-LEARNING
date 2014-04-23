package com.example.horizontial_animation;

import java.util.ArrayList;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
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
	
	////////////////////////////////////////////
	HorizontalScrollView hrscrollView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	
	hrscrollView = (HorizontalScrollView)findViewById(R.id.horizontalscrollview);
	hrscrollView.fling(200);	
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
	
	// set ontouch event
	imageView_01.setOnTouchListener(ontouchImage);
	imageView_02.setOnTouchListener(ontouchImage);
	imageView_03.setOnTouchListener(ontouchImage);
	imageView_04.setOnTouchListener(ontouchImage);
	imageView_05.setOnTouchListener(ontouchImage);
	imageView_06.setOnTouchListener(ontouchImage);
	imageView_07.setOnTouchListener(ontouchImage);
	imageView_08.setOnTouchListener(ontouchImage);
	imageView_09.setOnTouchListener(ontouchImage);
	imageView_10.setOnTouchListener(ontouchImage);
	imageView_11.setOnTouchListener(ontouchImage);
	imageView_12.setOnTouchListener(ontouchImage);	 
	
	
	}
	
	
	OnTouchListener ontouchImage = new OnTouchListener() {
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		
		case MotionEvent.ACTION_DOWN: {  
			Log.i("TAG", "Bottom touched!");
			
			/* Find center child */
			int centerChildId = FindoutCenterChild();
		
			/* Clear previous focus */
			
			/* Focus on center child */
			
			/* Un-focus the others child */
			
			
		  break;
		}
		}
	
		return true;
	
	}
	};
	
	private int FindoutCenterChild ()
	{
		int image_center_id = 0;
		
		ArrayList<int []> location = new ArrayList<int []>();
		int[] tmp = {0,0};
		
		for (int i = 0; i < num_image; i++)
			location.add(i, tmp);
		
		imageView_01.getLocationOnScreen(location.get(0));
		Log.i("TAG", "01: x = " + location.get(0)[0] + ", y =  " + location.get(0)[1]);
		
		/*imageView_01.getLocationOnScreen(location.get(0));
		Log.i("TAG", "01: x = " + location.get(0)[0] + ", y =  " + location.get(0)[1]);
		
		imageView_01.getLocationOnScreen(location.get(0));
		Log.i("TAG", "01: x = " + location.get(0)[0] + ", y =  " + location.get(0)[1]);
		
		imageView_01.getLocationOnScreen(location.get(0));
		Log.i("TAG", "01: x = " + location.get(0)[0] + ", y =  " + location.get(0)[1]);
		
		imageView_01.getLocationOnScreen(location.get(0));
		Log.i("TAG", "01: x = " + location.get(0)[0] + ", y =  " + location.get(0)[1]);
		
		imageView_01.getLocationOnScreen(location.get(0));
		Log.i("TAG", "01: x = " + location.get(0)[0] + ", y =  " + location.get(0)[1]);
		
		imageView_01.getLocationOnScreen(location.get(0));
		Log.i("TAG", "01: x = " + location.get(0)[0] + ", y =  " + location.get(0)[1]);
		
		imageView_01.getLocationOnScreen(location.get(0));
		Log.i("TAG", "01: x = " + location.get(0)[0] + ", y =  " + location.get(0)[1]);
		
		imageView_01.getLocationOnScreen(location.get(0));
		Log.i("TAG", "01: x = " + location.get(0)[0] + ", y =  " + location.get(0)[1]);
		
		imageView_01.getLocationOnScreen(location.get(0));
		Log.i("TAG", "01: x = " + location.get(0)[0] + ", y =  " + location.get(0)[1]);
		
		imageView_01.getLocationOnScreen(location.get(0));
		Log.i("TAG", "01: x = " + location.get(0)[0] + ", y =  " + location.get(0)[1]);
		
		imageView_01.getLocationOnScreen(location.get(0));
		Log.i("TAG", "01: x = " + location.get(0)[0] + ", y =  " + location.get(0)[1]);
		*/
		
		
		/* algorithm */
		
		return image_center_id;
	}
}


