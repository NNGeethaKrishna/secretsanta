package io.helidon.examples.quickstart.mp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import io.helidon.examples.quickstart.mp.pojo.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class BookRepository_Jdbc {
	
	DataSource dataSource;
	
	
	//this inject is similar to the autowire in spring ,we can directly use 
	//it on top of the var but that is not a best practice as it  will expose
	//it publically
	@Inject
	public BookRepository_Jdbc(DataSource dataSource) {
		this.dataSource=dataSource;
	}

	public Book createOrUpdateBook(Book book) {
		Book book1 = null;
		
		try {
			Connection con = dataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("insert into book_helidon values(?,?,?)");
			ps.setInt(1, book.getIsbn());
			ps.setString(2, book.getBookName());
			ps.setLong(3, book.getPrice());
			
			int rows = ps.executeUpdate();
			if(rows>0) {
				book1=book;
			}
		}	
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return book1;	
		
	}
	
	public Book findBook(int isbn) {
		Book book = new Book();
		
		try {
			Connection con = dataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from book_helidon where isbn=?");
			ps.setInt(1, isbn);
			
			ResultSet rows = ps.executeQuery();
			while(rows.next()) {
				
				book.setIsbn(rows.getInt(1));
				book.setBookName(rows.getString(2));
				book.setPrice(rows.getLong(3));
			}
		}	
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return book;
	}
	
	public List<Book> findAllBooks() {
		
		List<Book> books = new ArrayList<Book>();
		try {
			Connection con = dataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from book_helidon");
		
			ResultSet rows = ps.executeQuery();
			while(rows.next()) {
				Book book = new Book();
				book.setIsbn(rows.getInt(1));
				book.setBookName(rows.getString(2));
				book.setPrice(rows.getLong(3));
				books.add(book);
			}
		}	
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return books;
	}
	
	public Book updateBookName(int isbn,String name) {
		return null;
	}
	
	public Book  deleteBook(int isbn) {
		return null;
	}
	
	
}
