package searchTree;

import java.util.Collection;

/**
 * This interface describes the interface for both empty and non-empty search
 * trees.
 */
public interface Tree <K extends Comparable<K>,V >{

	/**
	 * Find the value that this key is bound to in this tree.
	 * 
	 * @param key --
	 *            Key to search for
	 * @return -- value associated with the key by this Tree, or null if the key
	 *         does not have an association in this tree.
	 */
	V search(K key);

	/**
	 * Insert/update the Tree with a new key:value pair. If the key already
	 * exists in the tree, update the value associated with it. If the key
	 * doesn't exist, insert the new key value pair.
	 * 
	 * This method returns a reference to an Tree that represents the updated
	 * value. In many, but not all cases, the method may just return a
	 * reference to this. This method is annotated @CheckReturnValue because
	 * you have to pay attention to the return value; if you simply invoke insert on
	 * a Tree and ignore the return value, your code is almost certainly wrong. 
	 * 
	 * @param key --
	 *            Key
	 * @param value --
	 *            Value that the key maps to
	 * @return -- updated tree
	 */
	NonEmptyTree<K,V> insert(K key, V value);

	/**
	 * Delete any binding the key has in this tree. If the key isn't bound, this
	 * is a no-op
	 * 
	 * This method returns a reference to an Tree that represents the updated
	 * value. In many, but not all cases, the method may just return a
	 * reference to this. This method is annotated @CheckReturnValue because
	 * you have to pay attention to the return value; if you simply invoke delete on
	 * a Tree and ignore the return value, your code is almost certainly wrong. 
	 * 
	 * @param key --
	 *            Key
	 * @return -- updated tree
	 */
	Tree<K,V> delete(K key);

	/**
	 * Return the maximum key in the subtree
	 * 
	 * @return maximum key
	 * @throws TreeIsEmptyException if the tree is empty
	 */
	K max() throws TreeIsEmptyException;

	/**
	 * Return the minimum key in the subtree
	 * 
	 * @return minimum key
	 * @throws TreeIsEmptyException if the tree is empty
	 */
	K min() throws TreeIsEmptyException;

	/**
	 * Return number of keys that are bound in this tree.
	 * 
	 * @return number of keys that are bound in this tree.
	 */
	int size();

	/**
	 * Add all keys bound in this tree to the collection c.
	 * The elements must be added in their sorted order.
	 * 
	 * @param c -
	 *            A list that acts as an accumulator for keys.  Keys
	 *            are inserted in the list in increasing order. You may not
	 *            use any sorting method or Collections.sort to keep the 
	 *            list sorted.
	 */
	void addKeysToCollection(Collection<K> c);
	
	/**
	 * Returns a Tree containing all entries between fromKey and toKey, inclusive
	 * 
	 * @param fromKey - Lower bound value for keys in subtree
	 * @param toKey - Upper bound value for keys in subtree
	 * @return Tree containing all entries between fromKey and toKey, inclusive
	 */
	public Tree<K,V> subTree(K fromKey, K toKey);
}