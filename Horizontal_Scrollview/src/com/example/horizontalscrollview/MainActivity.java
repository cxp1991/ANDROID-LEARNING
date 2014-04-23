package com.example.horizontalscrollview;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	////////////////////////
	ImageView img_soundtrack_01;
	ImageView img_soundtrack_02;
	ImageView img_soundtrack_03;
	ImageView img_soundtrack_04;
	ImageView img_soundtrack_05;
	ImageView img_soundtrack_06;
	ImageView img_soundtrack_07;
	ImageView img_soundtrack_08;
	ImageView img_soundtrack_09;
	ImageView img_soundtrack_10;
	ImageView img_soundtrack_11;
	ImageView img_soundtrack_12;
	
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
	
	////////////////////////////////////////////
	HorizontalScrollView userSong_hrscrollView;
	HorizontalScrollView soundTrack_hrscrollView;
	private Integer numSoundtrack = 12;
	MediaPlayer mPlayer;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/* SoundTrack */
		soundTrack_hrscrollView = (HorizontalScrollView) findViewById(R.id.scrollView_soundtrack);
		
		// Get the image view
	    img_soundtrack_01 = (ImageView) findViewById(R.id.image_soundTrack_01);
	    img_soundtrack_02 = (ImageView) findViewById(R.id.image_soundTrack_02);
	    img_soundtrack_03 = (ImageView) findViewById(R.id.image_soundTrack_03);
	    img_soundtrack_04 = (ImageView) findViewById(R.id.image_soundTrack_04);
	    img_soundtrack_05 = (ImageView) findViewById(R.id.image_soundTrack_05);
	    img_soundtrack_06 = (ImageView) findViewById(R.id.image_soundTrack_06);
	    img_soundtrack_07 = (ImageView) findViewById(R.id.image_soundTrack_07);
	    img_soundtrack_08 = (ImageView) findViewById(R.id.image_soundTrack_08);
	    img_soundtrack_09 = (ImageView) findViewById(R.id.image_soundTrack_09);
	    img_soundtrack_10 = (ImageView) findViewById(R.id.image_soundTrack_10);
	    img_soundtrack_11 = (ImageView) findViewById(R.id.image_soundTrack_11);
	    img_soundtrack_12 = (ImageView) findViewById(R.id.image_soundTrack_12);
	    
		
		// Set ontouch event
	    img_soundtrack_01.setOnTouchListener(onSoundTrackTouch);
	    img_soundtrack_02.setOnTouchListener(onSoundTrackTouch);
	    img_soundtrack_03.setOnTouchListener(onSoundTrackTouch);
	    img_soundtrack_04.setOnTouchListener(onSoundTrackTouch);
	    img_soundtrack_05.setOnTouchListener(onSoundTrackTouch);
	    img_soundtrack_06.setOnTouchListener(onSoundTrackTouch);
	    img_soundtrack_07.setOnTouchListener(onSoundTrackTouch);
	    img_soundtrack_08.setOnTouchListener(onSoundTrackTouch);
	    img_soundtrack_09.setOnTouchListener(onSoundTrackTouch);
	    img_soundtrack_10.setOnTouchListener(onSoundTrackTouch);
	    img_soundtrack_11.setOnTouchListener(onSoundTrackTouch);
	    img_soundtrack_12.setOnTouchListener(onSoundTrackTouch);
		
	    /* User's songs */
		userSong_hrscrollView = (HorizontalScrollView) findViewById(R.id.horizontalscrollview);
		LinearLayout lin = (LinearLayout) findViewById( R.id.ln_main);
		int width = lin.getWidth();
		userSong_hrscrollView.scrollTo( width / 2, 0 );
		
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
	
	OnTouchListener onSoundTrackTouch = new OnTouchListener() {
			
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			switch (event.getAction()) {
            
				case MotionEvent.ACTION_DOWN: {
					
					Log.i("TAG", "Top touched!");
					
					/* view job */
	                ImageView view_2 = (ImageView) v;
	                //view_2.setColorFilter(0x88000000, PorterDuff.Mode.OVERLAY);
	                view_2.setBackgroundResource(R.drawable.image_border);
	                view_2.invalidate();
	                
	                if (previous_image != null && previous_image != view_2){
	                	previous_image.clearColorFilter();
	    				previous_image.setBackgroundResource(R.drawable.image_transparent_border);
	                    previous_image.invalidate();
					}
	               
	                previous_image = (ImageView) v;
	                
	                /* Sound job */
	               
	                if (v.getId() == img_soundtrack_01.getId())
	                {
	                	mPlayer = MediaPlayer.create(getBaseContext(), R.raw.waterfall_01);
	                }
	                else if (v.getId() == img_soundtrack_01.getId())
	                {
	                	mPlayer = MediaPlayer.create(getBaseContext(), R.raw.waterfall_01);
	                }
	                
	                mPlayer.start();
	                
	                break;
				}
			}
			return false;
		}
	};
	
	OnTouchListener ontouchImage = new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			switch (event.getAction()) {
	            
				case MotionEvent.ACTION_DOWN: {  
					Log.i("TAG", "Bottom touched!");
					
	                ImageView view_2 = (ImageView) v;
	                /*view_2.setColorFilter(0x88000000, PorterDuff.Mode.OVERLAY);
	                view_2.setBackgroundResource(R.drawable.image_border);
	                view_2.invalidate();
	                
	                if (previous_image != null && previous_image != view_2){
	                	previous_image.clearColorFilter();
	    				previous_image.setBackgroundResource(R.drawable.image_transparent_border);
	                    previous_image.invalidate();
					}
	               
	                previous_image = (ImageView) v;
	                */
	            	view_2 = (ImageView)imageView_01;
					if (imageView_01.getId() != v.getId())
					{
						view_2.setColorFilter(0xff000000, PorterDuff.Mode.OVERLAY);
						view_2.invalidate();
					}
					else
					{
						view_2.clearColorFilter();
					}
					
					view_2 = (ImageView)imageView_02;
					if (imageView_02.getId() != v.getId())
					{
						view_2.setColorFilter(0xff000000, PorterDuff.Mode.OVERLAY);
						view_2.invalidate();
					}
					else
					{
						view_2.clearColorFilter();
					}
					
					view_2 = (ImageView)imageView_03;
					if (imageView_03.getId() != v.getId())
					{
						view_2.setColorFilter(0xff000000, PorterDuff.Mode.OVERLAY);
						view_2.invalidate();
					}
					else
					{
						view_2.clearColorFilter();
					}
					
					view_2 = (ImageView)imageView_04;
					if (imageView_04.getId() != v.getId())
					{
						view_2.setColorFilter(0xff000000, PorterDuff.Mode.OVERLAY);
						view_2.invalidate();
					}
					else
					{
						view_2.clearColorFilter();
					}
					
					view_2 = (ImageView)imageView_05;
					if (imageView_05.getId() != v.getId())
					{
						view_2.setColorFilter(0xff000000, PorterDuff.Mode.OVERLAY);
						view_2.invalidate();
					}
					else
					{
						view_2.clearColorFilter();
					}
					
					view_2 = (ImageView)imageView_06;
					if (imageView_06.getId() != v.getId())
					{
						view_2.setColorFilter(0xff000000, PorterDuff.Mode.OVERLAY);
						view_2.invalidate();
					}
					else
					{
						view_2.clearColorFilter();
					}
					
					view_2 = (ImageView)imageView_07;
					if (imageView_07.getId() != v.getId())
					{
						view_2.setColorFilter(0xff000000, PorterDuff.Mode.OVERLAY);
						view_2.invalidate();
					}
					else
					{
						view_2.clearColorFilter();
					}
					
					view_2 = (ImageView)imageView_08;
					if (imageView_08.getId() != v.getId())
					{
						view_2.setColorFilter(0xff000000, PorterDuff.Mode.OVERLAY);
						view_2.invalidate();
					}
					else
					{
						view_2.clearColorFilter();
					}
					
					view_2 = (ImageView)imageView_09;
					if (imageView_09.getId() != v.getId())
					{
						view_2.setColorFilter(0xff000000, PorterDuff.Mode.OVERLAY);
						view_2.invalidate();
					}
					else
					{
						view_2.clearColorFilter();
					}
					
					view_2 = (ImageView)imageView_10;
					if (imageView_10.getId() != v.getId())
					{
						view_2.setColorFilter(0xff000000, PorterDuff.Mode.OVERLAY);
						view_2.invalidate();
					}
					else
					{
						view_2.clearColorFilter();
					}
					
					view_2 = (ImageView)imageView_11;
					if (imageView_11.getId() != v.getId())
					{
						view_2.setColorFilter(0xff000000, PorterDuff.Mode.OVERLAY);
						view_2.invalidate();
					}
					else
					{
						view_2.clearColorFilter();
					}
					
					view_2 = (ImageView)imageView_12;
					if (imageView_12.getId() != v.getId())
					{
						view_2.setColorFilter(0xff000000, PorterDuff.Mode.OVERLAY);
						view_2.invalidate();
					}
					else
					{
						view_2.clearColorFilter();
					}
	                break;
				}
			}
			
        return true;
		
		}
		
	};
	
}
