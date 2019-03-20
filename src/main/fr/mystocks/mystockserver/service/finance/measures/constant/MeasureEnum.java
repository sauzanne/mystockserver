package fr.mystocks.mystockserver.service.finance.measures.constant;

public enum MeasureEnum {

	MM10("technical.analysis.mm10"), MM150("technical.analysis.mm150"), MM100("technical.analysis.mm100"),
	MM200("technical.analysis.mm200"), MM50("technical.analysis.mm50"), LASTPRICE("common.price.last");

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
