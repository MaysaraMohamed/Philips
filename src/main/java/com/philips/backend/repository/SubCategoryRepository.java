package com.philips.backend.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.philips.backend.dao.Category;
import com.philips.backend.dao.SubCategory;

/**
 * @author maysara.mohamed
 * @version 1.0
 * @since 2018-08-20
 */
@RepositoryRestResource(collectionResourceRel = "subCategories", path = "subCategories")
public interface SubCategoryRepository extends PagingAndSortingRepository<SubCategory, Integer> {
	public List<SubCategory> findByCategory(Category category);
}