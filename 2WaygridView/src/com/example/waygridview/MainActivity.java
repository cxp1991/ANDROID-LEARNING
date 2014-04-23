package com.example.waygridview;

import java.util.ArrayList;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.waygridview.TwoWayAdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	public final static boolean ROTATING = true;
	public final static boolean STOP_ROTATING = false;
	public static ArrayList<DiscStatus> discStatusList = new ArrayList<DiscStatus>();
	private final int NUMBER_SONGS = 14;
	 public static MediaPlayer mPlayer = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		TwoWayGridView gridView = (TwoWayGridView) findViewById(R.id.gridview);
		
		// Instance of ImageAdapter Class
		gridView.setAdapter(new ImageAdapter(this));

		/**
		 * On Click event for Single Gridview Item
		 * */
		gridView.setOnItemClickListener(new OnItemClickListener() {
			
			public void onItemClick(TwoWayAdapterView parent, View v, int position, long id) {
				
				ImageView discView = (ImageView) v;
				Animation rotateAnimation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
				
				if (discStatusList.get(position).getStatus() == STOP_ROTATING){
					discView.startAnimation(rotateAnimation);
					// Lưu trạng thái vào bộ nhớ
					discStatusList.get(position).setStatus(ROTATING);
				} else {
					discView.clearAnimation();
					// Lưu trạng thái vào bộ nhớ
					discStatusList.get(position).setStatus(STOP_ROTATING);
				}
				
				if(position == 0){
					mPlayer = MediaPlayer.create(getBaseContext(), R.raw.naxd);
	                
					MainActivity.mPlayer.start();
					MainActivity.mPlayer.setLooping(true);
				}
				
				if(position == 1){
					mPlayer = MediaPlayer.create(getBaseContext(), R.raw.nhungayxuaemden);
	                
					MainActivity.mPlayer.start();
					MainActivity.mPlayer.setLooping(true);
				}
			}
		});
		
		setupArraylistdiscStatus();
				
	}
	
	public void setupArraylistdiscStatus(){
		for(int i =0; i < NUMBER_SONGS; i++){
			discStatusList.add(new DiscStatus(i, STOP_ROTATING));
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * On selecting action bar icons
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case R.id.action_favourite:
			
			return true;
		case R.id.action_share:
			// location found
			return true;
		case R.id.action_refresh:
			refresh();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void refresh() {
		// Stop play music
		
		// Stop rotate
		for(int i =0; i < NUMBER_SONGS; i++){
			discStatusList.add(new DiscStatus(i, STOP_ROTATING));
		}
	}
}
