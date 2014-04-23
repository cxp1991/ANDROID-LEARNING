package com.example.camera_capture_video;

import java.io.IOException;

import android.app.Activity;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

	Button myButton;
	MediaRecorder mediaRecorder;
	SurfaceHolder surfaceHolder;
	boolean recording;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        recording = false;
        
        mediaRecorder = new MediaRecorder();
        initMediaRecorder();
        
        setContentView(R.layout.capture_video);
        
        SurfaceView myVideoView = (SurfaceView)findViewById(R.id.videoview);
        surfaceHolder = myVideoView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        
        myButton = (Button)findViewById(R.id.mybutton);
        myButton.setOnClickListener(myButtonOnClickListener);
    }
    
    private Button.OnClickListener myButtonOnClickListener 
    = new Button.OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(recording){
				mediaRecorder.stop();
				mediaRecorder.release();
				finish();
			}else{
				mediaRecorder.start();
				recording = true;
				myButton.setText("STOP");
			}
		}};
    
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		prepareMediaRecorder();
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub

	}
	
	private void initMediaRecorder(){
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
        CamcorderProfile camcorderProfile_HQ = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        mediaRecorder.setProfile(camcorderProfile_HQ);
        mediaRecorder.setOutputFile("/sdcard/myvideo.mp4");
        mediaRecorder.setMaxDuration(60000); // Set max duration 60 sec.
        mediaRecorder.setMaxFileSize(5000000); // Set max file size 5M
	}
	
	private void prepareMediaRecorder(){
		mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
		try {
			mediaRecorder.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
