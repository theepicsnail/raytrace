import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.util.Pair;

public class Camera {
	
	int resolution, width, height, range;
	double fov, spacing;
	Container display;

	public Camera(Container display, int resolution, double fov) {
		this.fov = Math.PI * .4;
		this.resolution = resolution;
		this.display = display;
		this.width = display.getWidth() ;
		this.height = display.getHeight() ;// 2;
		this.spacing = this.width / (double)resolution;
		this.range = 100;
	}

	public void render(Player player, Map map, Graphics g) {
		this.drawSky(player.getDirection(), g);// , map.skybox, map.light);
		this.drawColumns(player, map, g);
	}

	private void drawSky(double direction, Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, display.getWidth(), display.getHeight());
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, display.getHeight() / 2, display.getWidth(),
				display.getHeight());
	}

	private void drawColumns(Player player, Map map, Graphics g) {
		for (int col = 0; col < this.resolution; col++) {
			double angle = this.fov * (col / (double) this.resolution - .5);
			ArrayList<Ray.Collision> ray = map.cast(player.getPosition(),
					player.getDirection() + angle, 10);
			this.drawColumn(col, ray, angle, map, g);
		}
	}

	private void drawColumn(int col, ArrayList<Ray.Collision> ray,
			double angle, Map map, Graphics g) {
		int hit = -1;
		int left = (int)(col * spacing);
		int width = (int)Math.ceil(this.spacing);
		
		BufferedImage texture = map.wallTexture;
		while (++hit < ray.size() && ray.get(hit).height <= 0)
			;
		
		for (int s = ray.size() - 1; s >= 0; s--) {
			Ray.Collision step = ray.get(s);

			if (s == hit) {
				int textureX = (int) (texture.getWidth() * step.offset);
				Pair<Integer, Integer> p = this.project(step.height, angle,
						step.distance);
				
				g.drawImage(texture, left, p.getKey(), left + width,
						p.getKey() + p.getValue(), textureX, 0, textureX + 1,
						texture.getHeight(), null);
			}
		}
	}

	/*
	 * Camera.prototype.project = function(height, angle, distance) { var z =
	 * distance * Math.cos(angle); var wallHeight = this.height * height / z;
	 * var bottom = this.height / 2 * (1 + 1 / z); return { top: bottom -
	 * wallHeight, height: wallHeight }; };
	 */
	private Pair<Integer, Integer> project(double height, double angle,
			double distance) {
		double z = distance * Math.cos(angle);
		//System.out.println("z:"+z);
		double wallHeight = this.height * height / z;
		double bottom = this.height / 2 * (1 + 1 / z);
		return new Pair<Integer, Integer>((int) (bottom - wallHeight),
				(int) wallHeight);
	}
}
