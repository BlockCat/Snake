package me.BlockCat.snake;

import java.applet.Applet;

public class SnakeApplet extends Applet {
	
	public void init() {
		
		this.setSize(800, 600);
		setLayout(null);
		setBounds(0, 0, 800, 600);
		
		setIgnoreRepaint(true);

		setVisible(true);
		final Snake snake = new Snake();
		snake.initApplet(this);
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				
				snake.startGame();
			}
		});
		t.run();
	}

}
