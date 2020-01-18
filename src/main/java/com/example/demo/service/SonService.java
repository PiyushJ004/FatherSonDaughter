package com.example.demo.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.hibernate.HibernateException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Son;
import com.example.demo.repository.SonRepository;
import com.example.demo.util.ReflectionUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class SonService {
	
	private static final Logger logger = LoggerFactory.getLogger(SonService.class);
	
	ReflectionUtil refUtil = ReflectionUtil.getInstance();
	@Autowired
	private ObjectMapper objectMapper;
	
	@PostConstruct
	public void setUp() {
		objectMapper.registerModule(new JavaTimeModule());
	}
	
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
	
	public Son updateSonById(String son, Long id) throws JsonParseException, JsonMappingException,
	IOException, ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Son sonFromDB = getSonBySonIdOnly(id);
		if(sonFromDB == null) {
			return null;
		}
		
		Son sonFromPayload = objectMapper.readValue(son, Son.class);
		LocalDateTime d = sonFromPayload.getsDob();
		
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(son);
		for(Iterator iterator = ((Map<String, String>) jsonObject).keySet().iterator(); iterator.hasNext();) {
			String prop = (String) iterator.next();
			
			if(prop.equals("s_dob")) {
				sonFromDB.setsDob(d);
				
			} else {
				refUtil.getSetterMethod("son", prop).invoke(sonFromDB, jsonObject.get(prop));
			}
		}
		
		Son s = sonRepository.save(sonFromDB);
		return s;
	}

}
