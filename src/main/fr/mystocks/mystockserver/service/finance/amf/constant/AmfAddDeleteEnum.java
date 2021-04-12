package fr.mystocks.mystockserver.service.finance.amf.constant;

public enum AmfAddDeleteEnum {

	Add("A"), Delete("D");

	String value;

	private AmfAddDeleteEnum(String value) {
		this.value = value;
	}

	public static AmfAddDeleteEnum getAmfAddDeleteByValue(String value) {

		if (value != null) {
			for (AmfAddDeleteEnum me : AmfAddDeleteEnum.values()) {
				if (me.getValue().equals(value)) {
					return me;
				}
			}
		}
		return null;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

}
