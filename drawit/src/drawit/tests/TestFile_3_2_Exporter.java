package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.shapegroups1.Extent;
import drawit.shapegroups1.LeafShapeGroup;
import drawit.shapegroups1.NonleafShapeGroup;
import drawit.shapegroups1.ShapeGroup;
import drawit.shapegroups1.exporter.ShapeGroupExporter;

class TestFile_3_2_Exporter {

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
		IntPoint iPoint6 = new IntPoint(150, 120);
		IntPoint iPoint7 = new IntPoint(40, 800);
		IntPoint iPoint8 = new IntPoint(120, 100);		
		IntPoint[] iPointArray2 = {iPoint5, iPoint6, iPoint7, iPoint8};
	    RoundedPolygon polygon2 = new RoundedPolygon();
	    polygon2.setVertices(iPointArray2);
	    polygon2.setRadius(10);
	    
		IntPoint[] iPointArray3 = {iPoint1, iPoint2, iPoint6, iPoint7};
	    RoundedPolygon polygon3 = new RoundedPolygon();
	    polygon3.setVertices(iPointArray3);
	    polygon3.setRadius(10);
	    
	    
	    ShapeGroup shape1 = new LeafShapeGroup(polygon1);
	    ShapeGroup shape2 = new LeafShapeGroup(polygon2);
	    ShapeGroup shape3 = new LeafShapeGroup(polygon3);

	    ShapeGroup[] shape1And2 = {shape1, shape2};
	    ShapeGroup shape4 = new NonleafShapeGroup(shape1And2);
	    ShapeGroup[] shape1And2And3 = {shape3, shape4};
	    ShapeGroup shape5 = new NonleafShapeGroup(shape1And2And3);
	    
	    
	    Object test1 = ShapeGroupExporter.toPlainData(shape4);
	    System.out.println(test1);
	    
	    
	    
	    
	    RoundedPolygon p4 = new RoundedPolygon();
	    IntPoint[] po4 = {new IntPoint(30,40), new IntPoint(60,190), new IntPoint(90, 150)};
	    p4.setVertices(po4);
	    p4.setRadius(5);
	    
	    RoundedPolygon p5 = new RoundedPolygon();
	    IntPoint[] po5 = {new IntPoint(35,45), new IntPoint(95,95), new IntPoint(50, 195)};
	    p5.setVertices(po5);
	    p5.setRadius(7);
	    
	    
	    ShapeGroup s4 = new LeafShapeGroup(p4);
	    ShapeGroup s5 = new LeafShapeGroup(p5);
	    
	    s4.setExtent(Extent.ofLeftTopRightBottom(10, 20, 15, 132));
	    s5.setExtent(Extent.ofLeftTopRightBottom(15, 22, 100, 200));

	    
	    ShapeGroup[] s45 = {s4, s5};
	    
	    ShapeGroup s6 = new NonleafShapeGroup(s45);
	    
	    s4.setExtent(Extent.ofLeftTopRightBottom(40, 50, 60, 70));
	    s5.setExtent(Extent.ofLeftTopRightBottom(45, 55, 65, 75));
	    s6.setExtent(Extent.ofLeftTopRightBottom(5, 7, 99, 88));
	    
	    ShapeGroup[] shape56 = {shape5, s6};
	    
	    ShapeGroup s6_45 = new NonleafShapeGroup(shape56);
	    System.out.println(ShapeGroupExporter.toPlainData(s6_45));
	    

	    
	    
	    

	    			
	    
	}

}
