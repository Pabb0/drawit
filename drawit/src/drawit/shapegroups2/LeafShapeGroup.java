package drawit.shapegroups2;

import java.util.Arrays;
import java.util.List;

import drawit.IntPoint;
import drawit.RoundedPolygon;


/**
 * @invar | getShape() != null
 */
public class LeafShapeGroup extends ShapeGroup{
	
	/**
	 * @invar shape != null
	 */
	RoundedPolygon shape;

	/**
	 * Returns the shape directly contained by this shape group.
	 * 
	 * @immutable
	 */
	public RoundedPolygon getShape() { return shape; }
	
	/**
	 * {@inheritDoc}
	 */
	public List<RoundedPolygon> getAllShapes() { return List.of(shape); }
	
	/**
	 * Initializes this object to represent a leaf shape group that directly contains the given shape.
	 * 
	 * @throws IllegalArgumentException if {@code shape} is null
	 *    | shape == null
	 * @throws IllegalArgumentException if {@code shape} has less than three vertices
	 *    | shape.getVertices().length < 3
	 * @mutates | this
	 * @post | getShape() == shape
	 * @post | getParentGroup() == null
	 * @post | getOriginalExtent().getLeft() == Arrays.stream(shape.getVertices()).mapToInt(p -> p.getX()).min().getAsInt()
	 * @post | getOriginalExtent().getTop() == Arrays.stream(shape.getVertices()).mapToInt(p -> p.getY()).min().getAsInt()
	 * @post | getOriginalExtent().getRight() == Arrays.stream(shape.getVertices()).mapToInt(p -> p.getX()).max().getAsInt()
	 * @post | getOriginalExtent().getBottom() == Arrays.stream(shape.getVertices()).mapToInt(p -> p.getY()).max().getAsInt()
	 * @post | getExtent().equals(getOriginalExtent())
	 */
	public LeafShapeGroup(RoundedPolygon shape) {
		if (shape == null)
			throw new IllegalArgumentException("shape is null");
		if (shape.getVertices().length < 3)
			throw new IllegalArgumentException("shape has less than three vertices");
		
		this.shape = shape;
		
		IntPoint[] vertices = shape.getVertices();
		int left = Integer.MAX_VALUE;
		int top = Integer.MAX_VALUE;
		int right = Integer.MIN_VALUE;
		int bottom = Integer.MIN_VALUE;
		for (int i = 0; i < vertices.length; i++) {
			IntPoint vertex = vertices[i];
			left = Math.min(left, vertex.getX());
			right = Math.max(right, vertex.getX());
			top = Math.min(top, vertex.getY());
			bottom = Math.max(bottom, vertex.getY());
		}
		originalExtent = Extent.ofLeftTopRightBottom(left, top, right, bottom);
		currentExtent = originalExtent;
	}
	
	/**
	* {@inheritDoc}
	* @post | result != null
	*/
	public String getDrawingCommands() {
		StringBuilder builder = new StringBuilder();
		
		double xscale = currentExtent.getWidth() * 1.0 / originalExtent.getWidth();
		double yscale = currentExtent.getHeight() * 1.0 / originalExtent.getHeight();
		builder.append("pushTranslate " + currentExtent.getLeft() + " " + currentExtent.getTop() + "\n");
		builder.append("pushScale " + xscale + " " + yscale + "\n");
		builder.append("pushTranslate " + -originalExtent.getLeft() + " " + -originalExtent.getTop() + "\n");
		
		builder.append(shape.getDrawingCommands());
		
		builder.append("popTransform popTransform popTransform\n");
		return builder.toString();
	}
	
}
