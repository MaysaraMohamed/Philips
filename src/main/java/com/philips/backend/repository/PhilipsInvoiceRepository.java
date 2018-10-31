package com.philips.backend.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.philips.backend.dao.PhilipsInvoice;

/**
 * @author maysara.mohamed
 * @version 1.0
 * @since 2018-08-20
 */
@RepositoryRestResource(collectionResourceRel = "philipsInvoices", path = "philipsInvoices")
public interface PhilipsInvoiceRepository extends PagingAndSortingRepository<PhilipsInvoice, String> {
}