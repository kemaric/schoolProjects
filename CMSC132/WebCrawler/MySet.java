import java.util.HashSet;

public class MySet<T> {

	private HashSet<T> set;

	public MySet() {
		set = new HashSet<T>();
	}

	/**
	 * This method returns the number of items in the set
	 * 
	 * @return size of set
	 */
	public synchronized int size(){
		return set.size();
	}

	/**
	 * This method removes the object from the set
	 * @param o
	 * @return
	 */
	public synchronized boolean remove(Object o){
		return set.remove((T)o);
	}

	/**
	 * This method adds an item to the set. 
	 */
	public synchronized boolean add(Object o){
		return set.add((T)o);
	}

	/**
	 * This method removes all items from the set.
	 */
	public synchronized void clear(){
		set.clear();
	}

	public synchronized boolean contains(Object o){
		return set.contains((T)o);
	}

}
