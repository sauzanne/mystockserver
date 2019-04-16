package fr.mystocks.mystockserver.dao;

import java.util.List;

import fr.mystocks.mystockserver.data.finance.measure.Measure;

public interface Dao<T> {

	void delete(T entity);

	void create(T entity);

	T update(T entity);

	T findById(Integer id);
	
	List<T> findAll();


}
