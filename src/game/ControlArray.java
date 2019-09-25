package game;

import java.util.HashMap;
import java.util.ArrayList;

public class ControlArray {
	
	private HashMap<String, Control> map;
	
	public ControlArray() {
		this.map = new HashMap<String, Control>();
	}
	
	public void addControl(String name, String button) {
		Control control = new Control(name, button);
		this.map.put(name, control);
	}
	
	public Control getControl(String name) {
		return this.map.get(name);
	}
	
	public ArrayList<Control> getList(){
		ArrayList<Control> list = new ArrayList<Control>();
		for(String name : this.map.keySet()) {
			list.add(this.map.get(name));
		}
		return list;
	}

}
