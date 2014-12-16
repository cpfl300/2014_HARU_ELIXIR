package elixir.model;

public enum ServiceUnit {
	
	MORNING(false), AFTERNOON(true);
	
	final private boolean value;
	
	private ServiceUnit(boolean value) {
		this.value = value;
	}

	public boolean getValue() {
		return value;
	}

	public static ServiceUnit getEnum(boolean value) {
		for (ServiceUnit v : values())
			if (v.getValue() == value) return v;
		
		throw new IllegalArgumentException();
	}

}
