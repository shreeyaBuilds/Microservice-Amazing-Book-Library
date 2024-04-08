package com.edureka.bookIssuerService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edureka.bookIssuerService.model.BookIssueDetails;

@Repository
public interface BookIssuerDetailsRepository extends JpaRepository<BookIssueDetails, String>{

	BookIssueDetails getByCustId(String customerId);

}
