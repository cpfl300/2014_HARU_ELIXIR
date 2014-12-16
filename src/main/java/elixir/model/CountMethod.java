package elixir.model;

public class CountMethod {
	
	private int id;
	private int value;
	private Unit unit;
	
	// empty
	public CountMethod() { }

	
	// setter getter
	public void setId(int id) {
		this.id = id;		
	}
	
	public int getId() {
		return id;
	}
	
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}



}
