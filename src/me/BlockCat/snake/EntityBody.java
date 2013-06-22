package me.BlockCat.snake;


public class EntityBody extends Entity{

	private int live = 0;
	private EntitySnake snake;
	
	public EntityBody(String ref, int x, int y, int live,EntitySnake snake) {
		super(ref, x, y);
		this.snake = snake;
		this.live = live;
	}
	
	public void tick() {
		die();
		if (live <= 0) {
			snake.removeEntity(this);
		}
	}
	
	public void die() {
		live--;
	}

}
