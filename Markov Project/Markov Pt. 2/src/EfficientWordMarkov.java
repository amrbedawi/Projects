import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class EfficientWordMarkov extends BaseWordMarkov{
	/**
	 * Create a new instance variable myMap
	 */
	private Map<WordGram, ArrayList<String>> myMap; 
	
	/**
	 * Constructor of an EfficientWordMarkov Object
	 * Initializes myMap
	 * @param order
	 */
	public EfficientWordMarkov(int order) {
		super(order);
		myMap = new HashMap<WordGram, ArrayList<String>>(); 
	}
	
	/**
	 * Default Constructor of order two
	 */
	public EfficientWordMarkov() {
		this(2); 
	}
	
	/**
	 * Takes text as a string and splits it at the white spaces
	 * to create a string array of words. That array is iterated through
	 * to create a map where the key is a WordGram object and the value is 
	 * an ArrayList<String> containing all the following words after
	 * every instance of that set of words
	 * @param text
	 */
	@Override
	public void setTraining(String text) {
		
		myWords = text.split("\\s+");
		myMap.clear();
		
		for(int k = 0; k < myWords.length + 1 - myOrder; k += 1) {
			
			if(!myMap.containsKey(new WordGram(myWords, k, myOrder))) {
				myMap.put(new WordGram(myWords, k, myOrder), new ArrayList<String>()); 
			}
			if(k == myWords.length - myOrder) {
				myMap.get(new WordGram(myWords, k, myOrder)).add(PSEUDO_EOS); 
			}
			else {
				myMap.get(new WordGram(myWords, k, myOrder)).add(myWords[k + myOrder]); 
			}
		}
	}
	
	/**
	 * Returns the ArrayList of following words for a wordGram
	 * @param kGram
	 * @return ArrayList<String>
	 */
	@Override
	public ArrayList<String> getFollows(WordGram kGram) {
		if(!myMap.containsKey(kGram)) {
			throw new NoSuchElementException(kGram+" not in map"); 
		}
		return myMap.get(kGram);
	}
	
}
