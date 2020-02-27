package drawit;

public class DoublePoint {
	private double x;
	private double y;

	public DoublePoint(double xGiven, double yGiven) {
		x = xGiven;
		y = yGiven;
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
		int xRounded = (int) (x + 0.5);
		int yRounded = (int) (y + 0.5);

		return new IntPoint(xRounded, yRounded);
	}
}