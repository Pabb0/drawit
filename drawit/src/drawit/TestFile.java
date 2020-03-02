package drawit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class TestFile {

	@Test
	void test() {
		DoubleVector dVector1 = new DoubleVector(2.0, 0.0);
		DoubleVector dVector2 = new DoubleVector(1.0, 1.0);	
		DoubleVector dVector3 = new DoubleVector(2.5, 1.3);
		DoubleVector dVector4 = new DoubleVector(3.5, 0.0);
		DoubleVector dVector5 = new DoubleVector(3.0, 1.0);
		DoubleVector dVector6 = new DoubleVector(14.0, 0.0);

		assert dVector1.asAngle() == 0.0;
		assert dVector2.asAngle() == Math.PI / 4;
		assert dVector3.getX() == 2.5;
		assert dVector4.getY() == 0.0;
		assert dVector1.getSize() == 2.0;
		assert dVector2.getSize() == Math.sqrt(2.0);
		DoubleVector dVectorSum = dVector1.plus(dVector2);
		assert dVectorSum.getX() == dVector5.getX();
		assert dVectorSum.getY() == dVector5.getY();
		DoubleVector dVectorScale= dVector4.scale(4.0);
		assert dVectorScale.getX() == dVector6.getX();
		assert dVectorScale.getY() == dVector6.getY();
		assert dVector1.dotProduct(dVector2) == 2.0;
		assert dVector4.dotProduct(dVector5) == 3.0*3.5;
		assert dVector3.dotProduct(dVector5) == 8.8;
		assert dVector1.dotProduct(dVector3) == 5.0;
		assert dVector1.crossProduct(dVector2) == 2.0;
		assert dVector1.crossProduct(dVector4) == 0.0;
		assert dVector2.crossProduct(dVector3) == 1.0 * 1.3 - 2.5;
		assert dVector1.asAngle() == 0.0;
		assert dVector2.asAngle() == Math.PI / 4;
		

		DoublePoint dPoint1 = new DoublePoint(2.0, 0.0);
		DoublePoint dPoint2 = new DoublePoint(3.14, 2.70);
		DoublePoint dPoint3 = new DoublePoint(3.0, -0.5);
		DoublePoint dPoint4 = new DoublePoint(6.0, 0.5);
		DoublePoint dPoint5 = new DoublePoint(7.5, 1.0);
		
		assert dPoint1.getX() == 2.0;
		assert dPoint2.getY() == 2.70;
		DoublePoint dPointSum = dPoint3.plus(dVector4);
		DoubleVector dVectorDiff = dPoint5.minus(dPoint1);
		assert dPointSum.getX() == 6.5;
		assert dPointSum.getY() == -0.5;
		assert dVectorDiff.getX() == 5.5;
		assert dVectorDiff.getY() == 1.0;
		IntPoint IntDPoint2 = dPoint2.round();
		IntPoint IntDPoint4 = dPoint4.round();
		assert IntDPoint2.getX() == 3;
		assert IntDPoint2.getY() == 3;
		assert IntDPoint4.getX() == 6;
		assert IntDPoint4.getY() == 1;


		IntVector iVector1 = new IntVector(1,2);
		IntVector iVector2 = new IntVector(5,5);
		IntVector iVector3 = new IntVector(-2,4);
		IntVector iVector4 = new IntVector(11,22);
		IntVector iVector5 = new IntVector(1,2);

		
		assert iVector1.getX() == 1;
		assert iVector3.getY() == 4;
		assert iVector1.dotProduct(iVector2) == 15;
		assert iVector4.crossProduct(iVector5) == 0;
		assert iVector5.isCollinearWith(iVector4) == true;
		assert iVector5.isCollinearWith(iVector2) == false;
		DoubleVector iToDVector = iVector1.asDoubleVector();
		assert iToDVector.getX() == 1.0;
		assert iToDVector.getY() == 2.0;
		

		IntPoint iPoint1 = new IntPoint(2, 0);
		IntPoint iPoint1Copy = new IntPoint(2, 0);
		IntPoint iPoint2 = new IntPoint(-3, 4);
		IntPoint iPoint3 = new IntPoint(1, -2);
		IntPoint iPoint4 = new IntPoint(3, 2);
		IntPoint iPoint5 = new IntPoint(0, 2);
		IntPoint iPoint6 = new IntPoint(0, 0);
		IntPoint iPoint7 = new IntPoint(1, 0);
		IntPoint iPoint8 = new IntPoint(2, 2);
		IntPoint iPoint9 = new IntPoint(100, 100);
		IntPoint iPoint10 = new IntPoint(200, 100);
		IntPoint iPoint11 = new IntPoint(200, 200);
		IntPoint iPoint12 = new IntPoint(100, 200);
		IntPoint iPoint13 = new IntPoint(150, 150);
		IntPoint iPoint14 = new IntPoint(100, 150);
		IntPoint iPoint15 = new IntPoint(0, 0);
		IntPoint iPoint16 = new IntPoint(200, 200);
		IntPoint iPoint17 = new IntPoint(175, 125);
		IntPoint iPoint18 = new IntPoint(-200, 200);
		
		//IntPoint iPoint19 = new IntPoint(Integer.MAX_VALUE - 20, 200);
		//IntPoint iPoint20 = new IntPoint(-70, 200);
		
		IntVector iDiff1 = iPoint1.minus(iPoint2);
		IntVector iDiff2 = iPoint9.minus(iPoint10);
		IntVector iDiff3 = iPoint9.minus(iPoint2);
		IntVector iDiff4 = iPoint1.minus(iPoint5);
		//IntVector iDiff5 = iPoint19.minus(iPoint20);

		IntPoint iAdd1 = iPoint1.plus(iVector1);
		IntPoint iAdd2 = iPoint2.plus(iVector2);
		IntPoint iAdd3 = iPoint3.plus(iVector3);
		IntPoint iAdd4 = iPoint4.plus(iVector4);




		assert iPoint1.getX() == 2;
		assert iPoint1.getY() == 0;
		assert iPoint2.getX() == -3;
		assert iPoint2.getY() == 4;
		assert iPoint1.equals(iPoint2) == false;
		assert iPoint1.equals(iPoint1Copy) == true;
		assert iPoint1.isOnLineSegment(iPoint1Copy, iPoint2) == false;
		assert iPoint1.isOnLineSegment(iPoint3, iPoint4) == true;
		assert iPoint2.isOnLineSegment(iPoint3, iPoint4) == false;
		assert iPoint7.isOnLineSegment(iPoint1, iPoint6) == true;
		assert IntPoint.lineSegmentsIntersect(iPoint1, iPoint3, iPoint2, iPoint4) == false;
		assert IntPoint.lineSegmentsIntersect(iPoint1, iPoint2, iPoint3, iPoint4) == false;
		assert IntPoint.lineSegmentsIntersect(iPoint1, iPoint5, iPoint4, iPoint6) == true;
		
		
		IntPoint[] iPointArray1 = {iPoint1, iPoint4, iPoint5, iPoint6};
		IntPoint[] iPointArray2 = {iPoint1, iPoint1, iPoint4, iPoint5, iPoint6};
		IntPoint[] iPointArray3 = {iPoint1, iPoint4, iPoint7, iPoint5, iPoint6};
		IntPoint[] iPointArray4 = {iPoint6, iPoint8, iPoint1, iPoint5};
		IntPoint[] iPointArray5 = {iPoint9, iPoint10, iPoint11, iPoint12};
		IntPoint[] iPointArray6 = {iPoint9, iPoint2};
		IntPoint[] iPointArray7 = {iPoint9, iPoint17, iPoint13, iPoint11, iPoint12};

		IntPoint[] iPointArrayRemove = PointArrays.remove(iPointArray1, 0);
		assert iPointArrayRemove[0] == iPoint4;
		assert iPointArrayRemove[1] == iPoint5;
		assert iPointArrayRemove[2] == iPoint6;
		IntPoint[] iPointArrayInsert = PointArrays.insert(iPointArray1, 2, iPoint2);
		assert iPointArrayInsert[0] == iPoint1;
		assert iPointArrayInsert[1] == iPoint4;
		assert iPointArrayInsert[2] == iPoint2;
		assert iPointArrayInsert[3] == iPoint5;
		assert iPointArrayInsert[4] == iPoint6;
		IntPoint[] iPointArrayUpdate = PointArrays.update(iPointArray1, 1, iPoint2);
		assert iPointArrayUpdate[0] == iPoint1;
		assert iPointArrayUpdate[1] == iPoint2;
		assert iPointArrayUpdate[2] == iPoint5;
		assert iPointArrayUpdate[3] == iPoint6;
		assert Arrays.equals(iPointArray1, PointArrays.copy(iPointArray1));
		assert Arrays.equals(iPointArray2, PointArrays.copy(iPointArray2));
		assert Arrays.equals(iPointArray3, PointArrays.copy(iPointArray3));
		assert Arrays.equals(iPointArray4, PointArrays.copy(iPointArray4));
		assert Arrays.equals(iPointArray5, PointArrays.copy(iPointArray5));
		

		String message1 = PointArrays.checkDefinesProperPolygon(iPointArray1);
		String message2 = PointArrays.checkDefinesProperPolygon(iPointArray2);
		String message3 = PointArrays.checkDefinesProperPolygon(iPointArray3);
		String message4 = PointArrays.checkDefinesProperPolygon(iPointArray4);
		String message5 = PointArrays.checkDefinesProperPolygon(iPointArray5);
		String message6 = PointArrays.checkDefinesProperPolygon(iPointArray6);

		assert message1 == null;
		assert message2 == "The given array of points does not define a proper polygon "
				+ "because at least two vertices coincide.";
		assert message3 == "The given array of points does not define a proper polygon "
				+ "because at least one vertex is on any edge.";
		assert message4 == "The given array of points does not define a proper polygon "
				+ "because at least two edges intersect.";
		assert message5 == null;
		assert message6 == "The given array of points does not define a proper polygon "
				+ "because the array contains two or less points.";

		

	    RoundedPolygon polygon1 = new RoundedPolygon();
	    polygon1.setVertices(iPointArray5);
	    polygon1.setRadius(10);
	    assert Arrays.equals(polygon1.getVertices(), iPointArray5);
	    assert polygon1.getRadius() == 10;
	    polygon1.remove(0);
	    assert polygon1.getVertices().length == 3;
	    assert polygon1.getVertices()[0] == iPoint10;
	    assert polygon1.getVertices()[1] == iPoint11;
	    assert polygon1.getVertices()[2] == iPoint12;
	    polygon1.insert(0, iPoint2);
	    assert polygon1.getVertices().length == 4;
	    assert polygon1.getVertices()[0] == iPoint2;
	    assert polygon1.getVertices()[1] == iPoint10;
	    assert polygon1.getVertices()[2] == iPoint11;
	    assert polygon1.getVertices()[3] == iPoint12;
	    polygon1.update(0, iPoint9);
	    assert polygon1.getVertices().length == 4;
	    assert polygon1.getVertices()[0] == iPoint9;
	    assert polygon1.getVertices()[1] == iPoint10;
	    assert polygon1.getVertices()[2] == iPoint11;
	    assert polygon1.getVertices()[3] == iPoint12;
	    assert polygon1.contains(iPoint13) == true;
	    assert polygon1.contains(iPoint15) == false;
	    assert polygon1.contains(iPoint14) == true;
	    assert polygon1.contains(iPoint16) == true;
	    assert polygon1.contains(iPoint17) == true;

	    RoundedPolygon polygon2 = new RoundedPolygon();
	    polygon2.setVertices(iPointArray7);
	    assert polygon2.contains(iPoint18) == false;

	    System.out.println(polygon1.getDrawingCommands());
	    
	}
}
