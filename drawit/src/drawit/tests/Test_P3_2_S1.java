package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.shapegroups1.LeafShapeGroup;
import drawit.shapegroups1.ShapeGroup;
import drawit.shapes1.ControlPoint;
import drawit.shapes1.RoundedPolygonShape;

class Test_P3_2_S1 {

	@Test
	void test() {
		IntPoint iPoint1 = new IntPoint(100, 100);
		IntPoint iPoint2 = new IntPoint(200, 100);
		IntPoint iPoint3 = new IntPoint(200, 200);
		IntPoint iPoint4 = new IntPoint(100, 200);		
		IntPoint[] iPointArray1 = {iPoint1, iPoint2, iPoint3, iPoint4};
	    RoundedPolygon polygon1 = new RoundedPolygon();
	    polygon1.setVertices(iPointArray1);
	    polygon1.setRadius(10);
	    
		IntPoint iPoint5 = new IntPoint(200, 100);
		IntPoint iPoint6 = new IntPoint(50, 120);
		IntPoint iPoint7 = new IntPoint(40, 800);
		IntPoint iPoint8 = new IntPoint(200, 200);		
		IntPoint[] iPointArray2 = {iPoint5, iPoint6, iPoint7, iPoint8};
	    RoundedPolygon polygon2 = new RoundedPolygon();
	    polygon2.setVertices(iPointArray2);
	    polygon2.setRadius(10);
	    
	    ShapeGroup shape1 = new LeafShapeGroup(polygon1);
	    ShapeGroup shape2 = new LeafShapeGroup(polygon1);
	    
	    RoundedPolygonShape roundedPolygonShape1_1 = new RoundedPolygonShape(null, polygon1);
	    assert roundedPolygonShape1_1.getParent() == null;
	    assert roundedPolygonShape1_1.getPolygon() == polygon1;
	    assert roundedPolygonShape1_1.toGlobalCoordinates(iPoint1) == iPoint1;
	    assert roundedPolygonShape1_1.toShapeCoordinates(iPoint1) == iPoint1;
	    assert roundedPolygonShape1_1.contains(iPoint1);
	    assert !roundedPolygonShape1_1.contains(iPoint7);
	    ControlPoint[] controlPoints1_1 = roundedPolygonShape1_1.createControlPoints();
	    for (ControlPoint controlPoint : controlPoints1_1) {
	    	assert Arrays.stream(iPointArray1).anyMatch(p -> p.equals(roundedPolygonShape1_1.toGlobalCoordinates(controlPoint.getLocation())));
	    }

	    RoundedPolygonShape roundedPolygonShape2_1 = new RoundedPolygonShape(null, polygon2);

	    RoundedPolygonShape roundedPolygonShape1_2 = new RoundedPolygonShape(shape1, polygon1);
	    RoundedPolygonShape roundedPolygonShape2_2 = new RoundedPolygonShape(shape2, polygon2);


	    
	    

	    
	    
	}

}
