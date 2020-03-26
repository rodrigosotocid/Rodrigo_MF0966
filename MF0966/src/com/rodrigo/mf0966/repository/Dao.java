package com.rodrigo.mf0966.repository;

public interface Dao<T> {
	
	Iterable<T> getAll();
	T getById(Integer id);
	
	Integer insert(T objeto);
	void update(T objeto);
	void delete(Integer id);
}
