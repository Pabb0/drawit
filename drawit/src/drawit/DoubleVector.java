package drawit;

public class DoubleVector {
	private final double x;
	private final double y;
	
	public DoubleVector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getSize() {
		return Math.sqrt(x*x + y*y);
	}

	public DoubleVector plus(DoubleVector other) {
		double xSum = x + other.getX();
		double ySum = y + other.getY();

		return new DoubleVector(xSum, ySum);
	}

	public DoubleVector scale(double d) {
		return new DoubleVector(x*d, y*d);
	}

	public double dotProduct(DoubleVector other) {
		return x*other.getX() + y*other.getY();
	}

	public double crossProduct(DoubleVector other) {
		return x*other.getY() - y*other.getX();
	}

	public double asAngle( ) {
		return Math.atan2(y, x);
	}
	
}