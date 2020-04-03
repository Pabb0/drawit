package drawit.shapegroups2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import drawit.shapegroups2.Extent;
import drawit.IntPoint;
import drawit.IntVector;
import drawit.RoundedPolygon;


/**
* @invar This ShapeGroup its children have this ShapeGroup as their parent and this ShapeGroup has no shape
* 		  or this ShapeGroup has no children but does have a shape.
* 		| getSubgroups() == null && getShape() != null ||
* 		| getSubgroups().stream().allMatch(child -> child.getParentGroup() == this) && getShape() == null
* @invar This shapeGroup is the root ShapeGroup (i.e. it has no parent) or else it is among its parent's children.
* 		| getParentGroup() == null || getParentGroup().getSubgroups().contains(this)
* @invar 	
* 		| getExtent() != null && getOriginalExtent() != null
*/
public class ShapeGroup {
	
	/**
	 * @peerObject
     * @invar This ShapeGroup has children whose parent is this ShapeGroup and this ShapeGroup has no shape
	 * 		  or this ShapeGroup has no children but does have a shape.
	 * 		| firstChild == null && shape != null ||
	 * 		| (firstChild != null && shape == null)
	 * 
	 * @invar	| nextSibling == null || nextSibling.previousSibling == this
	 * @invar	| previousSibling == null || previousSibling.nextSibling == this
	 * @invar 	| getSiblingsPrivate().stream().allMatch(sibling -> sibling.parent == this.parent)
	 * @invar This shapeGroup is the root ShapeGroup (i.e. it has no parent) or else it is among its parent's children.
	 * 		| parent == null || parent.getSiblingsPrivate().contains(this)
	 * @invar	| getAncestors().contains(this)
	 */
	private ShapeGroup parent;
	
	/**
	 * @peerObjects
	 * @invar 	| (firstChild == null && lastChild == null) || (firstChild != null && lastChild != null)
	 */
	private ShapeGroup firstChild;
	private ShapeGroup lastChild;
	private ShapeGroup previousSibling;
	private ShapeGroup nextSibling;
	
	
	private RoundedPolygon shape;
	
	/**
	 * @invar 	| extent != null
	 * @invar	| originalExtent != null
	 */
	private Extent extent;
	private final Extent originalExtent;
	
	/**
	 * @invar 	| translation != null
	 * @invar 	| scaling != null && (int) Math.round(scaling[0] * scaling[2]) == 1 && (int) Math.round(scaling[1] * scaling[3]) == 1		
	 */
	private double[] translation;
	private double[] scaling; 
	
	
	public ShapeGroup(RoundedPolygon shape) {
		this.parent = null;
		this.firstChild = null;
		this.lastChild = null;
		this.previousSibling = null;
		this.nextSibling = null;
		this.shape = shape;
		
		IntPoint[] points = shape.getVertices();
		int smallestX = points[0].getX();
		int smallestY = points[0].getY();
		int largestX = points[0].getX();
		int largestY = points[0].getY();
		
		// Finding extent of shape
		for (int i = 1; i < points.length; i++) {
			if (points[i].getX() < smallestX) {
				smallestX = points[i].getX();
			} else if (points[i].getX() > largestX) {
				largestX = points[i].getX();
			} 
			if (points[i].getY() < smallestY) {
				smallestY = points[i].getY();
			} else if (points[i].getY() > largestY) {
				largestY = points[i].getY();
			}
		}
		
		this.extent = Extent.ofLeftTopRightBottom(smallestX, smallestY, largestX, largestY);
		this.originalExtent = Extent.ofLeftTopRightBottom(smallestX, smallestY, largestX, largestY);
		
		// Identity transformation
		this.translation = new double[] {0, 0, 0, 0};
		this.scaling = new double[] {1, 1, 1, 1};
	}
	
