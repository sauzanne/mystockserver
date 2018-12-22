package fr.mystocks.mystockserver.service.finance.measures.constant;

public enum MeasureEnum {

	MM10("technical.analysis.mm10"), MM150("technical.analysis.mm150"), MM200("technical.analysis.mm200");

	private String properties;

	private MeasureEnum(String properties) {
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

}
