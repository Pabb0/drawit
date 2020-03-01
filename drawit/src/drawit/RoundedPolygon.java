package drawit;

import java.util.Arrays;

/**
 * An instance of this class is a mutable abstraction storing a rounded polygon defined by a set of 2D points with integer coordinates and a nonnegative corner radius
 * 
 * @invar This object's radius is larger than or equal to zero
 * 		| this.getRadius() >= 0
 * 
 */
public class RoundedPolygon {
	/**
	 * @invar | radius >= 0
	 */
	private int radius;
	private IntPoint[] vertices;

	public RoundedPolygon() {}

	public IntPoint[] getVertices() {
		return PointArrays.copy(vertices);
	}
	
	public int getRadius() {
		return radius;
	}
	
	/**
	 * @mutates | this
	 * 
	 * @pre The given array of vertices defines a proper polygon
	 * 		| PointArrays.checkDefinesProperPolygon(newVertices) == null
	 * @post Arrays.equals(this.getVertices(), newVertices)
	 */
	public void setVertices(IntPoint[] newVertices) {
		vertices = PointArrays.copy(newVertices);
	}
	
	/**
	 * @mutates | this
	 * 
	 * @pre The radius is larger than or equal to zero
	 * 		| newRadius >= 0
	 * @post | this.getRadius() == newRadius
	 */
	public void setRadius(int newRadius) {
		radius = newRadius;
	}
	
	/**
	 * Inserts the given point at the given index of this object.
	 * 
	 * @mutates | this
	 * 
	 * @post
	 *     | this.getVertices().length == old(this.getVertices().length) + 1 &&
	 *     | Arrays.equals(this.getVertices(), 0, index, old(this.getVertices()), 0, index) &&
	 *     | this.getVertices()[index] == point &&
	 *     | Arrays.equals(this.getVertices(), index + 1, this.getVertices().length, old(this.getVertices()), index, old(this.getVertices().length))    
	 */
	public void insert(int index, IntPoint point) {
		vertices = PointArrays.insert(vertices, index, point);
	}
	
	/**
	 * Removes the point at the given index of this object.
	 *
	 * @mutates | this
	 * 
	 * @post
	 *     | this.getVertices().length == old(this.getVertices().length) - 1 &&
	 *     | Arrays.equals(this.getVertices(), 0, index, old(this.getVertices()), 0, index) &&
	 *     | Arrays.equals(this.getVertices(), index, this.getVertices().length, old(this.getVertices()), index + 1, old(this.getVertices().length))    
	 */
	public void remove(int index) {
		vertices = PointArrays.remove(vertices, index);
	}
	
	/**
	 * Replaces the point at the given index of this object with the given point.
	 * 
	 * @mutates | this
	 * 
	 * @post
	 *     | this.getVertices().length == old(this.getVertices().length) &&
	 *     | Arrays.equals(this.getVertices(), 0, index, old(this.getVertices()), 0, index) &&
	 *     | this.getVertices()[index] == point &&
	 *     | Arrays.equals(this.getVertices(), index + 1, this.getVertices().length, old(this.getVertices()), index + 1, this.getVertices().length)    
	 * 
	 */
	public void update(int index, IntPoint point) {
		vertices = PointArrays.update(vertices, index, point);
	}
	
	public boolean contains(IntPoint point) {
		boolean result = false;

		for (int i = 0; i < vertices.length; i++) {
			IntPoint firstPoint = vertices[i];
			IntPoint nextPoint = vertices[(i + 1) % vertices.length];
			IntPoint pointOnRight = new IntPoint(Math.max(firstPoint.getX(), nextPoint.getX()) + 1, point.getY());


			if (point.equals(firstPoint)  || point.isOnLineSegment(firstPoint, nextPoint)) {
				return true;
			}
			if ((point.getY() == firstPoint.getY() && point.getX() < firstPoint.getX()) || IntPoint.lineSegmentIntersect(firstPoint, nextPoint, point, pointOnRight)) {
				result = !result;
			}
		}
		return result;

	}
	
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