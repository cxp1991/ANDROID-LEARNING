package com.example.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class MyFragment extends Fragment{

	private static final String EXTRA_MESSAGE="IMAGE_ID";
	
	public static final MyFragment newInstance(int imageId)
	{
		MyFragment f = new MyFragment();
		Bundle bdl = new Bundle(1);
		bdl.putInt(EXTRA_MESSAGE, imageId);
		f.setArguments(bdl);
		
		return f;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		int imageId = getArguments().getInt(EXTRA_MESSAGE);
		ImageView rootView = (ImageView) inflater.inflate(R.layout.viewpager_item, container, false);
		rootView.setImageResource(imageId);
		
		return rootView;
	}
	
}
