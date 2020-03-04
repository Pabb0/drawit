package drawit;

/**
 * Each instance of this class represents a vector in 2D-space with integer values.
 * 
 * @immutable
 */
public class IntVector {
	private final int x;
	private final int y;
	
	/**
	 * Initializes this object with the given x-value and y-value.
	 * 
	 * @mutates | this
	 * 
	 * @post This object's x-value equals the given x-value.
	 * 		| this.getX() == xGiven
	 * @post This object's y-value equals the given y-value.
	 * 		| this.getY() == yGiven
	 */
	public IntVector(int xGiven, int yGiven) {
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
	 * Returns the dot product of this vector and the given vector.
	 * 
	 * @inspects | this
	 * @inspects | other
	 * 
	 * @pre Argument {@code other} is not {@code null}.
	 * 		other != null
	 * @post The result equals the dot product of this vector with the given vector.
	 * 		| result == (long) (this.getX() * other.getX()) + (long) (this.getY() * other.getY())
	 */
	// There will never be overflow when converting to long (Integer.MAX_VALUE * Integer.MAX_VALUE * 2 < Long.MAX_VALUE)
	public long dotProduct(IntVector other) {
		return (long) (this.getX() * other.getX()) + (long) (this.getY() * other.getY());
	}

	/**
	 * Returns the cross product of this vector and the given vector.
	 * 
	 * @inspects | this
	 * @inspects | other
	 *
	 * @pre Argument {@code other} is not {@code null}.
	 * 		other != null
	 * @post The result equals the cross product of this vector with the given vector
	 * 		| result == (long) (this.getX() * other.getY()) - (long) (this.getY() * other.getX())
	 */
	// There will never be overflow when converting to long (Integer.MAX_VALUE * Integer.MAX_VALUE * 2 < Long.MAX_VALUE)
	public long crossProduct(IntVector other) {
		return (long) (x * other.getY()) - (long) (y * other.getX());
	}

	/**
	 * Returns whether this vector is collinear with the given vector.
	 * 
	 * @inspects | this
	 * @inspects | other
	 * 
	 * @pre Argument {@code other} is not {@code null}.
	 * 		other != null
	 * @post Result is true if and only if the cross product with this vector and the other vector is zero.
	 * 		| result == (this.crossProduct(other) == 0)
	 */
	public boolean isCollinearWith(IntVector other) {
		return (this.crossProduct(other) == 0);
	}

	// No documentation required
	
	public DoubleVector asDoubleVector() {
		double xDouble = (double) this.getX();
		double yDouble = (double) this.getY();

		return new DoubleVector(xDouble, yDouble);
	}
}