package com.philips.backend.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.philips.backend.dao.SubmitedInvoiceCategories;

/**
 * @author maysara.mohamed
 * @version 1.0
 * @since 2018-08-30
 */
@RepositoryRestResource(collectionResourceRel = "submitedInvoiceCategories", path = "submitedInvoiceCategories")
public interface SubmitedInvoiceCategoriesRepository
		extends PagingAndSortingRepository<SubmitedInvoiceCategories, Integer> {

	// public List<Users> findByUserName(String userName);
}