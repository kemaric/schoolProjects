package tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import searchTree.NonEmptyTree;
import searchTree.SearchTreeMap;
import searchTree.Tree;
import junit.framework.TestCase;

public class StudentTests extends TestCase {
	Integer max,min, middle;
	public void testMaxAndMin() {
		SearchTreeMap<Integer,String> s = new SearchTreeMap<Integer,String>();
		assertEquals(0, s.size());
		s.put(2, "Two");
		s.put(1, "One");
		s.put(3, "Three");
		s.put(1, "OneSecondTime");
		assertEquals(3, s.size());
		assertEquals("OneSecondTime", s.get(1));
		assertEquals("Two", s.get(2));
		assertEquals("Three", s.get(3));
		assertEquals(null, s.get(8));
		min = 1; max = 3;

		assertEquals(max, s.getMax());
		assertEquals(min, s.getMin());

		s.put(4, "Abc");
		s.put(15,"l" );
		s.put(10, "four");
		min = 1; max = 15;

		assertEquals(max, s.getMax());
		assertEquals(min, s.getMin());

		SearchTreeMap<Integer,String> b = new SearchTreeMap<Integer,String>();
		assertEquals(0, b.size());
		//b.getMax();
		//b.getMin();
	}

	public void testDelete() {
		SearchTreeMap<Integer,String> s = new SearchTreeMap<Integer,String>();
		assertEquals(0, s.size());
		s.put(2, "Two");
		s.put(1, "One");
		s.put(3, "Three");
		s.put(1, "OneSecondTime");
		assertEquals(3, s.size());
		assertEquals("OneSecondTime", s.get(1));
		assertEquals("Two", s.get(2));
		assertEquals("Three", s.get(3));
		assertEquals(null, s.get(8));
		min = 1; max = 3; middle = 2;

		s.remove(middle);

	}

	public void testDelete2() {
		SearchTreeMap<Integer,String> s = new SearchTreeMap<Integer,String>();
		assertEquals(0, s.size());
		s.put(2, "Two");
		s.put(1, "One");
		s.put(3, "Three");
		s.put(1, "OneSecondTime");
		s.put(4, "Abc");
		s.put(15,"l" );
		s.put(10, "four");

		//assertEquals(3, s.size());
		assertEquals("OneSecondTime", s.get(1));
		assertEquals("Two", s.get(2));
		assertEquals("Three", s.get(3));
		assertEquals(null, s.get(8));
		min = 1;max = 15; middle = 3;


		System.out.println(s.toString());
	}
	
	public void testDelete3(){
		SearchTreeMap<Integer,String> s = new SearchTreeMap<Integer,String>();
		assertEquals(0, s.size());
		s.put(2, "Two");
		s.put(1, "One");
		s.put(3, "Three");
		s.put(1, "OneSecondTime");
		assertEquals(3, s.size());
		assertEquals("OneSecondTime", s.get(1));
		assertEquals("Two", s.get(2));
		assertEquals("Three", s.get(3));
		List<Integer> b = s.keyList();
		assertEquals("[1, 2, 3]", b.toString());
		
		SearchTreeMap<Integer,String> t = new SearchTreeMap<Integer,String>();
		t.put(2, "Two");
		t.put(1, "One");
		t.put(3, "Three");
		t.put(1, "OneSecondTime");
		t.put(4, "Abc");
		t.put(15,"l" );
		t.put(10, "four");
		
		t.remove(2);
		List<Integer> a = t.keyList();
		assertEquals("[1, 3, 4, 10, 15]", a.toString());
		
		//List<Integer> q = t.keyList();
	}
	public void testDeleteFromEmpty() {
		SearchTreeMap<Integer,String> s = new SearchTreeMap<Integer,String>();
		assertEquals(0, s.size());
		s.put(2, "Two");
		s.put(1, "One");
		s.put(3, "Three");
		s.put(1, "OneSecondTime");
		assertEquals(3, s.size());
		assertEquals("OneSecondTime", s.get(1));
		assertEquals("Two", s.get(2));
		assertEquals("Three", s.get(3));
		assertEquals(null, s.get(8));
		min = 1; max = 3; middle = 2;

		s.remove(2);
		s.remove(1);
		s.remove(3);
		assertEquals(0, s.size());
		s.remove(1);
		assertEquals(0,s.size());
		s.remove(max);
		
		//assertEquals("searchTree.EmptyTree@13e205f",s.toString());
	}

	public void testSubTree(){
		SearchTreeMap<Integer,String> s = new SearchTreeMap<Integer,String>();
		assertEquals(0, s.size());
		s.put(2, "Two");
		s.put(1, "One");
		s.put(3, "Three");
		s.put(1, "OneSecondTime");
		assertEquals(3, s.size());
		assertEquals("OneSecondTime", s.get(1));
		assertEquals("Two", s.get(2));
		assertEquals("Three", s.get(3));
		s.put(4, "Abc");
		s.put(15,"l" );
		s.put(10, "four");

		SearchTreeMap<Integer, String> a = s.subMap(1, 3);
		System.out.println(a);

		SearchTreeMap<Integer, String> b = s.subMap(3, 10);
		System.out.println(b);

		
	}
	
	public void testAddKeysToCollection(){
		SearchTreeMap<Integer,String> s = new SearchTreeMap<Integer,String>();
		assertEquals(0, s.size());
		s.put(2, "Two");
		s.put(1, "One");
		s.put(3, "Three");
		s.put(1, "OneSecondTime");
		System.out.println();

		s.put(4, "Abc");
		s.put(15,"l" );
		s.put(10, "four");
		List<Integer> b = s.keyList();
		System.out.println(b);
		assertEquals("[1, 2, 3, 4, 10, 15]", b.toString());
		
	}
	
	public void testSearch(){
		SearchTreeMap<Integer,String> s = new SearchTreeMap<Integer,String>();
		assertEquals(0, s.size());
	}
}


