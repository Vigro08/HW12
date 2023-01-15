package de.uni.koeln.se.bookstore.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.uni.koeln.se.bookstore.service.BookService;
import de.uni.koeln.se.bookstore.datamodel.Book;

@RequestMapping("/bookStore")
@RestController
public class BookController {
	
	@Autowired
	BookService bookSer;
	
	@GetMapping
	public ResponseEntity<List<Book>> getAllbooks() {
		List<Book> books = new ArrayList<Book> ();
		books = bookSer.findBooks();
		
		return new ResponseEntity<>(books, HttpStatus.OK);
	}
	
	@GetMapping("/{id}" )
	public ResponseEntity<Book> getBookById(@PathVariable int id) {
		return new ResponseEntity<>(bookSer.fetchBook(id).get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Book> addBookt(@RequestBody Book book)  {
		bookSer.addBook(book);
		
		return new ResponseEntity<>(book, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/{id}" )
	public ResponseEntity<Book> removeBookById(@PathVariable int id) {
		Book book = bookSer.fetchBook(id).get();
		
		if (bookSer.deleteBook(id)) {
			return new ResponseEntity<>(book, HttpStatus.OK );
		} else {
			return new ResponseEntity<>(book, HttpStatus.BAD_REQUEST );
		}
	}
	
	@GetMapping("/newest")
	public ResponseEntity<Book> getNewestBook() {
		
		List<Book> books = new ArrayList<Book> ();
		books = bookSer.findBooks();
		
		Book newest = books.get(0);
		
		
		if (books.size() > 1) {
			for (int i = 1; i <= books.size() - 1; i++) {
			
				Book temp = books.get(i);
			
				if (newest.getYear() < temp.getYear()) {
					newest = temp;
				}
			}}
		return new ResponseEntity<>(newest, HttpStatus.OK);
	}
	
	@GetMapping("/oldest")
	public ResponseEntity<Book> getOldestBook() {
		
		List<Book> books = new ArrayList<Book> ();
		books = bookSer.findBooks();
		
		Book oldest = books.get(0);
		
		if (books.size() > 1) {
			for (int i = 1; i <= books.size() - 1; i++) {
			
				Book temp = books.get(i);
			
				if(oldest.getYear() > temp.getYear()) {
					oldest = temp;
				}
			}
		}
		return new ResponseEntity<>(oldest, HttpStatus.OK);
	}
}
