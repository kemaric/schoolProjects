import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;


public class TestQueue<T> extends TestCase {

	public void testQueueConstruct(){
		MyQueue<T> x = new MyQueue<T>();
		String a = "apple";
		String b = "pie";
		String c = "pop";
		String d = "dog";
		List<String> lst = new ArrayList<String>();
		lst.add(a);
		lst.add(b);
		lst.add(c);
		lst.add(d);
		for(int i = 0; i < lst.size() ; i++){
			x.enqueue(lst.get(i));
		}
	}
	
	public void testEnandDequeue(){
		MyQueue<T> x = new MyQueue<T>();
		String a = "apple";
		String b = "pie";
		String c = "pop";
		String d = "dog";
		List<String> lst = new ArrayList<String>();
		lst.add(a);
		lst.add(b);
		lst.add(c);
		lst.add(d);
		for(int i = 0; i < lst.size() ; i++){
			x.enqueue(lst.get(i));
		}
		System.out.println(x.size());
		assertEquals(4, x.size());
		
		x.clear();
	}
}
