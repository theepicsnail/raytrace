import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Map {
	public BufferedImage wallTexture;
	private boolean[][] wallGrid;
	private int width, height;

	public Map() throws IOException {
		//wallTexture = ImageIO.read(new File("wall_texture.jpg"));
		String path = "C:\\Users\\Steven\\Downloads\\SimpleCraft_7\\assets\\minecraft\\textures\\blocks\\";
		wallTexture = ImageIO.read(new File(
			path + "door_iron_upper.png"
		//	path + "brick.png"
		));
		
	}
	public Map(int size) throws IOException {
		this();
		width = size;
		height = size;
		wallGrid = new boolean[width][height];
		randomize();
	}
	public Map(String[] mapData) throws IOException {
		this();
		wallGrid = new boolean[mapData.length][mapData[0].length()];
		for(int row = 0 ; row < wallGrid.length ; row ++)
			for(int col= 0 ; col < wallGrid[0].length ; col++)
				wallGrid[row][col] = mapData[row].charAt(col) != ' '; 
		height = wallGrid.length; 
		width = wallGrid[0].length;
		
	}

	private void randomize() {
		int mid = wallGrid.length/2;
		
		double filled_percent = .3;
		for (boolean[] row : wallGrid) {
			for (int col = row.length - 1; col >= 0; --col) {
				row[col] = false;
				//Math.random() < filled_percent;
				
			}
		}
		
		for(int i = 0 ; i < wallGrid.length; i++){
			wallGrid[i][0] = true;
			wallGrid[0][i] = true;
			wallGrid[wallGrid.length-1][i] = true;
			wallGrid[i][wallGrid.length-1] = true;
		}
		wallGrid[mid][mid] = true;
		
		for (boolean[] row : wallGrid)
		{
			for(boolean val : row)
				System.out.print(val?"X":"_");
			System.out.println();
		}
	}

	public boolean get(double x, double y) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return false;
		return wallGrid[(int)(y)][(int)(x)];
	}

	public ArrayList<Ray.Collision> cast(Point2D.Double origin, double angle,
			double range) {
		ArrayList<Ray.Collision> out = new ArrayList<Ray.Collision>();
		
		Ray ray = new Ray();
		ray.x = origin.x;
		ray.y = origin.y;
		ray.dx = Math.cos(angle);
		ray.dy = Math.sin(angle);
		range = 100;
		double distance = 0;
		out.add(new Ray.Collision().setDistance(0).setHeight(0));
		if(Controls.debug())
			System.out.println("Casting");
		while(distance <= range) {
			//Ray step_x = step(ray, false);
			//Ray step_y = step(ray, true);
			Ray step_x = step(ray.dy, ray.dx, ray.x, ray.y, false);
			Ray step_y = step(ray.dx, ray.dy, ray.y, ray.x, true);
			if(Controls.debug())
				//System.out.println(step_x.length() + ", " + step_y.length());
				System.out.println("\nx:"+step_x + "\ny:"+step_y);
			Ray.Collision collision = step_x.length() < step_y.length() ? 
					inspect(step_x, 1, 0, distance, step_x.y) :
					inspect(step_y, 0, 1, distance, step_y.x);
			out.add(collision);
			
			if (step_x.length() < step_y.length()) {
				//System.out.println("step_x" + step_x +" => "+ ray);
				ray = step_x;
			} else {
				//System.out.println("step_y" + step_y +" => "+ ray);
				ray = step_y;
			}
			distance = collision.distance;
			if(Controls.debug())
				System.out.println("Dist:" + distance);
		}
		return out;
	}

	private Ray step(double rise ,double run, double x, double y, boolean inverted) {
		
		if(run == 0) return new Ray().setDX(Double.MAX_VALUE);

		
		double dx = run > 0 ? Math.floor( x + 1) - x : Math.ceil( x - 1) - x;
		double dy = dx * (rise / run);

		return new Ray()
			.setX(inverted? y + dy : x + dx)
			.setY(inverted? x + dx : y + dy)
			.setDX(inverted? dy:dx)
			.setDY(inverted? dx:dy); // not inverted, this is only used for length calculation
	}
	
	private Ray.Collision inspect(Ray step, int shift_x, int shift_y, double distance, double offset) {
		int dx = step.dx < 0 ? shift_x : 0;
		int dy = step.dy < 0 ? shift_y : 0;
		double height = this.get(step.x - dx, step.y - dy) ? 1 : 0;
		distance += Math.sqrt(step.length());
		
		double shading = 0;
		if (shift_x != 0) {
			shading = step.dx < 0 ? 2 : 0;
		} else {
			shading = step.dy < 0 ? 2 : 1;
		}
		
		offset -= Math.floor(offset);
		//System.out.println(offset);
		return new Ray.Collision()
			.setHeight(height)
			.setDistance(distance)
			.setShading(shading)
			.setOffset(offset);
	}
}
