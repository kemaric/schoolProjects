

import java.util.AbstractCollection;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.HashMap;

/**
 * <P>
 * The DenseBag class implements a Set-like collection that allows duplicates (a
 * lot of them).
 * </P>
 * <P>
 * The DenseBag class provides Bag semantics: it represents a collection with
 * duplicates. The "Dense" part of the class name comes from the fact that the
 * class needs to efficiently handle the case where the bag contains 100,000,000
 * copies of a particular item (e.g., don't store 100,000,000 references to the
 * item).
 * </P>
 * <P>
 * In a Bag, removing an item removes a single instance of the item. For
 * example, a Bag b could contain additional instances of the String "a" even
 * after calling b.remove("a").
 * </P>
 * <P>
 * The iterator for a dense bag must iterate over all instances, including
 * duplicates. In other words, if a bag contains 5 instances of the String "a",
 * an iterator will generate the String "a" 5 times.
 * </P>
 * <P>
 * In addition to the methods defined in the Collection interface, the DenseBag
 * class supports several additional methods: uniqueElements, getCount, and
 * choose.
 * </P>
 * <P>
 * The class extends AbstractCollection in order to get implementations of
 * addAll, removeAll, retainAll and containsAll. 
 * All other methods defined in
 * the Collection interface will be implemented here.
 * </P>
 */
public class DenseBag<T> extends AbstractCollection<T> {

	private Map<T, Integer> denseBagMap; // the dense bag
	private int size;  // Total number of elements in the bag

	/**
	 * Initialize a new, empty DenseBag
	 */
	{size = 0;} // initializes size to 0.
	public DenseBag() {
		denseBagMap = new HashMap<T, Integer>();
	};

	/**
	 * Generate a String representation of the DenseBag. 
	 */
	@Override
	public String toString() {

		return denseBagMap.keySet() + " ";
	}

	/**
	 * Tests if two DenseBags are equal. Two DenseBags are considered equal if they
	 * contain the same number of copies of the same elements.
	 * Comparing a DenseBag to an instance of
	 * any other class should return false;
	 */
	@Override
	public boolean equals(Object o) {
		if(o == this){
			return true;
		}
		if(!(o instanceof DenseBag)){
			return false;
		}
		DenseBag newBag = (DenseBag) o;

		return this.hashCode() == newBag.hashCode() && this.size == newBag.size && this.containValues(newBag);
	}

	/**
	 * This method takes a DenseBag and checks to see if the key mappings are the same as the current object's 
	 * key mapping. If the keys are mapped to the same value, then the method returns true, else, it returns 
	 * false.
	 * @param other
	 * @return true if the two bag's keys are mapped to the same value, else false
	 */
	private boolean containValues(DenseBag other){
		boolean valueCheck = false; // checker used to see if all the values are the same
		if(this.containsAll(other)){
			int count = 0; // used to record when the values are the same for the given key
			for(T element: this.uniqueElements()){
				if(this.denseBagMap.get(element) == other.denseBagMap.get(element)){
					count++;
				}
			}
			if(count == uniqueElements().size()){
				valueCheck = true;
			}
		}else{
			return false;
		}
		return valueCheck;
	}




	/**
	 * Return a hashCode that fulfills the requirements for hashCode (such as
	 * any two equal DenseBags must have the same hashCode) as well as desired
	 * properties (two unequal DenseBags will generally, but not always, have
	 * unequal hashCodes).
	 */
	@Override
	public int hashCode() {
		return denseBagMap.hashCode();
	}

	/**
	 * <P>
	 * Returns an iterator over the elements in a dense bag. Note that if a
	 * Dense bag contains 3 a's, then the iterator must iterator over 3 a's
	 * individually.
	 * </P>
	 * <P>
	 * The user must not call next in the case where hasNext would return 
	 * false.<br \>
	 * If the user violates this rule, then the call to next will throw
	 * a NoSuchElementException.
	 * </P>
	 * <P>
	 * The "remove" method throws an UnsupportedOperationException.
	 * </P>
	 */
	@Override
	public Iterator<T> iterator() {
		return new MyIterator();
	}

	class MyIterator implements Iterator<T> {

		private int current = 1; // marker for position in the collection
		private int sizeOfCollection = size; 
		private Set<T> bagInSet = uniqueElements(); // the denseBag in the form of a set with the unique keys
		private Iterator<T> setIt = bagInSet.iterator(); // iterator for the bag in set form
		private Integer numberOfElement = 0; // value attached to a key for the number of occurrences of that key
		private T element = null; // reference to the current element

