import java.awt.geom.Point2D;


public class Player {
	public static final double PI2 = Math.PI*2;
	private double direction;
	private double y;
	private double x;

	public Player(double x, double y, double direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	public void rotate(double angle) {
		this.direction += angle + PI2;
		this.direction %= PI2;
	//	System.out.println("Pos("+this.x +", " + this.y +") Dir(" + this.direction +")");
	}
	
	public void walk(double distance, Map m) {
		double dx = Math.cos(this.direction) * distance;
		double dy = Math.sin(this.direction) * distance;
		if (!m.get(this.x + dx, this.y)) 
			this.x += dx;
		if (!m.get(this.x, this.y + dy)) 
			this.y += dy;
	//	System.out.println("Pos("+this.x +", " + this.y +") Dir(" + this.direction +")");
	}
	
	public void update(Map m, double seconds) {
		if (Controls.forward()) 
			this.walk(3 * seconds, m);
		if (Controls.backward())
			this.walk(-3 * seconds, m);
		if (Controls.left())
			this.rotate(-Math.PI * seconds);
		if (Controls.right())
			this.rotate(Math.PI * seconds);
		
	}

	public Point2D.Double getPosition() {
		return new Point2D.Double(this.x, this.y);
	}

	public double getDirection() {
		return direction;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	
	
}
