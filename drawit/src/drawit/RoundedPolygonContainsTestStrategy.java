package drawit;

/**
 * Interface that defines a test strategy to test whether or not a point lies inside of a polygon.
 *  
 */
public interface RoundedPolygonContainsTestStrategy {
	
	/**
	 * Returns whether or not (true or false) the given point is contained by the given polygon
	 * 
	 * @pre | polygon != null 
	 * @pre | point != null
	 * 
	 * @post The result cannot be false if the point is contained by the polygon itself.
	 * 		| !(result == false && polygon.contains(point)) 
	 * @post The result cannot be true if the point is not contained by the polygon its extent.
	 * 		| !(result == true && !(polygon.getExtent().contains(point)))
	 */
	boolean contains(RoundedPolygon polygon, IntPoint point);
}
