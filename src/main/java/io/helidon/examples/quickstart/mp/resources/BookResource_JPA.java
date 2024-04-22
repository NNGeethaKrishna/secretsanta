package io.helidon.examples.quickstart.mp.resources;

import java.util.List;

import io.helidon.examples.quickstart.mp.BookRepository_JPA;
import io.helidon.examples.quickstart.mp.BookRepository_Jdbc;
import io.helidon.examples.quickstart.mp.pojo.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/books-JPA")
@ApplicationScoped
public class BookResource_JPA {

    BookRepository_JPA bookRepo;
	
	@Inject
	public BookResource_JPA(BookRepository_JPA bookRepo) {
		this.bookRepo=bookRepo;
	}

	@GET
	public List<Book> getBook() {
		
		List<Book> books = bookRepo.findAllBooks();
		
		return books;
	}

	@GET
	@Path("/isbn/{isbn}")
//	public Book getBook(@PathParam("isbn") int isbn) {
	public Response getBook(@PathParam("isbn") int isbn) {

		Book book = bookRepo.findBook(isbn);
		if(book!=null)
			return Response.status(Response.Status.OK).entity(book).build();
		else
			return Response.status(Response.Status.NO_CONTENT).build();
	}

	@GET
	@Path("/name")
	public Book getBook(@DefaultValue("Spring 5.0") @QueryParam("name") String name) {
		return new Book(123, name, 1200l);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createBook(Book book) {
		System.out.println("consumer sent:-" + book);
//		book.setBookName(book.getBookName().toUpperCase());
		
		if(book.getIsbn()>0) {
			Book book_inserted = bookRepo.createOrUpdateBook(book);
			return Response.status(Response.Status.CREATED).entity(book_inserted).build();

		}
		return Response.status(Response.Status.NO_CONTENT).build();

	}
//
	@POST
	@Path("/form")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createBook(@FormParam("name") String name, @FormParam("isbn") int isbn,
			@FormParam("price") long price) {
//		System.out.println("consumer sent:-" + book);
//		book.setBookName(book.getBookName().toUpperCase());
		if(isbn<=0) {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
		Book book_inserted = bookRepo.createOrUpdateBook(new Book(isbn,name,price));
		return Response.status(Response.Status.CREATED).entity(book_inserted).build();
	}

	@PUT	
	@Path("/isbn/{isbn}/name/{name}")
	public Response updateBook(@PathParam("name") String name,@PathParam("isbn") int isbn) {
		Book book = bookRepo.updateBookName(isbn, name);
		if(book==null){
			return Response.status(Response.Status.NO_CONTENT).build();
		}
		return Response.status(Response.Status.OK).entity(book).build();
		
	}
	@DELETE
	@Path("/isbn/{isbn}")
	public Response deleteBook(@PathParam("isbn") int isbn) {
		Response res = bookRepo.deleteBook(isbn);
		return res;
		
	}
	
}
