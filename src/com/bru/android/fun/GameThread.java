package com.bru.android.fun;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 12/11/12
 * Time: 21:29
 */
public class GameThread extends Thread {

	public static final int CNT = 10;

	private Display display;
	private Random random;
	private Sprite[] sprites;

	public GameThread(Display display) {
		this.display = display;
		this.random = new Random();

		sprites = new Sprite[CNT];
		for (int i = 0; i < sprites.length; i++) {
			sprites[i] = new Sprite(random.nextInt(20), random.nextInt(20), random.nextInt());
		}
	}

	@Override
	public void run() {
		while (display.isRunning()) {

			if (display.getDisplayWidth() == 0){
				System.out.println("waiting for display...");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}

			for (Sprite sprite : sprites) {
				sprite.x = random.nextInt(display.getDisplayWidth() - sprite.width);
				sprite.y = random.nextInt(display.getDisplayHeight() - sprite.height);
			}
			display.draw(sprites);
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
