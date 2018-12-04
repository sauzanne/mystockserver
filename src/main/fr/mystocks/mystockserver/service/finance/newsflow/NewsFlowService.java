package fr.mystocks.mystockserver.service.finance.newsflow;

import java.util.List;

import fr.mystocks.mystockserver.data.finance.newsflow.NewsFlow;

public interface NewsFlowService {

	List<NewsFlow> getNewsFlowByToken(String token);

	Integer createNewsFlow(String token, Integer id, String name, String url, String keywords, Boolean notification);

}
