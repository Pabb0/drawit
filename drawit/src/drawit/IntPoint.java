package drawit;

/**
 * Each instance of this class represents an immutable abstraction for a point in 2D-space with integer values.
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
	 * 		| this.getX() == x
	 * @post This object's y-value equals the given y-value.
	 * 		| this.getY() == y
	 */
	public IntPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	/**
	 * Returns whether this point and the given point are equal.
	 * 
	 * @pre Argument {@code other} is not {@code null}.
	 * 		| other != null
	 * @post The result is true if the x-value of this point and the given point are equal and the y-value of this point and the given point are equal.
	 * 		| result == (this.getX() == other.getX() && this.getY() == other.getY())
	 */
	public boolean equals(IntPoint other) {
		return (x == other.getX() && y == other.getY());
	}
	
	public static boolean equals(IntPoint p1, IntPoint p2) {
		return
				p1 == p2 ||
				p1 != null && p2 != null &&
				p1.equals(p2);
	}
	/**
	 * Returns a new {@code IntPoint} object which is the addition of this {@code IntPoint} with the given {@code IntVector}.
	 *
	 * @inspects | this
	 * @inspects | other
	 * @creates | result
	 *
	 * @pre Argument {@code other} is not {@code null}.
	 * 		| other != null
	 * @pre The addition of the given vector to this point may not lead to an arithmetic overflow or underflow.
	 * 		| !(this.getX() > 0 ? Integer.MAX_VALUE - this.getX() < other.getX() : Integer.MIN_VALUE - this.getX() > other.getX()) &&
	 * 		| !(this.getY() > 0 ? Integer.MAX_VALUE - this.getY() < other.getY() : Integer.MIN_VALUE - this.getY() > other.getY())
     * 
	 * @post The resulting point is the sum of this point and the given vector
	 * 		| (result.getX() == (this.getX() + other.getX())) && (result.getY() == (this.getY() + other.getY()))
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
	 * @pre Argument {@code other} is not {@code null}.
	 * 		| other != null
	 * @pre The addition of the given vector to this point may not lead to an arithmetic overflow or underflow.
	 * 		| !(this.getX() > 0 ? Integer.MIN_VALUE + this.getX() > other.getX() : Integer.MAX_VALUE + this.getX() < other.getX()) &&
	 * 		| !(this.getY() > 0 ? Integer.MIN_VALUE + this.getY() > other.getY() : Integer.MAX_VALUE + this.getY() < other.getY())
	 * @post The resulting point is the sum of this point and the given vector
	 * 		| (result.getX() == (this.getX() - other.getX())) && (result.getY() == (this.getY() - other.getY()))
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
	 * @inspects | this
	 * @inspects | b
	 * @inspects | c
	 * 
	 * @pre Argument {@code b} is not {@code null}.
	 * 		| b != null
	 * @pre Argument {@code c} is not {@code null}.
	 * 		| c != null
	 * 
	 * @post Returns true if the 3 points are collinear equals zero and this point lies in between the two given points.
	 * 		| result == ((this.getX() * (b.getY() - c.getY())
	 * 		|			+ b.getX() * (c.getY() - this.getY())
	 * 		|			+ c.getX() * (this.getY() - b.getY()) == 0) &&
	 * 		| (Math.min(b.getX(), c.getX()) < this.getX() && this.getX() < Math.max(b.getX(), c.getX()) ||
	 * 		| Math.min(b.getY(), c.getY()) < this.getY() && this.getY() < Math.max(b.getY(), c.getY())))
	 * 
	 */
	public boolean isOnLineSegment(IntPoint b, IntPoint c) {
		IntVector ba = this.minus(b);
		IntVector bc = c.minus(b);

		if (!(ba.isCollinearWith(bc))) {
			return false;
		}
		
		return (ba.dotProduct(bc) > 0 && ba.dotProduct(bc) < bc.dotProduct(bc));
	}
	
	// No documentation required
	public static boolean lineSegmentsIntersect(IntPoint a, IntPoint b, IntPoint c, IntPoint d) {
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
	 * @inspects | this
	 * @creates | result
	 * 
	 * @post The x-value of the result is equal to the x-value of this point.
	 * 		| result.getX() == this.getX()
	 * @post The y-value of the result is equal to the y-value of this point.
	 * 		| result.getY() == this.getY()
	 */
	public DoublePoint asDoublePoint() {
		double xDouble = (double) this.getX();
		double yDouble = (double) this.getY();

		return new DoublePoint(xDouble, yDouble);
	}

}