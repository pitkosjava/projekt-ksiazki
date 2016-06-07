/**
 * 
 */
package book.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;

import book.ejb.entity.Book;
import book.ejb.services.BookServices;
import book.ejb.utils.Log;

@Named
@javax.faces.view.ViewScoped
public class BookAdd implements Serializable {

	private static final String ERROR_ADD_BOOK = "error";

	private static final String SUCCES_ADD_BOOK = "succes";

	private static final long serialVersionUID = -278943616206899390L;

	private Book book;

	@Inject
	@Log
	private Logger logger;

	@Inject
	private BookServices bookServices;

	private boolean editBook;

	private UploadedFile file;

	@PostConstruct
	private void init() {
		try {
			Flash flash = FacesContext.getCurrentInstance()
					.getExternalContext().getFlash();
			String edit = (String) flash.get("edit");
			if (edit != null) {
				if (!edit.isEmpty() && "true".equals(edit)) {
					String idEditBook = (String) flash.get("idEditBook");
					book = bookServices.find(Long.parseLong(idEditBook));
					flash.clear();
					logger.info("edit book");
					editBook = true;
					return;
				}
			} else {
				editBook = false;
				logger.info("new book");
				book = new Book();
				return;
			}
		} catch (Exception e) {
			logger.info("", e);
		}
		/** catch exceptions **/
		editBook = false;
		book = new Book();
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String addBook() {
		try {
			if (editBook) {
				bookServices.merge(book);
				editBook = false;
			} else {
				bookServices.persist(book);
			}
			return SUCCES_ADD_BOOK;
		} catch (Exception e) {
			logger.error("", e);
			return ERROR_ADD_BOOK;
		}
	}

	public boolean isEditBook() {
		return editBook;
	}

}
