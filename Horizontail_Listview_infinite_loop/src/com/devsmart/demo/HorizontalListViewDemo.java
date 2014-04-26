package com.devsmart.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.devsmart.android.ui.HorizontalListView;

public class HorizontalListViewDemo extends Activity 
{

	@Override
	protected void onCreate (Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewdemo);
		
		HorizontalListView listview = (HorizontalListView) findViewById(R.id.listview);
		int middle = (Integer.MAX_VALUE/2) - (Integer.MAX_VALUE/2) % 5;
		listview.setAdapter(mAdapter);
		listview.setSelection(middle);
	}
	
	private BaseAdapter mAdapter = new BaseAdapter() 
	{

		@Override
		public int getCount () 
		{
			return Integer.MAX_VALUE;
		}

		@Override
		public Object getItem (int position) 
		{
			return null;
		}

		@Override
		public long getItemId (int position)
		{
			return 0;
		}

		@Override
		public View getView (int position, View convertView, ViewGroup parent) 
		{
			Log.i("TAG", "getView");
			View retval = LayoutInflater.from (parent.getContext()).inflate(R.layout.viewitem, null);
			TextView title = (TextView) retval.findViewById(R.id.title);
			title.setText(position % 5 + "");
			
			return retval;
		}
		
	};

}
