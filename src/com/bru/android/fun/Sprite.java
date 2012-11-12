package com.bru.android.fun;


/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 07/10/12
 * Time: 11:43
 */
public class Sprite {

	public int x;
	public int y;
	public int width;
	public int height;
	public int[] colors;

	public Sprite(int width, int height, int color){
		this.width = width;
		this.height = height;
		colors = new int[width*height];
		for (int i = 0; i < width; i++){
			for (int j = 0; j < height; j++){
				colors[i*height+j] = color;
			}
		}
	}

}
