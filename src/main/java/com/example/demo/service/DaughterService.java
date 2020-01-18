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

import com.example.demo.model.Daughter;
import com.example.demo.repository.DaughterRepository;
import com.example.demo.repository.FatherRepository;
import com.example.demo.util.ReflectionUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class DaughterService {
	public static final Logger logger = LoggerFactory.getLogger(DaughterService.class);

	ReflectionUtil refUtil = ReflectionUtil.getInstance();
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private FatherRepository fatherRepo;

	@PostConstruct
	private void setUp() {
		objectMapper.registerModule(new JavaTimeModule());
	}

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
			throw new HibernateException("Sorry, No daughter found with id: and daughterName: " + daughterId + name);
		}
	}

	public List<Daughter> getAllDaughters() {
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
			logger.info("Daughter value: " + daughterFromDB);
			return daughterFromDB;
		} catch (final Exception e) {
			logger.error("************Inside getDaughterByDaughterIdOnly catch exception***************");
			throw new RuntimeException(e.getMessage());
		}

	}

	public Daughter updateDaughterById(String daughter, Long id) throws JsonParseException, JsonMappingException,
			IOException, ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Daughter daughterFromDB = getDaughterByDaughterIdOnly(id);
		if (daughterFromDB == null) {
			return null;
		}
		logger.info("daughter detials   " + daughterFromDB);
		Daughter daughterFromPayload = objectMapper.readValue(daughter, Daughter.class);
		LocalDateTime d = daughterFromPayload.getdDob();
		logger.info("Date hey ye" + d);
		Long id1 = null;
		if (daughterFromPayload.getFather_id() != null) {
			id = daughterFromPayload.getFather_id().getId();
		}

		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(daughter);
		for (Iterator iterator = ((Map<String, String>) jsonObject).keySet().iterator(); iterator.hasNext();) {
			String prop = (String) iterator.next();

			logger.info("Props value:- " + prop);
			if (prop.equals("d_Dob")) {

				daughterFromDB.setdDob(d);
			} else if (prop.equals("father_id")) {

				if (fatherRepo.fatherByIdOnly(id) != null) {
					daughterFromDB.setFather_id(fatherRepo.fatherByIdOnly(id));
				} else {
					logger.info("Sorry Father with id:- " + id + " Does Not Exist. Will will Retrieve Prevous value ");
				}
			} else {
				logger.info("Prop Name:- " + prop + "->" + jsonObject.get(prop));
				refUtil.getSetterMethod("Daughter", prop).invoke(daughterFromDB, jsonObject.get(prop));

			}
		}

		Daughter da = daughterRepository.save(daughterFromDB);
		return da;

	}
}
