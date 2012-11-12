package com.bru.android.fun;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainActivity extends Activity {

	MySurfaceView mySurfaceView;

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

	class MySurfaceView extends SurfaceView implements Runnable, Display {

		private Thread drawingThread;
		private Thread gameThread;
		private SurfaceHolder surfaceHolder;
		private volatile boolean running;
		private volatile int[] colors;
		private Paint paint;
		private int width;
		private int height;

		public MySurfaceView(Context context) {
			super(context);
			surfaceHolder = getHolder();
			paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		}

		public void onResumeMySurfaceView() {
			running = true;
			drawingThread = new Thread(this);
			gameThread = new GameThread(this);
			drawingThread.start();
			gameThread.start();
		}

		public void onDraw(Canvas canvas) {
			if (width == 0) {
				width = canvas.getWidth();
				height = canvas.getHeight();
				assert (width > 0);
				assert (height > 0);
				colors = new int[width * height];
			}
			canvas.drawBitmap(colors, 0, width, 0, 0, width, height, true, paint);
		}

		public int getDisplayWidth(){
			return width;
		}

		public int getDisplayHeight(){
			return height;
		}


		public void onPauseMySurfaceView() {
			boolean retry = true;
			running = false;
			while (retry) {
				try {
					drawingThread.join();
					gameThread.join();
					retry = false;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		public void run() {
			while (running) {
				if (surfaceHolder.getSurface().isValid()) {
					Canvas canvas = surfaceHolder.lockCanvas();
					if (canvas != null) {
						onDraw(canvas);
					}
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}

		public void setPixel(int x, int y, int argb) {
			colors[y * width + x] = argb;
		}

		public void draw(Sprite... sprites) {

			try {
				if (colors != null) {
					for (Sprite sprite : sprites) {

						int x = 0;
						int y = 0;

						for (int color : sprite.colors) {

							int p_x = sprite.x + x;
							int p_y = sprite.y + y;
							setPixel(p_x, p_y, color);

							x++;
							if (x == sprite.width) {
								x = 0;
								y++;
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}


