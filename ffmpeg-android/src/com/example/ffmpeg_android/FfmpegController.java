package com.example.ffmpeg_android;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.util.Log;

public class FfmpegController {

	private final static String TAG = "FFMPEG";
	private Context context;
	private File ffmpegbin;
	
	/* Copy & chmod ffmpeg binary file */
	public FfmpegController(Context ctext){
		context = ctext;
		ffmpegbin = new File(context.getFilesDir().getPath() + "/ffmpeg");
		
		if (ffmpegbin.exists() == false)
		{
			/* Copy ffmpeg binary from assets to /data/data/<app>/files */ 
			try {
				copy_From_Assets_Into_AppCache(context.getAssets().open("ffmpeg"), ffmpegbin);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			/* chmod 755 */
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec("chmod 755 " + ffmpegbin.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	public int execFFMPEG(String FFMPEGCommand) throws IOException, InterruptedException {		
		List<String> cmd = new ArrayList<String>();
		String[] splited = FFMPEGCommand.split("\\s+");
		
		cmd.add(ffmpegbin.getAbsolutePath());
		for (int i = 1; i< splited.length; i++)
			cmd.add(splited[i]);
		
		//ensure that the arguments are in the correct Locale format
		for (String tmp :cmd)
		{
			tmp = String.format(Locale.US, "%s", tmp);
		}
				
		ProcessBuilder pb = new ProcessBuilder(cmd);
		pb.redirectErrorStream(true);
		Process process = pb.start();    
		
		BufferedReader reader = new BufferedReader(
            new InputStreamReader(process.getInputStream()));
		int read;
		char[] buffer = new char[4096];
    	StringBuffer output = new StringBuffer();
    	while ((read = reader.read(buffer)) > 0) {
    		output.append(buffer, 0, read);
    	}	
    
    	Log.i(TAG, output.toString());
        int exitVal = process.waitFor();
        
        return exitVal;
		
	}
	
	private void copy_From_Assets_Into_AppCache(InputStream myInput, File dest) throws IOException {
	    OutputStream myOutput = new FileOutputStream(dest.getAbsolutePath());
	    Log.i(TAG, dest.getAbsolutePath());
	    
	    byte[] buff = new byte[1024];
        int read = 0;

        while ((read = myInput.read(buff)) > 0) {
         myOutput.write(buff, 0, read);
        }
        
	    // Close the streams
	    myOutput.flush();
	    myOutput.close();
	    myInput.close();
	}

	
}

