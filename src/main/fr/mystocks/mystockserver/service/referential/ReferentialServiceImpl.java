package fr.mystocks.mystockserver.service.referential;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;

@Transactional
@Service("referentialService")
public class ReferentialServiceImpl implements ReferentialService, ApplicationContextAware {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private ApplicationContext applicationContext;

	private final static String DAO_EXTENSION = "Dao";

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findAll(String dataName) {
		try {

			Object dao = (Object) applicationContext.getBean(dataName + DAO_EXTENSION);

			Method findAll = dao.getClass().getMethod("findReferentialItems");

			return (List<Object>) findAll.invoke(dao);
		} catch (NoSuchMethodException e) {
		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);

		} catch (IllegalAccessException e) {
			ExceptionTools.processException(this, logger, e);
		} catch (InvocationTargetException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
