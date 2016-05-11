/**
 * 
 */
package book.ejb.services;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import book.ejb.dao.BookDao;
import book.ejb.entity.Book;

@Named
@RequestScoped
public class BookServices{

	@Inject
	private BookDao bookDao;
	
	public Book find(Object id) {
		return bookDao.find(id);
	}

	public void remove(Book entity) {
		bookDao.remove(entity);
	}

	public Book merge(Book entity) {
		return bookDao.merge(entity);
	}

	public void persist(Book entity) {
		bookDao.persist(entity);
	}

	public List<Book> findAll() {
		return bookDao.findAll();
	}

}
