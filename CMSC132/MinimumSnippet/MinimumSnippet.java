import java.util.ArrayList;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;
/**
 * When you do a web search, the results page shows you a 
 * <a href="http://searchengineland.com/anatomy-of-a-google-snippet-38357">snippet</a> 
 * for each result, showing you search terms in context. For purposes of this project, a
 * snippet is a subsequence of a document that contains all the search terms.
 * 
 * For this project, you will write code that, given a document (a sequence of
 * words) and set of search terms, find the minimal length subsequence in the
 * document that contains all of the search terms.
 * 
 * If there are multiple subsequences that have the same minimal length, you may
 * return any one of them.
 * 
 */
public class MinimumSnippet {

	/**
	 * Compute minimum snippet.
	 * 
	 * Given a document (represented as an List<String>), and a set of terms
	 * (represented as a List<String>), find the shortest subsequence of the
	 * document that contains all of the terms.
	 * 
	 * This constructor should find the minimum snippet, and store information
	 * about the snippet in fields so that the methods can be called to query
	 * information about the snippet. All significant computation should be done
	 * during construction.
	 * 
	 * @param document
	 *            The Document is an Iterable<String>. Do not change the
	 *            document. It is an Iterable, rather than a List, to allow for
	 *            implementations where we scan very large documents that are
	 *            not read entirely into memory. If you have problems figuring
	 *            out how to solve it with the document represented as an
	 *            Iterable, you may cast it to a List<String>; in all but a very
	 *            small number of test cases, in will in fact be a List<String>.
	 * 
	 * @param terms
	 *            The terms you need to look for. The terms will be unique
	 *            (e.g., no term will be repeated), although you do not need to
	 *            check for that. There should always be at least one term and 
	 *            your code should
	 *            throw an IllegalArgumentException if "terms" is
	 *            empty.
	 * 
	 * 
	 */
	public int minimumSnippetSize; // the smallest size a snippet can be
	private int termsSize; // length of the terms list
	private Iterable<String> doc;
	private List<String> termSt;
	public ArrayList<String> minimumSnippet; // the smallest snippet
	public ArrayList<String> docInArray; // where the copy of document is stored
	int beginingOfSnippet; // end of the snippet 
	int endOfSnippet; // beginning of the snippet

	public MinimumSnippet(Iterable<String> document, List<String> terms) {
		ArrayList<String> temporarySnippet = new ArrayList<String>(); // holder for the snippet
		termsSize = terms.size();
		this.doc = document;
		this.termSt = terms;
		minimumSnippetSize = termSt.size();
		minimumSnippet = new ArrayList<String>();
		docInArray = convertToArrayList(this.doc); 
		if(terms.isEmpty()){
			throw new IllegalArgumentException("There were no terms entered for the search!");
		}else{
			if(foundAllTerms() == true){
				boolean shouldAddTerm = false;
				int positionCount = 0;
				while((temporarySnippet.size() != minimumSnippetSize)){
					for(int currentElement = positionCount; (currentElement < docInArray.size()) && 
					!temporarySnippet.containsAll(termSt); currentElement++){
						for(String element: termSt){
							if(element.equals(docInArray.get(currentElement)) && shouldAddTerm == false){
								//firstAppearanceOfTerm = currentElement;
								shouldAddTerm = true;
							} 
							if(shouldAddTerm == true && !(temporarySnippet.containsAll(termSt))){
								temporarySnippet.add(docInArray.get(currentElement));
								if(temporarySnippet.size() == 1){
									beginingOfSnippet = currentElement;

								}
								if(temporarySnippet.size() == minimumSnippetSize && 
										temporarySnippet.containsAll(terms)){
									endOfSnippet = currentElement;
								}
								if(temporarySnippet.containsAll(termSt)){
									shouldAddTerm = false;
								}
								break;
							}
						}
					}
					if(minimumSnippetSize == temporarySnippet.size() && temporarySnippet.containsAll(terms)){
						minimumSnippet = temporarySnippet;
						break;
					}else{
						temporarySnippet = new ArrayList<String>();
						positionCount++;
					}
					if(positionCount == docInArray.size()){
						positionCount = 0;
						minimumSnippetSize++;
					}
				}
			}
		}

	}



	/*
	 * Creates a copy of the document in an ArrayList.
	 * 
	 * @return copy of documents as an ArrayList.
	 */
	private ArrayList<String> convertToArrayList(Iterable<String> file){
		ArrayList<String> newDocument = new ArrayList<String>();
		file = this.doc;
		for(String element: file){
			newDocument.add(element);
		}
		return newDocument;
	}


	/**
	 * Returns whether or not all terms were found in the document. If all terms
	 * were not found, then none of the other methods should be called.
	 * 
	 * @return whether all terms were found in the document.
	 */
	public boolean foundAllTerms() {
		if(docInArray.containsAll(termSt) == false){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * Return the starting position of the snippet
	 * 
	 * @return the index in the document of the first element of the snippet
	 */
	public int getStartingPos() {
		if(foundAllTerms() != true){
			throw new IllegalArgumentException("There were no search terms, so the snippet wasn't made.");
		}else{
			return beginingOfSnippet;
		}
	}

	/**
	 * Return the ending position of the snippet
	 * 
	 * @return the index in the document of the last element of the snippet
	 */
	public int getEndingPos() {
		if(foundAllTerms() != true){
			throw new IllegalArgumentException("There were no search terms, so the snippet wasn't made.");
		}else{
			return endOfSnippet;
		}
	}

	/**
	 * Return total number of elements contained in the snippet.
	 * 
	 * @return
	 */
	public int getLength() {
		if(foundAllTerms() != true){
			throw new IllegalArgumentException("There were no search terms, so the snippet wasn't made.");
		}else{
			return minimumSnippet.size();
		}
	}

	/**
	 * Returns the position of one of the search terms as it appears in the original document
	 * 
	 * @param index index of the term in the original list of terms.  For example, if index
	 * is 0 then the method will return the position (in the document) of the first search term.
	 * If the index is 1, then the method will return the position (in the document) of the
	 * second search term.  Etc.
	 *  
	 * @return position of the term in the document
	 */
	public int getPos(int index) {
		int position = 0;
		if(foundAllTerms() != true){
			throw new IllegalArgumentException("There were no search terms in the document!");
		}else{
			for(int positionInDoc = beginingOfSnippet; positionInDoc <= endOfSnippet; positionInDoc++){
				if(termSt.get(index).equals(docInArray.get(positionInDoc))){
					position = positionInDoc;
				}
			}
		}
		return position;
	}

}
