package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import drawit.shapegroups1.Extent;

class Test_P3_5_S1 {

	@Test
	void test() {
		
		// Zie deel over Object --> We @Overriden methodes uit de Object klasse
		// bijgevolg moet de naam en de parameter overeen komen
		// dus moeten de Extents gedefinieerd worden als Object
		// via instanceof kan dan een cast gebeuren naar een Extent object.
		Object e1 = Extent.ofLeftTopRightBottom(0, 0, 100, 100);
		Object e2 = Extent.ofLeftTopRightBottom(0, 0, 100, 100);
		Object e3 = Extent.ofLeftTopRightBottom(0, 0, 100, 150);
		Object e4 = Extent.ofLeftTopWidthHeight(0, 0, 100, 100);
		Object e5 = Extent.ofLeftTopRightBottom(100, 100, 300, 300);
		Object e6 = Extent.ofLeftTopWidthHeight(100, 100, 200, 200);
		Object e7 = Extent.ofLeftTopRightBottom(100, 100, 123, 123);
		
		
		assert e1.equals(e2);
		assert !e1.equals(e3);
		assert e1.equals(e4);
		assert e5.equals(e6);

		ArrayList<Object> l1 = new ArrayList<>();
		l1.add(e2);
		l1.add(e3);
		
		assert l1.contains(e1);
		assert l1.contains(e2);
		assert l1.contains(e3);
		assert l1.contains(e4);
		assert !l1.contains(e5);
		assert !l1.contains(e6);
		assert !l1.contains(e7);
		
		HashSet<Object> s1 = new HashSet<>();
		
		s1.add(e2);
		s1.add(e3);
		
		assert s1.contains(e1);
		assert s1.contains(e2);
		assert s1.contains(e3);
		assert s1.contains(e4);
		assert !s1.contains(e5);
		assert !s1.contains(e6);
		assert !s1.contains(e7);

		
		System.out.println(e1.toString());
		System.out.println(e2.toString());
		System.out.println(e3.toString());
		System.out.println(e4.toString());
		System.out.println(e5.toString());
		System.out.println(e6.toString());
		System.out.println(e7.toString());

	}

}
