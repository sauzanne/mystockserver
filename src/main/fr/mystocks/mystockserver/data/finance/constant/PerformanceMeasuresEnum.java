package fr.mystocks.mystockserver.data.finance.constant;


public enum PerformanceMeasuresEnum {
	EARNINGS("e"), REVENUES("r"), EBIT("et"), EBITDA("eda"), CURRENT_EBIT("ce"), BOOK("b"), DIVIDEND("d"), NET_DEBT("nd"), NET_GEARING("ng"),  NUMBER_SHARES("ns"), CAPITALIZATION("cap"), PRICE("p");

	//, GROSS_PROFIT("gp"), QUICK_RATIO("qr"),NET_GEARING("ng"),
	private String label;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}


	PerformanceMeasuresEnum(String label) {
		this.label = label;
	}

}
