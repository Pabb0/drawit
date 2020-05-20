package drawit.shapegroups1.exporter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.shapegroups1.Extent;
import drawit.shapegroups1.LeafShapeGroup;
import drawit.shapegroups1.NonleafShapeGroup;
import drawit.shapegroups1.ShapeGroup;

public class ShapeGroupExporter {
	
	public static Object toPlainData(ShapeGroup shapeGroup) {
	
		if (shapeGroup instanceof LeafShapeGroup) {
			LeafShapeGroup leaf = (LeafShapeGroup) shapeGroup;
			RoundedPolygon shape = leaf.getShape();
			Extent ogExtent = leaf.getOriginalExtent();
			Extent extent = leaf.getExtent();
				
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			
			map.put("originalExtent", Map.of(
											"left", ogExtent.getLeft(), 
											"top", ogExtent.getTop(),
											"right", ogExtent.getRight(), 
											"bottom", ogExtent.getBottom()));
			map.put("extent", Map.of(
									"left", extent.getLeft(), 
									"top", extent.getTop(),
									"right", extent.getRight(), 
									"bottom", extent.getBottom()));
			
			
			List<Map<String, Object>> vertices = new ArrayList<Map<String, Object>>();
			for(IntPoint vertex: shape.getVertices()) 
				vertices.add(Map.of("x", vertex.getX(), "y", vertex.getY()));
			
			Map<String, Object> colors = new LinkedHashMap<String, Object>();
			colors = Map.of("red", shape.getColor().getRed(), "green", shape.getColor().getGreen(), "blue", shape.getColor().getBlue());
			
			map.put("shape", Map.of(
									"vertices", vertices,
									"radius", shape.getRadius(),
									"color", colors));
		
			
			return map;
		}
		else {
			NonleafShapeGroup nonLeaf = (NonleafShapeGroup) shapeGroup;
			Extent ogExtent = nonLeaf.getOriginalExtent();
			Extent extent = nonLeaf.getExtent();
			
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			
			map.put("originalExtent", Map.of(
											"left", ogExtent.getLeft(), 
											"top", ogExtent.getTop(),
											"right", ogExtent.getRight(), 
											"bottom", ogExtent.getBottom()));
			
			map.put("extent", Map.of(
									"left", extent.getLeft(), 
									"top", extent.getTop(),
									"right", extent.getRight(), 
									"bottom", extent.getBottom()));
			
			
			List<Object> subgroupList = new ArrayList<Object>();
			for (ShapeGroup subgroup : nonLeaf.getSubgroups()) {
				subgroupList.add(toPlainData(subgroup));
			}
			map.put("subgroups", subgroupList);
			
			
			return map;
				
			}
		
	}				
}
