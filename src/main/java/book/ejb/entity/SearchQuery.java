/**
 * 
 */
package book.ejb.entity;

import java.io.Serializable;


public class SearchQuery implements Serializable{

	private static final long serialVersionUID = 1L;

	private String title;
	
	private String isbn;
	
	private String forename;
	
	private String surname;
	
	public SearchQuery() {
		super();
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getIsbn() {
		return isbn;
	}
	
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	
	
	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
}
