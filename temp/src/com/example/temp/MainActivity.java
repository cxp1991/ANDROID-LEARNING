package com.example.temp;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	  ViewGroup layout;
	    LinearLayout lr;

	    int x;
	    int total = 4;
	    int count = 0;
	    LinearLayout lay;

	    @Override
	    public void onCreate(Bundle savedInstanceState) 
	    {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        count = 0;
	        layout = (ViewGroup)findViewById(R.id.linear_1);
	        x = (total/3)+(total%3 > 0?1:0);;
	        for(int i = 0; i < x; i++)
	        {
	            lr = new LinearLayout(this);
	            lr.setOrientation(LinearLayout.VERTICAL);
	            layout.addView(lr);
	            for(int j = 0; j < 3; j++ )
	            {
	                count++;
	                final View child = getLayoutInflater().inflate(R.layout.inflateview, null);
	                lay = (LinearLayout)child.findViewById(R.id.threeByThree_tableRow1_1_Layout1);
	                lay.setOnClickListener(new View.OnClickListener() 
	                {   
	                    @Override
	                    public void onClick(View v) 
	                    {
	                        Toast.makeText(getApplicationContext(), "selected  id  is "+child.getId(), Toast.LENGTH_SHORT).show();
	                    }
	                });
	                lr.addView(child);
	                child.setId(count);
	                if(i == total)
	                {
	                    break;
	                }
	            }
	        }      
	    }
}