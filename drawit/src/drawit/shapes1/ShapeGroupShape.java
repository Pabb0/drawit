package drawit.shapes1;

import drawit.IntPoint;
import drawit.IntVector;
import drawit.shapegroups1.Extent;
import drawit.shapegroups1.ShapeGroup;

public class ShapeGroupShape implements Shape{
	
	private ShapeGroup group;
	
	public ShapeGroupShape(ShapeGroup group) {
		this.group = group;
	}
	
	public ShapeGroup getShapeGroup() {
		return group;
	}
	
	public ShapeGroup getParent() {
		return group.getParentGroup();
	}
	
	public boolean contains(IntPoint p) {
		return group.getExtent().contains(p);
	}
	
	public String getDrawingCommands() {
		return group.getDrawingCommands();
	}
	
	public IntPoint toShapeCoordinates(IntPoint p) {
		if (group.getParentGroup() == null) {
			return p;
		}
		return group.getParentGroup().toInnerCoordinates(p);
	}
	
	public IntVector toShapeCoordinates(IntVector v) {
		if (group.getParentGroup() == null) {
			return v;
		}
		return group.getParentGroup().toInnerCoordinates(v);
	}
	
	public IntPoint toGlobalCoordinates(IntPoint p) {
		if (group.getParentGroup() == null) {
			return p;
		}
		return group.getParentGroup().toGlobalCoordinates(p);
	}

	
	public ControlPoint[] createControlPoints() {
		final Extent extent = this.group.getExtent();
		
		return new ControlPoint[] {
		
			new ControlPoint(){
				@Override
				public IntPoint getLocation() {
					return extent.getTopLeft();
				}
	
				@Override
				public void remove() {}
	
				@Override
				public void move(IntVector delta) {
					delta = ShapeGroupShape.this.toShapeCoordinates(delta);
					ShapeGroupShape.this.group.setExtent(Extent.ofLeftTopRightBottom(
							extent.getLeft() + delta.getX(), 
							extent.getTop() + delta.getY(), 
							extent.getRight(), 
							extent.getBottom()));
					}
				},
				
			new ControlPoint() {
				@Override
				public IntPoint getLocation() {
					return extent.getBottomRight();
				}
	
				@Override
				public void remove() {}
	
				@Override
				public void move(IntVector delta) {
					delta = ShapeGroupShape.this.toShapeCoordinates(delta);
					ShapeGroupShape.this.group.setExtent(Extent.ofLeftTopRightBottom(
							extent.getLeft(), 
							extent.getTop(), 
							extent.getRight() + delta.getX(), 
							extent.getBottom() + delta.getY()));
				}
			}
		};
			

		
	}


}
