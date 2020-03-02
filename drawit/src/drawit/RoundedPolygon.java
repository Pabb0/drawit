package drawit;

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
 */
public class RoundedPolygon {
	/**
	 * @invar 
	 * 		| radius >= 0
	 * @invar 
	 * 		| vertices != null
	 * @invar 
	 * 		| Arrays.stream(vertices).allMatch(e -> e != null)
	 */
	private int radius;
	private IntPoint[] vertices;
	
	/**
	 * @mutates | this
     * @post This object's list of vertices is empty.
     *		| getVertices().length == 0
	 */
	public RoundedPolygon() {vertices = new IntPoint[0];}

	public IntPoint[] getVertices() {
		return PointArrays.copy(vertices);
	}
	
	public int getRadius() {
		return radius;
	}
	
	/**
	 * @mutates | this
	 * 
	 * @throws IllegalArgumentException if {@code newVertices} is {@code null}.
	 * 		| newVertices == null
	 * @throws IllegalArgumentException if the given array of vertices leads to a non-proper polygon.
	 * 		| PointArrays.checkDefinesProperPolygon(newVertices) != null
	 * 
	 * @post This object's vertices equal the given vertices.
	 * 		| Arrays.equals(this.getVertices(), newVertices)
	 */
	public void setVertices(IntPoint[] newVertices) {
		if (newVertices == null) {
			throw new IllegalArgumentException("newVertices is null.");
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
	 * @post  This object's radius equals the given radius.
	 * 		| this.getRadius() == newRadius
	 */
	public void setRadius(int newRadius) {
		if (newRadius < 0) {
			throw new IllegalArgumentException("The given radius is less than 0.");
		}
		radius = newRadius;
	}
	
	/**
	 * Inserts the given point at the given index of this object.
	 * 
	 * @mutates | this
	 * 
	 * @throws IllegalArgumentException if {@code point} is {@code null}.
	 * 		| point == null
	 * @throws IllegalArgumentException if inserting the given point at the given index leads to a non-proper polygon
	 * 		| PointArrays.checkDefinesProperPolygon(PointArrays.insert(vertices, index, point)) != null
	 * @throws IllegalArgumentException if the given index is not between 0 (inclusive) and the length of this array's length (inclusive).
	 * 		| 0 <= index && index <= points.length
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
			throw new IllegalArgumentException("Inserting the given point at the given index leads to a non-proper polygon");
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
	 * 		| PointArrays.checkDefinesProperPolygon(PointArrays.remove(vertices, index)) != null
	 * @throws IllegalArgumentException if the given index is not between 0 (inclusive) and the length of this array's length (exclusive).
	 * 		| 0 <= index && index < points.length
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
	 * 		| PointArrays.checkDefinesProperPolygon(PointArrays.update(vertices, index, point)) != null
	 * @throws IllegalArgumentException if the given index is not between 0 (inclusive) and the length of this array's length (exclusive).
	 * 		| 0 <= index && index < points.length
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
		}	}
	
	// No documentation required
	public boolean contains(IntPoint point) {
		boolean result = false;

		for (int i = 0; i < vertices.length; i++) {
			IntPoint firstPoint = vertices[i];
			IntPoint nextPoint = vertices[(i + 1) % vertices.length];
			IntPoint pointOnRight = new IntPoint(Math.max(firstPoint.getX(), nextPoint.getX()) + 1, point.getY());


			if (point.equals(firstPoint)  || point.isOnLineSegment(firstPoint, nextPoint)) {
				return true;
			}
			if ((point.getY() == firstPoint.getY() && point.getX() < firstPoint.getX()) || IntPoint.lineSegmentsIntersect(firstPoint, nextPoint, point, pointOnRight)) {
				result = !result;
			}
		}
		return result;

	}
	
	// No documentation required
	public String getDrawingCommands() {
		String result = "";
		
		if (vertices.length < 3) {
			return result;
		}
		
		for(int i = 0; i < vertices.length; i++) {
			IntPoint A = vertices[((i-1) + vertices.length) % vertices.length];
			IntPoint B = vertices[i];
			IntPoint C = vertices[(i+1) % vertices.length];
			
			IntVector BA = A.minus(B);
			IntVector BC = C.minus(B);
			
			DoublePoint BAC = B.asDoublePoint().plus(BA.asDoubleVector().scale(0.5));
			DoublePoint BCC = B.asDoublePoint().plus(BC.asDoubleVector().scale(0.5));
			
			if (BA.isCollinearWith(BC)){
				result += "line " + String.valueOf((int) Math.round((BAC.getX()))) + " "
				+ String.valueOf((int) Math.round((BAC.getY()))) + " "
				+ String.valueOf((int) Math.round((BCC.getX()))) + " " 
				+ String.valueOf((int) Math.round((BCC.getY()))) + "\n";		
			} else {
				DoubleVector BAU = BA.asDoubleVector();
				BAU = BAU.scale(1 / BAU.getSize());
				DoubleVector BCU = BC.asDoubleVector();
				BCU = BCU.scale(1 / BCU.getSize());
				DoubleVector BSU = BAU.plus(BCU);
				BSU = BSU.scale(1 / BSU.getSize());
				
				double BAUcutoff = BAU.dotProduct(BSU);
				double unitRadius = Math.abs(BAU.crossProduct(BSU));
				
				double appliedScaleFactor = Math.min(this.getRadius() / unitRadius, (0.5 * Math.min(BA.asDoubleVector().getSize(),  BC.asDoubleVector().getSize()) / BAUcutoff));
				
				double actualRadius = unitRadius * appliedScaleFactor;
				DoublePoint actualCorner = B.asDoublePoint().plus(BSU.scale(appliedScaleFactor));
				
				DoublePoint arcStart = B.asDoublePoint().plus(BAU.scale(BAUcutoff * appliedScaleFactor));
				DoublePoint arcEnd = B.asDoublePoint().plus(BCU.scale(BAUcutoff * appliedScaleFactor));

				DoubleVector centerToStart = arcStart.minus(actualCorner);
				double startAngle = centerToStart.asAngle();
				
				DoubleVector centerToEnd = arcEnd.minus(actualCorner);
				double endAngle = centerToEnd.asAngle();
				
				double angleExtent = endAngle - startAngle;
				if (angleExtent < -Math.PI) {
					angleExtent += 2 * Math.PI;
				} else if (angleExtent > Math.PI) {
					angleExtent -= 2 * Math.PI;
				}
				
				result += "line " + String.valueOf((int) Math.round(BAC.getX())) + " "
				+ String.valueOf((int) Math.round(BAC.getY())) + " "
				+ String.valueOf((int) Math.round(arcStart.getX())) + " " 
				+ String.valueOf((int) Math.round(arcStart.getY())) + "\n";
				
				result += "arc " + String.valueOf((int) Math.round(actualCorner.getX())) + " "
				+ String.valueOf((int) Math.round(actualCorner.getY())) + " "
				+ String.valueOf((int) Math.round(actualRadius)) + " "
				+ String.valueOf(startAngle) + " "
				+ String.valueOf(angleExtent) + "\n";
				
				result += "line " + String.valueOf((int) Math.round(arcEnd.getX())) + " "
				+ String.valueOf((int) Math.round(arcEnd.getY())) + " "
				+ String.valueOf((int) Math.round(BCC.getX())) + " " 
				+ String.valueOf((int) Math.round(BCC.getY())) + "\n";		
			}
			
		}
		return result;	
	}
}