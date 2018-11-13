package com.philips.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.philips.backend.dao.SubmitedInvoice;

/**
 * @author maysara.mohamed
 * @version 1.0
 * @since 2018-08-20
 */
@RepositoryRestResource(collectionResourceRel = "submitedInvoices", path = "submitedInvoices")
public interface SubmitedInvoiceRepository extends PagingAndSortingRepository<SubmitedInvoice, String> {
	@Query("from SubmitedInvoice s where s.status = :status")
	public List<SubmitedInvoice> findByStatus(@Param("status") String status);
	
}