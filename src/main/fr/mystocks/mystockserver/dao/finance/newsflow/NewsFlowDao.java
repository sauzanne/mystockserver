package fr.mystocks.mystockserver.dao.finance.newsflow;

import java.util.List;

import fr.mystocks.mystockserver.dao.Dao;
import fr.mystocks.mystockserver.data.finance.newsflow.NewsFlow;

public interface NewsFlowDao<T> extends Dao<T> {
    
	List<NewsFlow> getNewsFlowByToken(String token);

	
}
