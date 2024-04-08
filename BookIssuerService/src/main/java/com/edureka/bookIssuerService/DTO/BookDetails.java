package com.edureka.bookIssuerService.DTO;


public class BookDetails {

	private String bookIsbn;
	
	private int noOfCopiesAvailable;

	public int getNoOfCopiesAvailable() {
		return noOfCopiesAvailable;
	}

	public void setNoOfCopiesAvailable(int noOfCopiesAvailable) {
		this.noOfCopiesAvailable = noOfCopiesAvailable;
	}

	public String getBookIsbn() {
		return bookIsbn;
	}

	public void setBookIsbn(String bookIsbn) {
		this.bookIsbn = bookIsbn;
	}
	
}
