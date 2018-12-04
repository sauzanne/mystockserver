package fr.mystocks.mystockserver.service.finance.newsflow;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.mystocks.mystockserver.dao.finance.newsflow.NewsFlowDao;
import fr.mystocks.mystockserver.dao.security.account.AccountDao;
import fr.mystocks.mystockserver.data.finance.newsflow.NewsFlow;
import fr.mystocks.mystockserver.data.security.Account;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;

@Transactional
@Service("newsFlowService")
public class NewsFlowServiceImpl implements NewsFlowService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private NewsFlowDao<NewsFlow> newsFlowDao;

	@Autowired
	private AccountDao<Account> accountDao;

	@Override
	public List<NewsFlow> getNewsFlowByToken(String token) {
		try {

			return newsFlowDao.getNewsFlowByToken(token);

		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;
	}

	@Override
	public Integer createNewsFlow(String token, Integer id, String name, String url, String keyword,
			Boolean notification) {
		LocalDateTime now = LocalDateTime.now();
		try {
			Account account = accountDao.getAccountByToken(token);

			// si le compte n'est pas identifié on sort de la fonction
			if (account == null) {
				return null;
			}
			NewsFlow n = new NewsFlow();
			if (id != null) {
				n = newsFlowDao.findById(id);

				// impossible d'identifier ce news flow avec cet id
				if (n == null) {
					throw new FunctionalException(this, "error.finance.newsflow.notexist");
				}
			}

			n.setAccount(account);
			n.setName(name);
			n.setUrl(url);
			n.setKeyword(keyword);
			n.setNotification(notification);

			if (n.getId() == null) {
				n.setFirstInput(now);
				newsFlowDao.create(n);
			} else {
				n.setLastModified(now);
				newsFlowDao.update(n);
			}
			return n.getId();
		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;
	}

}
