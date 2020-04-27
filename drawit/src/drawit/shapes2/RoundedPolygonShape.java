package drawit.shapes2;

import drawit.IntPoint;
import drawit.IntVector;
import drawit.RoundedPolygon;
import drawit.shapegroups2.ShapeGroup;

public class RoundedPolygonShape implements Shape{
	
	private final ShapeGroup parent;
	private final RoundedPolygon polygon;

	public RoundedPolygonShape(ShapeGroup parent, RoundedPolygon polygon) {
		this.parent = parent;
		this.polygon = polygon;
	}
	
	public drawit.RoundedPolygon getPolygon() {
		return polygon;
	}
	
	public boolean contains(drawit.IntPoint point) {
		return polygon.contains(point);
	}
	
	public String getDrawingCommands() {
		return polygon.getDrawingCommands();
	}
	
	public ShapeGroup getParent() {
		return parent;
	}

	public drawit.IntPoint toShapeCoordinates(IntPoint point) {
		if (parent == null) {
			return point;
		}
		return parent.toInnerCoordinates(point);
	}
	
	public drawit.IntVector toShapeCoordinates(IntVector vector) {
		if (parent == null) {
			return vector;
		}
		return parent.toInnerCoordinates(vector);
	}
	
	public drawit.IntPoint toGlobalCoordinates(IntPoint point) {
		if (parent == null) {
			return point;
		}
		return parent.toGlobalCoordinates(point);
	}
	
	public ControlPoint[] createControlPoints() {
		final IntPoint[] vertices = polygon.getVertices();
		
		ControlPoint[] controlPoints = new ControlPoint[vertices.length];
		for (int i = 0; i < vertices.length; i++) {
			int index = i;
			IntPoint vertex = polygon.getVertices()[i];
			
			controlPoints[i] = new ControlPoint() {
				@Override
				public IntPoint getLocation() {
					return vertex;
				}

				@Override
				public void remove() {
					RoundedPolygonShape.this.polygon.remove(index);
				}

				@Override
				public void move(IntVector delta) {
					delta = RoundedPolygonShape.this.toShapeCoordinates(delta);
					vertices[index] = vertex.plus(delta);
					RoundedPolygonShape.this.polygon.setVertices(vertices);	
				}
			};
		}
		return controlPoints;	
	}
	
}


