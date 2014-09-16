import java.util.Iterator;

import junit.framework.TestCase;

public class PublicTests extends TestCase {

	public void testSimpleAdd() {
		MyHashSet<String> s = new MyHashSet<String>();
		s.add("hello");
		s.add("apple");
		assertEquals(4, s.getCapacity());
		assertEquals(2, s.getSize());
		s.add("apple");
		assertEquals(4, s.getCapacity());
		assertEquals(2, s.getSize());
	}

	public void testNext() {
		MyHashSet<String> s = new MyHashSet<String>();
		s.add("hello");
		s.add("apple");
		assertEquals(4, s.getCapacity());
		assertEquals(2, s.getSize());
		s.add("apple");
		assertEquals(4, s.getCapacity());
		assertEquals(2, s.getSize());
		s.add("pipe");
		s.add("pineapple");
		s.add("helll");
		s.add("hoe");
		s.add("ass");
		s.add("lap");
		s.add("boobs");

		String ap = "";
		for(String e:s){
			ap += e + " ,";
		}
		System.out.println(ap);
		assertTrue(ap.equals("pipe ,lap ,helll ,ass ,apple ,pineapple ,boobs ,hello ,hoe ,"));
	}

	public void testNoDuplicates() {
		MyHashSet<String> s = new MyHashSet<String>();
		for (int i = 0; i < 10; i++) {
			s.add("hello");
			s.add("apple");
			s.add("cat");
			s.add("last");
		}
		assertEquals(8, s.getCapacity());
		assertEquals(4, s.getSize());
	}


	public void testReHash() {
		MyHashSet<String> s = new MyHashSet<String>();
		for (int i = 0; i < 1000; i++) {
			s.add("Entry " + i);
		}
		int cap = s.getCapacity();
		assertEquals(2048, s.getCapacity());
		assertEquals(1000, s.getSize());
	}

	public void testNext2() {
		MyHashSet<String> s = new MyHashSet<String>();
		s.add("hello");
		s.add("apple");
		assertEquals(4, s.getCapacity());
		assertEquals(2, s.getSize());
		s.add("apple");
		assertEquals(4, s.getCapacity());
		assertEquals(2, s.getSize());
		s.add("pipe");
		s.add("pineapple");
		s.add("helll");
		s.add("hoe");
		s.add("ass");
		s.add("lap");
		s.add("boobs");
		Iterator<String> it = s.iterator();
		
		System.out.println(it.next());
		System.out.println();
		it.remove();
		//it.remove();
		
		MyHashSet<String> s2 = new MyHashSet<String>();
		Iterator<String> it2 = s2.iterator();
		//System.out.println(it2.hasNext());
		//it2.next();
		

	}
}




