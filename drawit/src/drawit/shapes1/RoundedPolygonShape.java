package drawit.shapes1;

public class RoundedPolygonShape implements Shape{
	
	private drawit.shapegroups1.ShapeGroup parent;
	private drawit.RoundedPolygon polygon;

	public RoundedPolygonShape(drawit.shapegroups1.ShapeGroup parent, drawit.RoundedPolygon polygon) {
		this.parent = parent;
		this.polygon = polygon;
		
	}
	
	public drawit.RoundedPolygon getPolygon() {
		return polygon;
	}
	
	public boolean contains(drawit.IntPoint p) {
		return polygon.contains(p);
	}
	
	public String getDrawingCommands() {
		return polygon.getDrawingCommands();
	}
	
	public drawit.shapegroups1.ShapeGroup getParent() {
		return parent;
	}
	
	// Wat als parent == null?
	// Wat zijn ShapeCoordinates precies (inner of outer)
	public drawit.IntPoint toShapeCoordinates(drawit.IntPoint p) {
		return parent.toInnerCoordinates(p);
	}
	
	// Wat als parent == null?
	// Wat zijn ShapeCoordinates precies (inner of outer)
	public drawit.IntPoint toGlobalCoordinates(drawit.IntPoint p) {
		return parent.toGlobalCoordinates(p);
	}
	
	public ControlPoint[] createControlPoints() {
		return null;
	}
	
}

