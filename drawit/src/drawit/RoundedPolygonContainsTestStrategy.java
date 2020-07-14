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
	 */
	boolean contains(RoundedPolygon polygon, IntPoint point);
}
