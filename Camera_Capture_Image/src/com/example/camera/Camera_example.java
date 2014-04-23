package com.example.camera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

/*
 * 1. Capture images then save into sdcard
 * 2. Capture video then save into sdcard
 * 
 */
public class Camera_example extends Activity {
	private Camera camera;
	private int cameraId = 0;
	private CameraPreview mPreview;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera_example);
		
		checkCamera();
		  
		mPreview = new CameraPreview(this, camera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        
        Button captureButton = (Button) findViewById(R.id.button_capture);
    	captureButton.setOnClickListener(
    	    new View.OnClickListener() {
    	        @Override
    	        public void onClick(View v) {
    	            // get an image from the camera
    	            camera.takePicture(null, null, mPicture);
    	        }
    	    }
    	);
	}	
	
	
	// Handle save image captured from camera 
	private PictureCallback mPicture = new PictureCallback() {

	    @Override
	    public void onPictureTaken(byte[] data, Camera camera) {

	        File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
	        if (pictureFile == null){
	            Log.d("CAMERA", "Error creating media file, check storage permissions: ");
	            return;
	        }

	        try {
	            FileOutputStream fos = new FileOutputStream(pictureFile);
	            fos.write(data);
	            fos.close();
	        } catch (FileNotFoundException e) {
	            Log.d("CAMERA", "File not found: " + e.getMessage());
	        } catch (IOException e) {
	            Log.d("CAMERA", "Error accessing file: " + e.getMessage());
	        }
	        
	        Toast.makeText(getBaseContext(), pictureFile.toString(), Toast.LENGTH_LONG).show();
	    }
	};

	public void checkCamera(){
		 if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			 Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG).show();
		 } else {
			 
		     cameraId = findFrontFacingCamera();
			 //cameraId = findBackFacingCamera();
		      
		     if (cameraId < 0) {
		        Toast.makeText(this, "No front facing camera found.",Toast.LENGTH_LONG).show();
		     } else {
		    	 try{
		    	  camera = Camera.open(cameraId);
		    	 }catch (Exception e){
		    		 Toast.makeText(this, "Could not open camera",Toast.LENGTH_LONG).show();
		    	 }
		     }
		 }
	}
	
	private int findFrontFacingCamera() {
	    int cameraId = -1;
	    // Search for the front facing camera
	    int numberOfCameras = Camera.getNumberOfCameras();
	    
	    for (int i = 0; i < numberOfCameras; i++) {
	      CameraInfo info = new CameraInfo();
	      Camera.getCameraInfo(i, info);
	      if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
	        Log.d("CAMERA", "Camera front found");
	        cameraId = i;
	        break;
	      }
	    }
	    
	    return cameraId;
	}
	
	private int findBackFacingCamera() {
	    int cameraId = -1;
	    // Search for the front facing camera
	    int numberOfCameras = Camera.getNumberOfCameras();
	    
	    for (int i = 0; i < numberOfCameras; i++) {
	      CameraInfo info = new CameraInfo();
	      Camera.getCameraInfo(i, info);
	      if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
		        Log.d("CAMERA", "Camera back found");
		        cameraId = i;
		        break;
	      }
	    }
	    
	    return cameraId;
	}
	
	/** Create a file Uri for saving an image or video */
	private static Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */
	private static File getOutputMediaFile(int type){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.
		
		// save in /sdcard/pictures/mycameraapp
	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "MyCameraApp");
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("MyCameraApp", "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE){
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "IMG_"+ timeStamp + ".jpg");
	    } else if(type == MEDIA_TYPE_VIDEO) {
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "VID_"+ timeStamp + ".mp4");
	    } else {
	        return null;
	    }

	    return mediaFile;
	}
	
	@Override
	protected void onPause() {
	    if (camera != null) {
	      camera.release();
	      camera = null;
	    }
	    
	    super.onPause();
	}

}
