package fr.mystocks.mystockserver.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDaoImpl<T> implements Dao<T> {

	/** */
	private Class<T> persistentClass;

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Constructeur par d�faut permettant de définir la class et le nom de la
	 * classe de la classe fille
	 */
	@SuppressWarnings("unchecked")
	public AbstractDaoImpl() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	// public abstract T find(Integer id);

	@Override
	public T update(T entity) {
		getSession().update(entity);
		return entity;
	}

	@Override
	public void create(T entity) {
		getSession().persist(entity);
	}

	@Override
	public void delete(T entity) {
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findById(Integer id) {
		Criteria criteria = getSession().createCriteria(persistentClass);
		criteria.add(Restrictions.eq("id", id));
		return (T) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		Criteria criteria = getSession().createCriteria(persistentClass);
		return (List<T>) criteria.list();
	}

}
