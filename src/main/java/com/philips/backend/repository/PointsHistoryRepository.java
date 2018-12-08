package com.philips.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.philips.backend.dao.PointsHistory;
import com.philips.backend.dao.SubmitedInvoice;
import com.philips.backend.dao.Users;

/**
 * @author maysara.mohamed
 * @version 1.0
 * @since 2018-08-20
 */
@RepositoryRestResource(collectionResourceRel = "pointsHistory", path = "pointsHistory")
public interface PointsHistoryRepository extends PagingAndSortingRepository<PointsHistory, Integer> {
	public List<PointsHistory> findByUser(Users user);

	@Query(" select p from PointsHistory p where p.user = :user and p.pointsDate = CURRENT_DATE")
	public PointsHistory getPointsRecordToday(@Param("user") Users user);
	
	@Query(" select p from PointsHistory p where p.user = :user ORDER BY p.pointsDate")
	public List<PointsHistory> getLastPointsRecord(@Param("user") Users user);

	@Query(" select sum(points)-sum(usedPoints) from PointsHistory p where p.user = :user")
	public Double getTotalUserNetPoints(@Param("user") Users user);
	
	@Query(" select sum(usedPoints) from PointsHistory p where p.user = :user")
	public Double getTotalRedeemedPoints(@Param("user") Users user);
	
	@Query(" select sum(points) from PointsHistory p where p.user = :user")
	public Double getTotalUserPoints(@Param("user") Users user);
	
	
	public List<PointsHistory> findTop15ByUserOrderByPointsDateDesc(Users user);
}