package fr.mystocks.mystockserver.dao.finance.newsflow;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import fr.mystocks.mystockserver.dao.AbstractDaoImpl;
import fr.mystocks.mystockserver.data.finance.newsflow.NewsFlow;

@Repository("newsFlowDao")
public class NewsFlowDaoImpl extends AbstractDaoImpl<NewsFlow> implements NewsFlowDao<NewsFlow> {

	private final static String BIND_TOKEN = "token";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NewsFlow> getNewsFlowByToken(String token) {
		StringBuilder request = new StringBuilder();

		request.append("select nf from NewsFlow nf");
		request.append(" join fetch nf.account a ");

		request.append(" where a.token = :"+BIND_TOKEN);

		Query query = getSession().createQuery(request.toString());

		query.setParameter(BIND_TOKEN, token);
		return query.list();
	}


}
