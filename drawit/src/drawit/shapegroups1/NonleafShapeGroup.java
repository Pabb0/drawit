package drawit.shapegroups1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import drawit.IntPoint;
import drawit.RoundedPolygon;
import logicalcollections.LogicalList;

/**
 * @invar | getSubgroups() != null
 * @invar | LogicalList.distinct(getSubgroups())
 * @invar | getSubgroups().stream().allMatch(g -> g != null && g.getParentGroup() == this)
 */
public class NonleafShapeGroup extends ShapeGroup{
	/**
	 * @invar | subgroups != null
	 * @invar | LogicalList.distinct(subgroups)
	 * @invar | subgroups.stream().allMatch(g -> g != null && g.parent == this)
	 * @representationObject
	 * @peerObjects
	 */
	ArrayList<ShapeGroup> subgroups;
	
	/**
	 * Returns the list of subgroups of this NonleafShapeGroup.
	 * 
	 * @basic
	 * @peerObjects
	 */
	public List<ShapeGroup> getSubgroups() { return subgroups == null ? null : List.copyOf(subgroups); }

	/**
	 * Returns the number of subgroups of this non-leaf shape group.
	 * 
	 * @throws UnsupportedOperationException if this is a leaf shape group
	 *    | getSubgroups() == null
	 * @post | result == getSubgroups().size()
	 */
	public int getSubgroupCount() { return subgroups.size(); }
	
	/**
	 * Returns the subgroup at the given (zero-based) index in this non-leaf shape group's list of subgroups.
	 * 
	 * @throws IllegalArgumentException if the given index is out of bounds
	 *    | index < 0 || getSubgroups().size() <= index
	 * @post | result == getSubgroups().get(index)
	 */
	public ShapeGroup getSubgroup(int index) {
		if (index < 0 || getSubgroups().size() <= index)
			throw new IllegalArgumentException("index out of bounds");
		return subgroups.get(index);
	}
	/**
	 * {@inheritDoc}
	 */
	public List<RoundedPolygon> getAllShapes() { 
		return subgroups.stream().flatMap(subgroup -> subgroup.getAllShapes().stream()).collect(Collectors.toList());
	}
	
	/**
	 * Return the first subgroup in this non-leaf shape group's list of subgroups whose
	 * extent contains the given point, expressed in this shape group's inner coordinate system.
	 * 
	 * @throws IllegalArgumentException if {@code innerCoordinates} is null
	 *    | innerCoordinates == null
	 * @post
	 *    | Objects.equals(result,
	 *    |     getSubgroups().stream().filter(g -> g.getExtent().contains(innerCoordinates))
	 *    |         .findFirst().orElse(null))
	 */
	public ShapeGroup getSubgroupAt(IntPoint innerCoordinates) {
		if (innerCoordinates == null)
			throw new IllegalArgumentException("innerCoordinates is null");
		
		for (ShapeGroup group : subgroups)
			if (group.getExtent().contains(innerCoordinates))
				return group;
		return null;
	}
	
	
	//
	/**
	 * Initializes this object to represent a non-leaf shape group that directly contains the given
	 * subgroups, in the given order.
	 * 
	 * @mutates | this
	 * @inspects | subgroups
	 * 
	 * @throws IllegalArgumentException if {@code subgroups} is null
	 *    | subgroups == null
	 * @throws IllegalArgumentException if {@code subgroups} has less than two elements
	 *    | subgroups.length < 2
	 * @throws IllegalArgumentException if any element of {@code subgroups} is null
	 *    | Arrays.stream(subgroups).anyMatch(g -> g == null)
	 * @throws IllegalArgumentException if the given subgroups are not distinct
	 *    | !LogicalList.distinct(List.of(subgroups))
	 * @throws IllegalArgumentException if any of the given subgroups already has a parent
	 *    | Arrays.stream(subgroups).anyMatch(g -> g.getParentGroup() != null)
	 * @throws IllegalArgumentException if any of the given subgroups is an ancestor of this shape group
	 *    | !Collections.disjoint(getAncestors(), Set.of(subgroups))
	 * 
//	 * @mutates_properties | (...subgroups).getParentGroup()
	 * 
	 * @post | Objects.equals(getSubgroups(), List.of(subgroups))
	 * @post | Arrays.stream(subgroups).allMatch(g -> g.getParentGroup() == this)
	 * @post | getParentGroup() == null
	 * @post | getOriginalExtent().getLeft() == Arrays.stream(subgroups).mapToInt(g -> g.getExtent().getLeft()).min().getAsInt()
	 * @post | getOriginalExtent().getTop() == Arrays.stream(subgroups).mapToInt(g -> g.getExtent().getTop()).min().getAsInt()
	 * @post | getOriginalExtent().getRight() == Arrays.stream(subgroups).mapToInt(g -> g.getExtent().getRight()).max().getAsInt()
	 * @post | getOriginalExtent().getBottom() == Arrays.stream(subgroups).mapToInt(g -> g.getExtent().getBottom()).max().getAsInt()
	 * @post | getExtent().equals(getOriginalExtent())
	 */
	public NonleafShapeGroup(ShapeGroup[] subgroups) {
		if (subgroups == null)
			throw new IllegalArgumentException("subgroups is null");
		if (subgroups.length < 2)
			throw new IllegalArgumentException("subgroups has less than two elements");
		if (Arrays.stream(subgroups).anyMatch(g -> g == null))
			throw new IllegalArgumentException("subgroups has null elements");
		if (!LogicalList.distinct(List.of(subgroups)))
			throw new IllegalArgumentException("subgroups has duplicate elements");
		if (Arrays.stream(subgroups).anyMatch(g -> g.getParentGroup() != null))
			throw new IllegalArgumentException("some of the given groups already have a parent");
		if (!Collections.disjoint(getAncestors(), Set.of(subgroups)))
			throw new IllegalArgumentException("some of the given groups are ancestors of this shape group");
		
		this.subgroups = new ArrayList<>(Arrays.asList(subgroups));
		for (ShapeGroup group : subgroups) {
			assert group.parent == null;
			group.parent = this;
		}
		
		int left = Integer.MAX_VALUE;
		int top = Integer.MAX_VALUE;
		int right = Integer.MIN_VALUE;
		int bottom = Integer.MIN_VALUE;
		for (ShapeGroup group : subgroups) {
			left = Math.min(left, group.getExtent().getLeft());
			right = Math.max(right, group.getExtent().getRight());
			top = Math.min(top, group.getExtent().getTop());
			bottom = Math.max(bottom, group.getExtent().getBottom());
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
		
		for (int i = subgroups.size() - 1; 0 <= i; i--) {
			ShapeGroup subgroup = subgroups.get(i);
			if (subgroup instanceof NonleafShapeGroup) {
				builder.append(((NonleafShapeGroup) subgroup).getDrawingCommands());
			}
			else {
				builder.append(((LeafShapeGroup) subgroup).getDrawingCommands());
			}
		}
		
		builder.append("popTransform popTransform popTransform\n");
		return builder.toString();
	}


}
