package drawit;

/**
 * This class implements a precise, but slow, strategy to test whether or not a point is contained by a polygon.
 */
public class PreciseRoundedPolygonContainsTestStrategy implements RoundedPolygonContainsTestStrategy {


	/**
	 * Returns whether or not a point is contained by a polygon.
	 * Precision is prioritized over speed.
	 * 
	 * @throws if polygon is null
	 * 		| polygon == null
	 * @throws if point == null
	 * 		| point == null
	 * 
	 * @post | result == polygon.contains(point)
	 */
	@Override
	public boolean contains(RoundedPolygon polygon, IntPoint point) {
		if (polygon == null) {
			throw new IllegalArgumentException("polygon is null.");
		}
		if (point == null) {
			throw new IllegalArgumentException("point is null.");
		}
		return polygon.contains(point);
	}

}
