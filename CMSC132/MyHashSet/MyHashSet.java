import java.util.Iterator;




/** 
 * The MyHashSet API is similar to the Java Set interface. This
 * collection is backed by a hash table.
 */
public class MyHashSet<T> implements Iterable<T> {

	/** Unless otherwise specified, the table will start as
	 * an array of this length.*/
	public final static int DEFAULT_INITIAL_CAPACITY = 4;

	/** When the ratio of size/capacity reaches or exceeds this
	 * value, it is time for the table to be expanded*/
	public final static double LOAD_FACTOR = .75;

	private Node<T>[] table;
	private int size;           // number of elements in the table

	private static class Node<T> {
		private T data;
		private Node<T> next;

		private Node(T data) {
			this.data = data;
			next = null;
		}

		@SuppressWarnings("unchecked")
		private static <T> Node<T>[] makeArray(int size) {
			return new Node[size];
		}

		public String toString() {
			String retValue = "[" + data.toString() + ", ";
			retValue += (next == null)? "null" : next.toString();
			retValue += "]";
			return retValue;
		}
	}

	/**
	 * Initializes an empty table of the specified length (capacity).  
	 * Relies on the static makeArray method of the Node class.
	 * @param initialCapacity initial length (capacity) of table
	 */
	{size = 0;}
	public MyHashSet(int initialCapacity) {
		table = Node.makeArray(initialCapacity);
	}

	/**
	 * Initializes an empty table of length equal to 
	 * DEFAULT_INITIAL_CAPACITY
	 */
	public MyHashSet() {
		table = Node.makeArray(DEFAULT_INITIAL_CAPACITY);
	}

	/**
	 * Returns the number of elements stored in the table.
	 * @return number of elements in the table
	 */
	public int getSize(){
		return size;
	}

	/**
	 * This takes the element and converts it into a hash value according to the capacity of the table.
	 * 
	 * @param element
	 * @return the hash value of the element
	 */
	private int getHashValue(T element){
		int hashValueOfElement = Math.abs((937 * element.hashCode()) % getCapacity()); // the absolute value hash code
		return hashValueOfElement;
	}
	/**
	 * Returns the length of the table (the number of buckets).
	 * @return length of the table (capacity)
	 */
	public int getCapacity(){
		return table.length;
	}

	/**
	 * 
	 */
	public void reHash(){
		Node<T>[] newTable = Node.makeArray(getCapacity() * 2); //new hash table with twice the size of original 
		for(int index = 0; index < table.length; index++){ 
			if(table[index] != null){
				Node current = table[index];

				/*This loop traverses through the hash table and checks to see which index of the table has a 
				 * a non-null value. When it finds that index, the sets the current node to whatever data is 
				 * stored in that index.
				 */
				while(current != null){
					int currentElmHash = Math.abs((937 * current.data.hashCode()) % newTable.length); 
					//current element's hash code with respect to the new table

					Node currInNew = new Node(current.data); //current element in table that will be rehashed to new
					if(newTable[currentElmHash] == null){
						newTable[currentElmHash] = currInNew;
						/*If the index corresponding with the element's hash code is empty, then the element from the 
						 * original table is added.
						 */
					}else{
						Node spot = newTable[currentElmHash]; //the Node in the current index of the new table

						while(spot.next != null){
							spot = spot.next;
						}
						if(spot.next == null){
							spot.next = currInNew;
						}
						/*If the next node doesn't equal null, then you make the current spot the next node.
						 *If the next spot is null, which is the end of the list, then the current element to add is 
						 *made the next.*/
					}
					current = current.next;
				}
			}

		}
		table = newTable;
	}

	/** 
	 * Looks for the specified element in the table.
	 * 
	 * @param element to be found
	 * @return true if the element is in the table, false otherwise
	 */
	public boolean contains(T element) {
		Node current = table[getHashValue(element)]; //current element in the table at the element's hash code
		if(current == null){
			return false; //if the current index is empty, then it returns false. 
		}
		while(current.next != null){
			if(current.data.equals(element)){
				return true;
			}
			current = current.next;
			if(current.next == null){
				if(current.data.equals(element)){
					return true;
				}
			}
		}
		/*This loop iterates through the bucket starting with the first element in the bucket. It then checks to see if 
		 * the current element is equal to the parameter and if it is, it returns true. If the last element in the list 
		 * equals the parameter it also returns true.
		 * 
		 */

		return false; //returns false by default
	}

