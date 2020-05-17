package drawit;

/**
 * This class implements a fast, but imprecise, strategy to test whether or not a point is contained by a polygon.
 */
public class FastRoundedPolygonContainsTestStrategy implements RoundedPolygonContainsTestStrategy {


	/**
	 * Returns whether or not a point is contained by a polygon.
	 * Speed is prioritized over precision.
	 * 
	 * @pre | polygon != null
	 * @pre | point != null
	 * 
	 * @post result == polygon.getExtent().contains(point)
	 */
	@Override
	public boolean contains(RoundedPolygon polygon, IntPoint point) {
		return polygon.getExtent().contains(point);
	}

}
