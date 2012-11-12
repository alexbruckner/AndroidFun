package com.bru.android.fun;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 12/11/12
 * Time: 19:38
 */
public class GameThread extends Thread {

	private Display display;

	public GameThread(Display display) {
		this.display = display;
	}

	@Override
	public void run() {

		Sprite test = new Sprite();
		Sprite test2 = new Sprite();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < display.getDisplayWidth()-10; i++){
			test.x = test.y = i;
			display.draw(test, test2);
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
