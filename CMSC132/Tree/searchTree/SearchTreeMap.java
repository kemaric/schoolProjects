package searchTree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.List;


/**
 * This class provides a partial implementation of the Map interface. The
 * implementation uses classes implementing the Tree interface to represent the
 * actual search tree.  All the methods of this class have been implemented, except
 * keyList and subMap.
 *  
 */
public class SearchTreeMap<K extends Comparable<K>, V>  {
	Tree<K,V> root = EmptyTree.getInstance();
	private List<K> list = new ArrayList<K>();
	/**
	 * Find the value the key is mapped to
	 * 
	 * @param k -
	 *            Search key
	 * @return value k is mapped to, or null if there is no mapping for the key
	 */
	public V get(K k) {
		return root.search(k);
	}

	/**
	 * Update the mapping for the key
	 * 
	 * @param k -
	 *            key value
	 * @param v -
	 *            value the key should be bound to
	 */
	public void put(K k, V v) {
		root = root.insert(k, v);
	}

	/**
	 * Return number of keys bound by this map
	 * 
	 * @return number of keys bound by this map
	 */
	public int size() {
		return root.size();
	}

	/**
	 * Remove any existing binding for a key
	 * 
	 * @param k -
	 *            key to be removed from the map
	 */
	public void remove(K k) {
		root = root.delete(k);
	}

	/**
	 * Return a Set of all the keys in the map
	 * 
	 * @return Set of all the keys in the map
	 */

	public Set<K> keySet() {
		Set<K> result = new HashSet<K>();
		root.addKeysToCollection(result);
		return result;
	}

	/**
	 * Return the minimum key value in the map
	 * 
	 * @return the minimum key value in the map
	 * @throws NoSuchElementException if the map is empty
	 */
	public K getMin() {
		try {
			return root.min();
		} catch (TreeIsEmptyException e) {
			throw new NoSuchElementException();
		}
	}

	/**
	 * Return the maximum key value in the map
	 * 
	 * @return the maximum key value in the map
	 * @throws NoSuchElementException if the map is empty
	 */
	public K getMax() {
		try {
			return root.max();
		} catch (TreeIsEmptyException e) {
			throw new NoSuchElementException();
		}
	}

	/**
	 * Return a string representation of the tree
	 */
	public String toString() {
		return root.toString();
	}

	/**
	 * Return list of keys in map in natural sorted order
	 * 
	 * @return list of keys in map in sorted order
	 */
	public List<K> keyList( ) {
		root.addKeysToCollection(list);
		return list;
	}

	/**
	 * Return subset of TreeMap between the values fromKey-toKey.  It will
	 * include fromKey and toKey if they are found in the original map.
	 * The values for fromKey and toKey do not actually need to be in the map.
	 * Assume that fromKey is less than or equal to toKey.
	 * 
	 * @return TreeMap consisting of subset of SearchTreeMap
	 */
	public SearchTreeMap<K, V> subMap(K fromKey, K toKey) {
		SearchTreeMap<K,V> mapTree = new SearchTreeMap<K,V>();
		mapTree.root = root.subTree(fromKey, toKey);
		return mapTree;
	}
}