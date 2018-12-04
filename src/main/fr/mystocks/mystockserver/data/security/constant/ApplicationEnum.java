package fr.mystocks.mystockserver.data.security.constant;

public enum ApplicationEnum {
    MYSTOCKS("mystocks");
    
    private final String name;

    /**
     *
     * @param name
     */
    private ApplicationEnum(String name) {
	this.name = name;
    }

    /**
     * @author sauzanne @return the name
     */
    public String getName() {
        return name;
    }

    
    
}
