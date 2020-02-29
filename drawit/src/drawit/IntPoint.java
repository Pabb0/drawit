package drawit;

/**
 * Each instance of this class represents a point in 2D-space with integer values.
 * @immutable
 */
public class IntPoint {
	private final int x;
	private final int y;

	/**
	 * Initializes this object with the given x-value and y-value.
	 * 
	 * @mutates | this
	 * 
	 * @post This object's x-value equals the given x-value.
	 * 		| getX() == xGiven
	 * @post This object's y-value equals the given y-value.
	 * 		| getY() == yGiven
	 */
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
	
	/**
	 * Returns whether two points are equal.
	 * 
	 * @inspects | this
	 * @inspects | other
	 * @creates | result
	 * 
	 * @post 
	 * 		| result == (this.getX() == other.getX() && this.getY() == other.getY())
	 * @post This point has remained unchanged.
	 * 		| this == old(this)
	 * @post The other given point has remained unchanged.
	 * 		| other == old(other)
	 */
	public boolean equals(IntPoint other) {
		return (x == other.getX() && y == other.getY());
	}
	
	/**
	 * Returns a new point which is the addition of this point with the given vector.
	 *
	 * @inspects | this
	 * @inspects | other
	 * @creates | result
	 *
	 * @throws IllegalArgumentException if the addition of the given vector to this point leads to an arithmetic overflow for the x-value. 
     * @throws IllegalArgumentException if the addition of the given vector to this point leads to an arithmetic overflow for the y-value.
     * 
	 * @post The resulting point is the sum of this point and the given vector
	 * 		| (result.getX() == (this.getX() + other.getX())) && (result.getY() == (this.getY() + other.getY()))
	 * @post This point has remained unchanged.
	 * 		| this == old(this)
	 * @post The other given point has remained unchanged.
	 * 		| other == old(other)
	 */
	public IntPoint plus(IntVector other) {
		if (this.getX() > 0
            ? Integer.MAX_VALUE - this.getX() < other.getX()
            : Integer.MIN_VALUE - this.getX() > other.getX()) {
			throw new IllegalArgumentException("The x-value of the given vector leads to an arithmetic overflow/underflow.");
		}
		if (this.getY() > 0
	        ? Integer.MAX_VALUE - this.getY() < other.getY()
	        : Integer.MIN_VALUE - this.getY() > other.getY()) {
			throw new IllegalArgumentException("The y-value of the given vector leads to an arithmetic overflow/underflow.");
			}
		
		int xSum = this.getX() + other.getX();
		int ySum = this.getY() + other.getY();

		return new IntPoint(xSum, ySum);
	}
	
	/**
	 * Returns a new vector which is the difference between this point and the given point.
	 * 
	 * @inspects | this
	 * @inspects | other
	 * @creates | result
	 *
	 * @throws IllegalArgumentException if the difference between this point and the given point leads to an arithmetic overflow/underflow for the x-value. 
	 * @throws IllegalArgumentException if the difference between this point and the given point leads to an arithmetic overflow/underflow for the y-value. 
     * 
	 * @post The resulting point is the sum of this point and the given vector
	 * 		| (result.getX() == (this.getX() - other.getX())) && (result.getY() == (this.getY() - other.getY()))
	 * @post This point has remained unchanged.
	 * 		| this == old(this)
	 * @post The other given point has remained unchanged.
	 * 		| other == old(other)
	 */
	public IntVector minus(IntPoint other) {
		if (this.getX() > 0
	            ? Integer.MIN_VALUE + this.getX() > other.getX()
	            : Integer.MAX_VALUE + this.getX() < other.getX()) {
				throw new IllegalArgumentException("The x-value of the given point leads to an arithmetic overflow/underflow.");
			}
			if (this.getY() > 0
		        ? Integer.MIN_VALUE + this.getY() > other.getY()
		        : Integer.MAX_VALUE + this.getY() < other.getY()) {
				throw new IllegalArgumentException("The y-value of the given point leads to an arithmetic overflow/underflow.");
				}
			
		int xDiff = this.getX() - other.getX();
		int yDiff = this.getY() - other.getY();

		return new IntVector(xDiff, yDiff);
	}
	/**
	 * Returns true if and only if this point is on open line segment bc. An open line segment does not include its endpoints.
	 * 
	 * @inspects this
	 * @inspects b
	 * @inspects c
	 * @creates result
	 * 
	 * @post This point has remained unchanged.
	 * 		| this == old(this)
	 * @post The other given points have remained unchanged.
	 * 		| b == old(b) && c == old(c)
	 */
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

	/**
	 * Returns a new point whose x-value and y-value are the double representation of the integer x-value and integer y-value of this vector.
	 * 
	 * @inspects this
	 * @creates result
	 * 
	 * @post The x-value of the result is equal to the x-value of this point.
	 * 		| result.getX() == this.getX()
	 * @post The y-value of the result is equal to the y-value of this point.
	 * 		| result.getY() == this.getY()
	 * @post This point has remained unchanged.
	 * 		| this == old(this)
	 */
	public DoublePoint asDoublePoint() {
		double xDouble = (double) this.getX();
		double yDouble = (double) this.getY();

		return new DoublePoint(xDouble, yDouble);
	}

}