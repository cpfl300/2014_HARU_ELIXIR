package elixir.model;

public enum Unit {
	
	MIN("minute"), HOUR("hour"), DAY("day");
	
	final private String name;
	
	private Unit(String name) {
		this.name = name;
	}

	public String getName() {
		
		return name;
	}

	 public static Unit getEnum(String value) {
        for(Unit v : values())
            if(v.getName().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }
	
	
}
