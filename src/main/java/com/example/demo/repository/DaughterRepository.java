package com.example.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Daughter;

@Repository
public interface DaughterRepository extends CrudRepository<Daughter, Long> {

	@Query("Select Daughter from #{#entityName} Daughter where id = ?1")
	public Daughter daughterByIdOnly(Long daughterId);

	@Query("Select Daughter from #{#entityName} Daughter where id = ?1 and dName = ?2")
	public Daughter daughterById(Long daughterId, String daughterName);

	@Query("Select Daughter from #{#entityName} Daughter")
	public List<Daughter> getAllDaughters();

	@Modifying
	@Transactional
	@Query("Delete from #{#entityName} Daughter where id = ?1")
	public void deleteById(Long deleteId);

}
