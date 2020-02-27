package drawit;

public class IntVector {
	private int x;
	private int y;

	IntVector(int xGiven, int yGiven) {
		x = xGiven;
		y = yGiven;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public long dotProduct(IntVector other) {
		return (long) (x * other.getX()) + (long) (y * other.getY());
	}

	public long crossProduct(IntVector other) {
		return (long) (x * other.getY()) - (long) (y * other.getX());
	}

	public boolean isCollinearWith(IntVector other) {
		return (this.crossProduct(other) == 0);
	}

	public DoubleVector asDoubleVector() {
		double xDouble = (double) x;
		double yDouble = (double) y;

		return new DoubleVector(xDouble, yDouble);
	}
}