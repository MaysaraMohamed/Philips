package com.philips.backend.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.philips.backend.dao.SubmitedInvoice;

/**
 * @author maysara.mohamed
 * @version 1.0
 * @since 2018-08-20
 */
@RepositoryRestResource(collectionResourceRel = "submitedInvoices", path = "submitedInvoices")
public interface SubmitedInvoiceRepository extends PagingAndSortingRepository<SubmitedInvoice, Integer> {
}