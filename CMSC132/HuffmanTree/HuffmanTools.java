import java.util.*;

public class HuffmanTools {

	/**
	 *  The Iterator will provide a sequence of Character objects that will be
	 *  the basis for generating a Huffman Tree.  The return value will be the
	 *  root of the Huffman Tree.  (Obviously, you should use the class 
	 *  HuffmanLeaf to represent the leaves in the tree and HuffmanInternalNode 
	 *  to represent the non-leaves.) 
	 */

	private static HuffmanTree getSmallest(List<HuffmanTree> a){
		HuffmanTree smallestLeaf = null;
		int index = 0;
		int smallestFrequency = Integer.MAX_VALUE;
		while(index < a.size()){
			if(a.get(index).getFrequency() < smallestFrequency){
				smallestFrequency = a.get(index).getFrequency();
				smallestLeaf = a.get(index);
			}
			index++;
		}
		return smallestLeaf;
	}

	public static HuffmanTree buildHuffmanTree(Iterator<Character> symbols) {
		HashMap<Character, Integer> document = new HashMap<Character, Integer>(); 
		//Map that will hold the keys with the frequency
		Character symbol = null; // Symbol to be put in the map
		HuffmanLeaf leaf = null; // the leaf with the symbol and it's frequency
		while(symbols.hasNext()){
			symbol = symbols.next();
			if(document.containsKey(symbol)){
				document.put(symbol, document.get(symbol)+ 1);
			}else{
				document.put(symbol, 1);
			}
		}
		/*This iterates through the symbols and adds the symbols to the HashMap and increments
		 * the frequency of the characters. If the characters aren't already in the document, then
		 * the frequency is just set to 0. 
		 */

		List<HuffmanTree> listOfLeaves = new ArrayList<HuffmanTree>();
		for(Character key: document.keySet()){
			leaf = new HuffmanLeaf(key,document.get(key));
			listOfLeaves.add(leaf);
		}
		/*This loop iterates through the keySet of the document, making each key and its frequency
		 * a leaf. It then adds the leaf to the list.
		 */
		HuffmanInternalNode internalTree = null; // the internal node with the smallest 2 leaves. 
		HuffmanTree tempSmall1 = null; // holder for the smallest leaf
		HuffmanTree tempSmall2 = null; // holder for the second smallest leaf
		while(listOfLeaves.size() > 1){
			tempSmall1 = getSmallest(listOfLeaves);
			listOfLeaves.remove(getSmallest(listOfLeaves));
			tempSmall2 = getSmallest(listOfLeaves);
			listOfLeaves.remove(getSmallest(listOfLeaves));
			internalTree = new HuffmanInternalNode(tempSmall1,tempSmall2);
			listOfLeaves.add(internalTree);
		}
		return listOfLeaves.get(0);  
	}	

	/**
	 *  The parameter "tree" is an existing Huffman Tree; the parameter
	 *  "symbols" is an iterator that will cycle through some Character objects.
	 *  
	 *  This method will involve a loop.  Each pass through the loop will remove
	 *  one Character from the Iterator and call the "findCode" method on the
	 *  Huffman Tree, which will return a String of '0' and '1' characters.
	 *  
	 *  The return value for this method is the concatenation of all of the
	 *  return values from the successive calls to "findCode".
	 */
	public static String encode(HuffmanTree tree, Iterator<Character> symbols) {
		String code = "";
		Character symbol = null;
		while(symbols.hasNext()){
			symbol = symbols.next();
			code = code + tree.findCode(symbol);
		}
		return code;
	}

	/**
	 *  The parameter "tree" is an existing Huffman Tree; the parameter "bits"
	 *  is an Iterator that will cycle through a sequence of '0' and '1' 
	 *  Character objects.  
	 * 
	 *  This method will consist of a loop that will run as long as the 
	 *  Iterator still "hasNext()".  Each pass through the loop will call
	 *  the "decode" method on the tree.  The decode method in the 
	 *  HuffmanInternalNode class will extract from the Iterator whatever 
	 *  bits are necessary to decode a particular character, so DO NOT CALL 
	 *  "bits.next()" IN THIS METHOD!
	 * 
	 *  The return value is a String which consists of the concatenation of
	 *  all Characters returned by the successive calls to "decode" on the tree.
	 */
	public static String decode(HuffmanTree tree, Iterator<Character> bits) {
		String characters = "";
		while(bits.hasNext()){
			characters = characters + tree.decode(bits);
		}
		return characters;
		//throw new UnsupportedOperationException("You must implement this");
	}
}
