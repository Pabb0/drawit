package drawit.shapes2;

import drawit.IntPoint;
import drawit.IntVector;

public interface ControlPoint {
	
	
	IntPoint getLocation();
	
	void remove();
	
	void move(IntVector delta);
	
	
}