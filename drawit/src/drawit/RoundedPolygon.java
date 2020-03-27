package drawit;

import java.awt.Color;
import java.util.Arrays;

/**
 * An instance of this class is a mutable abstraction storing a rounded polygon defined by a set of 2D points with integer coordinates and a nonnegative corner radius
 * 
 * @invar This object's radius is larger than or equal to zero
 * 		| this.getRadius() >= 0
 * @invar 
 * 		| this.getVertices() != null
 * @invar 
 * 		| Arrays.stream(this.getVertices()).allMatch(e -> e != null)
 * @invar
 * 		| PointArrays.checkDefinesProperPolygon(getVertices()) == null
 */
public class RoundedPolygon {
	/**
	 * @invar 
	 * 		| radius >= 0
	 * @invar 
	 * 		| vertices != null
	 * @invar 
	 * 		| Arrays.stream(vertices).allMatch(e -> e != null)
	 * @invar
	 * 		| PointArrays.checkDefinesProperPolygon(vertices) == null
	 * @representationObject
	 */
	private int radius;
	private IntPoint[] vertices;
	private java.awt.Color color;
	
	/**
	 * @mutates | this
	 * 
     * @post This object's list of vertices is empty.
     *		| getVertices().length == 0
     * @post 
     * 		| getRadius() == 0
	 */
	public RoundedPolygon() {
		radius = 0;
		vertices = new IntPoint[0];
		color = Color.YELLOW;
		}
	/**
	 * @creates result
	 */
	public IntPoint[] getVertices() {
		return PointArrays.copy(vertices);
	}
	
	public int getRadius() {
		return radius;
	}
	
	public java.awt.Color getColor() {
		return color;
	}
	
	/**
	 * @mutates | this
	 * 
	 * @throws IllegalArgumentException if {@code newVertices} is {@code null}.
	 * 		| newVertices == null
	 * @throws IllegalArgumentException
	 *    | Arrays.stream(newVertices).anyMatch(v -> v == null)
	 * @throws IllegalArgumentException if the given array of vertices leads to a non-proper polygon.
	 * 		| PointArrays.checkDefinesProperPolygon(newVertices) != null
	 * 
	 * @post This object's vertices equal the given vertices.
	 * 		| Arrays.equals(this.getVertices(), newVertices)
	 */
	public void setVertices(IntPoint[] newVertices) {
		if (newVertices == null) {
			throw new IllegalArgumentException("The given array of vertices is null.");
		}
		if (Arrays.stream(newVertices).anyMatch(v -> v == null)) {
			throw new IllegalArgumentException("An element of newVertices is null");
		}
		if (PointArrays.checkDefinesProperPolygon(newVertices) != null) {
			throw new IllegalArgumentException("The given array of vertices leads to a non-proper polygon.");
		}
		vertices = PointArrays.copy(newVertices);
	}
	
	/**
	 * @mutates | this
	 * 
	 * @throws IllegalArgumentException if the given radius is less than 0.
	 * 		| newRadius < 0
	 * 
	 * @post | PointArrays.equals(getVertices(), old(getVertices()))
	 * @post  This object's radius equals the given radius.
	 * 		| this.getRadius() == newRadius
	 */
	public void setRadius(int newRadius) {
		if (newRadius < 0) {
			throw new IllegalArgumentException("The given radius is less than 0.");
		}
		radius = newRadius;
	}
	
	public void	setColor(java.awt.Color color) {
		this.color = color;
	}
	
	/**
	 * Inserts the given point at the given index of this object.
	 * 
	 * @mutates | this
	 * 
	 * @throws IllegalArgumentException if {@code point} is {@code null}.
	 * 		| point == null
	 * @throws IllegalArgumentException if inserting the given point at the given index leads to a non-proper polygon
	 * 		| PointArrays.checkDefinesProperPolygon(PointArrays.insert(getVertices(), index, point)) != null
	 * @throws IllegalArgumentException if the given index is not between 0 (inclusive) and the length of this array's length (inclusive).
	 * 		| 0 <= index && index <= getVertices().length
	 * 
	 * @post This object's array of {@code IntPoint} objects equals the old array of {@code IntPoint} objects with the given point inserted at the given index.	 
	 *     	| this.getVertices().length == old(this.getVertices().length) + 1 &&
	 *     	| Arrays.equals(this.getVertices(), 0, index, old(this.getVertices()), 0, index) &&
	 *     	| this.getVertices()[index] == point &&
	 *     	| Arrays.equals(this.getVertices(), index + 1, this.getVertices().length, old(this.getVertices()), index, old(this.getVertices().length))    
	 */
	public void insert(int index, IntPoint point) {
		if (point == null) {
			throw new IllegalArgumentException("point is null.");
		}
		if (!(0 <= index && index <= vertices.length)) {
			throw new IllegalArgumentException("The given index is out of range.");
		}
		IntPoint[] newVertices = PointArrays.insert(vertices, index, point);
		if (PointArrays.checkDefinesProperPolygon(newVertices) != null) {
			throw new IllegalArgumentException("Inserting the given point at the given index leads to a non-proper polygon.");
		} else {
			vertices = newVertices;
		}
	}
	
