package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import drawit.FastRoundedPolygonContainsTestStrategy;
import drawit.IntPoint;
import drawit.PreciseRoundedPolygonContainsTestStrategy;
import drawit.RoundedPolygon;
import drawit.shapegroups1.Extent;

class Test_P3_4 {

	@Test
	void test() {
		IntPoint iPoint1 = new IntPoint(100, 100);
		IntPoint iPoint2 = new IntPoint(200, 100);
		IntPoint iPoint3 = new IntPoint(200, 200);
		IntPoint iPoint4 = new IntPoint(100, 200);
		IntPoint iPoint5 = new IntPoint(0, 150);
		IntPoint[] iPointArray1 = {iPoint1, iPoint2, iPoint3, iPoint4, iPoint5};
	    RoundedPolygon polygon1 = new RoundedPolygon();
	    polygon1.setVertices(iPointArray1);
	    polygon1.setRadius(10);
	    
	    
	    assert polygon1.getExtent().equals(Extent.ofLeftTopRightBottom(0, 100, 200, 200));
	    FastRoundedPolygonContainsTestStrategy frpcts = new FastRoundedPolygonContainsTestStrategy();
	    PreciseRoundedPolygonContainsTestStrategy prpcts = new PreciseRoundedPolygonContainsTestStrategy();

	    assert frpcts.contains(polygon1, new IntPoint(0, 175));
	    assert !prpcts.contains(polygon1, new IntPoint(0, 175));
	    
	    polygon1.remove(4);
	    assert polygon1.getExtent().equals(Extent.ofLeftTopRightBottom(100, 100, 200, 200));
	    
	    assert !frpcts.contains(polygon1, new IntPoint(0, 175));
	    assert !prpcts.contains(polygon1, new IntPoint(0, 175));
	    
	    polygon1.insert(2, new IntPoint(400, 170));
	    
	    assert polygon1.getExtent().equals(Extent.ofLeftTopRightBottom(100, 100, 400, 200));
	    assert frpcts.contains(polygon1, new IntPoint(375, 200));
	    assert !prpcts.contains(polygon1, new IntPoint(375, 200));

	    
	}

}
