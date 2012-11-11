package com.bru.android.fun;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class MainActivity extends Activity {

	MySurfaceView mySurfaceView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mySurfaceView = new MySurfaceView(this);
		setContentView(mySurfaceView);
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

	class MySurfaceView extends SurfaceView implements Runnable{

		Thread thread = null;
		SurfaceHolder surfaceHolder;
		volatile boolean running = false;

		private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		Random random;

		volatile boolean touched = false;
		volatile float touched_x, touched_y;

		public MySurfaceView(Context context) {
			super(context);
			surfaceHolder = getHolder();
			random = new Random();
		}

		public void onResumeMySurfaceView(){
			running = true;
			thread = new Thread(this);
			thread.start();
		}

		public void onPauseMySurfaceView(){
			boolean retry = true;
			running = false;
			while(retry){
				try {
					thread.join();
					retry = false;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		public void run() {
			while(running){
				if(surfaceHolder.getSurface().isValid()){
					Canvas canvas = surfaceHolder.lockCanvas();
					//... actual drawing on canvas

					paint.setStyle(Paint.Style.STROKE);
					paint.setStrokeWidth(3);

					int w = canvas.getWidth();
					int h = canvas.getHeight();
					int x = random.nextInt(w-1);
					int y = random.nextInt(h-1);
					int r = random.nextInt(255);
					int g = random.nextInt(255);
					int b = random.nextInt(255);
					paint.setColor(0xff000000 + (r << 16) + (g << 8) + b);
					canvas.drawPoint(x, y, paint);

					if(touched){
						paint.setStrokeWidth(50);
						paint.setColor(Color.BLACK);
						canvas.drawPoint(touched_x, touched_y, paint);
					}

					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {

			touched_x = event.getX();
			touched_y = event.getY();

			int action = event.getAction();
			switch(action){
				case MotionEvent.ACTION_DOWN:
					touched = true;
					break;
				case MotionEvent.ACTION_MOVE:
					touched = true;
					break;
				case MotionEvent.ACTION_UP:
					touched = false;
					break;
				case MotionEvent.ACTION_CANCEL:
					touched = false;
					break;
				case MotionEvent.ACTION_OUTSIDE:
					touched = false;
					break;
				default:
			}
			return true; //processed
		}

	}
}
