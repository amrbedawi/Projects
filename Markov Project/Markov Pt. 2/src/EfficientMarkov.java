import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class EfficientMarkov extends BaseMarkov {
	
	/**
	 * Create a new instance variable myMap
	 */
	private Map<String, ArrayList<String>> myMap; 
	
	/**
	 * Constructors an EfficientMarkov Object of a certain order
	 * @param order 
	 */
	public EfficientMarkov(int order) {
		super(order);
		myMap = new HashMap<String, ArrayList<String>>(); 
	}
	
	/**
	 * Default Constructor of order three
	 */
	public EfficientMarkov() {
		this(3); 
	}
	
	/**
	 * Takes a text as a string and creates a map 
	 * that contains substrings of a specified size (myOrder) as the key
	 * and an ArrayList of all the following characters after
	 * every instance of that substring
	 * @param text 
	 */
	@Override
	public void setTraining(String text) {
		
		myText = text; 
		myMap.clear();
		 
		 for(int k = 0; k < (text.length() - myOrder + 1); k += 1) {
			 
			 if(!myMap.containsKey(text.substring(k, k + myOrder))) { 
				 myMap.put(text.substring(k, k + myOrder), new ArrayList<String>()); 
			 }
			 if(k == text.length() - myOrder) {
				 myMap.get(text.substring(k, k + myOrder)).add(PSEUDO_EOS); 
			 }
			 else {
				 myMap.get(text.substring(k, k + myOrder)).add(text.substring(k + myOrder, k + myOrder + 1)); 
			 }
		 } 
		 
	}
	
	/**
	 * Simply returns the ArrayList associated 
	 * with the desired substring
	 * @param text
	 * @return ArrayList<String>
	 */
	@Override
	public ArrayList<String> getFollows(String key) {
		if(!myMap.containsKey(key)) {
			throw new NoSuchElementException(key+" not in map"); 
		}
		return myMap.get(key); 
	}
	
}
