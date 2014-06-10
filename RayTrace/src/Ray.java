import java.awt.geom.Point2D;

public class Ray {
	public double x, y, dx, dy;
	public String toString() {
		return "(" + x +", " + y +") d(" + dx +", "+dy+")";
	}
	public double length() {
		return dx * dx + dy * dy;
	}

	public Ray setX(double x) {
		this.x = x;
		return this;
	}

	public Ray setY(double y) {
		this.y = y;
		return this;
	}

	public Ray setDX(double dx) {
		this.dx = dx;
		return this;
	}

	public Ray setDY(double dy) {
		this.dy = dy;
		return this;
	}

	public static class Collision {
		public double height;

		public Collision setHeight(double height) {
			this.height = height;
			return this;
		}

		public double distance;

		public Collision setDistance(double distance) {
			this.distance = distance;
			return this;
		}

		public double shading;

		public Collision setShading(double shading) {
			this.shading = shading;
			return this;
		}

		public double offset;

		public Collision setOffset(double offset) {
			this.offset = offset;
			return this;
		}

	}
}
