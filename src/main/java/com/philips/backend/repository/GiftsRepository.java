package com.philips.backend.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.philips.backend.dao.Gifts;

/**
 * @author maysara.mohamed
 * @version 1.0
 * @since 2018-08-20
 */
@RepositoryRestResource(collectionResourceRel = "gifts", path = "gifts")
public interface GiftsRepository extends PagingAndSortingRepository<Gifts, Integer> {
	public List<Gifts> findByUserTypeAndPointsLessThanEqual(String userType, double points); 
}