	/**
	 * Removes the point at the given index of this object.
	 *
	 * @mutates | this
	 * 
	 * @throws IllegalArgumentException if removing the point at the given index leads to a non-proper polygon.
	 * 		| PointArrays.checkDefinesProperPolygon(PointArrays.remove(getVertices(), index)) != null
	 * @throws IllegalArgumentException if the given index is not between 0 (inclusive) and the length of this array's length (exclusive).
	 * 		| 0 <= index && index < getVertices().length
	 * 
	 * @post This object's array of {@code IntPoint} objects equals the old array of {@code IntPoint} objects with the point at the given index removed.	 
	 *		| this.getVertices().length == old(this.getVertices().length) - 1 &&
	 *		| Arrays.equals(this.getVertices(), 0, index, old(this.getVertices()), 0, index) &&
	 *		| Arrays.equals(this.getVertices(), index, this.getVertices().length, old(this.getVertices()), index + 1, old(this.getVertices().length))    
	 */
	public void remove(int index) {
		if (!(0 <= index && index < vertices.length)) {
			throw new IllegalArgumentException("The given index is out of range.");
		}
		IntPoint[] newVertices = PointArrays.remove(vertices, index);
		if (PointArrays.checkDefinesProperPolygon(newVertices) != null) {
			throw new IllegalArgumentException("Removing the point at the given index leads to a non-proper polygon.");
		} else {
			vertices = newVertices;
		}
	}
	
	/**
	 * Replaces the point at the given index of this object with the given point.
	 * 
	 * @mutates | this
	 * 
	 * @throws IllegalArgumentException if {@code point} is {@code null}.
	 * 		| point == null
	 * @throws IllegalArgumentException if replacing the point at the given index by the given point leads to a non-proper polygon.
	 * 		| PointArrays.checkDefinesProperPolygon(PointArrays.update(getVertices(), index, point)) != null
	 * @throws IllegalArgumentException if the given index is not between 0 (inclusive) and the length of this array's length (exclusive).
	 * 		| 0 <= index && index < getVertices().length
	 *  
	 * @post This object's array of {@code IntPoint} objects equals the old array of {@code IntPoint} objects with the given point taking the place of the point at the given index.	 
	 *		| this.getVertices().length == old(this.getVertices().length) &&
	 *		| Arrays.equals(this.getVertices(), 0, index, old(this.getVertices()), 0, index) &&
	 *		| this.getVertices()[index] == point &&
	 *		| Arrays.equals(this.getVertices(), index + 1, this.getVertices().length, old(this.getVertices()), index + 1, this.getVertices().length)    
	 * 
	 */
	public void update(int index, IntPoint point) {
		if (!(0 <= index && index < vertices.length)) {
			throw new IllegalArgumentException("The given index is out of range.");
		}
		if (point == null) {
			throw new IllegalArgumentException("point is null.");
		}
		IntPoint[] newVertices = PointArrays.update(vertices, index, point);
		if (PointArrays.checkDefinesProperPolygon(newVertices) != null) {
			throw new IllegalArgumentException("Replacing the point at the given index by the given point leads to a non-proper polygon.");
		} else {
			vertices = newVertices;
		}	
	}
	
