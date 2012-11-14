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

			effects(display.getColors());

			if (display.getDisplayWidth() == 0) {
				System.out.println("waiting for display...");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}

			display.clear(sprites);

			for (Sprite sprite : sprites) {

				if (sprite.v_x == 0) {
					sprite.v_x = 1 + random.nextInt(4);
					sprite.v_y = 1 + random.nextInt(4);
					sprite.x = random.nextInt(display.getDisplayWidth() - sprite.width);
					sprite.y = random.nextInt(display.getDisplayHeight() - sprite.height);
				}

				if (sprite.x >= (display.getDisplayWidth() - sprite.width) || sprite.x <= 0) {
					sprite.v_x = sprite.v_x * -1;
				}

				if (sprite.y >= (display.getDisplayHeight() - sprite.height) || sprite.y <= 0) {
					sprite.v_y = sprite.v_y * -1;
				}

				sprite.move();


				if (sprite.x > (display.getDisplayWidth() - sprite.width)) {
					sprite.x = display.getDisplayWidth() - sprite.width;
				}

				if (sprite.x < 0) {
					sprite.x = 0;
				}

				if (sprite.y > (display.getDisplayHeight() - sprite.height)) {
					sprite.y = display.getDisplayHeight() - sprite.height;
				}

				if (sprite.y < 0) {
					sprite.y = 0;
				}


			}

			display.draw(sprites);

			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void effects(int[] colors) {

		if (colors != null) {

			for (int i = 0; i < colors.length; i++) {

				colors[i] = colors[i] + 1;

			}
		}
	}
}