	/** Adds the specified element to the collection.  If the element
	 * is already in the collection, then this method should do nothing.
	 * 
	 * Important:  After adding this element to the table, consider
	 * the ratio of size/capacity.  If this ratio is greater than or equal
	 * to the LOAD_FACTOR then you must double the size of the table.
	 * This will require re-hashing of all of the data!
	 * 
	 * @param element the element to be added to the collection
	 */
	public void add(T element) {
		if(contains(element)){
			return; //if the table already contains the element, it does nothing.
		}else{
			Node newElement = new Node(element); //this is a node with the element's data
			if(table[getHashValue(element)] == null){
				table[getHashValue(element)] = newElement;
				//if the hash position in the table is null, then the element is just added to that index
			}else{
				Node current = table[getHashValue(element)]; //the current node in the hash index

				while(current.next != null){
					current = current.next;
				}
				//this loop finds the last node in the current bucket
				if(current.next == null){
					current.next = newElement;
				}
				//when the last node is found, the node then points to the newElement 
			}
			size++; 
		}
		if(((double)getSize() / (double)getCapacity())>= LOAD_FACTOR){
			reHash();
		}
		//if the size to capacity ratio is greater then or equal to the LOAD_FACTOR, then the table gets rehashed. 
	}

	/** Removes the specified element from the collection.  If the
	 * element is not present then this method should do nothing.
	 *
	 * @param element the element to be removed
	 */
	public void remove(T element) {
		if(!contains(element)){
			return; //if the table doesn't contain the element, it does nothing.
		}else{
			Node current = table[getHashValue(element)]; // node with the current hash index's element
			Node previous = null; // previous node/element
			while(current != null){
				if(current.data.equals(element)){
					if(current == table[getHashValue(element)]){
						table[getHashValue(element)] = table[getHashValue(element)].next;
					}else{
						previous.next = current.next;
					}
					break;
				}else{
					previous = current;
					current = current.next;
				}
			}
			/*This loop iterates through the bucket at the specified hash index of the element that we want to remove.
			 *if the current node's data equals the data you want to remove, the previous.next is made the current.next
			 *data. Else, the previous is made the current's data and the current is set to the next data. 
			 */
		}
	}

	/** Returns an Iterator that can be used to iterate over
	 * all of the elements in the collection.
	 */
	public Iterator<T> iterator() {
		return new MyIterator(); 
	}
	class MyIterator implements Iterator<T>{
		private int current = 0; // marker for position in the collection
		private int position = 0; //marker for position in the table
		Node<T> currentNode; //the currentNode for the bucket
		Node<T> returnNode; // the node returned in next and removed from remove
		boolean nextWasCalled = false; 
		//makes sure next was called prior to the call to remove, and makes sure remove is not called 2times in a row

		/**
		 * Returns whether there are more elements in the collection. If the current position is less than or equal
		 * than or equal to the size of the collection, returns true. 
		 */
		public boolean hasNext(){
			return (current < getSize()? true: false);
		}

		/**
		 * If hasNext is true, this returns the previous element and goes to the next element.
		 */
		@Override
		public T next() {
			if(hasNext()){ // if the is a next, then next can be called, else an exception is thrown
				if(table[position] == null || currentNode == null){
					while(table[position] == null){
						position++;
					}
					currentNode = table[position];
				}
				/*While the table at the position is empty or the currentNode is null, then the position is incremented
				 * till a non-null bucket is found. The currentNode is made the first element in the non-null bucket. 
				 */
				if(currentNode != null){
					returnNode = currentNode;
					currentNode = currentNode.next;
				}
				/*If the currentNode does not equal null, then the node returned will be the currentNode and the
				 * currentNode is the next node.
				 */
				if(currentNode == null){
					position++;
				}
				current++;
			}else{
				throw new IllegalStateException("You can't call next for an imaginary thing all willy nilly!");
			}
			nextWasCalled = true; // shows that next was called. This is used for remove. 
			return returnNode.data;
		}

		/**
		 * If next was called, this method removes the element that was returned from next. Also, this method returns
		 * an IllegalStateException whenever next was not called prior to the user calling remove. 
		 */
		@Override
		public void remove() {
			if(nextWasCalled == true){ // remove can only be called when next was called.
				nextWasCalled = false; //this allows and makes sure the user does not call remove twice in a row 
				MyHashSet.this.remove(returnNode.data);
			}else{
				throw new IllegalStateException("You can't call remove on something not there!");
			}
		}
	}
}
