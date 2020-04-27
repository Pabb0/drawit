package drawit.shapes2;

import drawit.IntPoint;
import drawit.shapegroups2.ShapeGroup;
import drawit.shapes2.ControlPoint;

public interface Shape {
	ShapeGroup getParent();
	
	boolean contains(drawit.IntPoint p);
	
	String getDrawingCommands();
	
	IntPoint toShapeCoordinates(drawit.IntPoint p);
	
	IntPoint toGlobalCoordinates(drawit.IntPoint p);
	
	ControlPoint[] createControlPoints();

}
