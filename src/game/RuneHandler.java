package game;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logic.Vector;

/**
 * This class handles the many types of runes I intend to implement without having to make many of the same checks twice.
 * Another benefit of this class is that all the reflection required to do this is separated from the rest of the program
 * 
 * @author Karl Bennett
 *
 */
public class RuneHandler {
	private Map<Long, Class<? extends Rune>> classes = new HashMap<Long, Class<? extends Rune>>();
	private ArrayList<Method> checks = new ArrayList<Method>();
	
	RuneHandler(){
		
	}
	
	/**
	 * Adds the Rune type to the handler so that  
	 * @param runeClass
	 * 		A class that extends Rune to be included in this game
	 * @param checks
	 * 		Methods must be static and return a boolean
	 */
	public void addRune(Class<? extends Rune> runeClass, List<Method> checks) {
		//Add method to list without duplicates
		
		for(Method method : checks) {
			if(!this.checks.contains(method)) {
				this.checks.add(method);
			}
		}
		long code = 0L;
		for(int i = 0; i < this.checks.size(); i++) {
			if(checks.contains(this.checks.get(i))){
				code += (long)Math.pow(2,i);
			}
		}
		if(this.classes.containsKey(code)) {
			throw new DuplicateRuneException();
		}
		//code = long representation of which checks need to pass to create this rune
		this.classes.put(code, runeClass);
		
	}
	/**
	 * Thrown to indicate two rune types have the checks 
	 * @author Karl Bennett
	 *
	 */
	public class DuplicateRuneException extends RuntimeException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public DuplicateRuneException() { super(); }
	}

	public Rune getRune(ArrayList<Vector> points) {
		long code = 0L;
		for(int i = 0; i < checks.size(); i++) {
			try {
				if((boolean)checks.get(i).invoke(null, points)) {
					code += Math.pow(2, i);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		try {
			return (Rune) (classes.get(code)).getDeclaredConstructors()[0].newInstance(points);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		return null;
	}
}
