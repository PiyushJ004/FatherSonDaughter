package com.example.demo.service;

import java.util.List;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Son;
import com.example.demo.repository.SonRepository;

@Service
public class SonService {
	
	private static final Logger logger = LoggerFactory.getLogger(SonService.class);
	
	@Autowired
	public SonRepository sonRepository;
	
	public Son registerSon(Son son) {
		logger.info("************Inside SonService in registerSon method**************");
		try {
			logger.info("**************Inside registerSon try****************");
			Son sonToDb = sonRepository.save(son);
			logger.info("Son value: " + sonToDb);
			return sonToDb;
		} catch (final Exception e) {
			logger.error("***************Inside registerSon Catch exception*************");
			throw new HibernateException("Sorry, No Son with id : ");
		}
	}
	
	public Son getSonById(Long sonId, String name) {
		logger.info("*****************Inside SonService in getSonById method****************");
		try {
			logger.info("*************Inside getSonById try**************");
			Son sonFromDb = sonRepository.sonById(sonId, name);
			logger.info("Son value:" + sonFromDb);
			return sonFromDb;
		} catch (final Exception e) {
			logger.error("*****************Inside getSonById catch exception**************");
			throw new HibernateException("Sorry, No son with id: and sonName:" + sonId + name);
		}
	}
	
	public List<Son> getAllSons(){
		logger.info("**************Inside SonService in getAllSons method***************");
		try {
			logger.info("***********Inside getAllSons try*************");
			List<Son> sonFromDb = sonRepository.getAllSons();
			logger.info("Father value: " + sonFromDb);
			return sonFromDb;
		} catch(final Exception e) {
			logger.error("************Inside getAllSons catch exception**************");
			throw new HibernateException("Sorry, No Son found");
		}
	}
	
	public String deleteSonById(Long sonId) {
		logger.info("*****************Inside SonService in deleteSonById method****************");
		try {
			logger.info("****************Inside deleteSonById try**************");
			sonRepository.deleteById(sonId);
			logger.info("Son value: ");
			return "Successfully Deleted";
		} catch(final Exception e) {
			logger.error("*************Inside deleteSonById catch exception*****************");
			throw new HibernateException("Sorry, No son found");
		}
	}
	
	public Son getSonBySonIdOnly(Long id) {
		logger.info("************Inside SonService in getSonBySonIdOnly method************** ");
		try {
			logger.info("************Inside getSonBySonIdOnly try*************** ");
			Son sonFromDB = sonRepository.sonByIdOnly(id);
			logger.info("Son value: " + sonFromDB );
			return sonFromDB;
		} catch (final Exception e) {
			logger.error("************Inside getSonBySonIdOnly catch exception***************");
			throw new RuntimeException(e.getMessage());
		}

	}

}
