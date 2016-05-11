/**
 * 
 */
package pit.kos.book.ejb.dao;

import java.util.List;


public interface DaoInterface<T> {

	T find(Object id);

	void remove(T entity);

	T merge(T entity);

	void persist(final T entity);

	List<T> findAll();
}
