package me.BlockCat.snake;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import me.BlockCat.snake.Sprite.Sprite;
import me.BlockCat.snake.Sprite.SpriteStore;


public class Entity {
	
	public int x;
	public int y;
	public int vX;
	public int vY;
	public int vibX;
	public int vibY;
	private Color color = null;
	public Sprite sprite;
	
	public Entity(String ref,int x, int y) {
		this.x = x;
		this.y = y;
		this.sprite = SpriteStore.get().getSprite(ref);		
	}
	
	public void move(long delta) {
		y+= vY;
		x+= vX;
	}
	
	public void setVibX(int v) {
		vibX = v;
	}
	
	public void setVibY(int v) {
		vibY = v;
	}
	
	public void setYSpeed(int speed) {
		vY = speed;
	}
	
	public void setXSpeed (int speed) {
		vX = speed;
	}
	
	public Color getColor() {
		if (color == null) {
		Image image = sprite.getImage();
		int clr=  ((BufferedImage) image).getRGB(10,10); 
		  int  red   = (clr & 0x00ff0000) >> 16;
		  int  green = (clr & 0x0000ff00) >> 8;
		  int  blue  =  clr & 0x000000ff;
		  color = new Color(red,green,blue);
		  return color;
		} else {
			return color;
		}
	}

	public void draw(Graphics2D g) {
		int xt = x+vibX;
		int yt = y+vibY;
		sprite.draw(g, xt, yt);		
	}

	

}
