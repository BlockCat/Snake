package me.BlockCat.snake.Sprite;

import java.awt.Graphics2D;
import java.awt.Image;

public class Sprite {
	private Image image;

	public Sprite(Image image) {
		this.image = image;
	}

	public void draw(Graphics2D g, int x, int y) {
		g.drawImage(image, x, y, null);	
	}
	
	public Image getImage() {
		return image;
	}

}
