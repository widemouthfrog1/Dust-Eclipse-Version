package game.object.rune;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import logic.TreeMap;
import logic.Vector;

/**
 * This class handles the many types of runes I intend to implement without having to make many of the same checks twice.
 * Another benefit of this class is that all the reflection required to do this is separated from the rest of the program
 * 
 * @author Karl Bennett
 *
 */
public class RuneHandler {
	private TreeMap<Method, Class<? extends Rune>> treeMap = new TreeMap<Method, Class<? extends Rune>>();
	private Rune defaultRune = null;
	public RuneHandler(){}
	
	/**
	 * Adds the Rune type to the handler. Throws a DuplicateRuneException if there is already a rune with those checks in the handler.
	 * @param runeClass
	 * 		A class that extends Rune to be included in this game
	 * @param checks
	 * 		Methods must be static and return a List of points (of type Vector)
	 */
	public void addRune(Class<? extends Rune> runeClass, List<Method> checks) {
		
		if(!treeMap.add(runeClass, checks)) {
			throw new DuplicateRuneException();
		}
		
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

	public Rune getRune(List<Vector> vertices, List<Vector> points) {
		treeMap.resetPointer();
		List<Method> route = new ArrayList<Method>();
		while(true) {	
			try {
				Method method = treeMap.next(); 
				if(method == null) {
					//The drawing matched no known rune
					return defaultRune;
				}
				@SuppressWarnings("unchecked")
				List<Vector> passed = (List<Vector>) method.invoke(null, vertices);
				if(!passed.isEmpty()) {
					route.add(method);
					
					//remove all but the last point of passed
					vertices.removeAll(passed);
					if(vertices.size() == 0) {
						break; //break if all vertices have been used up
					}
					vertices.add(0,passed.get(passed.size()-1));
				}else {
					treeMap.previous();
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
			return (Rune) (treeMap.get(route)).getDeclaredConstructors()[0].newInstance(points);
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
		} catch (NullPointerException e) {
			return null;
		}

		return defaultRune;
	}
}
