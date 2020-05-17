package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.shapegroups1.*;



class Test_P3_1_S1 {

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
	    
	    // Testen van de drawingcommands gebeurt ook/vooral via de GUI. 
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
	    
	    
	    
	    IntPoint iPoint9 = new IntPoint(100, 100);
		IntPoint iPoint10 = new IntPoint(200, 100);
		IntPoint iPoint11 = new IntPoint(200, 200);
		IntPoint iPoint12 = new IntPoint(100, 200);
		IntPoint iPoint13 = new IntPoint(150, 150);
		IntPoint iPoint14 = new IntPoint(175, 125);
		
		IntPoint[] iPointArray4 = {iPoint9, iPoint10, iPoint11, iPoint12};
		IntPoint[] iPointArray5 = {iPoint9, iPoint14, iPoint13, iPoint11, iPoint12};

	    RoundedPolygon polygon4 = new RoundedPolygon();
	    polygon4.setVertices(iPointArray4);
	    polygon4.setRadius(25);
	    
	    RoundedPolygon polygon5 = new RoundedPolygon();
	    polygon5.setVertices(iPointArray5);
	    polygon5.setRadius(10);
	    
	    
	    ShapeGroup shapeGroup1 = new LeafShapeGroup(polygon4);
	    shapeGroup1.setExtent(Extent.ofLeftTopRightBottom(0, 0, 100, 100));
	    assert shapeGroup1.getExtent().getTop() == 0;
	    assert shapeGroup1.getExtent().getLeft() == 0;
	    assert shapeGroup1.getExtent().getRight() == 100;
	    assert shapeGroup1.getExtent().getBottom() == 100;
	    assert shapeGroup1.getOriginalExtent().getTop() == 100;
	    assert shapeGroup1.getOriginalExtent().getLeft() == 100;
	    assert shapeGroup1.getOriginalExtent().getRight() == 200;
	    assert shapeGroup1.getOriginalExtent().getBottom() == 200;
	    shapeGroup1.setExtent(Extent.ofLeftTopRightBottom(100, 100, 200, 200));

	    ShapeGroup shapeGroup2 = new LeafShapeGroup(polygon5);
	    ShapeGroup[] shapeGroup1And2 = {shapeGroup1, shapeGroup2};
	    ShapeGroup shapeGroup3 = new NonleafShapeGroup(shapeGroup1And2);
	    
	    assert shapeGroup1 instanceof LeafShapeGroup;
	    assert shapeGroup2 instanceof LeafShapeGroup;
	    assert shapeGroup3 instanceof NonleafShapeGroup;

	    assert ((NonleafShapeGroup) shapeGroup3).getSubgroupCount() == 2;
	    assertEquals(Arrays.asList(shapeGroup1And2), ((NonleafShapeGroup) shapeGroup3).getSubgroups());
	    shapeGroup1.bringToFront();
	    assert ((NonleafShapeGroup) shapeGroup3).getSubgroup(0) == shapeGroup1;
	    assert ((NonleafShapeGroup) shapeGroup3).getSubgroups().get(1) == shapeGroup2;
	    shapeGroup2.bringToFront();
	    assert ((NonleafShapeGroup) shapeGroup3).getSubgroups().get(0) == shapeGroup2;
	    assert ((NonleafShapeGroup) shapeGroup3).getSubgroups().get(1) == shapeGroup1;
	    shapeGroup2.sendToBack();
	    assert ((NonleafShapeGroup) shapeGroup3).getSubgroups().get(0) == shapeGroup1;
	    assert ((NonleafShapeGroup) shapeGroup3).getSubgroup(1) == shapeGroup2;
	    shapeGroup1.sendToBack();
	    assert ((NonleafShapeGroup) shapeGroup3).getSubgroups().get(0) == shapeGroup2;
	    assert ((NonleafShapeGroup) shapeGroup3).getSubgroups().get(1) == shapeGroup1;
	    
	    assert shapeGroup1.getParentGroup() == shapeGroup3;
	    assert shapeGroup2.getParentGroup() == shapeGroup3;
	    assert shapeGroup3.getParentGroup() == null;
	    assert ((LeafShapeGroup) shapeGroup1).getShape() == polygon4;
	    assert ((LeafShapeGroup) shapeGroup2).getShape() == polygon5;
	    assert shapeGroup1.getAllShapes().size() == 1;
	    assert shapeGroup1.getAllShapes().contains(polygon4);
	    assert shapeGroup2.getAllShapes().size() == 1;
	    assert shapeGroup2.getAllShapes().contains(polygon5);	    
	    assert shapeGroup3.getAllShapes().size() == 2;
	    assert shapeGroup3.getAllShapes().contains(polygon4);	    
	    assert shapeGroup3.getAllShapes().contains(polygon5);	    
	    
	    shapeGroup3.setExtent(Extent.ofLeftTopRightBottom(50, 50, 200, 200));
	    assert shapeGroup3.getExtent().getTopLeft().equals(new IntPoint(50, 50)) && shapeGroup3.getExtent().getBottomRight().equals(new IntPoint(200, 200));
	    
	    
	    RoundedPolygon triangle = new RoundedPolygon();
	    triangle.setVertices(new IntPoint[] {new IntPoint(10, 10), new IntPoint(30, 10), new IntPoint(20, 20)});

