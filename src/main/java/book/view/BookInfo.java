package book.view;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;

import book.ejb.entity.Book;
import book.ejb.services.BookServices;
import book.ejb.utils.Log;


@Named
@RequestScoped
public class BookInfo implements Serializable {

	private static final long serialVersionUID = 1547031612818939534L;

	private List<Book> listBook;
	@Inject
	@Log
	private Logger logger;
	@Inject
	private BookServices bookServices;

	@Produces
	@Named
	public List<Book> getListBook() {
		try {
			listBook = bookServices.findAll();
			return listBook;
		} catch (Exception e){
			logger.error("", e);
		}
		return null;
	}
}
