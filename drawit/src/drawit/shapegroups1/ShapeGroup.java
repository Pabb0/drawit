package drawit.shapegroups1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import drawit.DoublePoint;
import drawit.DoubleVector;
import drawit.IntPoint;
import drawit.IntVector;
import drawit.RoundedPolygon;

public class ShapeGroup {
	
	private ArrayList<ShapeGroup> subGroups;
	private ShapeGroup parent;
	private RoundedPolygon shape;
	private Extent extent;
	private Extent originalExtent;
	private double[] translation;
	private double[] scaling; 
	
	
	public ShapeGroup(RoundedPolygon shape) {
		this.subGroups = new ArrayList<ShapeGroup>();
		this.parent = null;
		this.shape = shape;
		
		IntPoint[] points = shape.getVertices();
		int smallestX = points[0].getX();
		int smallestY = points[0].getY();
		int largestX = points[0].getX();
		int largestY = points[0].getY();

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
		this.translation = new double[] {0, 0, 0, 0};
		this.scaling = new double[] {1, 1, 1, 1};
	}
	
	public ShapeGroup(ShapeGroup[] subgroups) {
		subGroups = new ArrayList<ShapeGroup>();
		subGroups.add(subgroups[0]);
		subgroups[0].parent = this;
		this.shape = null;
		
		int smallestX = subgroups[0].getExtent().getLeft();
		int largestX = subgroups[0].getExtent().getRight();
		int smallestY = subgroups[0].getExtent().getTop();
		int largestY = subgroups[0].getExtent().getBottom();

		for (int index = 1; index < subgroups.length; index++) {
			this.subGroups.add(subgroups[index]);
			subgroups[index].parent = this;

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
		
		this.translation = new double[] {0, 0, 0, 0};
		this.scaling = new double[] {1, 1, 1, 1};
	}
	
	public Extent getExtent() {
		return extent;
	}
	
	public Extent getOriginalExtent() {
		return originalExtent;
	}	
	
	public ShapeGroup getParentGroup() {
		return parent;
	}
	
	public java.util.List<ShapeGroup> getSubgroups() {
		if (subGroups.size() != 0) {
			return List.copyOf(subGroups);
		} else {
			return null;
		}
	}
	public ShapeGroup getSubgroup(int index) {
		return subGroups.get(index);
	}
	
	public int getSubgroupCount() {
		return subGroups.size();
	}
	
	public ShapeGroup getSubgroupAt(IntPoint innerCoordinates) {
		for (ShapeGroup subGroup : subGroups) {
			if (subGroup.getExtent().contains(innerCoordinates)) {
				return subGroup;
			}
		}
		return null;
	}
	
	public RoundedPolygon getShape() {
		if (subGroups.size() == 0) {
			return shape;
		} else {
			return null;
		}
	}
	
	private ArrayList<ShapeGroup> getAncestors() {
		ArrayList<ShapeGroup> ancestors = new ArrayList<ShapeGroup>();
		ShapeGroup shape = this;
		while (shape.parent != null) {
			ancestors.add(shape.parent);
			shape = shape.parent;
		}
		return ancestors;
	}
	
	public void setExtent(Extent newExtent) {
		extent = newExtent;
		
		this.transformation(this.originalExtent, this.extent);
	}
	
	private void transformation(Extent inner, Extent outer) {
		this.scaling[0] = (double) outer.getWidth() / inner.getWidth();
		this.scaling[1] = (double) outer.getHeight() / inner.getHeight();
		this.scaling[2] = (double) inner.getWidth() / outer.getWidth();
		this.scaling[3] = (double) inner.getHeight() / outer.getHeight();
		
		this.translation[0] = outer.getLeft() - this.scaling[0] * inner.getLeft();
		this.translation[1] = outer.getTop() - this.scaling[1] * inner.getTop();
		this.translation[2] = inner.getLeft() - this.scaling[2] * outer.getLeft();
		this.translation[3] = inner.getTop() - this.scaling[3] * outer.getTop();
		
		

	}
	
	
	private IntPoint transforms(IntPoint point, Extent from, Extent to) {
		
		int newX = (int) Math.round(this.scaling[0] * point.getX() + translation[0]);
		int newY = (int) Math.round(this.scaling[1] * point.getY() + translation[1]);

		return new IntPoint(newX, newY);
//		
//		int newX = (int) Math.round(to.getLeft() + ((double)to.getWidth() / (double)from.getWidth()) * (point.getX() - from.getLeft()));
//		int newY = (int) Math.round(to.getTop() + ((double)to.getHeight() / (double)from.getHeight()) * (point.getY() - from.getTop()));
//		
//		return new IntPoint(newX, newY);
//		 

	}
	
	public IntPoint toInnerCoordinates(IntPoint globalCoordinates) {
		ArrayList<ShapeGroup> ancestors = this.getAncestors();
		IntPoint point = globalCoordinates;
		while(ancestors.size() > 0) {
			ShapeGroup ancestor = ancestors.get(ancestors.size() - 1);
			
			int newX = (int) Math.round(ancestor.scaling[2] * point.getX() + ancestor.translation[2]);
			int newY = (int) Math.round(ancestor.scaling[3] * point.getY() + ancestor.translation[3]);
			
			point = new IntPoint(newX, newY);
			
			ancestors.remove(ancestors.size() - 1);
		}
		
		int newX = (int) Math.round(this.scaling[2] * point.getX() + this.translation[2]);
		int newY = (int) Math.round(this.scaling[3] * point.getY() + this.translation[3]);
		
		point = new IntPoint(newX, newY);

		return point;
	}
	
	public IntVector toInnerCoordinates(IntVector relativeGlobalCoordinates) {
		ArrayList<ShapeGroup> ancestors = this.getAncestors();
		IntPoint point = new IntPoint(relativeGlobalCoordinates.getX(), relativeGlobalCoordinates.getY());
		while(ancestors.size() > 0) {
			ShapeGroup ancestor = ancestors.get(ancestors.size() - 1);
			
			int newX = (int) Math.round(ancestor.scaling[2] * point.getX() + ancestor.translation[2]);
			int newY = (int) Math.round(ancestor.scaling[3] * point.getY() + ancestor.translation[3]);
			
			point = new IntPoint(newX, newY);
			
			ancestors.remove(ancestors.size() - 1);
		}
		
		int newX = (int) Math.round(this.scaling[2] * point.getX() + this.translation[2]);
		int newY = (int) Math.round(this.scaling[3] * point.getY() + this.translation[3]);
		
		point = new IntPoint(newX, newY);

		IntVector vector = new IntVector(point.getX(), point.getY());
		return vector;
	}
	
	public IntPoint toGlobalCoordinates(IntPoint innerCoordinates) {
		ShapeGroup shapeGroup = this;
		IntPoint point = innerCoordinates;

		
		boolean rootNodeFound = false;
		while(!rootNodeFound) { 
			int newX = (int) Math.round(shapeGroup.scaling[0] * point.getX() + shapeGroup.translation[0]);
			int newY = (int) Math.round(shapeGroup.scaling[1] * point.getY() + shapeGroup.translation[1]);
			point = new IntPoint(newX, newY);
			
			shapeGroup = shapeGroup.parent;
			if(shapeGroup == null) {
				rootNodeFound = true;
			};
		}
		
		return point;
	}
	
	public void bringToFront() {
		Collections.swap(this.parent.subGroups, 0, this.parent.subGroups.indexOf(this));	
	}
	
	public void sendToBack() {
		Collections.swap(this.parent.subGroups, this.parent.subGroups.size() - 1, this.parent.subGroups.indexOf(this));	
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
			for (int i = subGroups.size() - 1; i >= 0; i--) {
				ShapeGroup subGroup = subGroups.get(i);
				commands.append("pushTranslate " + this.translation[0] + " " + this.translation[1] + "\n");
				commands.append("pushScale " + this.scaling[0] + " " + this.scaling[1] + "\n");
				commands.append(subGroup.getDrawingCommands());
				commands.append("popTransform" + "\n");
				commands.append("popTransform" + "\n");
			}
		}
		return commands.toString();
	}
	
}
