package drawit;

public class PointArrays {

	public static IntPoint[] copy(IntPoint[] points) {
		IntPoint[] newArray = new IntPoint[points.length];
		
		for(int i = 0; i < points.length; i++) {
			newArray[i] = points[i];
		}
		
		return newArray;
	}
	
	public static IntPoint[] insert(IntPoint[] points, int index, IntPoint point) {
		IntPoint[] newArray = new IntPoint[points.length + 1];

		for(int i = 0; i < index; i++) {
			newArray[i] = points[i];
		}
		newArray[index] = point;
		for(int i = index + 1; i < newArray.length; i++) {
			newArray[i] = points[i - 1];
		}
		return newArray;
	}
	
	public static IntPoint[] remove(IntPoint[] points, int index) {
		IntPoint[] newArray = new IntPoint[points.length - 1];
		
		for(int i = 0; i < index; i++) {
			newArray[i] = points[i];
		}
		for(int i = index; i < newArray.length; i++) {
			newArray[i] = points[i + 1];
		}
		return newArray;
	}
	
	public static IntPoint[] update(IntPoint[] points, int index, IntPoint value) {
		IntPoint[] newArray = new IntPoint[points.length];
		for(int i = 0; i < index; i++) {
			newArray[i] = points[i];
		}
		newArray[index] = value;
		for(int i = index + 1; i < newArray.length; i++) {
			newArray[i] = points[i];
		}
		return newArray;
	}
	
	public static String checkDefinesProperPolygon(IntPoint[] points) {
		if (points.length <= 2) {
			return "The given array of points does not define a proper polygon because the array contains two or less points.";
		}
		for(int i = 0; i < points.length; i++) {
			for(int j = 0; j < points.length; j++) {
				if (points[i].equals(points[j]) && i != j) {
					return "The given array of points does not define a proper polygon because at least two vertices coincide.";
				}
				if (points[j].isOnLineSegment(points[i], points[(i + 1) % points.length])) {
					return "The given array of points does not define a proper polygon because at least one vertex is on any edge.";
				}
				if (IntPoint.lineSegmentIntersect(points[i], points[(i + 1) % points.length], points[j], points[(j + 1) % points.length]) && i != j) {
					return "The given array of points does not define a proper polygon because at least two edges intersect.";
				}
			}
		}
		
		return null;
	}
}