		/**
		 * Returns whether there are more elements in the collection. If the current position is less than or equal
		 * than or equal to the size of the collection, returns true. 
		 */
		@Override
		public boolean hasNext() {
			return current <= sizeOfCollection;
		}

		/**
		 * If hasNext is true, this returns the previous element and goes to the next element.
		 */
		@Override
		public T next() {
			if(hasNext()){
				if(numberOfElement > 0){
					--numberOfElement;
				}
				// as we cycle through the key, the number of occurrences go down to 0
				if(numberOfElement == 0){
					element = setIt.next();	
					numberOfElement = denseBagMap.get(element);
				}
				/*when we cycle through all the occurrences of a key, the reference is changed to the
				 *next unique key and the numberOfElement is set to the value attached to the new key.
				 * 
				 */
				current++;  // Increment our position in the collection
			}else{
				throw new NoSuchElementException("There are no more elements left in the bag!");
			}
			return element;
		}
		/**
		 * Not supported. 
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException("Remove is not supported.");
		}
	}

	/**
	 * return a Set of the elements in the Bag (since the returned value is a
	 * set, it will contain one value for each UNIQUE value in the Bag).
	 * 
	 * @return A set of elements in the Bag
	 */
	public Set<T> uniqueElements() {
		return denseBagMap.keySet();
	}

	/**
	 * Return the number of instances of a particular object in the bag. Return
	 * 0 if it doesn't exist at all.
	 * 
	 * @param o
	 *            object of interest
	 * @return number of times that object occurs in the Bag
	 */
	public int getCount(Object o) {
		if(!denseBagMap.containsKey(o)){
			return 0;
		}
		return denseBagMap.get(o);
	}

	/**
	 * Given a random number generator, randomly choose an element from the Bag
	 * according to the distribution of objects in the Bag (e.g., if a Bag
	 * contains 7 a's and 3 b's, then 70% of the time choose should return an a,
	 * and 30% of the time it should return a b.
	 * 
	 * @param r
	 *            Random number generator
	 * @return randomly chosen element
	 */
	public T choose(Random r) {
		T chosenElement = null;
		int count = 0;		
		int index = r.nextInt(size());
		for(T element:this.denseBagMap.keySet()){
			count += denseBagMap.get(element);
			if(count > index){
				chosenElement = element;
				break;
			}
		}
		/*This loop cycles through the keys in the keySet of the current bag. As it cycles through, it adds the
		 * value attached to the key to count. The index stores the random number that is between 0 and the size 
		 * of the collection. While cycling through the set, when ever the value in count exceeds the random number,
		 * the key whose values was just added to count is then the chosen element. The loop then breaks and that key
		 * is returned. 
		 */

		return chosenElement;
	}

	/**
	 * Returns true if the Bag contains one or more instances of o
	 */
	@Override
	public boolean contains(Object o) {
		boolean inBag = false;
		if(!denseBagMap.containsKey(o)){
			return false;
		}else if(denseBagMap.containsKey(o)){
			inBag = true;
		}
		return inBag;

	}

	/**
	 * Adds an instance of o to the Bag
	 * 
	 * @return always returns true, since added an element to a bag always
	 *         changes it
	 * 
	 */
	@Override
	public boolean add(T o) {
		Integer keySize = denseBagMap.get(o);
		if(!(denseBagMap.containsKey(o))){
			denseBagMap.put(o, 1); // adds the key to the bag and sets its value to 1.
		}else{
			denseBagMap.put(o, ++keySize); //Increments the value attached to the key
		}
		size++;
		return true;
	}

	/**
	 * Decrements the number of instances of o in the Bag.
	 * 
	 * @return return true if and only if at least one instance of o exists in
	 *         the Bag and was removed.
	 */
	@Override
	public boolean remove(Object o) {
		Integer keySize = denseBagMap.get(o);
		if(denseBagMap.containsKey(o) && denseBagMap.get(o) > 0){
			if(keySize >= 2){ 
				denseBagMap.put((T)o, --keySize); // decrements the key value if at least 2
				size--;
			}else{
				denseBagMap.remove(o); // if there is only one, the key is removed entirely.
				size--;
			}
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Total number of instances of any object in the Bag (counting duplicates)
	 */
	@Override
	public int size() {
		return size; // size of the bag
	}
}