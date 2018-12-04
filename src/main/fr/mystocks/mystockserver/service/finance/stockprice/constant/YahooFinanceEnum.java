package fr.mystocks.mystockserver.service.finance.stockprice.constant;

public enum YahooFinanceEnum {

    URL("https://query1.finance.yahoo.com/v7/finance/download/"), QUOTE("quotes.csv"), M1("a="), D1("b="), Y1("c="), M2(
	    "d="), D2("e="), Y2("f="), END1("g=d"), END2("ignore=.csv"), SEPLINE("\\n"), SEPCOL(","), SEPDATE(
		    "-"), PERIOD1("period1"), PERIOD2(
			    "period2"), INTERVAL("interval"), EVENTS("events"), CRUMB("crumb"), COOKIES_NAME("B"), HISTORY("history"), P("p=");

    public String label;

    /** Constructeur */
    YahooFinanceEnum(String pLabel) {
	this.label = pLabel;
    }
}
