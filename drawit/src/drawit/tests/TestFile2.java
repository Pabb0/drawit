package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.shapegroups2.Extent;
import drawit.shapegroups2.LeafShapeGroup;
import drawit.shapegroups2.NonleafShapeGroup;
import drawit.shapegroups2.ShapeGroup;

class TestFile2 {

	@Test
	void test() {
		System.out.println("DEEL 3 - PART 1 - DRAWIT2");
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
	    
	    assert shape1 instanceof LeafShapeGroup;
	    assert shape2 instanceof LeafShapeGroup;
	    assert shape3 instanceof LeafShapeGroup;
	    assert shape4 instanceof NonleafShapeGroup;
	    assert shape5 instanceof NonleafShapeGroup;
	    
	    assert shape1.getAllShapes().size() == 1;
	    assert shape2.getAllShapes().size() == 1;
	    assert shape3.getAllShapes().size() == 1;
	    assert shape4.getAllShapes().size() == 2;
	    assert shape5.getAllShapes().size() == 3;
	    
	    assert shape1.getAllShapes().contains(((LeafShapeGroup) shape1).getShape());
	    assert shape2.getAllShapes().contains(((LeafShapeGroup) shape2).getShape());
	    assert shape3.getAllShapes().contains(((LeafShapeGroup) shape3).getShape());

	    assert shape4.getAllShapes().contains(((LeafShapeGroup) shape1).getShape());
	    assert shape4.getAllShapes().contains(((LeafShapeGroup) shape2).getShape());

	    assert shape5.getAllShapes().contains(((LeafShapeGroup) shape1).getShape());
	    assert shape5.getAllShapes().contains(((LeafShapeGroup) shape2).getShape());
	    assert shape5.getAllShapes().contains(((LeafShapeGroup) shape3).getShape());
	    
	    System.out.println("Begin Shape1 commands");
	    System.out.println(shape1.getDrawingCommands());
	    System.out.println("End Shape1 commands \n");

	    System.out.println("Begin Shape2 commands");
	    System.out.println(shape2.getDrawingCommands());
	    System.out.println("End Shape2 commands \n");

	    System.out.println("Begin Shape3 commands");
	    System.out.println(shape3.getDrawingCommands());
	    System.out.println("End Shape3 commands \n");

	    System.out.println("Begin Shape4 commands");
	    System.out.println(shape4.getDrawingCommands());
	    System.out.println("End Shape4 commands \n");

	    System.out.println("Begin Shape5 commands");
	    System.out.println(shape5.getDrawingCommands());
	    System.out.println("End Shape5 commands \n");
	    
	    assert shape1.getParentGroup() == shape4;
	    assert shape2.getParentGroup() == shape4;
	    assert shape3.getParentGroup() == shape5;
	    assert shape4.getParentGroup() == shape5;
	    assert shape5.getParentGroup() == null;

	    shape1.bringToFront();
	    assert ((NonleafShapeGroup)shape4).getSubgroup(0).equals(shape1);
	    shape2.bringToFront();
	    assert ((NonleafShapeGroup)shape4).getSubgroup(0).equals(shape2);
	    shape3.bringToFront();
	    assert ((NonleafShapeGroup)shape5).getSubgroup(0).equals(shape3);
	    shape4.bringToFront();
	    assert ((NonleafShapeGroup)shape5).getSubgroup(0).equals(shape4);

	    shape1.setExtent(Extent.ofLeftTopRightBottom(0, 0, 100, 100));
	    assert shape1.getExtent().getLeft() == 0;
	    assert shape1.getExtent().getTop() == 0;
	    assert shape1.getExtent().getRight() == 100;
	    assert shape1.getExtent().getBottom() == 100;
	    assert shape1.getOriginalExtent().getLeft() == 100;
	    assert shape1.getOriginalExtent().getTop() == 100;
	    assert shape1.getOriginalExtent().getRight() == 200;
	    assert shape1.getOriginalExtent().getBottom() == 200;
	    
	}

}
