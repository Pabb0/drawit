package drawit;

/**
 * Each instance of this class represents a vector in 2D-space with integer values.
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
	 * @creates | result
	 * 
	 * @post 
	 * 		| result == (long) (this.getX() * other.getX()) + (long) (this.getY() * other.getY())
	 * @post This vector has remained unchanged.
	 * 		| this == old(this)
	 * @post The other given vector has remained unchanged.
	 * 		| other == old(other)
	 */
	// There will never be overflow when converting to long
	public long dotProduct(IntVector other) {
		return (long) (this.getX() * other.getX()) + (long) (this.getY() * other.getY());
	}

	/**
	 * Returns the cross product of this vector and the given vector.
	 * 
	 * @inspects | this
	 * @inspects | other
	 * @creates | result
	 * 
	 * @post 
	 * 		| result == (long) (this.getX() * other.getY()) - (long) (this.getY() * other.getX())
	 * @post This vector has remained unchanged.
	 * 		| this == old(this)
	 * @post The other given vector has remained unchanged.
	 * 		| other == old(other)
	 */
	// There will never be overflow when converting to long
	public long crossProduct(IntVector other) {
		return (long) (x * other.getY()) - (long) (y * other.getX());
	}

	/**
	 * Returns whether this vector is collinear with the given vector.
	 * 
	 * @inspects | this
	 * @inspects | other
	 * @creates | result
	 * 
	 * @post 
	 * 		| result == (this.crossProduct(other) == 0)
	 * @post This vector has remained unchanged.
	 * 		| this == old(this)
	 * @post The other given vector has remained unchanged.
	 * 		| other == old(other)
	 */
	public boolean isCollinearWith(IntVector other) {
		return (this.crossProduct(other) == 0);
	}

	public DoubleVector asDoubleVector() {
		double xDouble = (double) this.getX();
		double yDouble = (double) this.getY();

		return new DoubleVector(xDouble, yDouble);
	}
}