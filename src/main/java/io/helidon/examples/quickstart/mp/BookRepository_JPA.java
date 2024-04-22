package io.helidon.examples.quickstart.mp;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import io.helidon.examples.quickstart.mp.pojo.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
@ApplicationScoped
public class BookRepository_JPA {


		@PersistenceContext
		EntityManager em;
		
		
		public BookRepository_JPA() {
			// TODO Auto-generated constructor stub
			
		

		}

		@Transactional
		//to prevent manual commit or rollback
		//this is called declarative transaction management
		public Book createOrUpdateBook(Book book1) {
			try {
				//to update we can use the merge method
				em.persist(book1);
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}

			
			return book1;
		}

		public Book findBook(int isbn) {
			Book book = null;
			try {
			book = em.find(Book.class, isbn);
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return book;
		}

		//jpql
		public List<Book> findAllBooks() {
			Query q = em.createQuery("From Book b");
			
			return q.getResultList();
		}
		@Transactional
		//for every update delete and create operations we should use thos transactina
		public Book updateBookName(int isbn, String name) {
			Book book = findBook(isbn);
			Book updatedBook = null;
			if(book!=null) {
				book.setBookName(name);
				updatedBook=em.merge(book);
			}
			return updatedBook;
		}
@Transactional
		public Response deleteBook(int isbn) {
			
			Book book = findBook(isbn);
			if(book!=null) {
				em.remove(book);
				return Response.status(Response.Status.OK).build();
			}			
			return Response.status(Response.Status.NO_CONTENT).build();
		}

	}

