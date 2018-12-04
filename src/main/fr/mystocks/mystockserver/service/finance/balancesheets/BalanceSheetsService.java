package fr.mystocks.mystockserver.service.finance.balancesheets;

public interface BalanceSheetsService {

	Integer storeBalanceSheets(String token, Integer id, Integer assetsId, Integer liabilitiesId, Integer equitiesId);

}
