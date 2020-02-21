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
	 * Calcule si ce jour est un jour d'ouverture de la bourse en prenant en compte
	 * les jours où le marché est fermé (en paramètre)
	 * 
	 * @param date        la date à vérifier
	 * @param closingDate la date de fermeture
	 * @return vrai si le jour est un jour de cotation
	 */
	public static boolean isOpenDateWithMarketConditions(LocalDate date, List<LocalDate> closingDate) {
		return !(date.getDayOfWeek() == DayOfWeek.SUNDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY)
				&& closingDate.stream().filter(d -> d.equals(date)).count() == 0;

	}

	/**
	 * Récupère la dernière date d'ouverture en fonction des conditions de marchés et des week ends
	 *  
	 * @param date        la date à vérifier
	 * @param closingDate la date de fermeture
	 * @return la dernière date d'ouverture
	 */
	public static LocalDate lastOpenDateWithMarketConditions(LocalDate date, List<LocalDate> closingDate) {
		LocalDate result = LocalDate.from(date);
		while (true) {
			if (!isOpenDateWithMarketConditions(result, closingDate)) {
				result = result.minusDays(1);
			} else {
				return result;
			}
		}

	}

	/**
	 * @author sauzanne
	 * 
	 *         Recherche le prix d'une action à une date précise
	 * @param listStockPrice la liste des actions
	 * @param dateToFind     la date recherchée
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
