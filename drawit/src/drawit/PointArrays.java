package drawit;

import java.util.Arrays;

/**
 * Declares a number of methods useful for working with arrays of {@code IntPoint} objects.
 */
public class PointArrays {
	
	private PointArrays() {}
	
	/**
	 * Returns a new array with the same contents as the given array.
	 * 
	 * @inspects | points
	 * @creates | result
	 * 
	 * @pre Argument {@code points} is not {@code null}.
	 * 		| points != null
	 * @pre None of the elements of the given array are {@code null}.
	 * 		| !(Arrays.stream(points).anyMatch(e -> e == null))
	 * @post The contents of the given array and the result are equals.
	 * 		| points.length == result.length &&
	 * 		| Arrays.equals(points, 0, points.length, result, 0, result.length)
	 * 
	 */
	public static IntPoint[] copy(IntPoint[] points) {
		IntPoint[] newArray = new IntPoint[points.length];
		
		for(int i = 0; i < points.length; i++) {
			newArray[i] = points[i];
		}
		
		return newArray;
	}
	/**
	 * Returns a new array whose elements are the elements of the given array with the given point inserted at the given index.
	 * 
	 * @inspects | points
	 * @creates | result
	 * 
	 * @pre Argument {@code points} is not {@code null}.
	 * 		| points != null
	 * @pre None of the elements of the given array are {@code null}.
	 * 		| !(Arrays.stream(points).anyMatch(e -> e == null))
	 * @pre Argument {@code point} is not {@code null}.
	 * 		| point != null
	 * @pre The given index is between 0 (inclusive) and the length of the array (inclusive)
	 * 		| 0 <= index && index <= points.length
	 * @post The new array is equal to the given array with the given point inserted at the given index
	 *		| points.length + 1 == result.length &&
	 *		| Arrays.equals(points, 0, index, result, 0, index) &&
	 *		| result[index] == point &&
	 *		| Arrays.equals(points, index, points.length, result, index + 1, result.length)
	 */
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
	/**
	 * Returns a new array whose elements are the elements of the given array with the element at the given index removed.
	 * 
	 * @inspects | points
	 * @creates | result
	 * 
	 * @pre Argument {@code points} is not {@code null}.
	 * 		| points != null
	 * @pre None of the elements of the given array are {@code null}.
	 * 		| !(Arrays.stream(points).anyMatch(e -> e == null))
	 * @pre The given index is between 0 (inclusive) and the length of the array (exclusive)
	 * 		| 0 <= index && index < points.length
	 * @post The new array is equal to the given array with point at the given index from the given array removed.
	 *		| points.length - 1 == result.length &&
	 *		| Arrays.equals(points, 0, index, result, 0, index) &&
	 *		| Arrays.equals(points, index + 1, points.length, result, index, result.length)
	 * 
	 */
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
	
	/**
	 * Returns a new array whose elements are the elements of the given array with the element at the given index replaced by the given point.
	 * 
	 * @inspects | points
	 * @creates | result
	 * 
	 * @pre Argument {@code points} is not {@code null}.
	 * 		| points != null
	 * @pre None of the elements of the given array are {@code null}.
	 * 		| !(Arrays.stream(points).anyMatch(e -> e == null))
	 * @pre Argument {@code point} is not {@code null}.
	 * 		| point != null
	 * @pre The given index is between 0 (inclusive) and the length of the array (exclusive)
	 * 		| 0 <= index && index < points.length
	 * @post The new array is equal to the given array with the given point replacing the point at the given index of the given array.
	 *		| points.length == result.length &&
	 *		| Arrays.equals(points, 0, index, result, 0, index) &&
	 *		| result[index] == point &&
	 *		| Arrays.equals(points, index + 1, points.length, result, index + 1, result.length)
	 */
	public static IntPoint[] update(IntPoint[] points, int index, IntPoint point) {
		IntPoint[] newArray = new IntPoint[points.length];
		for(int i = 0; i < index; i++) {
			newArray[i] = points[i];
		}
		newArray[index] = point;
		for(int i = index + 1; i < newArray.length; i++) {
			newArray[i] = points[i];
		}
		return newArray;
	}
	
	/**
	 * Returns {@code null} if the given array of points defines a proper polygon; otherwise, returns a string describing why it does not.
	 * 
	 * @inspects | points
	 * @creates | result
	 * 
	 * @pre Argument {@code points} is not {@code null}.
	 * 		| points != null
	 * @pre None of the elements of the given array are {@code null}.
	 * 		| !(Arrays.stream(points).anyMatch(e -> e == null))
	 * @post The result is {@code null} if the given array of points defines a proper polygon.
	 * 		 A proper polygon is one where no two vertices coincide, no vertex is on any edge of the polygon and no two edges of the polygon intersect.
	 * 		 A proper polygon also has at least 3 vertices.
	 */
	public static String checkDefinesProperPolygon(IntPoint[] points) {
		if (points.length <= 2) {
			return "The given array of points does not define a proper polygon because the array contains two or less points. (The given array contains " + String.valueOf(points.length) + " points).";
		}
		for(int i = 0; i < points.length; i++) {
			for(int j = 0; j < points.length; j++) {
				if (i != j && points[i].equals(points[j])) {
					return "The given array of points does not define a proper polygon because at least two vertices coincide. (vertex " + String.valueOf(i) + " and vertex " + String.valueOf(j) + " from the array).";
				}
				if (points[j].isOnLineSegment(points[i], points[(i + 1) % points.length])) {
					return "The given array of points does not define a proper polygon because at least one vertex is on any edge. (vertex " + String.valueOf(j) + " is on the line between vertex " + String.valueOf(i) + " and vertex " + String.valueOf((i+1) % points.length) + " ).";
				}
				if (i != j && IntPoint.lineSegmentsIntersect(points[i], points[(i + 1) % points.length], points[j], points[(j + 1) % points.length])) {
					return "The given array of points does not define a proper polygon because at least two edges intersect. (The line between vertex " + String.valueOf(i) + " and vertex " + String.valueOf((i+1) % points.length) + " intersects with the line between vertex" + String.valueOf(j) + " and vertex " + String.valueOf((j+1) % points.length) + ").";
					
				}
			}
		}
		
		return null;
	}
}