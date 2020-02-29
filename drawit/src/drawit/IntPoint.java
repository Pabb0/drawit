package drawit;

public class IntPoint {
	private int x;
	private int y;

	public IntPoint(int xGiven, int yGiven) {
		x = xGiven;
		y = yGiven;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean equals(IntPoint other) {
		return (x == other.getX() && y == other.getY());
	}

	public IntPoint plus(IntVector other) {
		int xSum = x + other.getX();
		int ySum = y + other.getY();

		return new IntPoint(xSum, ySum);
	}

	public IntVector minus(IntPoint other) {
		int xDiff = x - other.getX();
		int yDiff = y - other.getY();

		return new IntVector(xDiff, yDiff);
	}

	public boolean isOnLineSegment(IntPoint b, IntPoint c) {
		IntVector ba = this.minus(b);
		IntVector bc = c.minus(b);

		if (!(ba.isCollinearWith(bc))) {
			return false;
		}
		
		return (ba.dotProduct(bc) > 0 && ba.dotProduct(bc) < bc.dotProduct(bc));
	}

	public static boolean lineSegmentIntersect(IntPoint a, IntPoint b, IntPoint c, IntPoint d) {
		IntVector ab = b.minus(a);
		IntVector ac = c.minus(a);
		IntVector ad = d.minus(a);
		IntVector ca = a.minus(c);
		IntVector cb = b.minus(c);
		IntVector cd = d.minus(c);

		float con1 = Math.signum(ac.crossProduct(ab)) * Math.signum(ad.crossProduct(ab));
		float con2 = Math.signum(ca.crossProduct(cd)) * Math.signum(cb.crossProduct(cd));

		return (con1 < 0 && con2 < 0);
	}

	public DoublePoint asDoublePoint( ) {
		double xDouble = (double) x;
		double yDouble = (double) y;

		return new DoublePoint(xDouble, yDouble);
	}

}