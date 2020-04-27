package drawit.shapes1;

import drawit.IntPoint;
import drawit.shapegroups1.ShapeGroup;

public interface Shape {
	
	ShapeGroup getParent();
	
	boolean contains(drawit.IntPoint p);
	
	String getDrawingCommands();
	
	IntPoint toShapeCoordinates(drawit.IntPoint p);
	
	IntPoint toGlobalCoordinates(drawit.IntPoint p);
	
	ControlPoint[] createControlPoints();
}
