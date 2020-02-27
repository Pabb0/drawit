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
	
	public void setRadius(int newRadius) {
		radius = newRadius;
	}
	
	public void insert(int index, IntPoint point) {
		PointArrays.insert(vertices, index, point);
	}
	
	public void remove(int index, IntPoint point) {
		PointArrays.remove(vertices, index);
	}
	
	public void update(int index, IntPoint point) {
		PointArrays.update(vertices, index, point);
	}
	
	public boolean contains(IntPoint point) {
		return false;
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
				result += "line " + String.valueOf((int) (BAC.getX() + 0.5)) + " "
				+ String.valueOf((int) (BAC.getY() + 0.5)) + " "
				+ String.valueOf((int) (B.getX() + 0.5)) + " " 
				+ String.valueOf((int) (B.getY() + 0.5)) + "\n";
				
				result += "line " + String.valueOf((int) (B.getX() + 0.5)) + " "
				+ String.valueOf((int) (B.getY() + 0.5)) + " "
				+ String.valueOf((int) (BCC.getX() + 0.5)) + " " 
				+ String.valueOf((int) (BCC.getY() + 0.5)) + "\n";
				
			} else {
				DoubleVector BAU = A.minus(B).asDoubleVector();
				BAU = BAU.scale(1 / BAU.getSize());
				DoubleVector BCU = C.minus(B).asDoubleVector();
				BCU = BCU.scale(1 / BCU.getSize());
				DoubleVector BSU = BAU.plus(BCU);
				BSU = BSU.scale(1 / BSU.getSize());
				
				double BAUcutoff = BAU.dotProduct(BSU);
				double unitRadius = Math.abs(BAU.crossProduct(BSU));
				
				double appliedScaleFactor = Math.min(this.getRadius() / unitRadius, (0.5 * Math.min(BA.asDoubleVector().getSize(),  BC.asDoubleVector().getSize()) / BAUcutoff));
				
				DoublePoint actualCorner = B.asDoublePoint().plus(BSU.scale(appliedScaleFactor));
				double actualRadius = unitRadius * appliedScaleFactor;
				
				DoublePoint arcStart = B.asDoublePoint().plus(BA.asDoubleVector().scale((1 / (BAUcutoff*appliedScaleFactor))));
				DoublePoint arcEnd = B.asDoublePoint().plus(BC.asDoubleVector().scale((1 / (BAUcutoff*appliedScaleFactor))));
				
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
				
				result += "line " + String.valueOf((int) (BAC.getX() + 0.5)) + " "
				+ String.valueOf((int) (BAC.getY() + 0.5)) + " "
				+ String.valueOf((int) (arcStart.getX() + 0.5)) + " " 
				+ String.valueOf((int) (arcStart.getY() + 0.5)) + "\n";
				
				result += "arc " + String.valueOf((int) (actualCorner.getX() + 0.5)) + " "
				+ String.valueOf((int) (actualCorner.getY() + 0.5)) + " "
				+ String.valueOf((int) (actualRadius + 0.5)) + " "
				+ String.valueOf(startAngle) + " "
				+ String.valueOf(angleExtent) + "\n";
				
				result += "line " + String.valueOf((int) (arcEnd.getX() + 0.5)) + " "
				+ String.valueOf((int) (arcEnd.getY() + 0.5)) + " "
				+ String.valueOf((int) (BCC.getX() + 0.5)) + " " 
				+ String.valueOf((int) (BCC.getY() + 0.5)) + "\n";		
			}
			
		}
		return result;	
	}
}