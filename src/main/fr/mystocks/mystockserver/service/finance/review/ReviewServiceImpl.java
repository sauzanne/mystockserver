package fr.mystocks.mystockserver.service.finance.review;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.mystocks.mystockserver.dao.finance.review.ReviewDao;
import fr.mystocks.mystockserver.dao.security.account.AccountDao;
import fr.mystocks.mystockserver.data.finance.balancesheets.BalanceSheets;
import fr.mystocks.mystockserver.data.finance.currency.Currency;
import fr.mystocks.mystockserver.data.finance.operations.Operations;
import fr.mystocks.mystockserver.data.finance.review.Review;
import fr.mystocks.mystockserver.data.finance.stock.Stock;
import fr.mystocks.mystockserver.data.finance.valuation.Valuation;
import fr.mystocks.mystockserver.data.security.Account;
import fr.mystocks.mystockserver.data.security.constant.RoleEnum;
import fr.mystocks.mystockserver.service.finance.constant.PeriodEnum;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;
import jersey.repackaged.com.google.common.collect.Lists;

@Service("reviewService")
@Transactional
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewDao<Review> reviewDao;

	@Autowired
	private AccountDao<Account> accountDao;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<Review> findReview(Integer stockId, String token, Integer startYear, Integer endYear,
			PeriodEnum period) {
		try {
			List<Review> reviews = reviewDao.findReview(stockId, null, startYear, endYear, period);
			Map<String, Review> reviewMap = new HashMap<>();

			/* we are looking for the best review for each period */
			for (Review r : reviews) {

				String key = stockId.toString() + r.getStartYear()
						+ (r.getEndYear() == null ? r.getStartYear() : r.getEndYear()) + r.getPeriod();
				Review reviewInMap = reviewMap.get(key);

				if (reviewInMap != null) {
					if (r.getAccount().getToken().equals(token)) { /* our own review is always on top priority */
						reviewMap.put(key, r);
					} else {

						if (RoleEnum.valueOf(r.getAccount().getAccountType().getCode())
								.getAuthorizationLevel() > RoleEnum
										.valueOf(reviewInMap.getAccount().getAccountType().getCode())
										.getAuthorizationLevel()) {
							reviewMap.put(key, r);
						}
					}
				} else {
					reviewMap.put(key, r);
				}
			}

			return Lists.newArrayList(reviewMap.values());

		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;
	}

	@Override
	public Integer createUpdateReview(String token, Integer id, Integer stockId, PeriodEnum periodEnum,
			Integer startYear, Integer endYear, LocalDate startDate, LocalDate publicationDate,
			BigInteger nbSharesEndPeriod, Double freeFloat, Integer operationsId, Integer balanceSheetsId,
			Integer currencyId, Integer valuationId) {
		try {

			Account account = accountDao.getAccountByToken(token);

			// si le compte n'est pas identifié on sort de la fonction
			if (account == null) {
				return null;
			}
			Review r = new Review();
			if (id != null) {
				r = reviewDao.findById(id);

				if (r == null) {
					throw new FunctionalException(this, "error.finance.item.notexist", new String[] { id.toString() });
				}
			} else if (reviewDao.findReview(stockId, token, startYear, endYear, periodEnum) != null) {
					throw new FunctionalException(this, "error.finance.review.exist");
			}

			r.setStock(new Stock(stockId));

			r.setAccount(account);

			if (operationsId != null) {
				r.setOperations(new Operations(operationsId));
			}

			if (balanceSheetsId != null) {
				r.setBalanceSheets(new BalanceSheets(balanceSheetsId));
			}

			if (balanceSheetsId != null) {
				r.setBalanceSheets(new BalanceSheets(balanceSheetsId));
			}

			if (valuationId != null) {
				r.setValuation(new Valuation(valuationId));
			}

			r.setStartYear(startYear);

			r.setEndYear(endYear != null ? endYear : startYear);

			r.setPublicationDate(publicationDate);

			r.setStartDate(startDate);

			r.setCurrency(new Currency(currencyId));

			r.setPeriod(periodEnum.name());

			r.setFreeFloat(freeFloat);

			r.setNbSharesEndPeriod(nbSharesEndPeriod);

			LocalDateTime now = LocalDateTime.now();

			if (r.getId() == null) {
				r.setFirstInput(now);
				reviewDao.create(r);
			} else {
				r.setLastModified(now);
				reviewDao.update(r);
			}
			return r.getId();
		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;
	}

}
