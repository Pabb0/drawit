package drawit.shapes1;

public class ShapeGroupShape {
	
	private drawit.shapegroups1.ShapeGroup group;
	
	public ShapeGroupShape(drawit.shapegroups1.ShapeGroup group) {
		this.group = group;
	}
	
	public drawit.shapegroups1.ShapeGroup getShapeGroup() {
		return group;
	}
	
	// Kan eig gewoon met een return
	public drawit.shapegroups1.ShapeGroup getParent() {
		if (group.getParentGroup() != null) {
			return group.getParentGroup();
		} else {
			return null;
		}
	}
	
	public boolean contains(drawit.IntPoint p) {
		return group.getExtent().contains(p);
	}
	
	public String getDrawingCommands() {
		return group.getDrawingCommands();
	}
	
	public drawit.IntPoint toShapeCoordinates(drawit.IntPoint p) {
		return group.toInnerCoordinates(p);
	}
	
	public drawit.IntPoint toGlobalCoordinates(drawit.IntPoint p) {
		return group.outerToGlobalCoordinates(p);
	}
	
	public ControlPoint[] createControlPoints() {
		return null;
	}


}
