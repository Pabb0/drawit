package drawit;

public class DoublePoint {
	private final double x;
	private final double y;

	public DoublePoint(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public DoublePoint plus(DoubleVector other) {
		double xSum = x + other.getX();
		double ySum = y + other.getY();
		
		return new DoublePoint(xSum, ySum);
	}

	public DoubleVector minus(DoublePoint other) {
		double xDiff = x - other.getX();
		double yDiff = y - other.getY();

		return new DoubleVector(xDiff, yDiff);
	}

	public IntPoint round() {
		int xRounded = (int) Math.round(x);
		int yRounded = (int) Math.round(y);

		return new IntPoint(xRounded, yRounded);
	}
}