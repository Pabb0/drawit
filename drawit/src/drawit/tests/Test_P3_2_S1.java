package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.IntVector;
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
	    ShapeGroup shape2 = new LeafShapeGroup(polygon2);
	    
	    
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
	    assert roundedPolygonShape2_1.getParent() == null;
	    assert roundedPolygonShape2_1.getPolygon() == polygon2;
	    assert roundedPolygonShape2_1.toGlobalCoordinates(iPoint1) == iPoint1;
	    assert roundedPolygonShape2_1.toShapeCoordinates(iPoint1) == iPoint1;
	    assert roundedPolygonShape2_1.contains(iPoint5);
	    assert !roundedPolygonShape2_1.contains(iPoint1);
	    
	    ControlPoint[] controlPoints2_1 = roundedPolygonShape2_1.createControlPoints();
	    for (ControlPoint controlPoint : controlPoints2_1) {
	    	assert Arrays.stream(iPointArray2).anyMatch(p -> p.equals(roundedPolygonShape2_1.toGlobalCoordinates(controlPoint.getLocation())));
	    }
	    
	    
	    RoundedPolygonShape roundedPolygonShape1_2 = new RoundedPolygonShape(shape1, polygon1);
	    assert roundedPolygonShape1_2.getParent() == shape1;
	    assert roundedPolygonShape1_2.getPolygon() == polygon1;
	    assert roundedPolygonShape1_2.toGlobalCoordinates(iPoint1).equals(shape1.toGlobalCoordinates(iPoint1));
	    assert roundedPolygonShape1_2.toShapeCoordinates(iPoint1).equals(shape1.toInnerCoordinates(iPoint1));
	    assert roundedPolygonShape1_2.contains(shape1.toInnerCoordinates(iPoint1));
	    
	    ControlPoint[] controlPoints1_2 = roundedPolygonShape1_2.createControlPoints();
	    for (ControlPoint controlPoint : controlPoints1_2) {
	    	assert Arrays.stream(iPointArray1).anyMatch(p -> shape1.toInnerCoordinates(p).equals(roundedPolygonShape1_2.toGlobalCoordinates(controlPoint.getLocation())));
	    }
	    
	    
	    RoundedPolygonShape roundedPolygonShape2_2 = new RoundedPolygonShape(shape2, polygon2);
	    assert roundedPolygonShape2_2.getParent() == shape2;
	    assert roundedPolygonShape2_2.getPolygon() == polygon2;
	    assert roundedPolygonShape2_2.toGlobalCoordinates(iPoint5).equals(shape2.toGlobalCoordinates(iPoint5));
	    assert roundedPolygonShape2_2.toShapeCoordinates(iPoint5).equals(shape2.toInnerCoordinates(iPoint5));
	    assert roundedPolygonShape2_2.toGlobalCoordinates(iPoint1).equals(shape2.toGlobalCoordinates(iPoint1));
	    assert roundedPolygonShape2_2.toShapeCoordinates(iPoint1).equals(shape2.toInnerCoordinates(iPoint1));
	    assert roundedPolygonShape2_2.contains(shape2.toInnerCoordinates(iPoint5));
	    
	    ControlPoint[] controlPoints2_2 = roundedPolygonShape2_2.createControlPoints();
	    for (ControlPoint controlPoint : controlPoints2_2) {
	    	assert Arrays.stream(iPointArray2).anyMatch(p -> shape2.toInnerCoordinates(p).equals(roundedPolygonShape2_2.toGlobalCoordinates(controlPoint.getLocation())));
	    }
	    
	    
		IntPoint[] iPointArray1Updated = {iPoint1.plus(new IntVector(1,1)), iPoint2, iPoint3, iPoint4};
	    polygon1.setVertices(iPointArray1Updated);
	    for (ControlPoint controlPoint : controlPoints1_1) {
	    	assert Arrays.stream(iPointArray1).anyMatch(p -> shape1.toInnerCoordinates(p).equals(roundedPolygonShape1_1.toGlobalCoordinates(controlPoint.getLocation())));
	    }
	    

	    
	    

	    
	    
	}

}
