package com.edureka.bookIssuerService.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edureka.bookIssuerService.model.BookIssueDetails;
import com.edureka.bookIssuerService.repository.BookIssuerDetailsRepository;

@Service
public class BookIssuerService {
	
	@Autowired
	private BookIssuerDetailsRepository bookIssuerDetailsRepository;
	
	public void updateIssueDetails(BookIssueDetails bookIssueDetails) {
		
		bookIssuerDetailsRepository.save(bookIssueDetails);
		
	}
	
	public BookIssueDetails getIssueDetailsForCustomer(String customerId) {
		return bookIssuerDetailsRepository.getByCustId(customerId);
	}

}
