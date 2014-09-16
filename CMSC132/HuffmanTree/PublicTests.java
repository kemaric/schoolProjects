import junit.framework.TestCase;
import java.util.*;

public class PublicTests extends TestCase {
	public void testHuffmanTreeBuilder() {
		String input = "ABCA";
		Iterator<Character> it = InternalTools.getCharacterIteratorFromString(input);
		HuffmanTree tree = HuffmanTools.buildHuffmanTree(it);
		assertEquals(5, tree.countNodes());
		assertEquals(input.length(), tree.getFrequency());
		assertEquals(1, tree.findDepth(new Character('A')));
		assertEquals(2, tree.findDepth(new Character('B')));
		assertEquals(2, tree.findDepth(new Character('C')));
	}

	public void testEncoding() {
		String input = "ABCA";
		Iterator<Character> it = InternalTools.getCharacterIteratorFromString(input);
		HuffmanTree tree = HuffmanTools.buildHuffmanTree(it);
		it = InternalTools.getCharacterIteratorFromString("AAA");
		String code = HuffmanTools.encode(tree, it);
		assertTrue(code.equals("111") || code.equals("000"));
		it = InternalTools.getCharacterIteratorFromString("CCC");
		code = HuffmanTools.encode(tree, it);
		assertTrue(code.equals("010101") || code.equals("000000") ||
				code.equals("101010") || code.equals("111111"));
	}

	public void testDecoding() {
		String input = "ABCA";
		Iterator<Character> it = InternalTools.getCharacterIteratorFromString(input);
		HuffmanTree tree = HuffmanTools.buildHuffmanTree(it);
		it = InternalTools.getCharacterIteratorFromString("0011");
		String message = HuffmanTools.decode(tree, it);
		assertTrue(message.equals("AAC") || message.equals("AAB") ||
				message.equals("BAA") || message.equals("CAA"));
	}

	public void testFindCode(){
		String input = "ABCA";
		Iterator<Character> it = InternalTools.getCharacterIteratorFromString(input);
		HuffmanTree tree = HuffmanTools.buildHuffmanTree(it);
		String code = tree.findCode('A');
		assertTrue(code.equals("0"));
		String codeC = tree.findCode('C');
		assertTrue(codeC.equals("11"));
	}
	public void testDecodingBig(){
		String input = "";
			for(int i = 0; i < 35; i++){
				if(i < 5){
					input = input + "A";
				}
				if(i < 10){
					input = input + "BC";
				}if(i < 15){
					input = input + "D";
				}if(i < 20){
					input = input + "E";
				}if(i < 25){
					input = input + "F";
				}if(i< 30){
					input = input + "G";
				}if(i < 35){
					input = input + "H";
				}

			}

		Iterator<Character> it = InternalTools.getCharacterIteratorFromString(input);
		HuffmanTree tree = HuffmanTools.buildHuffmanTree(it);
		System.out.print(tree.toString());
		String codeH = tree.findCode('A');
		System.out.println();
		System.out.print(codeH);
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();


		//assertTrue(codeH.equals("10"));
		//String codeA = tree.findCode('A');
		//assertTrue(codeA.equals("11010"));
		//String codeG = tree.findCode('G');
		//assertTrue(codeG.equals("00"));
	}
	
	public void test5() {
		String input = "ASDFGHJKLPOIUYTREWQA";
		Iterator<Character> it = InternalTools.getCharacterIteratorFromString(input);
		HuffmanTree tree = HuffmanTools.buildHuffmanTree(it);
		System.out.println(tree.toString());
		System.out.println(tree.findCode('A'));
	}
}
