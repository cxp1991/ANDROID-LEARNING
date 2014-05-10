package com.devsmart.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.devsmart.android.ui.HorizontalListView;
import com.devsmart.android.ui.HorizontalListView.OnScrollListener;

public class HorizontalListViewDemo extends Activity 
{
	HorizontalListView listview;
	ImageView previous_image;
	int lastCenterposition=-1;
	private int middleLeftRange, middleRightRange;
	private int middleLeftThreadhold, middleRightThreadhold;
	private int itemWidth = 200;
	private int[] location = new int[2];
	int middle, width, height;
	int mfirstVisibleItem;
	View v;
	ImageView memoryImg = null;
	View memoryView = null;
	private int THREADHOLD_TO_MARSK_CENTER = 5;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewdemo);
		
		listview = (HorizontalListView) findViewById(R.id.listview);
		middle = (Integer.MAX_VALUE/2) - (Integer.MAX_VALUE/2) % 10;
		listview.setAdapter(mAdapter);
		
		Display display = getWindowManager().getDefaultDisplay(); 
		width = display.getWidth();  // deprecated
		height = display.getHeight();  // deprecated
		
		middleLeftRange = width/2 - itemWidth/2;
		middleRightRange = width/2 + itemWidth/2;
		middleLeftThreadhold = middleLeftRange - THREADHOLD_TO_MARSK_CENTER;
		middleRightThreadhold = middleLeftRange + THREADHOLD_TO_MARSK_CENTER;
		Log.d("TAG", "" + middleLeftRange + middleRightRange);
		
		/* Set item 0 in center at beginning */
		listview.setSelectionFromLeft(middle, width/2 - itemWidth/2); //100 is width of listview's item
		
		/* Scroll touched item to center 
		listview.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				 Find item has been touched 
				if (event.getAction() == MotionEvent.ACTION_DOWN)
				{
					Log.d("TAG", "x = " + event.getX() + ", y = " + event.getY());
				}
				
				 Scroll touched item to center 
				else if (event.getAction() == MotionEvent.ACTION_UP)
				{
						
				}
				
				return true;
			}
		});*/
		
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> mlistview, View view, int position,
					long id) {
				Log.d("TAG", "position = " + position);
				int firstPosition = mlistview.getFirstVisiblePosition();
				
				int relativeItemClickedPosition = position - firstPosition;
				Log.d("TAG", "relativeItemClickedPosition = " + relativeItemClickedPosition);
				
				listview.setSelectionFromLeft(position, width/2 - itemWidth/2); //100 is width of listview's item
			}
		});
		
		
		
		
		listview.setOnScrollListener(new OnScrollListener() 
		{
			@Override
			public void onScrollStateChanged(AdapterView<?> view, int status) {
				if (status == OnScrollListener.SCROLL_IDLE) {
					
					int firstPosition = view.getFirstVisiblePosition();
					int lastPosition  = view.getLastVisiblePosition();
					
					if (memoryView != null && 
							(location[0] < middleLeftThreadhold 
									|| location[0] > middleRightThreadhold))
					{
						/* Clear old center item*/
						if (memoryImg != null)
						{
							memoryImg.setBackgroundResource(R.drawable.image_transparent_border);
						}
					}
						
					
					for (int i = 0; i < lastPosition - firstPosition; i++)
					{
						v = listview.getChildAt(i);
						v.getLocationOnScreen(location);
						
						if (location[0] >= middleLeftThreadhold && location[0] <= middleRightThreadhold)
						{
							/* Hightlight new center item */
							ImageView imgView = (ImageView) v.findViewById(R.id.image);
				            imgView.setBackgroundResource(R.drawable.image_border);
				            
				            memoryImg = imgView;
				            memoryView = v;
				            break;
						}
					}
					
					
					//int centerPosition = ((lastPosition - firstPosition)/2);
					
					
//					Log.d("TAG", "firstPosition = " + firstPosition + 
//							", lastPosition = " + lastPosition);
//					Log.d("TAG", "centerposition = " + centerPosition + ", lastCenterposition = " 
//							+ lastCenterposition);
					
//					if (centerPosition != lastCenterposition)
//					{
//						//listview.setSelectionFromLeft(mfirstVisibleItem + centerPosition, width/2 - itemWidth);
//						
//						/* Highlight center item */
//			            View centerView = listview.getChildAt(centerPosition);
//			            
//			            centerView.getLocationOnScreen(location);
//			            Log.d("TAG", "centerView[x] = " + location[0] + 
//			            		", centerView[y] = " + location[1]);
//			            
//			            ImageView imgView = (ImageView) centerView.findViewById(R.id.image);
//			            imgView.setBackgroundResource(R.drawable.image_border);
//			            
//			            /* Refresh */
//			            if (lastCenterposition >= 0)
//			            {
//			            	View viewTmp 		= listview.getChildAt(lastCenterposition);
//				            ImageView imgTmp 	= (ImageView) viewTmp.findViewById(R.id.image);
//				            imgTmp.setBackgroundResource(R.drawable.image_transparent_border);
//			            }
//					}
					
					//lastCenterposition = centerPosition;
					
		        }
			}
			
			@Override
			public void onScroll(AdapterView<?> view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				 mfirstVisibleItem = firstVisibleItem;
			}
		});
		
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
		public View getView(int position, View convertView, ViewGroup parent) 
		{
//		    if(convertView == null)
//		    {
//		    	Log.d("TAG", "convertview == null");
//		        convertView = LayoutInflater.from (parent.getContext()).inflate(R.layout.viewitem, null);
//		    }
		    
		    View  v = LayoutInflater.from (parent.getContext()).inflate(R.layout.viewitem, null);
		    TextView title = (TextView) v.findViewById(R.id.title);
//		    int[] location = new int [2];
		    
//		    v.getLocationOnScreen(location);
//		    Log.d("TAG", "x = " + location[0] + ", y = " + location[1] + ", num = " + parent.getChildCount());
		    title.setText(position % 10 + "");

		    return v;
		}
		
	};

}
