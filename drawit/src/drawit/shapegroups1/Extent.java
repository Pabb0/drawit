package drawit.shapegroups1;

import drawit.IntPoint;

public class Extent {
	
	private int left;
	private int right;
	private int top;
	private int bottom;

	private Extent(int left, int top, int right, int bottom) {	
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
	}
	
	public int getLeft() {
		return this.left;
	}
	
	public int getRight() {
		return this.right;
	}
	
	public int getTop() {
		return this.top;
	}
	
	public int getBottom() {
		return this.bottom;
	}
	
	public int getWidth() {
		return (this.right - this.left);
	}
	
	public int getHeight() {
		return (this.bottom - this.top);
	}	
	
	public IntPoint getTopLeft() {
		return new IntPoint(getLeft(), getTop());
	}
	
	public IntPoint getBottomRight() {
		return new IntPoint(getRight(), getBottom());
	}
	
	public boolean contains(IntPoint point) {
		return (getLeft() <= point.getX() && point.getX() <= getRight() &&
				getTop() <= point.getY() && point.getY() <= getBottom());
	}
	
	public static Extent ofLeftTopRightBottom(int left, int top, int right, int bottom) {
		return new Extent(left, top, right, bottom);
	}
	
	public static Extent ofLeftTopWidthHeight(int left, int top, int width, int height) {
		return new Extent(left, top, left + width, top + height);
	}
	
	public Extent withLeft(int newLeft) {
//		if (newLeft < right) {
			return new Extent(newLeft, top, right, bottom);
//		} else {
//			return new Extent(right, top, newLeft, bottom);
//		}
	}
	
	public Extent withTop(int newTop) {
//		if (newTop < bottom) {
			return new Extent(left, newTop, right, bottom);
//		} else {
//			return new Extent(left, bottom, right, newTop);
//		}
	}
	
	public Extent withRight(int newRight) {
//		if (newRight > left) {
			return new Extent(left, top, newRight, bottom);
//		} else {
//			return new Extent(newRight, top, left, bottom);
//		}
	}
	
	public Extent withBottom(int newBottom) {
//		if (newBottom > top) {
			return new Extent(left, top, right, newBottom);
//		} else {
//			return new Extent(left, newBottom, right, top);
//		}
	}
	
	public Extent withWidth(int newWidth) {
		return new Extent(left, top, left + newWidth, bottom);
	}
	
	public Extent withHeight(int newHeight) {
		return new Extent(left, top, right, bottom + newHeight);
	}
	
}
