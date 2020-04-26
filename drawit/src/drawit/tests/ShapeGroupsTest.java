//package drawit.tests;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import java.util.Arrays;
//
//import org.junit.jupiter.api.Test;
//
//import drawit.IntPoint;
//import drawit.RoundedPolygon;
//import drawit.shapegroups1.Extent;
//import drawit.shapegroups1.ShapeGroup;
//
//class ShapeGroupsTest {
//
//	@Test
//	void test() {
//	    //
//	    // shapegroups1 tests
//		IntPoint iPoint1 = new IntPoint(100, 100);
//		IntPoint iPoint2 = new IntPoint(200, 100);
//		IntPoint iPoint3 = new IntPoint(200, 200);
//		IntPoint iPoint4 = new IntPoint(100, 200);
//		IntPoint iPoint5 = new IntPoint(150, 150);
//		IntPoint iPoint6 = new IntPoint(175, 125);
//		
//		IntPoint[] iPointArray5 = {iPoint1, iPoint2, iPoint3, iPoint4};
//		IntPoint[] iPointArray7 = {iPoint1, iPoint6, iPoint5, iPoint3, iPoint4};
//
//	    RoundedPolygon polygon1 = new RoundedPolygon();
//	    polygon1.setVertices(iPointArray5);
//	    polygon1.setRadius(10);
//	    RoundedPolygon polygon2 = new RoundedPolygon();
//	    polygon2.setVertices(iPointArray7);
//	    polygon2.setRadius(10);
//	    
//	    Extent ext11 = Extent.ofLeftTopRightBottom(10, 20, 30, 50);
//	    assert ext11.getLeft() == 10;
//	    assert ext11.getTop() == 20;
//	    assert ext11.getRight() == 30;
//	    assert ext11.getBottom() == 50;
//	    assert ext11.getWidth() == 20;
//	    assert ext11.getHeight() == 30;
//	    IntPoint ext11TopLeft = ext11.getTopLeft();
//	    assert ext11TopLeft.equals(new IntPoint(10, 20));
//	    IntPoint ext11BottomRight = ext11.getBottomRight();
//	    assert ext11BottomRight.equals(new IntPoint(30, 50));
//	    assert ext11.contains(new IntPoint(20, 30));
//	    assert !(ext11.contains(new IntPoint(10, 10)));
//	    assert ext11.contains(new IntPoint(10, 30));
//	    assert ext11.contains(ext11TopLeft);
//	    assert ext11.contains(ext11BottomRight);
//
//	    Extent ext11WithRight = ext11.withRight(100);
//	    assert ext11WithRight.getLeft() == 10;
//	    assert ext11WithRight.getTop() == 20;
//	    assert ext11WithRight.getRight() == 100;
//	    assert ext11WithRight.getBottom() == 50;
//	    assert ext11WithRight.getWidth() == 90;
//	    assert ext11WithRight.getHeight() == 30;
//	    
//	    Extent ext12 = Extent.ofLeftTopWidthHeight(10, 20, 20, 30);
//	    assert ext12.getLeft() == 10;
//	    assert ext12.getTop() == 20;
//	    assert ext12.getRight() == 30;
//	    assert ext12.getBottom() == 50;
//	    assert ext12.getWidth() == 20;
//	    assert ext12.getHeight() == 30;
//	    IntPoint ext12TopLeft = ext12.getTopLeft();
//	    assert ext12TopLeft.equals(new IntPoint(10, 20));
//	    IntPoint ext12BottomRight = ext12.getBottomRight();
//	    assert ext12BottomRight.equals(new IntPoint(30, 50));
//	    assert ext12.contains(new IntPoint(20, 30));
//	    assert !(ext12.contains(new IntPoint(10, 10)));
//	    assert ext12.contains(new IntPoint(10, 30));
//	    assert ext12.contains(ext12TopLeft);
//	    assert ext12.contains(ext12BottomRight);
//
//	    Extent ext12WithRight = ext12.withRight(100);
//	    assert ext12WithRight.getLeft() == 10;
//	    assert ext12WithRight.getTop() == 20;
//	    assert ext12WithRight.getRight() == 100;
//	    assert ext12WithRight.getBottom() == 50;
//	    assert ext12WithRight.getWidth() == 90;
//	    assert ext12WithRight.getHeight() == 30;
//	    
//	    
//	    ShapeGroup shapeGroup1 = new ShapeGroup(polygon1);
//	    assert shapeGroup1.getSubgroupCount() == 0;
//	    assert shapeGroup1.getSubgroups() == null;
//	    shapeGroup1.setExtent(Extent.ofLeftTopRightBottom(0, 0, 100, 100));
//	    assert shapeGroup1.getExtent().getTop() == 0;
//	    assert shapeGroup1.getExtent().getLeft() == 0;
//	    assert shapeGroup1.getExtent().getRight() == 100;
//	    assert shapeGroup1.getExtent().getBottom() == 100;
//	    assert shapeGroup1.getOriginalExtent().getTop() == 100;
//	    assert shapeGroup1.getOriginalExtent().getLeft() == 100;
//	    assert shapeGroup1.getOriginalExtent().getRight() == 200;
//	    assert shapeGroup1.getOriginalExtent().getBottom() == 200;
//	    shapeGroup1.setExtent(Extent.ofLeftTopRightBottom(100, 100, 200, 200));
//
//
//	    
//	    ShapeGroup shapeGroup2 = new ShapeGroup(polygon2);
//	    assert shapeGroup2.getSubgroupCount() == 0;
//	    assert shapeGroup2.getSubgroups() == null;
//	    
//	    ShapeGroup[] shapeGroup1And2 = {shapeGroup1, shapeGroup2};
//	    
//	    ShapeGroup shapeGroup3 = new ShapeGroup(shapeGroup1And2);
//	    assert shapeGroup3.getSubgroupCount() == 2;
//	    assertEquals(Arrays.asList(shapeGroup1And2), shapeGroup3.getSubgroups());
//	    shapeGroup1.bringToFront();
//	    assert shapeGroup3.getSubgroup(0) == shapeGroup1;
//	    assert shapeGroup3.getSubgroups().get(1) == shapeGroup2;
//	    shapeGroup2.bringToFront();
//	    assert shapeGroup3.getSubgroups().get(0) == shapeGroup2;
//	    assert shapeGroup3.getSubgroups().get(1) == shapeGroup1;
//	    shapeGroup2.sendToBack();
//	    assert shapeGroup3.getSubgroups().get(0) == shapeGroup1;
//	    assert shapeGroup3.getSubgroup(1) == shapeGroup2;
//	    shapeGroup1.sendToBack();
//	    assert shapeGroup3.getSubgroups().get(0) == shapeGroup2;
//	    assert shapeGroup3.getSubgroups().get(1) == shapeGroup1;
//	    
//	    assert shapeGroup1.getParentGroup() == shapeGroup3;
//	    assert shapeGroup2.getParentGroup() == shapeGroup3;
//	    assert shapeGroup3.getParentGroup() == null;
//	    assert shapeGroup1.getShape() == polygon1;
//	    assert shapeGroup2.getShape() == polygon2;
//	    
//	    assert shapeGroup3.getShape() == null;
//
//
//	    shapeGroup3.setExtent(Extent.ofLeftTopRightBottom(50, 50, 200, 200));
//	    assert shapeGroup3.getExtent().getTopLeft().equals(new IntPoint(50, 50)) && shapeGroup3.getExtent().getBottomRight().equals(new IntPoint(200, 200));
//	    
//	    
//	    RoundedPolygon triangle = new RoundedPolygon();
//	    triangle.setVertices(new IntPoint[] {new IntPoint(10, 10), new IntPoint(30, 10), new IntPoint(20, 20)});
//
//	    ShapeGroup leaf = new ShapeGroup(triangle);
//	    assert leaf.getExtent().getTopLeft().equals(new IntPoint(10, 10)) && leaf.getExtent().getBottomRight().equals(new IntPoint(30, 20));
//	    leaf.setExtent(Extent.ofLeftTopWidthHeight(0, 0, 20, 10));
//
//	    ShapeGroup nonLeaf = new ShapeGroup(new ShapeGroup[] {leaf});
//	    assert nonLeaf.getExtent().getTopLeft().equals(new IntPoint(0, 0)) && nonLeaf.getExtent().getBottomRight().equals(new IntPoint(20, 10));
//	    nonLeaf.setExtent(Extent.ofLeftTopWidthHeight(0, 0, 10, 5));
//	    
//	    leaf.setExtent(Extent.ofLeftTopWidthHeight(1000, 2000, 20, 10));
//	    
//	    
//	    assert leaf.toGlobalCoordinates(new IntPoint(10,10)).getX() == 500;
//	    assert leaf.toGlobalCoordinates(new IntPoint(10,10)).getY() == 1000;
//
//	    assert leaf.toInnerCoordinates(new IntPoint(500,1000)).getX() == 10;
//	    assert leaf.toInnerCoordinates(new IntPoint(500,1000)).getY() == 10;
//	    leaf.setExtent(Extent.ofLeftTopWidthHeight(2000, 4000, 40, 20));
//	    
//	    assert leaf.toGlobalCoordinates(new IntPoint(20, 15)).getX() == 1010;
//
//
//	    RoundedPolygon fig = new RoundedPolygon();
//	    fig.setVertices(new IntPoint[] {new IntPoint(0, 0), new IntPoint(20, 10), new IntPoint(20, 30)});
//
//	    ShapeGroup kind = new ShapeGroup(fig);
//	    assert kind.getExtent().getRight() == 20;
//	    assert kind.getExtent().getTopLeft().equals(new IntPoint(0, 0)) && kind.getExtent().getBottomRight().equals(new IntPoint(20, 30));
//	    kind.setExtent(Extent.ofLeftTopRightBottom(50, 50, 100, 100));
//	    assert kind.getOriginalExtent().getTopLeft().equals(new IntPoint(0, 0)) && kind.getOriginalExtent().getBottomRight().equals(new IntPoint(20, 30));
//
//	    ShapeGroup mama = new ShapeGroup(new ShapeGroup[] {kind});
//	    assert mama.getExtent().getTopLeft().equals(new IntPoint(50, 50)) && mama.getExtent().getBottomRight().equals(new IntPoint(100, 100));
//	    mama.setExtent(Extent.ofLeftTopRightBottom(30, 30, 80, 100));
//	    assert mama.getOriginalExtent().getTopLeft().equals(new IntPoint(50, 50)) && mama.getOriginalExtent().getBottomRight().equals(new IntPoint(100, 100));
//	    kind.setExtent(Extent.ofLeftTopRightBottom(100, 50, 150, 150));
//
//	    assert kind.getOriginalExtent().getTopLeft().equals(new IntPoint(0, 0)) && kind.getOriginalExtent().getBottomRight().equals(new IntPoint(20, 30));
//	    assert kind.getExtent().getTopLeft().equals(new IntPoint(100, 50)) && kind.getExtent().getBottomRight().equals(new IntPoint(150, 150));
//
//	    assert kind.toGlobalCoordinates(new IntPoint(10, 15)).equals(new IntPoint(105, 100)); 
//	    assert kind.toInnerCoordinates(new IntPoint(105, 100)).equals(new IntPoint(10, 15)); 
//
//	    
//	      
//	}
//	@Test
//	void test2() {
//	    //
//	    // shapegroups2 tests
//		IntPoint iPoint1 = new IntPoint(100, 100);
//		IntPoint iPoint2 = new IntPoint(200, 100);
//		IntPoint iPoint3 = new IntPoint(200, 200);
//		IntPoint iPoint4 = new IntPoint(100, 200);
//		IntPoint iPoint5 = new IntPoint(150, 150);
//		IntPoint iPoint6 = new IntPoint(175, 125);
//		
//		IntPoint[] iPointArray5 = {iPoint1, iPoint2, iPoint3, iPoint4};
//		IntPoint[] iPointArray7 = {iPoint1, iPoint6, iPoint5, iPoint3, iPoint4};
//
//	    RoundedPolygon polygon1 = new RoundedPolygon();
//	    polygon1.setVertices(iPointArray5);
//	    polygon1.setRadius(10);
//	    RoundedPolygon polygon2 = new RoundedPolygon();
//	    polygon2.setVertices(iPointArray7);
//	    polygon2.setRadius(10);
//	    
//	    drawit.shapegroups2.Extent ext11 = drawit.shapegroups2.Extent.ofLeftTopRightBottom(10, 20, 30, 50);
//	    assert ext11.getLeft() == 10;
//	    assert ext11.getTop() == 20;
//	    assert ext11.getRight() == 30;
//	    assert ext11.getBottom() == 50;
//	    assert ext11.getWidth() == 20;
//	    assert ext11.getHeight() == 30;
//	    IntPoint ext11TopLeft = ext11.getTopLeft();
//	    assert ext11TopLeft.equals(new IntPoint(10, 20));
//	    IntPoint ext11BottomRight = ext11.getBottomRight();
//	    assert ext11BottomRight.equals(new IntPoint(30, 50));
//	    assert ext11.contains(new IntPoint(20, 30));
//	    assert !(ext11.contains(new IntPoint(10, 10)));
//	    assert ext11.contains(new IntPoint(10, 30));
//	    assert ext11.contains(ext11TopLeft);
//	    assert ext11.contains(ext11BottomRight);
//
//	    drawit.shapegroups2.Extent ext11WithRight = ext11.withRight(100);
//	    assert ext11WithRight.getLeft() == 10;
//	    assert ext11WithRight.getTop() == 20;
//	    assert ext11WithRight.getRight() == 100;
//	    assert ext11WithRight.getBottom() == 50;
//	    assert ext11WithRight.getWidth() == 90;
//	    assert ext11WithRight.getHeight() == 30;
//	    
//	    drawit.shapegroups2.Extent ext12 = drawit.shapegroups2.Extent.ofLeftTopWidthHeight(10, 20, 20, 30);
//	    assert ext12.getLeft() == 10;
//	    assert ext12.getTop() == 20;
//	    assert ext12.getRight() == 30;
//	    assert ext12.getBottom() == 50;
//	    assert ext12.getWidth() == 20;
//	    assert ext12.getHeight() == 30;
//	    IntPoint ext12TopLeft = ext12.getTopLeft();
//	    assert ext12TopLeft.equals(new IntPoint(10, 20));
//	    IntPoint ext12BottomRight = ext12.getBottomRight();
//	    assert ext12BottomRight.equals(new IntPoint(30, 50));
//	    assert ext12.contains(new IntPoint(20, 30));
//	    assert !(ext12.contains(new IntPoint(10, 10)));
//	    assert ext12.contains(new IntPoint(10, 30));
//	    assert ext12.contains(ext12TopLeft);
//	    assert ext12.contains(ext12BottomRight);
//
//	    drawit.shapegroups2.Extent ext12WithRight = ext12.withRight(100);
//	    assert ext12WithRight.getLeft() == 10;
//	    assert ext12WithRight.getTop() == 20;
//	    assert ext12WithRight.getRight() == 100;
//	    assert ext12WithRight.getBottom() == 50;
//	    assert ext12WithRight.getWidth() == 90;
//	    assert ext12WithRight.getHeight() == 30;
//	    
//	    
//	    
//	    drawit.shapegroups2.ShapeGroup shapeGroup1 = new drawit.shapegroups2.ShapeGroup(polygon1);
//	    assert shapeGroup1.getSubgroupCount() == 0;
//	    assert shapeGroup1.getSubgroups() == null;
//	    shapeGroup1.setExtent(drawit.shapegroups2.Extent.ofLeftTopRightBottom(0, 0, 100, 100));
//	    assert shapeGroup1.getExtent().getTop() == 0;
//	    assert shapeGroup1.getExtent().getLeft() == 0;
//	    assert shapeGroup1.getExtent().getRight() == 100;
//	    assert shapeGroup1.getExtent().getBottom() == 100;
//	    assert shapeGroup1.getOriginalExtent().getTop() == 100;
//	    assert shapeGroup1.getOriginalExtent().getLeft() == 100;
//	    assert shapeGroup1.getOriginalExtent().getRight() == 200;
//	    assert shapeGroup1.getOriginalExtent().getBottom() == 200;
//	    shapeGroup1.setExtent(drawit.shapegroups2.Extent.ofLeftTopRightBottom(100, 100, 200, 200));
//
//
//	    
//	    drawit.shapegroups2.ShapeGroup shapeGroup2 = new drawit.shapegroups2.ShapeGroup(polygon2);
//	    assert shapeGroup2.getSubgroupCount() == 0;
//	    assert shapeGroup2.getSubgroups() == null;
//	    
//	    drawit.shapegroups2.ShapeGroup[] shapeGroup1And2 = {shapeGroup1, shapeGroup2};
//	    
//	    drawit.shapegroups2.ShapeGroup shapeGroup3 = new drawit.shapegroups2.ShapeGroup(shapeGroup1And2);
//	    assert shapeGroup3.getSubgroupCount() == 2;
//	    assertEquals(Arrays.asList(shapeGroup1And2), shapeGroup3.getSubgroups());
//	    shapeGroup1.bringToFront();
//	    assert shapeGroup3.getSubgroup(0) == shapeGroup1;
//	    assert shapeGroup3.getSubgroups().get(1) == shapeGroup2;
//	    shapeGroup2.bringToFront();
//	    assert shapeGroup3.getSubgroups().get(0) == shapeGroup2;
//	    assert shapeGroup3.getSubgroups().get(1) == shapeGroup1;
//	    shapeGroup2.sendToBack();
//	    assert shapeGroup3.getSubgroups().get(0) == shapeGroup1;
//	    assert shapeGroup3.getSubgroup(1) == shapeGroup2;
//	    shapeGroup1.sendToBack();
//	    assert shapeGroup3.getSubgroups().get(0) == shapeGroup2;
//	    assert shapeGroup3.getSubgroups().get(1) == shapeGroup1;
//	    
//	    assert shapeGroup1.getParentGroup() == shapeGroup3;
//	    assert shapeGroup2.getParentGroup() == shapeGroup3;
//	    assert shapeGroup3.getParentGroup() == null;
//
//	    shapeGroup3.setExtent(drawit.shapegroups2.Extent.ofLeftTopRightBottom(50, 50, 200, 200));
//	    assert shapeGroup3.getExtent().getTopLeft().equals(new IntPoint(50, 50)) && shapeGroup3.getExtent().getBottomRight().equals(new IntPoint(200, 200));
//	    
//	    
//	    RoundedPolygon triangle = new RoundedPolygon();
//	    triangle.setVertices(new IntPoint[] {new IntPoint(10, 10), new IntPoint(30, 10), new IntPoint(20, 20)});
//
//	    ShapeGroup leaf = new ShapeGroup(triangle);
//	    assert leaf.getExtent().getTopLeft().equals(new IntPoint(10, 10)) && leaf.getExtent().getBottomRight().equals(new IntPoint(30, 20));
//	    leaf.setExtent(Extent.ofLeftTopWidthHeight(0, 0, 20, 10));
//
//	    ShapeGroup nonLeaf = new ShapeGroup(new ShapeGroup[] {leaf});
//	    assert nonLeaf.getExtent().getTopLeft().equals(new IntPoint(0, 0)) && nonLeaf.getExtent().getBottomRight().equals(new IntPoint(20, 10));
//	    nonLeaf.setExtent(Extent.ofLeftTopWidthHeight(0, 0, 10, 5));
//	    
//	    leaf.setExtent(Extent.ofLeftTopWidthHeight(1000, 2000, 20, 10));
//	    	    
//	    assert leaf.toGlobalCoordinates(new IntPoint(10,10)).getX() == 500;
//	    assert leaf.toGlobalCoordinates(new IntPoint(10,10)).getY() == 1000;
//
//	    assert leaf.toInnerCoordinates(new IntPoint(500,1000)).getX() == 10;
//	    assert leaf.toInnerCoordinates(new IntPoint(500,1000)).getY() == 10;
//
//	    leaf.setExtent(Extent.ofLeftTopWidthHeight(2000, 4000, 40, 20));
//	    
//	    assert leaf.toGlobalCoordinates(new IntPoint(20, 15)).getX() == 1010;
//
//	    
//	    
//	    
//	    RoundedPolygon fig = new RoundedPolygon();
//	    fig.setVertices(new IntPoint[] {new IntPoint(0, 0), new IntPoint(20, 10), new IntPoint(20, 30)});
//
//	    drawit.shapegroups2.ShapeGroup kind = new drawit.shapegroups2.ShapeGroup(fig);
//	    assert kind.getExtent().getRight() == 20;
//	    assert kind.getExtent().getTopLeft().equals(new IntPoint(0, 0)) && kind.getExtent().getBottomRight().equals(new IntPoint(20, 30));
//	    kind.setExtent(drawit.shapegroups2.Extent.ofLeftTopRightBottom(50, 50, 100, 100));
//	    assert kind.getOriginalExtent().getTopLeft().equals(new IntPoint(0, 0)) && kind.getOriginalExtent().getBottomRight().equals(new IntPoint(20, 30));
//
//	    drawit.shapegroups2.ShapeGroup mama = new drawit.shapegroups2.ShapeGroup(new drawit.shapegroups2.ShapeGroup[] {kind});
//	    assert mama.getExtent().getTopLeft().equals(new IntPoint(50, 50)) && mama.getExtent().getBottomRight().equals(new IntPoint(100, 100));
//	    mama.setExtent(drawit.shapegroups2.Extent.ofLeftTopRightBottom(30, 30, 80, 100));
//	    assert mama.getOriginalExtent().getTopLeft().equals(new IntPoint(50, 50)) && mama.getOriginalExtent().getBottomRight().equals(new IntPoint(100, 100));
//	    kind.setExtent(drawit.shapegroups2.Extent.ofLeftTopRightBottom(100, 50, 150, 150));
//	    assert kind.getOriginalExtent().getTopLeft().equals(new IntPoint(0, 0)) && kind.getOriginalExtent().getBottomRight().equals(new IntPoint(20, 30));
//	    assert kind.getExtent().getTopLeft().equals(new IntPoint(100, 50)) && kind.getExtent().getBottomRight().equals(new IntPoint(150, 150));
//
//	    
//	    assert kind.toGlobalCoordinates(new IntPoint(10, 15)).equals(new IntPoint(105, 100)); 
//	    assert kind.toInnerCoordinates(new IntPoint(105, 100)).equals(new IntPoint(10, 15)); 
//	
//	}
//}
