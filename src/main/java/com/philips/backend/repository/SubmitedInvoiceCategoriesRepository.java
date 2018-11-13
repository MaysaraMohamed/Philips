package com.philips.backend.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.philips.backend.dao.PhilipsInvoice;
import com.philips.backend.dao.PhilipsInvoiceCategories;
import com.philips.backend.dao.SubmitedInvoice;
import com.philips.backend.dao.SubmitedInvoiceCategories;

/**
 * @author maysara.mohamed
 * @version 1.0
 * @since 2018-08-30
 */
@RepositoryRestResource(collectionResourceRel = "submitedInvoiceCategories", path = "submitedInvoiceCategories")
public interface SubmitedInvoiceCategoriesRepository
		extends PagingAndSortingRepository<SubmitedInvoiceCategories, Integer> {

    public List<SubmitedInvoiceCategories> findBySubmitedInvoice(SubmitedInvoice submitedInvoice);
    @Transactional
    public Long deleteBySubmitedInvoice(SubmitedInvoice submitedInvoice); 
}