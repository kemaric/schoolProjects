package searchTree;

import java.util.Random;
import java.util.TreeMap;

public class TreeSpeed {

	public static void main(String[] args) {
		timeSearchTreeMap();
		timeTreeMap();
	}
	 
	private static void timeSearchTreeMap(){
		Random r;
		long time;
		SearchTreeMap<Integer,Integer> myTree;

		// Build tree with 5000 random numbers between 1 and 500000
		r = new Random(100L); 
		time = System.currentTimeMillis();
		myTree = new SearchTreeMap<Integer,Integer>();
		for (int i = 1; i<5000; i++) {
			int v = r.nextInt(500000); 
			myTree.put(v, i);
		}
		time = System.currentTimeMillis() - time;
		System.out.println("SearchTree Time(msec) = "+time);

		// Build tree with 5000 numbers in sequence
		time = System.currentTimeMillis();
		myTree = new SearchTreeMap<Integer,Integer>();
		for (int i = 1; i<5000; i++) {
			myTree.put(i, i);
		}
		time = System.currentTimeMillis() - time;
		System.out.println("SearchTree Time(msec) = "+time);
	}

	private static void timeTreeMap(){
		Random r;
		long time;
		TreeMap<Integer,Integer> myTree;

		// Build tree with 5000 random numbers between 1 and 500000
		r = new Random(100L); 
		time = System.currentTimeMillis();
		myTree = new TreeMap<Integer,Integer>();
		for (int i = 1; i<5000; i++) {
			int v = r.nextInt(500000); 
			myTree.put(v, i);
		}
		time = System.currentTimeMillis() - time;
		System.out.println("TreeMap Time(msec) = "+time);

		// Build tree with 5000 numbers in sequence
		time = System.currentTimeMillis();
		myTree = new TreeMap<Integer,Integer>();
		for (int i = 1; i<5000; i++) {
			myTree.put(i, i);
		}
		time = System.currentTimeMillis() - time;
		System.out.println("TreeMap Time(msec) = "+time);
	}
}
