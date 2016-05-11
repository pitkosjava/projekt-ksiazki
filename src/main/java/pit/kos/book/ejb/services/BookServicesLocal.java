/**
 * 
 */
package pit.kos.book.ejb.services;

import java.util.List;

import javax.ejb.Local;

import pit.kos.book.ejb.entity.Book;

@Local
public interface BookServicesLocal {
	Book find(Object id);
	void remove(Book entity);
	Book merge(Book entity);
	void persist(final Book entity);
	List<Book> findAll();
}
