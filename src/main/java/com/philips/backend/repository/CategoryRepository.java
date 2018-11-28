package com.philips.backend.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.philips.backend.dao.Category;



/**
 * @author maysara.mohamed
 * @version 1.0
 * @since 2018-08-20
 */
@RepositoryRestResource(collectionResourceRel = "categories", path = "categories")
public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {
	public Category findByCategoryName(String categoryName); 
	public Category findByArCategoryName(String arCategoryName); 
}