package drawit.shapegroups1;

import drawit.IntPoint;

/**
 * Each instance of this class represents a nonempty rectangular area in a 2D coordinate system, whose edges are parallel to the coordinate axes.
 * @invar | getLeft() < getRight()
 * @invar | getTop() < getBottom()
 * @invar | getWidth() > 0
 * @invar | getHeight() > 0
 */

public class Extent {
	
	/**
	 * @invar | left < right
	 * @invar | top < bottom
	 */
	private int left;
	private int right;
	private int top;
	private int bottom;

	/**
	 * @mutates | this
	 * 
	 * @throws IllegalArgumentException if {@code left} is not smaller than {@code right}.
	 * 		| left >= right
	 * @throws IllegalArgumentException if {@code top} is not smaller than {@code bottom}.
	 * 		| top >= bottom
	 * @post This object's left value equals the given left value.
	 * 		| this.getLeft() == left
	 * @post This object's right value equals the given right value.
	 * 		| this.getRight() == right
	 * @post This object's top value equals the given top value.
	 * 		| this.getTop() == top
	 * @post This object's bottom value equals the given bottom value.
	 * 		| this.getBottom() == bottom
	 */
	private Extent(int left, int top, int right, int bottom) {	
		if (!(left < right)) {
			throw new IllegalArgumentException("The given leftside of the extent is larger than or equals the rightside of the extent.");
		}
		if (!(top < bottom)) {
			throw new IllegalArgumentException("The given topside of the extent is larger than or equals the bottomside of the extent. (y-axis points downwards).");
		}
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
	}
	
	/**
	 * Returns the left side of this extent.
	 */
	public int getLeft() {
		return this.left;
	}
	
	/**
	 * Returns the right side of this extent.
	 */
	public int getRight() {
		return this.right;
	}
	
	/**
	 * Returns the top side of this extent.
	 */
	public int getTop() {
		return this.top;
	}
	
	/**
	 * Returns the bottom side of this extent.
	 */
	public int getBottom() {
		return this.bottom;
	}
	
	/**
	 * Returns the width of this extent. The width equals the right side of this extent minus the left side of this extent.
	 * @post | result == this.getRight() - this.getLeft()
	 * @post | result > 0
	 */
	public int getWidth() {
		return (this.right - this.left);
	}
	
	/**
	 * Returns the width of this extent. The width equals the bottom side of this extent minus the top side of this extent.
	 * @post | result == this.getBottom() - this.getTop()
	 * @post | result > 0
	 */
	public int getHeight() {
		return (this.bottom - this.top);
	}	
	
	/**
	 * Returns the point of the extent in the top left corner.
	 * @post | result.getX() == this.getLeft()
	 * @post | result.getY() == this.getTop()
	 * 
	 */
	public IntPoint getTopLeft() {
		return new IntPoint(getLeft(), getTop());
	}
	
	/**
	 * Returns the point of the extent in the bottom right corner.
	 * @post | result.getX() == this.getRight()
	 * @post | result.getY() == this.getBottom()
	 *  
	 */
	public IntPoint getBottomRight() {
		return new IntPoint(getRight(), getBottom());
	}
	
	/**
	 * Returns true iff the given point is inside this extent.
	 * @throws IllegalArgumentException if {@code point} is {@code null}.
	 * 		| point == null
	 * @post | result == (getLeft() <= point.getX() && getRight() >= point.getX() && getTop() <= point.getY() && getBottom() >= point.getY()) 
	 * 
	 */
	public boolean contains(IntPoint point) {
		if (point == null) {
			throw new IllegalArgumentException("The given point is null.");
		}
		return (getLeft() <= point.getX() && point.getX() <= getRight() &&
				getTop() <= point.getY() && point.getY() <= getBottom());
	}
	/**
	 * Returns an extent with the given left value, top value, right value and bottom value
	 *
	 * @creates | result
	 * 
	 * @throws IllegalArgumentException if {@code left} is not smaller than {@code right}.
	 * 		| left >= right
	 * @throws IllegalArgumentException if {@code top} is not smaller than {@code bottom}.
	 * 		| top >= bottom
	 */
	public static Extent ofLeftTopRightBottom(int left, int top, int right, int bottom) {
		if (!(left < right)) {
			throw new IllegalArgumentException("The given leftside of the extent is larger than or equals the rightside of the extent.");
		}
		if (!(top < bottom)) {
			throw new IllegalArgumentException("The given topside of the extent is larger than or equals the bottomside of the extent. (y-axis points downwards).");
		}
		return new Extent(left, top, right, bottom);
	}
	
