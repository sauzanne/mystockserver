package fr.mystocks.mystockserver.technic.exceptions;

public enum FunctionalExceptionEnum {
	
	DEFAULT;

	private String code;
	protected String label;

	/** Constructeur */
	private FunctionalExceptionEnum(String code, String pLabel) {
		this.code= code;
		this.label = pLabel;
	}

	private FunctionalExceptionEnum() {
	}

	/**
	 * Get state with a code
	 * 
	 * @param code
	 * @return
	 */
	public static FunctionalExceptionEnum valueOfByCode(String code) {
		for (FunctionalExceptionEnum state : values()) {
			if (code.equalsIgnoreCase(state.getCode())) {
				return state;
			}
		}
		return null;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
