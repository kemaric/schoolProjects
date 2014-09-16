package searchTree;

import java.util.Collection;

/**
 * This class represents a non-empty search tree. An instance of this class
 * should contain:
 * <ul>
 * <li>A key
 * <li>A value (that the key maps to)
 * <li>A reference to a left Tree that contains key:value pairs such that the
 * keys in the left Tree are less than the key stored in this tree node.
 * <li>A reference to a right Tree that contains key:value pairs such that the
 * keys in the right Tree are greater than the key stored in this tree node.`
 * </ul>
 *  
 */
public class NonEmptyTree<K extends Comparable<K>, V> implements Tree<K, V> {
	private Tree<K,V> left, right; //the left and right branches of the tree.
	private K key; // the key in the tree
	private V value; // the value in the tree 
	private int size; // the number of keys in the tree
	private K maximumKey, minimumKey;

	/**
	 * Constructor for the NonEmptyTree. It sets the key to the parameter and the same with the value. Also, the 
	 * left and right branches are set to the empty tree singleton. 
	 * 
	 * @param key
	 * @param value
	 */
	public NonEmptyTree(K key, V value){
		this.key = key;
		this.value = value;
		left = EmptyTree.getInstance();
		right = EmptyTree.getInstance();
	}
	
	/**
	 * This method searches for the key in the parameter and returns the value of that key. 
	 * If the key is not found in the tree, then the method returns null. 
	 */
	public V search(K key) {
		V searchedVal = null;
		if(this.key.compareTo(key) == 0){ 
			return searchedVal = this.value;
			// if the current key is the same as the parameter, then the method returns that value
		}else if(key.compareTo(this.key) < 0){
			searchedVal = left.search(key);
			/*If the parameter is less than the current key, then searchedVal is made the value of the 
			 * keys as the method traverses through the left branch. 
			 */
		}else{
			searchedVal = right.search(key);
			/*If the parameter is greater than the current key, then searchedVal is made the value of the 
			 * keys as the method traverses through the right branch. 
			 */
		}
		return searchedVal;
	}

	/**
	 * This method inserts a NonEmptyTree into the current tree. If the current tree is empty, then a 
	 * tree with the key parameter and the value parameter is added to the top of the tree. If the current tree 
	 * already has the key, then the value is just updated. If the parameter's key is greater than the current key,
	 * then the key is added to the right, else, it's added to the left.
	 */
	public NonEmptyTree<K, V> insert(K key, V value) {
		if(key.compareTo(this.key) == 0){
			this.value = value;
			// if the parameter is the same as the current key, the he value is just updated.
			return this;
		}else if(key.compareTo(this.key) > 0){
			right = right.insert(key, value);
			// if the parameter is greater than the current key, then the key is added to the right
			return this;
		}else{
			left = left.insert(key, value);
			return this;
		}
	}

	/**
	 * This method deletes the specified key in the tree. If the key is not found in the tree, then the method 
	 * returns the current tree. The method first checks to see if the parameter key is equal to the current key.
	 * If it is, then the method checks the left deletes it by replacing it with the maximum key on the left or 
	 * it checks the right and replaces it with the right most minimum key. 
	 * 
	 * The method also catches a TreeIsEmptyException if the tree is empty. 
	 */
	public Tree<K, V> delete(K key) {
		if(key.compareTo(this.key) == 0){// if the parameter is the current key
			try {
				this.key = left.max();
				this.value = left.search(this.key);
				left = EmptyTree.getInstance();
				return this;
				/*this block tries to delete the key by replacing it with the left max key. It then 
				 * changes the new key to the value of the new key. 
				 */
			} catch (TreeIsEmptyException e) { // if the tree is empty, then the exception is caught here. 
				try{
					this.key = right.min();
					this.value =right.search(this.key);
					right = EmptyTree.getInstance();
					return this;
					/*this block tries to delete the key by replacing it with the right minimum key. It then 
					 * changes the new key to the value of the new key. 
					 */
				}catch(TreeIsEmptyException f){// if the tree is empty, then the exception is caught here.
					return EmptyTree.getInstance(); 
					//if the exception is thrown, it is caught and an emptyTree is returned. 
				}
			}
		}else if(key.compareTo(this.key) > 0){// if the parameter is greater than the current key
			right = right.delete(key);
		}else{ // if the parameter is less than the current key
			left = left.delete(key);
		}
		return this;
	}

	/**
	 * This method recursively goes through the right most branches of the tree, finds and returns the maximum
	 * key in the tree. The method can also can also catch a TreeIsEmptyException where it will just return
	 * the current key. Else, it just returns the maximumKey. 
	 */
	public K max() {
		try{ //might throw a TreeIsEmptyException if the tree is empty
			maximumKey = right.max(); // recursively makes the variable be the right tree. 
		}catch(TreeIsEmptyException a){ 
			return key;
		}
		return maximumKey;

	}
	/**
	 * This method recursively goes through the left most branches of the tree, finds and returns the minimum
	 * key in the tree. The method can also can also catch a TreeIsEmptyException where it will just return
	 * the current key. Else, it just returns the minimumKey. 
	 */
	public K min() {
		try{//might throw a TreeIsEmptyException if the tree is empty
			minimumKey = left.min(); // recursively makes the variable be the left tree.
		}catch(TreeIsEmptyException a){
			return key;
		}
		return minimumKey;
	}

	/**
	 * This method traverses through the left and right branches, counting all the keys
	 * in each of those branches. It then returns the number of keys. 
	 */
	public int size() {
		return 1 + left.size() + right.size();
	}

	
	/**
	 * This method takes the tree and adds the keys from the tree to a given collection. This method uses 
	 * in-order traversal to sort the keys. 
	 */
	public void addKeysToCollection(Collection<K> c) {
		left.addKeysToCollection(c);
		c.add(this.key);
		right.addKeysToCollection(c);
	}

	/**
	 * This method returns a subtree of the tree. With the given fromKey and toKey, the method returns all 
	 * of the keys that are between the fromKey and toKey from the tree. 
	 */
	public Tree<K,V> subTree(K fromKey, K toKey) {
		if(this.key.compareTo(fromKey) < 0){
			return right.subTree(fromKey, toKey); 
			//if the current key is less than the fromKey, then the keys to the right are just returned. 
		}else if(this.key.compareTo(toKey) > 0){
			return left.subTree(fromKey, toKey);
			//if the current key is greater than the toKey, then the keys to the left are just returned. 

		}else{
			NonEmptyTree<K,V> subSubTree = new NonEmptyTree<K,V>(key, value);
			subSubTree.left = left.subTree(fromKey, toKey);
			subSubTree.right = right.subTree(fromKey, toKey);
			return subSubTree;
			/*if the keys in the tree are between from and two key, then the entire tree, from the from and toKey
			 * are just returned as a subtree.
			 */
		}
	}
	/**
	 * Prints the NonEmptyTree with the key, value, and the left and right branches. 
	 */
	@Override
	public String toString() {
		return "NonEmptyTree [key=" + key + ", value=" + value + ", left=" + left + ", right="
		+ right + "]";
	}




}