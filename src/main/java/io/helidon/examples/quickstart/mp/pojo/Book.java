package io.helidon.examples.quickstart.mp.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
@Entity //classname=table name or we can as well provide the table name
@Table(name = "book_helidon")
public class Book {

	@Id
	private int isbn;
	@NotEmpty(message = "Entity name must be provided.")
	//@Column(name = "bookName")
	private String bookName;
	private long price;
	
	public Book() {
		// TODO Auto-generated constructor stub
	}

	public Book(int isbn, String bookName, long price) {
		super();
		this.isbn = isbn;
		this.bookName = bookName;
		this.price = price;
	}

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Book [isbn=" + isbn + ", bookName=" + bookName + ", price=" + price + "]";
	}
	
	
	
}