	    ShapeGroup leaf = new LeafShapeGroup(triangle);
	    assert leaf instanceof LeafShapeGroup;
	    assert leaf.getExtent().getTopLeft().equals(new IntPoint(10, 10)) && leaf.getExtent().getBottomRight().equals(new IntPoint(30, 20));
	    leaf.setExtent(Extent.ofLeftTopWidthHeight(0, 0, 20, 10));
	    
	    RoundedPolygon triangle2 = new RoundedPolygon();
	    triangle2.setVertices(new IntPoint[] {new IntPoint(10, 10), new IntPoint(20, 10), new IntPoint(20, 20)});

	    ShapeGroup leaf2 = new LeafShapeGroup(triangle2);
	    assert leaf2 instanceof LeafShapeGroup;
	    assert leaf2.getExtent().getTopLeft().equals(new IntPoint(10, 10)) && leaf2.getExtent().getBottomRight().equals(new IntPoint(20, 20));
	    leaf2.setExtent(Extent.ofLeftTopWidthHeight(0, 0, 20, 10));
	    

	    ShapeGroup nonLeaf = new NonleafShapeGroup(new ShapeGroup[] {leaf, leaf2});
	    assert nonLeaf instanceof NonleafShapeGroup;
	    assert nonLeaf.getExtent().getTopLeft().equals(new IntPoint(0, 0)) && nonLeaf.getExtent().getBottomRight().equals(new IntPoint(20, 10));
	    nonLeaf.setExtent(Extent.ofLeftTopWidthHeight(0, 0, 10, 5));
	    
	    leaf.setExtent(Extent.ofLeftTopWidthHeight(1000, 2000, 20, 10));
	    
	    
	    assert leaf.toGlobalCoordinates(new IntPoint(10,10)).getX() == 500;
	    assert leaf.toGlobalCoordinates(new IntPoint(10,10)).getY() == 1000;

	    assert leaf.toInnerCoordinates(new IntPoint(500,1000)).getX() == 10;
	    assert leaf.toInnerCoordinates(new IntPoint(500,1000)).getY() == 10;
	    leaf.setExtent(Extent.ofLeftTopWidthHeight(2000, 4000, 40, 20));
	    
	    assert leaf.toGlobalCoordinates(new IntPoint(20, 15)).getX() == 1010;


	    RoundedPolygon fig = new RoundedPolygon();
	    fig.setVertices(new IntPoint[] {new IntPoint(0, 0), new IntPoint(20, 10), new IntPoint(20, 30)});

	    ShapeGroup kind = new LeafShapeGroup(fig);
	    assert kind instanceof LeafShapeGroup;
	    assert kind.getExtent().getTopLeft().equals(new IntPoint(0, 0)) && kind.getExtent().getBottomRight().equals(new IntPoint(20, 30));
	    kind.setExtent(Extent.ofLeftTopRightBottom(50, 50, 100, 100));
	    assert kind.getOriginalExtent().getTopLeft().equals(new IntPoint(0, 0)) && kind.getOriginalExtent().getBottomRight().equals(new IntPoint(20, 30));

	    RoundedPolygon fig2 = new RoundedPolygon();
	    fig2.setVertices(new IntPoint[] {new IntPoint(5, 5), new IntPoint(10, 10), new IntPoint(5, 10)});
	    
	    ShapeGroup kind2 = new LeafShapeGroup(fig2);
	    assert kind2 instanceof LeafShapeGroup;
	    assert kind2.getExtent().getTopLeft().equals(new IntPoint(5, 5)) && kind2.getExtent().getBottomRight().equals(new IntPoint(10, 10));
	    kind2.setExtent(Extent.ofLeftTopRightBottom(50, 50, 100, 100));
	    assert kind2.getOriginalExtent().getTopLeft().equals(new IntPoint(5, 5)) && kind2.getOriginalExtent().getBottomRight().equals(new IntPoint(10, 10));

	    
	    ShapeGroup mama = new NonleafShapeGroup(new ShapeGroup[] {kind, kind2});
	    assert mama instanceof NonleafShapeGroup;
	    assert kind instanceof LeafShapeGroup;
	    assert kind2 instanceof LeafShapeGroup;
	    assert mama.getExtent().getTopLeft().equals(new IntPoint(50, 50)) && mama.getExtent().getBottomRight().equals(new IntPoint(100, 100));
	    mama.setExtent(Extent.ofLeftTopRightBottom(30, 30, 80, 100));
	    assert mama.getOriginalExtent().getTopLeft().equals(new IntPoint(50, 50)) && mama.getOriginalExtent().getBottomRight().equals(new IntPoint(100, 100));
	    kind.setExtent(Extent.ofLeftTopRightBottom(100, 50, 150, 150));

	    assert kind.getOriginalExtent().getTopLeft().equals(new IntPoint(0, 0)) && kind.getOriginalExtent().getBottomRight().equals(new IntPoint(20, 30));
	    assert kind.getExtent().getTopLeft().equals(new IntPoint(100, 50)) && kind.getExtent().getBottomRight().equals(new IntPoint(150, 150));

	    assert kind.toGlobalCoordinates(new IntPoint(10, 15)).equals(new IntPoint(105, 100)); 
	    assert kind.toInnerCoordinates(new IntPoint(105, 100)).equals(new IntPoint(10, 15)); 

	     

	}

}
