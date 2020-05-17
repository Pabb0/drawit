package drawit;

public interface RoundedPolygonContainsTestStrategy {
	
	/**
	 * Returns {@code true} if the given point is contained by the (non-rounded) polygon defined by this rounded polygon's vertices.
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
