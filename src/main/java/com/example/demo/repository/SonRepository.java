package com.example.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Son;

@Repository
public interface SonRepository extends CrudRepository<Son, Long> {

	@Query("Select Son from #{entityName} Son where id = ?1")
	public Son sonByIdOnly(Long sonId);
	
	@Query("Select Son from #{entityName} Son where id = ?1 and sNAme = ?1")
	public Son sonById(Long sonId, String sonName);
	
	@Query("Select Son from #{entityName} Son")
	public List<Son> getAllSons();
	
	@Query("Delete #{#entityName} Son where id = ?1")
	public void deleteById(Long deleteId);
	
	@Modifying
	@Transactional
	@Query("Update Son from #{entityName} Son where id = ?1")
	public Son updateById(String updateId);
	
	
	
	
}
