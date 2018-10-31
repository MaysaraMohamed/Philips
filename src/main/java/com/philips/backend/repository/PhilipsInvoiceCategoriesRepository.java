package com.philips.backend.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.philips.backend.dao.PhilipsInvoice;
import com.philips.backend.dao.PhilipsInvoiceCategories;

/**
 * @author maysara.mohamed
 * @version 1.0
 * @since 2018-08-30
 */
@RepositoryRestResource(collectionResourceRel = "philipsInvoiceCategories", path = "philipsInvoiceCategories")
public interface PhilipsInvoiceCategoriesRepository
		extends PagingAndSortingRepository<PhilipsInvoiceCategories, Integer> {
	
	    public List<PhilipsInvoiceCategories> findByPhilipsInvoice(PhilipsInvoice philipsInvoice);
	    @Transactional
	    public Long deleteByPhilipsInvoice(PhilipsInvoice philipsInvoice); 
}