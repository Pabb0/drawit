package drawit.shapes2;

import drawit.IntPoint;
import drawit.IntVector;
import drawit.shapegroups2.Extent;
import drawit.shapegroups2.ShapeGroup;

public class ShapeGroupShape implements Shape{
	
	private final ShapeGroup group;
	
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
	
	public IntPoint toShapeCoordinates(IntPoint point) {
		if (group.getParentGroup() == null) {
			return point;
		}
		return group.getParentGroup().toInnerCoordinates(point);
	}
	
	public IntVector toShapeCoordinates(IntVector vector) {
		if (group.getParentGroup() == null) {
			return vector;
		}
		return group.getParentGroup().toInnerCoordinates(vector);
	}
	
	public IntPoint toGlobalCoordinates(IntPoint point) {
		if (group.getParentGroup() == null) {
			return point;
		}
		return group.getParentGroup().toGlobalCoordinates(point);
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
