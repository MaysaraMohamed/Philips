package com.philips.backend.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.philips.backend.dao.User;

/**
 * @author maysara.mohamed
 * @version 1.0
 * @since 2018-08-20
 */
@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
	public List<User> findByUserName(String userName); 
}