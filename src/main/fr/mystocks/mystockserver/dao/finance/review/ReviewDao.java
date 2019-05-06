package fr.mystocks.mystockserver.dao.finance.review;

import java.util.List;

import fr.mystocks.mystockserver.dao.Dao;
import fr.mystocks.mystockserver.data.finance.review.Review;
import fr.mystocks.mystockserver.service.finance.constant.PeriodEnum;

public interface ReviewDao<T> extends Dao<T> {

	List<Review> findReview(Integer stockId, String token, Integer startYear, Integer endYear, PeriodEnum period);

	List<Review> findLastReview(Integer stockId);
    
	
}
