package org.ssg.core.dao;

import java.util.List;

public interface GenericDao {
	<T> void save(T object);
	<T> T getById(int id, Class<T> objectType);
	<T> List<T> getAllByType(Class<T> objectType);
	<T> void delete(int id, Class<T> objectType);
}
