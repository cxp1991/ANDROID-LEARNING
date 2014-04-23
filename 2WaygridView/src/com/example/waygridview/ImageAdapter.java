package com.example.waygridview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	
	// Keep all Images in array
	public Integer[] mThumbIds = {
			R.drawable.longhorn_disc, R.drawable.longhorn_disc,
			R.drawable.longhorn_disc, R.drawable.longhorn_disc,
			R.drawable.longhorn_disc, R.drawable.longhorn_disc,
			R.drawable.longhorn_disc, R.drawable.longhorn_disc,
			R.drawable.longhorn_disc, R.drawable.longhorn_disc,
			R.drawable.longhorn_disc, R.drawable.longhorn_disc,
			R.drawable.longhorn_disc, R.drawable.longhorn_disc,
	};
	
	// Constructor
	public ImageAdapter(Context c){
		mContext = c;
	}

	@Override
	public int getCount() {
		return mThumbIds.length;
	}

	@Override
	public Object getItem(int position) {
		return mThumbIds[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {			
		ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        
        //Load old status
        if(MainActivity.discStatusList.get(position).getStatus() == MainActivity.ROTATING){
        	Animation rotateAnimation = AnimationUtils.loadAnimation(mContext, R.anim.rotate);
			imageView.startAnimation(rotateAnimation);
        }
        
        
        return imageView;
        
	}

}
