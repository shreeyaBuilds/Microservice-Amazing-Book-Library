package com.edureka.bookIssuerService.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.edureka.bookIssuerService.DTO.Book;
import com.edureka.bookIssuerService.DTO.BookDetails;
import com.edureka.bookIssuerService.Service.BookIssuerService;
import com.edureka.bookIssuerService.model.BookIssueDetails;


@RestController
@RequestMapping(value="issueBook")
public class BookIssuerRestController {
	
	@Autowired
	private BookIssuerService bookIssuerService;
	
	@GetMapping(value="/checkAvailable/{title}")
	public ResponseEntity<BookDetails> getAvailableBooks(@PathVariable("title")String title) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Book> response = restTemplate.exchange("http://localhost:8087/book-service/book/get/"+title, HttpMethod.GET, null, Book.class);
		BookDetails	bookDetails = new BookDetails();
		if(response.getStatusCode().equals(HttpStatus.OK)){
			Book book = (Book)response.getBody();
			bookDetails.setBookIsbn(book.getIsbn());
			bookDetails.setNoOfCopiesAvailable(book.getTotalCopies()-book.getIssuedCopies());
			
			return new ResponseEntity<BookDetails>(bookDetails,HttpStatus.OK);
		}
		
		return new ResponseEntity<BookDetails>(bookDetails,HttpStatus.NO_CONTENT);
	}
	
	@PostMapping(value="/{isbn}/{noOfBookToIssue}")
	public ResponseEntity<Integer> issuebook(@PathVariable("isbn")String isbn,@PathVariable("noOfBookToIssue")String noOfBookToIssue,@RequestBody String customerId){
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Integer> response;
		try {
			response = restTemplate.postForEntity("http://localhost:8087/book-service/book/update/bookIssued/"+isbn+"/"+noOfBookToIssue,null, Integer.class);
			if(response.getStatusCode().equals(HttpStatus.OK)){
				int noOfBooksIssued = (int)response.getBody();
				BookIssueDetails bookIssueDetails = new BookIssueDetails();
				bookIssueDetails.setCustId(customerId);
				bookIssueDetails.setIsbn(isbn);
				bookIssueDetails.setNoOfCopies(noOfBooksIssued);
				bookIssuerService.updateIssueDetails(bookIssueDetails);
				return new ResponseEntity<>(noOfBooksIssued,HttpStatus.OK);
			}
		} catch (RestClientException e) {
			return new ResponseEntity<>(Integer.parseInt(noOfBookToIssue),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(Integer.parseInt(noOfBookToIssue),HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping(value="returnbook/{isbn}/{noOfBookReturned}")
	public ResponseEntity<Integer> returnbook(@PathVariable("isbn")String isbn,@PathVariable("noOfBookReturned")String noOfBookReturned,@RequestBody String customerId){
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Integer> response;
		BookIssueDetails bookIssueDetails = bookIssuerService.getIssueDetailsForCustomer(customerId);
		if(bookIssueDetails.getNoOfCopies() < Integer.parseInt(noOfBookReturned)) {
			return new ResponseEntity<>(Integer.parseInt(noOfBookReturned),HttpStatus.BAD_REQUEST);
		}
		try {
			response = restTemplate.postForEntity("http://localhost:8087/book-service/book/update/bookReturned/"+isbn+"/"+noOfBookReturned,null, Integer.class);
			if(response.getStatusCode().equals(HttpStatus.OK)){
				int noOfBooksReturnedSuccess = (int)response.getBody();
				BookIssueDetails bookIssueDetailsUpdated = new BookIssueDetails();
				bookIssueDetailsUpdated.setCustId(customerId);
				bookIssueDetailsUpdated.setIsbn(isbn);
				bookIssueDetailsUpdated.setNoOfCopies(bookIssueDetails.getNoOfCopies() - noOfBooksReturnedSuccess);
				bookIssuerService.updateIssueDetails(bookIssueDetailsUpdated);
				return new ResponseEntity<>(noOfBooksReturnedSuccess,HttpStatus.OK);
			}
		} catch (RestClientException e) {
			return new ResponseEntity<>(Integer.parseInt(noOfBookReturned),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(Integer.parseInt(noOfBookReturned),HttpStatus.BAD_REQUEST);
	}
	

}
