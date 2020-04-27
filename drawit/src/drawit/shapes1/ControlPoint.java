package drawit.shapes1;

import drawit.IntPoint;
import drawit.IntVector;

public interface ControlPoint {
	
	
	IntPoint getLocation();
	
	void remove();
	
	void move(IntVector delta);
	
	
}
