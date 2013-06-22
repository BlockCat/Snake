package me.BlockCat.Particles;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import me.BlockCat.snake.Snake;

public class ParticleStore {


	private class Particle {
		public boolean active = false;
		public float life;
		public float fade;
		public Color color = Color.GREEN;
		public int x;
		public int y;
		public double yV = 0;
		public double xV = 0;

	}

	private ArrayList<Particle> particles = new ArrayList<Particle>();

	public ParticleStore(Snake snake) {

	}

	public void init(){
		for (int i = 0; i < 1000 ; i++) {
			Particle p = new Particle();
			p.life = 0.05F;
			p.fade = (float) (Math.random()/100 + 0.03F);
			p.x = 40;
			p.y = 40;
			particles.add(p);
		}
	}

	public void create(int x, int y, double d, double e, Color color) {
		for (Particle p : particles) {
			if (!p.active) {
				p.life = 0.05F;
				p.x = x;
				p.y = y;
				p.yV = d;
				p.xV = e;
				p.active = true;
				p.color = color;
				
				return;
			}
		}
	}

	public void move() {
		for (Particle p : particles) {
			if (p.active) {
				p.x +=p.xV;
				p.y +=p.yV;
				p.life -= p.fade;

				if (p.life <= 0.0) {
					p.x = -20;
					p.y = -20;
					p.xV = 0;
					p.yV = 0;
					p.active = false;
				}
			}
		}
	}

	public void draw(Graphics2D g) {
		for (Particle p : particles) {
			if (p.active) {
				//Color color = new Color(9, 9, 9, p.life * 250);
				g.setColor(p.color);
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, p.life*20));
				g.fillRect(p.x, p.y, 20, 20);
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
			}
		}
	}

}
