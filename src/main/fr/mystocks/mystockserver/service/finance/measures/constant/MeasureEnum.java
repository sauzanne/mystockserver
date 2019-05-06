package fr.mystocks.mystockserver.service.finance.measures.constant;

public enum MeasureEnum {

	MM10("technical.analysis.mm10", 10), MM150("technical.analysis.mm150", 150), MM100("technical.analysis.mm100", 100),
	MM200("technical.analysis.mm200", 200), MM50("technical.analysis.mm50", 50), LASTPRICE("common.price.last", 1),
	VALUATION_PE("valuation.pe", null);

	private String properties;

	private Integer duration;

	private MeasureEnum(String properties, Integer duration) {
		this.properties = properties;
		this.duration = duration;
	}

	/**
	 * @return the properties
	 */
	public String getProperties() {
		return properties;
	}

	/**
	 * @return the duration
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * @param duration the period to set
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
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
