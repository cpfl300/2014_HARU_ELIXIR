package elixir.model;

public enum ServiceUnit {
	
	MORNING(0), AFTERNOON(1);
	
	final private int value;
	
	private ServiceUnit(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static ServiceUnit getEnum(int value) {
		for (ServiceUnit v : values())
			if (v.getValue() == value) return v;
		
		throw new IllegalArgumentException();
	}

}