	// No documentation required
	public boolean contains(IntPoint point) {
		// We call the half-line extending from `point` to the right the "exit path"
		// Find first vertex that is not on the exit path
		int firstVertex;
		{
			int i = 0;
			for (;;) {
				if (i == vertices.length) // Zero or one vertices
					return false;
				if (vertices[i].equals(point))
					return true;
				if (!(vertices[i].getY() == point.getY() && vertices[i].getX() > point.getX()))
					break;
				i++;
			}
			firstVertex = i;
		}
		IntVector exitVector = new IntVector(1, 0);
		// Count how many times the exit path crosses the polygon
		int nbEdgeCrossings = 0;
		for (int index = firstVertex; ; ) {
			IntPoint a = vertices[index];
			// Find the next vertex that is not on the exit path
			boolean onExitPath = false;
			int nextIndex = index;
			IntPoint b;
			for (;;) {
				int nextNextIndex = (nextIndex + 1) % vertices.length;
				if (point.isOnLineSegment(vertices[nextIndex], vertices[nextNextIndex]))
					return true;
				nextIndex = nextNextIndex;
				b = vertices[nextIndex];
				if (b.equals(point))
					return true;
				if (b.getY() == point.getY() && b.getX() > point.getX()) {
					onExitPath = true;
					continue;
				}
				break;
			}
			if (onExitPath) {
				if ((b.getY() < point.getY()) != (a.getY() < point.getY()))
					nbEdgeCrossings++;
			} else {
				// Does `ab` straddle the exit path's carrier?
				if (Math.signum(a.getY() - point.getY()) * Math.signum(b.getY() - point.getY()) < 0) {
					// Does the exit path straddle `ab`'s carrier?
					IntVector ab = b.minus(a);
					if (Math.signum(point.minus(a).crossProduct(ab)) * Math.signum(exitVector.crossProduct(ab)) < 0)
						nbEdgeCrossings++;
				}
			}
			if (nextIndex == firstVertex)
				break;
			index = nextIndex;
		}
		return nbEdgeCrossings % 2 == 1;
	}
	
	// No documentation required
	public String getDrawingCommands() {
		if (vertices.length < 3)
			return "";
		StringBuilder commands = new StringBuilder();
		for (int index = 0; index < vertices.length; index++) {
			
			IntPoint a = vertices[(index + vertices.length - 1) % vertices.length];
			IntPoint b = vertices[index];
			IntPoint c = vertices[(index + 1) % vertices.length];
			DoubleVector ba = a.minus(b).asDoubleVector();
			DoubleVector bc = c.minus(b).asDoubleVector();
			DoublePoint baCenter = b.asDoublePoint().plus(ba.scale(0.5));
			DoublePoint bcCenter = b.asDoublePoint().plus(bc.scale(0.5));
			double baSize = ba.getSize();
			double bcSize = bc.getSize();
			if (ba.crossProduct(bc) == 0) {
				commands.append("line " + bcCenter.getX() + " " + bcCenter.getY() + " " + b.getX() + " " + b.getY() + "\n");
				commands.append("line " + b.getX() + " " + b.getY() + " " + baCenter.getX() + " " + baCenter.getY() + "\n");
			} else {
				DoubleVector baUnit = ba.scale(1/baSize);
				DoubleVector bcUnit = bc.scale(1/bcSize);
				DoubleVector bisector = baUnit.plus(bcUnit);
				bisector = bisector.scale(1/bisector.getSize());
				double unitEdgeDistance = baUnit.dotProduct(bisector);
				double unitRadius = Math.abs(bisector.crossProduct(baUnit));
				double scaleFactor = Math.min(this.radius / unitRadius, Math.min(baSize, bcSize) / 2 / unitEdgeDistance);
				DoublePoint center = b.asDoublePoint().plus(bisector.scale(scaleFactor));
				double radius = unitRadius * scaleFactor;
				DoublePoint bcCornerStart = b.asDoublePoint().plus(bcUnit.scale(unitEdgeDistance * scaleFactor));
				DoublePoint baCornerStart = b.asDoublePoint().plus(baUnit.scale(unitEdgeDistance * scaleFactor));
				double baAngle = baCornerStart.minus(center).asAngle();
				double bcAngle = bcCornerStart.minus(center).asAngle();
				double angleExtent = bcAngle - baAngle;
				if (angleExtent < -Math.PI)
					angleExtent += 2 * Math.PI;
				else if (Math.PI < angleExtent)
					angleExtent -= 2 * Math.PI;
				commands.append("line " + baCenter.getX() + " " + baCenter.getY() + " " + baCornerStart.getX() + " " + baCornerStart.getY() + "\n");
				commands.append("arc " + center.getX() + " " + center.getY() + " " + radius + " " + baAngle + " " + angleExtent + "\n");
				commands.append("line " + bcCornerStart.getX() + " " + bcCornerStart.getY() + " " + bcCenter.getX() + " " + bcCenter.getY() + "\n");
			}
		}
		commands.append("fill " + String.valueOf(this.getColor().getRed()) + " " +  String.valueOf(this.getColor().getGreen()) + " " +  String.valueOf(this.getColor().getBlue()) + "\n");
		return commands.toString();
	}
}