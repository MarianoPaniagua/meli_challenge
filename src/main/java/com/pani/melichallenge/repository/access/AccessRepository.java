package com.pani.melichallenge.repository.access;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.pani.melichallenge.service.access.Access;

@Repository("accessRepository")
public interface AccessRepository extends MongoRepository<Access, Double> {

	@Query(value = "{'code' : {$regex : ?0}}")
	public Access findByCode(String code);
	
	public Access findFirstByOrderByDistanceAsc();

	public Access findFirstByOrderByDistanceDesc();

}
