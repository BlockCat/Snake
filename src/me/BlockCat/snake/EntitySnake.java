package me.BlockCat.snake;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;


public class EntitySnake extends Entity{

	public int length = 1;

	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<Entity> removeList = new ArrayList<Entity>();

	private EntityFood food;
	private Snake snake;

	public EntitySnake(Snake snake, Entity food, String ref, int x, int y) {
		super(ref, x, y);
		this.snake = snake;
		this.food = (EntityFood)food;

	}


	public void move(long delta) {
		super.move(delta);


		for (Entity entity : entities) {

			if (x == entity.x && y == entity.y && (vX != 0 || vY != 0)) {
				Snake.alive(false);
			} else {
				continue;
			}
		}
		if (x == food.getX() && y == food.getY()) {
			length++;
			food.jump();
		}

		if (y > 580) {
			y = 0;
		}
		if (y < 0) {
			y = 580;
		}
		if (x > 780) {
			x = 0;
		}
		if (x < 0) {
			x = 780;
		}
		EntityBody body = new EntityBody("sprites/body.png", x, y, length, this);
		entities.add(body);

	}

	public void tick() {
		entities.removeAll(removeList);
		snake.removeEntities(removeList);
		for (Entity entity : entities) {			
			((EntityBody) entity).tick();
			Random r = new Random();
			int vx = r.nextInt(30)-15;
			int vy = r.nextInt(30)-15;

			snake.particles.create(entity.x, entity.y,vx,vy, Color.WHITE);
		}
		if (entities.isEmpty()) {
			Snake.alive(true);
			vX = 0;
			vY = 0;
		}
	}

	public void killed() {
		for (Entity entity : entities) {
			((EntityBody) entity).tick();
		}
	}

	public void removeEntity(Entity body) {
		removeList.add(body);
	}

	public void draw(Graphics2D g) {
		for (Entity entity : entities) {
			entity.draw(g);
		}
		super.draw(g);
	}

	public void feed() {
		length++;
	}



}
