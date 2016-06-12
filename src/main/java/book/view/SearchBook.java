/**
 * 
 */
package book.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import book.ejb.entity.Book;
import book.ejb.entity.SearchQuery;
import book.ejb.services.BookServices;

@Named("searchBook")
@javax.faces.view.ViewScoped
public class SearchBook implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private SearchQuery searchQuery;

	private List<Book> listBook;

	@Inject
	private BookServices bookServices;

	@PostConstruct
	private void init() {
		listBook = new ArrayList<Book>();
		searchQuery = new SearchQuery();
	}

	public SearchQuery getSearchQuery() {
		return searchQuery;
	}

	public void setSearchQuery(SearchQuery searchQuery) {
		this.searchQuery = searchQuery;
	}

	public List<Book> getListBook() {
		return listBook;
	}

	public String search(){
		listBook = bookServices.findAll(searchQuery);
		if (listBook == null) {
			init();
		}
		return "";
	}

	public String deleteBook(Long id) {
		for (Book book : listBook) {
			if (book.getId_book().equals(id)) {
				bookServices.remove(book);
				listBook.remove(book);
			}
		}
		return "";
	}

	public String editBook(Long id) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("edit", "true");
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("idEditBook", id.toString());
		return "/views/addbook.jsf?faces-redirect=true";
	}
}
