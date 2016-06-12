package book.ejb.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;

import book.ejb.entity.Author;
import book.ejb.entity.Book;
import book.ejb.entity.DBMS_NAMES;
import book.ejb.entity.SearchQuery;
import book.ejb.utils.Log;

@Stateless
public class BookDao {

	@PersistenceContext
	protected EntityManager em;

	@Inject
	@Log
	private Logger logger;

	public BookDao() {
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public Book find(Object id) {
		return em.find(Book.class, id);
	}

	public void remove(Book entity) {
		em.remove(em.merge(entity));
		em.flush();
	}

	public Book merge(Book entity) {
		Book t = em.merge(entity);
		em.flush();
		return t;
	}

	public void persist(Book entity) {
		try {
			TypedQuery<Author> query = em.createNamedQuery("Author.findauthor",Author.class);
			query.setParameter(DBMS_NAMES.AUTHOR_FORENAME, entity.getAuthor().getForename().toLowerCase());
			query.setParameter(DBMS_NAMES.AUTHOR_SURNAME, entity.getAuthor().getSurname().toLowerCase());
			try {
				Author results = query.getSingleResult();
				entity.setAuthor(results);
			} catch (NoResultException e) {
				logger.debug("", e);
			}
			em.persist(entity);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	public List<Book> findAll() {
		final CriteriaQuery<Book> criteriaQuery = em.getCriteriaBuilder()
				.createQuery(Book.class);
		criteriaQuery.select(criteriaQuery.from(Book.class));
		return Collections.unmodifiableList(em.createQuery(criteriaQuery)
				.getResultList());
	}

	public List<Book> findAll(SearchQuery searchBook) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Book> cq = cb.createQuery(Book.class);
		Root<Book> books = cq.from(Book.class); 
		Join<Book,Author> authors = null;
		if (!isNullOrEmpty(searchBook.getSurname()) || !isNullOrEmpty(searchBook.getForename())) {
			authors = books.join(DBMS_NAMES.AUTHOR_ENTITY, JoinType.INNER);
		}
		Predicate predicate = null;
		List<Predicate> pResult = new ArrayList<Predicate>();

		if (!isNullOrEmpty(searchBook.getTitle())) {
			 predicate = cb.equal(books.get(DBMS_NAMES.BOOK_TITLE), searchBook.getTitle());
			 pResult.add(cb.and(predicate));
		}
		if (!isNullOrEmpty(searchBook.getIsbn())) {
			predicate = cb.equal(books.get(DBMS_NAMES.BOOK_ISBN), searchBook.getIsbn());
			pResult.add(cb.and(predicate));
		}
		if (!isNullOrEmpty(searchBook.getForename())) {
			/** to param set example 
			  ParameterExpression<String> titleParam = cb.parameter(String.class, "forename");
			  predicate = cb.equal(authors.get("title"),titleParam); and query.setParameter("name", name); }*/
			 predicate = cb.equal(authors.get(DBMS_NAMES.AUTHOR_FORENAME), searchBook.getForename());
			 pResult.add(cb.and(predicate));
		}
		if (!isNullOrEmpty(searchBook.getSurname())) {
			predicate = cb.equal(authors.get(DBMS_NAMES.AUTHOR_SURNAME), searchBook.getSurname());
			pResult.add(cb.and(predicate));
		}

		predicate = cb.and(pResult.toArray(new Predicate[pResult.size()]));
		cq.select(books).where(predicate).distinct(true);
		TypedQuery<Book> typedQuery = em.createQuery(cq);
		return typedQuery.getResultList();
	}

	private boolean isNullOrEmpty(String value) {
		if (value == null) {
			return true;
		}
		if ("".equals(value)) {
			return true;
		}
		return false;
	}

}
