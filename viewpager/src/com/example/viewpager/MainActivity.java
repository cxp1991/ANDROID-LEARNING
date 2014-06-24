package com.example.viewpager;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity {

	private List<Fragment> fragments;
	private MyPagerAdapter mPager;
	private ViewPager mViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		fragments =  getFragments();
		
		mPager = new MyPagerAdapter(getSupportFragmentManager(), fragments);
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mViewPager.setAdapter(mPager);
	}

	private List<Fragment> getFragments(){
		  List<Fragment> fList = new ArrayList<Fragment>();
		  fList.add(MyFragment.newInstance(R.drawable.ic_music_01));
		  fList.add(MyFragment.newInstance(R.drawable.ic_music_02)); 
		  fList.add(MyFragment.newInstance(R.drawable.ic_music_03));
		  return fList;
	}
}
