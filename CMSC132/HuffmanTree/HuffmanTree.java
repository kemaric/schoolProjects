import java.util.*;

/*
 *  The interface for the HuffmanLeaf and HuffmanInternalNode classes.
 */
public interface HuffmanTree {
	
	/**
	 *   Return the sum of the frequencies of all leaves at the bottom of the
	 *   subtree rooted by the current Node.
	 */
	public int getFrequency();
	
	/**
	 *  Given a Character, find it's code (the sequence of 0's and 1's that
	 *  represent this symbol) in the subtree rooted by the current Node.
	 * 
	 *  The return value is a String of '0' and '1' characters.  (We are not
	 *  really using bits.)  So for example, a typical return value might
	 *  be the String "010101110".
	 * 
	 *  If the symbol does not occur in the subtree rooted at the current node,
	 *  then return null.
	 */
	public String findCode(Character symbol);
	
	/**
	 *  Decode a Character from a sequence of 0's and 1's.
	 * 
	 *  The iterator (it) will provide a sequence of Characters, each of which
	 *  is either a '0' or a '1'.  Note that these are not really bits.  They 
	 *  are Characters, as in "new Character('0')" or "new Character('1')".
	 * 
	 *  The return value is the Character that was found in a leaf of the tree.
	 *  (You may assume that the sequence of 0's and 1's will always yield a 
	 *  legitimate path to a leaf.)
	 * 
	 *  Hint: You will need to extract one bit from the iterator during each call
	 *  to HuffmanInternalNode.decode, but HuffmanLeaf.decode will not need to use
	 *  the iterator at all.
	 */
	public Character decode(Iterator<Character> it);
	
	/**
	 *  Return the number of nodes in the subtree rooted at the current Node.
	 *  Include the current node in the count, as well as all of it's
	 *  descendants, both leaves and non-leaves.
	 */
	public int countNodes();
	
	/** Find the "depth" of the Character c in the subtree rooted at the
	 *  current Node.  For example, if c is found in the current object then
	 *  the return value should be 0.  If c is found in a child of the current
	 *  object, then the return value should be 1.  If c is found in a child of
	 *  a child of the current object, then the return value should be 2.  Etc.
	 * 
	 *  If c is not found anywhere in or below the current Node, the return value
	 *  should be -1.
	 */
	public int findDepth(Character c);
	
	/**
	 *   Helper method for the toString method.  (This is provided for you, so
	 *   don't worry about implementing it.)
	 */
	public String display(String indentation);
}
