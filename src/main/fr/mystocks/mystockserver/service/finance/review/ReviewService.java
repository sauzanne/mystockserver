package fr.mystocks.mystockserver.service.finance.review;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import fr.mystocks.mystockserver.data.finance.review.Review;
import fr.mystocks.mystockserver.service.finance.constant.PeriodEnum;

public interface ReviewService {

	List<Review> findReview(Integer stockId, String token, Integer startYear, Integer endYear, PeriodEnum period);

	Integer createUpdateReview(String token, Integer id, Integer stockId, PeriodEnum periodEnum, Integer startYear, Integer endYear,
			LocalDate startDate, LocalDate publicationDate, BigInteger nbSharesEndPeriod, Double freeFloat, Integer operationsId, Integer balanceSheetsId, Integer currencyId);
    
}
