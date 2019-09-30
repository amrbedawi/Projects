import java.util.Arrays;

/**
 * WordGram objects represent a k-gram of strings/words.
 * 
 * @author AMR BEDAWI
 *
 */

public class WordGram {
	
	private String[] myWords;   
	private String myToString;  // cached string
	private int myHash;         // cached hash value

	/**
	 * Create WordGram (add comments)
	 * @param source - an array of words to include in the WordGram
	 * @param start - the starting index
	 * @param size - the size of the WordGram
	 */
	public WordGram(String[] source, int start, int size) {
		myWords = new String[size];
		// TODO: initialize myWords and ...
		for(int k = 0; k < size; ++k) {
			myWords[k] = source[start + k]; 
		}
		myHash = 0; 
		myToString = ""; 
	}

	/**
	 * Return string at specific index in this WordGram
	 * @param index in range [0..length() ) for string 
	 * @return string at index
	 */
	public String wordAt(int index) {
		if (index < 0 || index >= myWords.length) {
			throw new IndexOutOfBoundsException("bad index in wordAt "+index);
		}
		return myWords[index];
	}

	/**
	 * Complete this comment
	 * @return the length of the myWords instance variable
	 */
	public int length(){
		// TODO: change this
		int howLong = this.myWords.length; 
		return howLong; 
	}


	@Override
	public boolean equals(Object o) {
		 
		if (! (o instanceof WordGram) || o == null){
			return false;
		}
			WordGram wg = (WordGram) o;
			if(this.toString().equals(wg.toString())) {
				return true; 
			}
			return false; 
	}

	@Override
	public int hashCode(){
		// TODO: complete this method
		String allTheWords = this.toString(); 
		myHash = allTheWords.hashCode();  
		return myHash;
	}
	

	/**
	 * Create and complete this comment
	 * @param last is last String of returned WordGram
	 * @return wg returns a new WordGram object that has the shifted words
	 */
	public WordGram shiftAdd(String last) {
		WordGram wg = new WordGram(myWords,0,myWords.length);
		// TODO: Complete this method
		 
		for(int k = 0; k < (myWords.length - 1); k++) {
			wg.myWords[k] = wg.myWords[k+1];
		}
		wg.myWords[(wg.myWords.length - 1)] = last;
		return wg;
	}

	@Override
	public String toString(){
		// TODO: Complete this method
		if(myToString.equals("")){
			myToString = String.join(" ", myWords); 
		}
		
		return myToString;
	}
}