	/**
	 * @throws if any ShapeGroup in the subgroups already has a parent.
	 *  	| Arrays.stream(subgroups).anyMatch(s -> s.getParentGroup() != null)
	 *  @throws if one of the elements is zero.
	 *  	| Arrays.stream(subgroups).anyMatch(s -> s == null)
	 */
	public ShapeGroup(ShapeGroup[] subgroups) {
		if(Arrays.stream(subgroups).anyMatch(s -> s.getParentGroup() != null)) {
			throw new IllegalArgumentException("One or more ShapeGroups in the given array already have/has a parent.");
		}
		if (Arrays.stream(subgroups).anyMatch(s -> s == null)) {
			throw new IllegalArgumentException("One or more ShapeGroups in the given array are null.");
		}
		this.shape = null;
		
		// Initializing first child
		this.firstChild = subgroups[0];
		subgroups[0].parent = this;
		subgroups[0].previousSibling = null;
		if (subgroups.length > 1) {
			subgroups[0].nextSibling = subgroups[1];
		}
		this.lastChild = subgroups[subgroups.length - 1];

		int smallestX = subgroups[0].getExtent().getLeft();
		int largestX = subgroups[0].getExtent().getRight();
		int smallestY = subgroups[0].getExtent().getTop();
		int largestY = subgroups[0].getExtent().getBottom();
		
		for (int index = 1; index < subgroups.length; index++) {
			
			subgroups[index].parent = this;			
			subgroups[index].previousSibling = subgroups[index - 1];			
			if (index != subgroups.length - 1) {
				subgroups[index].nextSibling = subgroups[index + 1];
			} 
			
			// Finding the total extent
			if (subgroups[index].getExtent().getLeft() < smallestX) {
				smallestX = subgroups[index].getExtent().getLeft();
			}
			if (subgroups[index].getExtent().getRight() > largestX) {
				largestX = subgroups[index].getExtent().getRight();
			}
			if (subgroups[index].getExtent().getTop() < smallestY) {
				smallestY = subgroups[index].getExtent().getTop();
			} 
			if (subgroups[index].getExtent().getBottom() > largestY) {
				largestY = subgroups[index].getExtent().getBottom();
			}

		}
		this.extent =  Extent.ofLeftTopRightBottom(smallestX, smallestY, largestX, largestY);
		this.originalExtent =  Extent.ofLeftTopRightBottom(smallestX, smallestY, largestX, largestY);
		
		// Identity transformation
		this.translation = new double[] {0, 0, 0, 0};
		this.scaling = new double[] {1, 1, 1, 1};
	}
	
	/**
	 * Returns this ShapeGroupe its extent (outer coordinate system).
	 * @basic
	 */
	public Extent getExtent() {return extent;}
	
	/**
	 * Returns this ShapeGroupe its original extent (inner coordinate system).
	 * @basic
	 */
	public Extent getOriginalExtent() {return originalExtent;}	
	
	/**
	 * Returns this ShapeGroup its parent ShapeGroup (or null if it has no parent).
	 * 
	 * @peerObject
	 * @basic
	 */
	public ShapeGroup getParentGroup() {return parent;}
	
	/**
	 * Returns this ShapeGroup its subgroups.
	 * 
	 * @post | (result == null || result.stream().allMatch(child -> child != null))
	 * @peerObjects
	 */
	public java.util.List<ShapeGroup> getSubgroups() {
		if (this.firstChild != null) { // Non-leaf
			ArrayList<ShapeGroup> subGroups = new ArrayList<ShapeGroup>();
			for (ShapeGroup child = this.firstChild; child != null; child = child.nextSibling) {
				subGroups.add(child);
			}
			return subGroups;
		} else { // Leaf
			return null;
		}
	}
	
	/**
	 * Returns this ShapeGroup its subgroup at the given index.
	 * 
	 * @throws if the given index is out of range
	 * 		| index < 0 || index >= getSubgroupCount() 
	 * @post The resulting subgroup is a child of this ShapeGroup.
	 * 		| getSubgroups().contains(result)
	 * @post The parent of the result is this ShapeGroup
	 * 		| result.getParentGroup() == this
	 * @peerObject
	 */
	public ShapeGroup getSubgroup(int index) {
		if (index < 0 || index >= this.getSubgroupCount()) {
			throw new IllegalArgumentException ("The given index is out of range.");
		}
		return this.getSubgroups().get(index);
	}
	
	/**
	 * Returns the number of ShapeGroups that this ShapeGroup has in its subgroup.
	 */
	public int getSubgroupCount() {
		if (this.firstChild == null) { // Leaf
			return 0;
		} else { // Non-leaf
			return this.getSubgroups().size();
		}
	}
	
	/**
	 * Returns the first ShapeGroup of this ShapeGroup its subgroups whose extent contains the given coordinates. 
	 * If no such ShapeGroup exists, null is returned.
	 * 
	 * @throws if {@code innerCoordinates} is null.
	 * 		| innerCoordinates == null
	 * @post 	| result == null || result.getParentGroup() == this
	 * @post	| result == null || this.getSubgroups().contains(this)	
	 * @post	| result == null || result.getExtent().contains(innerCoordinates)
	 * @peerObject
	 */
	public ShapeGroup getSubgroupAt(IntPoint innerCoordinates) {
		if (innerCoordinates == null) {
			throw new IllegalArgumentException("The given coordinates is null.");
		}
		for(ShapeGroup subGroup = this; subGroup != null; subGroup = subGroup.nextSibling) {
			if (subGroup.getExtent().contains(innerCoordinates)) {
				return subGroup;
			}
		}
		return null;
	}
	
