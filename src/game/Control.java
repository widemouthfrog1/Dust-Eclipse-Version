package game;

public class Control {
	
	public static enum State {INACTIVE, PRESSED, HELD, RELEASED}
	private State state;
	private String name;
	private String button;
	
	public Control(String name, String button) {
		state = State.INACTIVE;
		this.name = name;
		this.button = button;
	}
	
	public String name() {
		return name;
	}
	
	public String button() {
		return button;
	}
	
	public boolean updateButton(String button) {
		this.button = button;
		return true;
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof Control)) {
			return false;
		}else {
			Control control = (Control)other;
			return this.name.equals(control.name);
		}
	}

	public State state() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

}
