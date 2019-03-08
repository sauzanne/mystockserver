package fr.mystocks.mystockserver.service.finance.measures.constant;

public enum BinaryOperatorEnum {

	GE("measure.comparator.greater.sign"), LE("measure.comparator.less.sign");
	
	private String properties;

	private BinaryOperatorEnum(String properties) {
		this.properties = properties;
	}

	/**
	 * @return the properties
	 */
	public String getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(String properties) {
		this.properties = properties;
	}

	public static MeasureEnum getMeasureEnumByProperties(String properties) {

		if (properties != null) {
			for (MeasureEnum me : MeasureEnum.values()) {
				if (me.getProperties().equals(properties)) {
					return me;
				}
			}
		}
		return null;
	}

}
