package me.BlockCat.snake;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import me.BlockCat.Particles.ParticleStore;

public class Snake extends Canvas {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

		final Snake snake = new Snake();
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				snake.initGame();
				snake.startGame();
				
			}
		});
		t.run();
	}

	private EntitySnake snake;
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private BufferStrategy strategy;
	private boolean gameRunning = false;
	private boolean left;
	private boolean right;
	private boolean down;
	private boolean up;
	private boolean updateDirection;
	private boolean initiated;
	private static boolean alive = false;
	private Font font = null;

	public ParticleStore particles;
	private EntityFood food;
	private Random r = new Random();
	
	private int score = 0;

	public void initApplet(SnakeApplet app) {
		

		setBounds(0, 0, 800, 600);
		app.add(this);

		setIgnoreRepaint(true);



		this.createBufferStrategy(2);
	}
	public void initGame() {
		JFrame container = new JFrame("Snake");
		JPanel pane = (JPanel) container.getContentPane();

		pane.setPreferredSize(new Dimension(800, 600));
		pane.setLayout(null);
		setBounds(0, 0, 800, 600);
		pane.add(this);

		setIgnoreRepaint(true);

		container.pack();
		container.setResizable(false);
		container.setVisible(true);

		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		this.createBufferStrategy(2);
		
		
	}

	public void startGame() {
		strategy = getBufferStrategy();
		addKeyListener(new KeyInputHandler());
		
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		particles = new ParticleStore(this);
		particles.init();
		this.initFond(g);

		food = new EntityFood("sprites/food.png", 90, 90);
		snake = new EntitySnake(this, food, "sprites/body.png", 20, 200);
		entities.add(snake);
		entities.add(food);

		gameRunning = true;
		alive = true;

		gameLoop();
	}

	private void gameLoop() {
		long lastTimeLoop = System.currentTimeMillis();
		int count = 0;
		while (gameRunning) {
			long delta = System.currentTimeMillis() - lastTimeLoop;
			lastTimeLoop = System.currentTimeMillis();

			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();

			this.initFond(g);

			Color c = new Color(150, 150, r.nextInt(10) + 135);
			g.setColor(c);
			g.fillRect(0, 0, 800, 600);

			particles.draw(g);
			for (Entity entity : entities) {
				entity.draw(g);
				this.drawText(g, "Lenght: " + snake.length, 10, 30);
			}

			snake.draw(g);

			snake.setYSpeed(0);
			snake.setXSpeed(0);
			count++;
			if (count < 0) {
				this.drawText(g, "You died!", 250, 300);
			}
			if (count >= 5) {
				if (alive) {
					if (left && !right) {
						snake.setXSpeed(-20);
					} else if (right && !left) {
						snake.setXSpeed(20);
					} else if (down && !up) {
						snake.setYSpeed(20);
					} else if (up && !down) {
						snake.setYSpeed(-20);
					}
					updateDirection = false;
					for (Entity entity : entities) {
						entity.move(delta);
						int vx = r.nextInt(30) - 15;
						int vy = r.nextInt(30) - 15;

						particles.create(entity.x, entity.y, vx, vy,
								entity.getColor());
					}

					for (int i = 0; i < 1; i++) {
						int vx = r.nextInt(30) - 15;
						int vy = r.nextInt(30) - 15;

						particles.create(food.x, food.y, vx, vy,
								food.getColor());
					}
					particles.move();
					count = 0;
				} else if (!alive) {
					g.setColor(Color.WHITE);
					snake.length = 1;
					up = false;
					down = false;
					left = false;
					right = false;
					count = -10;
				}

				snake.tick();
			}

			g.dispose();
			strategy.show();
			try {
				Thread.sleep(20);
			} catch (Exception e) {
			}
		}
	}

	private void initFond(Graphics2D g) {
		if (!initiated) {
			InputStream is = this.getClass().getClassLoader()
					.getResourceAsStream("fonts/TechnoHideo.ttf");

			try {
				font = Font.createFont(Font.TRUETYPE_FONT, is);

			} catch (Exception e) {
				e.printStackTrace();
				font = new Font(Font.SANS_SERIF, 4, 4);
			}
			font = font.deriveFont(40.0f);
			initiated = true;
		}
	}

	private void drawText(Graphics2D g, String string, int i, int j) {

		int d1 = r.nextInt(2) - 1;
		int d2 = r.nextInt(2) - 1;
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawString(string, i + 2 + d1, j + 2 + d2);
		g.setColor(Color.DARK_GRAY);
		g.drawString(string, i + d1, j + d2);
	}

	public static void alive(boolean dead) {
		alive = dead;
	}

	public void addEntities(Entity e) {
		entities.add(e);
	}

	public void removeEntities(ArrayList<Entity> removeList) {
		entities.removeAll(removeList);
	}

	private class KeyInputHandler extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_1) {
				for (Entity entity : entities) {
					particles.create(entity.x, entity.y,
							(((Math.random() + 0.2) * 2) - 1),
							((Math.random() + 0.2) * 2) - 1, entity.getColor());
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				if (!down) {
					up = true;
					left = false;
					right = false;
					down = false;
					updateDirection = true;
				}
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				if (!up) {
					up = false;
					left = false;
					right = false;
					down = true;
					updateDirection = true;
				}
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				if (!right) {
					up = false;
					left = true;
					right = false;
					down = false;
					updateDirection = true;
				}
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if (!left) {
					up = false;
					left = false;
					right = true;
					down = false;
					updateDirection = true;
				}
			}
		}
	}
}
