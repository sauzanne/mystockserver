package fr.mystocks.mystockserver.technic.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import fr.mystocks.mystockserver.data.finance.stockprice.StockPrice;

public class DateFinancialTools {

	/**
	 *
	 */
	private DateFinancialTools() {
		super();
	}

	public static boolean isOpenDate(LocalDate date) {

		return !(date.getDayOfWeek() == DayOfWeek.SUNDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY);

	}

	/**
	 * Calcule si ce jour est un jour d'ouverture de la bourse en prenant en compte les jours où le marché est fermé (en paramètre)
 	 * @param date la date à vérifier
	 * @param closingDate la date de fermeture
	 * @return vrai si le jour est un jour de cotation
	 */
	public static boolean isOpenDateWithMarketConditions(LocalDate date, List<LocalDate> closingDate) {
		return !(date.getDayOfWeek() == DayOfWeek.SUNDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY)
				&& closingDate.stream().filter(d -> d.equals(date)).count() == 0;

	}

	/**
	 * @author sauzanne
	 * 
	 *         Recherche le prix d'une action à une date précise
	 * @param listStockPrice
	 *            la liste des actions
	 * @param dateToFind
	 *            la date recherchée
	 * @return le prix à cette date ou null
	 */
	public static StockPrice getStockPriceFromDate(List<StockPrice> listStockPrice, LocalDate dateToFind) {

		for (StockPrice sp : listStockPrice) {
			if (sp.getStockPriceId().getInputDate().equals(dateToFind)) {
				return sp;
			}
		}
		return null;
	}

}
