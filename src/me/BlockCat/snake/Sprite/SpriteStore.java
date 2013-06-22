package me.BlockCat.snake.Sprite;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;


public class SpriteStore {
	
	private static SpriteStore instance = null;
	private HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
	
	private SpriteStore() {}

	public static SpriteStore get() {
		if (instance == null) {
			instance = new SpriteStore();
		}
		return instance;
	}

	public Sprite getSprite(String ref) {
		if (sprites.containsKey(ref)) return sprites.get(ref);
		
		BufferedImage sourceImage;
		
		try {
			URL url = this.getClass().getClassLoader().getResource(ref);
			if (url == null) {
				System.out.println("Can't find: " + ref);
			}
			sourceImage = ImageIO.read(url);
			
			GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
			Image image = gc.createCompatibleImage(sourceImage.getWidth(),sourceImage.getHeight(),Transparency.BITMASK);
			
			image.getGraphics().drawImage(sourceImage, 0, 0, null);
			
			Sprite sprite = new Sprite(image);
			sprites.put(ref, sprite);
			return sprite;
		} catch(Exception e) {
			return null;
		}
	}

}
