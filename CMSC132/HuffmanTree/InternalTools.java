import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/*
 *  Feel free to use these, if you find them helpful.  Take a look at the PublicTests
 *  to see examples.
 */
public class InternalTools {
	
	public static Iterator<Character> getCharacterIteratorFromString(String s) {
		List<Character> letters = new LinkedList<Character>();
		for (int i = 0; i < s.length(); i++)
			letters.add(new Character(s.charAt(i)));
		return letters.iterator();
	}
	
	public static Iterator<Character> getCharacterIteratorFromFile(String fileName) {
		InputStreamReader r = getReader(fileName);
		List<Character> letters = new LinkedList<Character>();
	
		try {
			while(true) {
				int value = r.read();
				if (value == -1)
					break;
				Character c = new Character((char)value);
				letters.add(c);
			}
		}
		catch(IOException e) {
			System.out.println("IO Exception!");
			System.exit(1);
		}
		return letters.iterator();
	}
	
	private static InputStreamReader getReader(String fileName) {
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(fileName);
		}
		catch (FileNotFoundException e) {
			System.out.println("Error -- file not found.");
			System.exit(1);
		}
		return new InputStreamReader(fis);
	}
}
