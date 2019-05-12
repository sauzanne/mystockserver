package fr.mystocks.mystockserver.service.finance.currency;

public interface CurrencyService {

	/**
	 * Permet de récuperer le taux de change
	 * @param base paire de base
	 * @param counterPart paire en contrepartie
	 * @return taux de change
	 */
	Price getCurrentFxRate(String base, String counterPart);
	

}
