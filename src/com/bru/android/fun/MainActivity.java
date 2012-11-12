package com.bru.android.fun;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	private MySurfaceView mySurfaceView;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mySurfaceView = new MySurfaceView(this);
		setContentView(mySurfaceView);
		onResume();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mySurfaceView.onResumeMySurfaceView();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mySurfaceView.onPauseMySurfaceView();
	}


}


