package me.BlockCat.snake;

import java.util.Random;


public class EntityFood extends Entity {
	private Random random = new Random();
	
	
	public EntityFood(String ref, int x, int y) {
		super(ref, x, y);
		
		
		this.x = random.nextInt(40)*20;
		this.y = random.nextInt(30)*20;
		
	}
	
	
	
	@Override
	public void move(long delta) {
		this.setVibX(random.nextInt(4)-2);
		this.setVibY(random.nextInt(4)-2);				
	}
	
	public void jump() {
		Random random = new Random();
		this.x = random.nextInt(40)*20;
		this.y = random.nextInt(30)*20;
	}

	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}

}