	/**
	 * Returns an extent with the given left value, top value, width value and height value. The right value equals the left value plus the width value, the bottom value equals the top value plus the height value.
	 * 
	 * @creates | result
	 * 
	 * @throws IllegalArgumentException if {@code width} is not larger than zero.
	 * 		| width <= 0
	 * @throws IllegalArgumentException if {@code height} is not larger than zero.
	 * 		| height <= 0
	 * 
	 * @post | result.getRight() == left + width
	 * @post | result.getBottom() == top + height
	 */
	public static Extent ofLeftTopWidthHeight(int left, int top, int width, int height) {
		if (!(width > 0)) {
			throw new IllegalArgumentException("The given width value is not larger than zero.");
		}
		if (!(height > 0)) {
			throw new IllegalArgumentException("The given height value is not larger than zero.");
		}
		return new Extent(left, top, left + width, top + height);
	}
	
	
	/**
	 * Returns a new extent that has the given left coordinate and the same right, top, and bottom coordinate as this extent.
	 * 
	 * @creates | result
	 * 
	 * @throws IllegalArgumentException if {@code newLeft} is not smaller than {@code right}.
	 * 		| newLeft >= getRight()
	 */
	public Extent withLeft(int newLeft) {
		if (!(newLeft < right)) {
			throw new IllegalArgumentException("The given leftside is larger than or equals the right side of this extent.");
		}
		
		return new Extent(newLeft, top, right, bottom);
	}
	/**
	 * Returns a new extent that has the given top coordinate and the same left, right, and bottom coordinate as this extent.
	 * 
	 * @creates | this
	 * 
	 * @throws IllegalArgumentException if {@code newTop} is not smaller than {@code bottom}.
	 * 		| newTop >= getBottom()
	 */
	public Extent withTop(int newTop) {
		if (!(newTop < bottom)) {
			throw new IllegalArgumentException("The given top side is larger than or equals the bottom side of this extent.");
		}
			return new Extent(left, newTop, right, bottom);
	}
	
	/**
	 * Returns a new extent that has the given right coordinate and the same left, top, and bottom coordinate as this extent.
	 * 
	 * @creates | result
	 * 
	 * @throws IllegalArgumentException if {@code newRight} is not greater than {@code left}.
	 * 		| newRight <= getLeft()
	 */	
	public Extent withRight(int newRight) {
		if (!(newRight > left)) {
			throw new IllegalArgumentException("The given right side is smaller than or equals the left side of this extent.");
		}
			return new Extent(left, top, newRight, bottom);

	}
	
	/**
	 * Returns a new extent that has the given bottom coordinate and the same left, top, and right coordinate as this extent.
	 * 
	 * @creates | result
	 * 
	 * @throws IllegalArgumentException if {@code newBottom} is not greater than {@code top}.
	 * 		| newBottom <= getTop()
	 */	
	public Extent withBottom(int newBottom) {
		if (!(newBottom > top)) {
			throw new IllegalArgumentException("The given bottom side is larger than or equals the top side of this extent.");
		}
			return new Extent(left, top, right, newBottom);

	}
	
	/**
	 * Returns a new extent that has the given width and the same left, top, and bottom coordinate as this extent.
	 * 
	 * @creates | result
	 * 
	 * @throws IllegalArgumentException if {@code newWidth} is not greater than zero.
	 * 		| newWidth <= 0
	 */	
	public Extent withWidth(int newWidth) {
		if (!(newWidth > 0)) {
			throw new IllegalArgumentException("The given width is not larger than 0.");
		}
		return new Extent(left, top, left + newWidth, bottom);
	}
	
	/**
	 * Returns a new extent that has the given height and the same left, top, and right coordinate as this extent.
	 * 
	 * @creates | result
	 * 
	 * @throws IllegalArgumentException if {@code newHeight} is not greater than zero.
	 * 		| newHeight <= 0
	 */
	public Extent withHeight(int newHeight) {
		if (!(newHeight > 0)) {
			throw new IllegalArgumentException("The given height is not larger than 0.");
		}
		return new Extent(left, top, right, top + newHeight);
	}
	
}
