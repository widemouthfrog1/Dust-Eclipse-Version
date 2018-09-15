package logic;

import java.util.ArrayList;
import java.util.List;

public class TreeMap<K,V> {
	private Node<K,V> root;
	private Node<K,V> pointer;

	public TreeMap(V defaultValue) {
		root = new Node<K,V>(null, defaultValue, null);
		pointer = root;
	}
	public TreeMap() {
		root = new Node<K,V>(null, null, null);
		pointer = root;
	}
	
	public boolean add(V value, List<K> route) {
		return root.addChild(value, route);
	}
	
	public V get(List<K> route) {
		return root.getValue(route);
	}
	
	public void setDefaultValue(V value) {
		root.setValue(value);
	}
	/**
	 * Gets the next unvisited child in a depth first traversal where backtracking is done manually with the use of previous()
	 * @return
	 * 		The key value of the child. Null if at the end of a branch
	 */
	public K next() {
		for(Node<K,V> child : pointer.children) {
			if(!child.visited) {
				pointer = child;
				pointer.visited = true;
				return pointer.key;
			}
		}
		return null;
	}
	/**
	 * Resets all nodes to be unvisited and sets the pointer for next() to the root of the tree
	 */
	public void resetPointer() {
		root.setUnvisited();
		pointer = root;
	}
	/**
	 * Backtracks one step in the tree. The node that was left is still visited so the next function will ignore it
	 */
	public void previous() {
		pointer = pointer.parent;
	}

	public static class Node<K,V> {
		private K key;
		private V value;
		private boolean visited = false;
		private Node<K,V> parent;
		private List<Node<K,V>> children;
		Node(K key, V value, Node<K,V> parent){
			this.key = key;
			this.value = value;
			this.parent = parent;
			this.children = new ArrayList<Node<K,V>>();
		}
		
		private void setUnvisited() {
			visited = false;
			for(Node<K,V> child : children) {
				child.setUnvisited();
			}
		}
		
		private void setValue(V value) {
			this.value = value;
		}
		
		private V getValue(List<K> route) {
			if(route.isEmpty()) {
				return value;
			}
			Node<K,V> next = this.getImmediateChild(route.remove(0));
			if(next == null) {
				return null;
			}
			return next.getValue(route);
		}
		
		private Node<K,V> getImmediateChild(K key) {
			for(Node<K,V> child : children) {
				if(child.key.equals(key)) {
					return child;
				}
			}
			return null;
		}
		
		private boolean addChild(V value, List<K> route) {
			if(route.size() == 1) {
				for(Node<K,V> child : children) {
					if(child.key.equals(route.get(0))){
						return false;
					}
				}
				children.add(new Node<K,V>(route.get(0),value, this));
				return true;
			}else {
				K key = route.remove(0);
				Node<K,V> next = this.getImmediateChild(key);
				if(next == null) {
					return new Node<K,V>(key, null, this).addChild(value, route);
				}
				return next.addChild(value, route);
			}
			
		}
	}
}
