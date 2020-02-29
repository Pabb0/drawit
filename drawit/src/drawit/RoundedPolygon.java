package drawit;

public class RoundedPolygon {
	private int radius;
	private IntPoint[] vertices;

	public RoundedPolygon() {}

	public IntPoint[] getVertices() {
		return vertices;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public void setVertices(IntPoint[] newVertices) {
		vertices = newVertices;
	}
	
	/**
	 * @pre The radius is larger than or equal to zero
	 * 		| newRadius >= 0
	 */
	public void setRadius(int newRadius) {
		System.out.println(newRadius);
		radius = newRadius;
	}
	
	public void insert(int index, IntPoint point) {
		vertices = PointArrays.insert(vertices, index, point);
	}
	
	public void remove(int index) {
		vertices = PointArrays.remove(vertices, index);
	}
	
	public void update(int index, IntPoint point) {
		vertices = PointArrays.update(vertices, index, point);
	}
	
	public boolean contains(IntPoint point) {
		int largeNumber = 100000;
		IntPoint pointOnRight = new IntPoint(largeNumber, point.getY());
		
		boolean result = false;
		for (int i = 0; i < vertices.length; i++) {
			if (point.equals(vertices[i])) {
				return true;
			}
			if (IntPoint.lineSegmentIntersect(point, pointOnRight, vertices[i], vertices[(i + 1) % vertices.length]) || vertices[i].isOnLineSegment(point, pointOnRight)) {
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