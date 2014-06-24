package com.example.gif_image;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		InputStream stream = null;
        try {
            stream = getAssets().open("rain.gif");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        GifDecoderView view = new GifDecoderView(this, stream);
        setContentView(view);
	}
}
