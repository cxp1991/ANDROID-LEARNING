package com.example.ffmpeg_android;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

	InputStream input;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		long lStartTime = System.nanoTime();
		
		FfmpegController ffmpegcontroler = new FfmpegController(getBaseContext()); 
		
		Log.i ("TAG", "Start");
		
		try {
			//ffmpegcontroler.execFFMPEG("ffmpeg -i /sdcard/derx.mp3  -i /sdcard/cbpd.mp3 -i /sdcard/rmacd.mp3 -filter_complex amix=inputs=3:duration=longest  -c:a libmp3lame -q:a 4 /sdcard/myffmpeg.mp3");
			ffmpegcontroler.execFFMPEG("ffmpeg -i /sdcard/output.wav -codec:a libmp3lame -q:a 4 /sdcard/output.mp3");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	
		long lEndTime = System.nanoTime();
		long difference = lEndTime - lStartTime;
		Log.i ("TAG", "Elapsed milliseconds: " + difference/1000000000);
		
	}
}
