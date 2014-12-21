package elixir.model;

public enum CountUnit {
	
	MIN("minute"), HOUR("hour"), DAY("day");
	
	final private String name;
	
	private CountUnit(String name) {
		this.name = name;
	}

	public String getName() {
		
		return name;
	}

	 public static CountUnit getEnum(String value) {
        for(CountUnit v : values())
            if(v.getName().equalsIgnoreCase(value)) return v;
        
        throw new IllegalArgumentException();
    }
	
	
}
