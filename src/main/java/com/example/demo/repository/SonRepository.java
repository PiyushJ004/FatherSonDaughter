
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

	@Query("Select Son from #{#entityName} Son where id = ?1")
	public Son sonByIdOnly(Long sonId);

	@Query("Select Son from #{#entityName} Son where id = ?1 and sNAme = ?1")
	public Son sonById(Long sonId, String sonName);

	@Query("Select Son from #{#entityName} Son")
	public List<Son> getAllSons();

	@Modifying
	@Transactional
	@Query("Delete #{#entityName} Son where id = ?1")
	public void deleteById(Long deleteId);

	//@Query(nativeQuery = true ,value="Select *from son_data where father_id->'id' = ?1")
	@Query("select Son from #{#entityName} Son where father_id.id = ?1")
	public List<Son> getAllSonByFatherId(Long id);

	@Modifying
	@Transactional
	@Query("Update #{#entityName} Son set father_id = null  where id = ?1")
	public void updateSonDetails(Long id);

}