	/**
	 * Returns the shape of the this ShapeGroup if its a leaf group (i.e. the ShapeGroup has no subgroups).
	 * If the shape is not a leaf group, null is returned.
	 * 
	 * @basic
	 */
	public RoundedPolygon getShape() {return shape;}
	
	/**
	 * Returns all the ancestors of a ShapeGroup, including itself. 
	 * (i.e the ShapeGroup itself, its parent, the parent of its parent, the parent of the parent of its parent,...).
	 * 
	 * @post | result != null
	 * 
	 * @peerObjects
	 */
	private ArrayList<ShapeGroup> getAncestors() {
		ArrayList<ShapeGroup> ancestors = new ArrayList<ShapeGroup>();
		ShapeGroup shape = this;
		ancestors.add(shape);
		while (shape.parent != null) {
			ancestors.add(shape.parent);
			shape = shape.parent;
		}
		return ancestors;
	}
	/**
	 * @peerObjects
	 */
	private List<ShapeGroup> getSiblingsPrivate() {
		return parent.getSubgroups();
	}
	
	/**
	 * Sets the given extent as this ShapeGroup its extent, expressed in this ShapeGroup its outer coordinate system.
	 * 
	 * @throws if {@code newExtent} is null.
	 * 		| newExtent == null
	 * @post 	| getExtent() == newExtent
	 * 
	 * @mutates_properties | getExtent()
	 */
	public void setExtent(Extent newExtent) {
		if (newExtent == null) {
			throw new IllegalArgumentException("The given extent is null.");
		}
		extent = newExtent;
		
		this.transformation(this.originalExtent, this.extent);
	}
	
	// Defines the transformation between the inner and outer extent of this ShapeGroup
	private void transformation(Extent inner, Extent outer) {
		// OUTER TO INNER
		this.scaling[0] = (double) outer.getWidth() / inner.getWidth();
		this.scaling[1] = (double) outer.getHeight() / inner.getHeight();
		this.translation[0] = outer.getLeft() - this.scaling[0] * inner.getLeft();
		this.translation[1] = outer.getTop() - this.scaling[1] * inner.getTop();
		
		// INNER TO OUTER
		this.scaling[2] = (double) inner.getWidth() / outer.getWidth();
		this.scaling[3] = (double) inner.getHeight() / outer.getHeight();
		this.translation[2] = inner.getLeft() - this.scaling[2] * outer.getLeft();
		this.translation[3] = inner.getTop() - this.scaling[3] * outer.getTop();
	}
	
	
	/**
	 * Returns the coordinates in this ShapeGroup its inner coordinate system of the point whose 
	 * coordinates in the global coordinate system are the given coordinates.
	 * 
	 * @throws if {@code globalCoordinates} is null.
	 * 		| globalCoordinates == null
	 * 
	 * @inspects | this
	 */
	public IntPoint toInnerCoordinates(IntPoint globalCoordinates) {
		if (globalCoordinates == null) {
			throw new IllegalArgumentException("The given IntPoint is null.");
		}
		ArrayList<ShapeGroup> ancestors = this.getAncestors();
		IntPoint point = globalCoordinates;
		for (int i = ancestors.size() - 1; i >= 0; i--) {
			ShapeGroup ancestor = ancestors.get(i);
			
			int newX = (int) Math.round(ancestor.scaling[2] * point.getX() + ancestor.translation[2]);
			int newY = (int) Math.round(ancestor.scaling[3] * point.getY() + ancestor.translation[3]);	
			point = new IntPoint(newX, newY);
		}
		return point;
	}
	
	/**
	 * Returns the coordinates in this ShapeGroup its inner coordinate system of the vector whose 
	 * coordinates in the global coordinate system are the given coordinates.
	 * 
	 * @throws if {@code relativeGlobalCoordinates} is null.
	 * 		| relativeGlobalCoordinates == null
	 * 
	 * @inspects | this
	 */
	public IntVector toInnerCoordinates(IntVector relativeGlobalCoordinates) {
		if (relativeGlobalCoordinates == null) {
			throw new IllegalArgumentException("The given IntVector is null.");
		}
		ArrayList<ShapeGroup> ancestors = this.getAncestors();
		IntVector vector = relativeGlobalCoordinates;
		for (int i = ancestors.size() - 1; i >= 0; i--) {
			ShapeGroup ancestor = ancestors.get(i);
			
			int newX = (int) Math.round(ancestor.scaling[2] * vector.getX());
			int newY = (int) Math.round(ancestor.scaling[3] * vector.getY());
			vector = new IntVector(newX, newY);
		}
		return vector;
	}
	
