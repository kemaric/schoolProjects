import java.util.*;

/*
 *  Please see the javadoc for the interface "HuffmanTree" for information about
 *  how to implement the methods of this class.
 */
public class HuffmanLeaf implements HuffmanTree {
	
	// You may not add any other fields to this class
	private Character data;
	private int frequency;
	
	public HuffmanLeaf(Character label, int count) {
		this.data = label;
		this.frequency = count;
	}
	
	/**
	 *  Increment this leaf's frequency
	 */
	public void incrementFrequency() {
		frequency++;
	}
	
	public Character getData() {
		return data;
	}
	
	public int getFrequency() {
		return frequency;
	}
	
	public String findCode(Character symbol) {
		String code = "";
		if(this.data.equals(symbol)){
			return code = "";
		}
		return null;
	}
	
	public Character decode(Iterator<Character> it) {
		return getData();
	}
	
	public int countNodes() {
		 int numNode = 1;
		return numNode;
	}
	
	public int findDepth(Character c) {
		if(this.data.equals(c)){
			return 1;
		}else{
			return 0;
		}
	}
	
	public String display(String indentation) {
		return indentation + toString();
	}
	
	public String toString() {
		return frequency + ": " + data;
	}
}
