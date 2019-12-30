package com.example.demo.service;

import java.util.List;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Daughter;
import com.example.demo.repository.DaughterRepository;

@Service
public class DaughterService {
	public static final Logger logger = LoggerFactory.getLogger(DaughterService.class);
	
	@Autowired
	public DaughterRepository daughterRepository;
	
	public Daughter registerDaughter(Daughter daughter) {
		logger.info("*************Inside DaughterService in registerDaughter method**************");
		try {
			logger.info("****************Inside registerDaughter try*******************");
			Daughter daughterToDb = daughterRepository.save(daughter);
			logger.info("Daughter value: " + daughterToDb);
			return daughterToDb;
		} catch (final Exception e) {
			logger.error("*********Inside registerDaughter catch exception");
			throw new HibernateException("Sorry, No daughter with id: ");
		}
	}
	
	public Daughter getDaughterById(Long daughterId, String name) {
		logger.info("*********************Inside DaughterService in getDaughterById method*******************");
		try {
			logger.info("******************Inside getDaughterById try********************");
			Daughter daughterFromDb = daughterRepository.daughterById(daughterId, name);
			logger.info("Daughter value:" + daughterFromDb);
			return daughterFromDb;
		} catch (final Exception e) {
			logger.error("**************Inside getDaughterById catch exception *****************");
			throw new HibernateException("Sorry, No daughter found with id: and daughterName: "+ daughterId +name);
		}
	}
	
	public List<Daughter> getAllDaughters(){
		logger.info("********************Inside DaughterServie in getAllDaughters method*******************");
		try {
			List<Daughter> daughterFromDb = daughterRepository.getAllDaughters();
			logger.info("Daughter value: " + daughterFromDb);
			return daughterFromDb;
		} catch (final Exception e) {
			logger.error("***************Inside getAllDaughters catch exception********************");
			throw new HibernateException("Sorry, No Daughter found");
		}
	}
	
	public String deleteSonById(Long daughterId) {
		logger.info("****************Inside DaughterService in deleteSonById method****************");
		try {
			logger.info("************Inside deleteSonById try***************");
			daughterRepository.deleteById(daughterId);
			logger.info("Daughter value: ");
			return "Successfully Deleted";
		} catch (final Exception e) {
			logger.error("*****************Inside deleteSonById catch exception***************");
			throw new HibernateException("Sorry, No Daughter found");
		}
	}
	
	public Daughter getDaughterByDaughterIdOnly(Long id) {
		logger.info("************Inside DaughterService in getDaughterByDaughterIdOnly method************** ");
		try {
			logger.info("************Inside getDaughterByDaughterIdOnly try*************** ");
			Daughter daughterFromDB = daughterRepository.daughterByIdOnly(id);
			logger.info("Daughter value: " + daughterFromDB );
			return daughterFromDB;
		} catch (final Exception e) {
			logger.error("************Inside getDaughterByDaughterIdOnly catch exception***************");
			throw new RuntimeException(e.getMessage());
		}

	}
}
