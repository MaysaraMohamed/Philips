package com.philips.backend.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.philips.backend.dao.PointsMapping;

/**
 * @author maysara.mohamed
 * @version 1.0
 * @since 2018-08-20
 */
@RepositoryRestResource(collectionResourceRel = "pointsMapping", path = "pointsMapping")
public interface PointsMappingRepository extends PagingAndSortingRepository<PointsMapping, Integer> {
	public List<PointsMapping> findAllByOrderByNetSaleDesc();
	public PointsMapping findByCategoryId(int categoryId);
}