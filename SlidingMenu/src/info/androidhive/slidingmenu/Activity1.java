package info.androidhive.slidingmenu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Activity1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity1_layout);
		Log.i("Activity1", "onCreate()");
		
		Button bt = (Button) findViewById(R.id.button1);
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				DialogFragment dialog = new ExitAppDialogFragment();
				dialog.show(getFragmentManager(), null);
			}
		});
	}

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		Log.i("Activity1", "onAttachedToWindow()");
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Log.i("Activity1", "onBackPressed()");
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		Log.i("Activity1", "onConfigurationChanged");
	}

	@Override
	public void onContentChanged() {
		// TODO Auto-generated method stub
		super.onContentChanged();
		Log.i("Activity1", "onContentChanged()");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("Activity1", "onDestroy()");
	}

	@Override
	public void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
		Log.i("Activity1", "onDetachedFromWindow()");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i("Activity1", "onPause()");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.i("Activity1", "onRestart()");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		Log.i("Activity1", "onRestoreInstanceState");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i("Activity1", "onResume()");
	}


	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		Log.i("Activity1", "onSaveInstanceState");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.i("Activity1", "onStart()");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i("Activity1", "onStop()");
	}

	/**
	 * Dialog show when user press back button
	 * @author cxphong
	 */
	public static class ExitAppDialogFragment extends DialogFragment {
	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the Builder class for convenient dialog construction
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        builder.setMessage("Do you want to exit?Do you want to exit?Do you want to exit?Do you want to exit?Do you wa" +
	        		"nt to exit?Do you want to exit?Do you want to exit?Do you want to exit?Do you want to exit?" +
	        		"Do you want to exit?Do you want to exit?Do you want to exit?Do you want to exit?" +
	        		"Do you want to exit?Do you want to exit?Do you want to exit?Do you want to exit?" +
	        		"Do you want to exit?Do you want to exit?Do you want to exit?Do you want to exit?" +
	        		"Do you want to exit?Do you want to exit?Do you want to exit?Do you want to exit?" +
	        		"o you want to exit?Do you want to exit?Do you want to exit?Do you want to exit?Do you wa" +
	        		"nt to exit?Do you want to exit?Do you want to exit?Do you want to exit?Do you want to exit?" +
	        		"Do you want to exit?Do you want to exit?Do you want to exit?Do you want to exit?" +
	        		"Do you want to exit?Do you want to exit?Do you want to exit?Do you want to exit?" +
	        		"Do you want to exit?Do you want to exit?Do you ")
	               .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       getActivity().finish();
	                   }
	               })
	               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       dismiss();
	                   }
	               });
	        
	        return builder.create();
	    }
	}

}
