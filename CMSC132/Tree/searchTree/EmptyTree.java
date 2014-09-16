package searchTree;

import java.util.Collection;

/**
 * This class is used to represent the empty search tree: a search tree that
 * contains no entries.
 * 
 * This class is a singleton class: since all empty search trees are the same,
 * there is no need for multiple instances of this class. Instead, a single
 * instance of the class is created and made available through the static field
 * SINGLETON.
 * 
 * The constructor is private, preventing other code from mistakenly creating
 * additional instances of the class.
 *  
 */
 public class EmptyTree<K extends Comparable<K>,V> implements Tree<K,V> {
	/**
	 * This static field references the one and only instance of this class.
	 * We won't declare generic types for this one, so the same singleton
	 * can be used for any kind of EmptyTree.
	 */
	private static EmptyTree SINGLETON = new EmptyTree();


	public static  <K extends Comparable<K>, V> EmptyTree<K,V> getInstance() {
		return SINGLETON;
	}

	/**
	 * Constructor is private to enforce it being a singleton
	 *  
	 */
	private EmptyTree() {
		// Nothing to do
	}
	
	/**
	 * Since the tree is empty, when search is called, the value returned is null.
	 */
	public V search(K key) {
		return null;
	}
	
	/**
	 * This inserts a NonEmptyTree in the place of the current empty tree.
	 */
	public NonEmptyTree<K, V> insert(K key, V value) {
		NonEmptyTree<K,V> newTree = new NonEmptyTree<K,V>(key, value);
		return newTree;
	}
	
	/**
	 * Since the tree is empty, when delete is called, the current EmptyTree is just returned.
	 */
	public Tree<K, V> delete(K key) {
		return this;
	}
	
	/**
	 * Since the tree is empty, when max is called, the method throws a new TreeIsEmptyException() since there 
	 * are no keys that can be a max. 
	 */
	public K max() throws TreeIsEmptyException {
		throw new TreeIsEmptyException();
	}

	/**
	 * Since the tree is empty, when min is called, the method throws a new TreeIsEmptyException() since there 
	 * are no keys that can be a min. 
	 */
	public K min() throws TreeIsEmptyException {
		throw new TreeIsEmptyException();
	}

	/**
	 * Since the tree is empty, the size of the tree is just 0.
	 */
	public int size() {
		return 0;
	}

	/**
	 * Since the tree is empty, when addKeysToCollection is called, it does nothing since there are no keys in the
	 * empty tree to add to a list.
	 */
	public void addKeysToCollection(Collection<K> c) {
		return;
	}

	/**
	 * Since the tree is empty, when subTree is called, the method just returns the current EmptyTree since there are 
	 * no keys to do a subtree. 
	 */
	public Tree<K,V> subTree(K fromKey, K toKey) {
		return this;
	}
}