import java.util.*;

/*
 *  Please see the javadoc for the interface HuffmanTree for information about
 *  how to implement the methods of this class.
 */
public class HuffmanInternalNode implements HuffmanTree {

	// You may NOT add any other fields to this class
	private HuffmanTree leftChild, rightChild;

	public HuffmanInternalNode(HuffmanTree left, HuffmanTree right) {
		leftChild = left;
		rightChild = right;
	}

	public int getFrequency() {
		int sum = 0;
		sum = this.leftChild.getFrequency() + this.rightChild.getFrequency();
		return sum;
	}

	public String findCode(Character symbol) {
		String code = "";
		if(this.leftChild.findCode(symbol) != null) 
				{
			code += '0' + this.leftChild.findCode(symbol);
		
			return code;
		}
		if(this.rightChild.findCode(symbol) != null) 
				{
			code += '1' + this.rightChild.findCode(symbol);
			return code;
		}
		return null;
	}

	public Character decode(Iterator<Character> it) {
		Character zero = new Character('0');
		Character one = new Character('1');

		if(it.hasNext()){
			if(it.next().equals(zero)){
				return leftChild.decode(it);
			}else {
				return rightChild.decode(it);
			}
		}else return null;
	}

	public int countNodes() {
		int numberOfNodes = 1 + this.leftChild.countNodes() + this.rightChild.countNodes();
		
		return numberOfNodes;
	}

	public int findDepth(Character c) {
		int size = 0;
		if(findCode(c) == null){
			size = -1;
			return size;
		}
		size = findCode(c).length();
		return size;
	}

	public String toString() {
		return display("");
	}

	public String display(String indentation) {
		return indentation + "Left: \n" + leftChild.display(indentation + "  ") + 
		"\n" + indentation + "Right: \n" + rightChild.display(indentation + "  ");
	}
}