	/**
	 * Returns the coordinates in the global coordinate system of the point whose 
	 * coordinates in the ShapeGroup its inner coordinate system are the given coordinates.
	 * 
	 * @throws if {@code innerCoordinates} is null.
	 * 		| innerCoordinates == null
	 * 
	 * @inspects | this
	 */
	public IntPoint toGlobalCoordinates(IntPoint innerCoordinates) {
		if (innerCoordinates == null) {
			throw new IllegalArgumentException("The given IntPoint is null.");
		}
		IntPoint point = innerCoordinates;

		for(ShapeGroup shapeGroup = this; shapeGroup != null; shapeGroup = shapeGroup.parent) {
			int newX = (int) Math.round(shapeGroup.scaling[0] * point.getX() + shapeGroup.translation[0]);
			int newY = (int) Math.round(shapeGroup.scaling[1] * point.getY() + shapeGroup.translation[1]);
			point = new IntPoint(newX, newY);
		}
		return point;
	}
	
	/**
	 * Moves this ShapeGroup to the front of its parent's list of subgroups.
	 * 
	 * @mutates_properties | getSubgroups()
	 * 
	 * @post The resulting list of subgroups is a permutation of the original
	 * 		| getParentGroup() == null ||  (getParentGroup().getSubgroups()).containsAll(old(getParentGroup().getSubgroups())) 
	 * 										&& (old(getParentGroup().getSubgroups()).containsAll(getParentGroup().getSubgroups()))
	 * @post | getParentGroup() == null ||  (getParentGroup().getSubgroup(0).equals(this))
	 * 										
	 */
	public void bringToFront() {
		// Remove the child from the children
		if (this.previousSibling != null) {
			this.previousSibling.nextSibling = this.nextSibling;
		} else {
			parent.firstChild = this.nextSibling;
		}
		if (this.nextSibling != null) {
			this.nextSibling.previousSibling = this.previousSibling;
		} else {
			parent.lastChild = this.previousSibling;
		}
		
		// Add the child at the front
		parent.firstChild.previousSibling = this;
		this.nextSibling = parent.firstChild;
		this.previousSibling = null;
		parent.firstChild = this;
		}
	
	/**
	 * Moves this ShapeGroup to the back of its parent's list of subgroups.
	 * 
	 * @mutates_properties | getSubgroups()
	 * 
	 * @post The resulting list of subgroups is a permutation of the original
	 * 		| getParentGroup() == null ||  (getParentGroup().getSubgroups()).containsAll(old(getParentGroup().getSubgroups())) 
	 * 										&& (old(getParentGroup().getSubgroups()).containsAll(getParentGroup().getSubgroups()))
	 * @post | getParentGroup() == null ||  getParentGroup().getSubgroup(getParentGroup().getSubgroupCount() - 1).equals(this)
	 */
	public void sendToBack() {
		// Remove the child from the children
		if (this.previousSibling != null) {
			this.previousSibling.nextSibling = this.nextSibling;
		} else {
			parent.firstChild = this.nextSibling;
		}
		if (this.nextSibling != null) {
			this.nextSibling.previousSibling = this.previousSibling;
		} else {
			parent.lastChild = this.previousSibling;
		}
		
		// Add the child at the back
		parent.lastChild.nextSibling = this;
		this.previousSibling = parent.lastChild;
		this.nextSibling = null;
		parent.lastChild = this;
		}
	
	public java.lang.String getDrawingCommands() {
		StringBuilder commands = new StringBuilder();

		if (this.shape != null) {			
			commands.append("pushTranslate " + this.translation[0] + " " + this.translation[1] + "\n");
			commands.append("pushScale " + this.scaling[0] + " " + this.scaling[1] + "\n");
			commands.append(this.shape.getDrawingCommands());
			commands.append("popTransform" + "\n");
			commands.append("popTransform" + "\n");
			return commands.toString();
		} 
		else {
			for (ShapeGroup child = this.lastChild; child != null; child = child.previousSibling) {
				commands.append("pushTranslate " + this.translation[0] + " " + this.translation[1] + "\n");
				commands.append("pushScale " + this.scaling[0] + " " + this.scaling[1] + "\n");
				commands.append(child.getDrawingCommands());
				commands.append("popTransform" + "\n");
				commands.append("popTransform" + "\n");
			}
		}
		return commands.toString();
	}
	
}
