package book.ejb.dao;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.slf4j.Logger;

import book.ejb.entity.Author;
import book.ejb.entity.Book;
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
			TypedQuery<Author> query = em.createNamedQuery("Author.findauthor",
					Author.class);
			query.setParameter("forename", entity.getAuthor().getForename()
					.toLowerCase());
			query.setParameter("surname", entity.getAuthor().getSurname()
					.toLowerCase());
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

}
