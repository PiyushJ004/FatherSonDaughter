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

import com.example.demo.exception.BaseException;
import com.example.demo.model.Father;
import com.example.demo.repository.FatherRepository;
import com.example.demo.util.ReflectionUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class FatherService {

	private static final Logger logger = LoggerFactory.getLogger(FatherService.class);

	ReflectionUtil refUtil = ReflectionUtil.getInstance();
	@Autowired
	private ObjectMapper objectMapper;

	@PostConstruct
	public void setUp() {
		objectMapper.registerModule(new JavaTimeModule());
	}

	@Autowired
	public FatherRepository fatherRepository;

	public Father registerFather(Father father) {
		logger.info("**************Inside FatherService in registerFather method************");
		try {
			logger.info("************Inside registerFather try***************");
			Father fatherToDb = fatherRepository.save(father);

			/* throw new BaseException("May hu aapna exceptin"); */

			logger.info("Father value: " + fatherToDb);
			return fatherToDb;
		} catch (final BaseException e) {
			logger.error("************Inside registerFather Catch Exception***************");
			throw new BaseException("Excepiton Caught: " + e.getMessage());
		}
	}

	public Father getFatherById(Long fatherId, String name) {
		logger.info("*************Inside FatherService in getFatherById method***************");
		try {
			logger.info("*************Inside getFatherById try**************");
			Father fatherFromDb = fatherRepository.fatherbyId(fatherId, name);
			logger.info("Father value:" + fatherFromDb);
			return fatherFromDb;

		} catch (final Exception e) {
			logger.error("************Inside getFatherById catch exception*************");
			throw new HibernateException("Sorry, No father with id: and fatherName:" + fatherId + name);

		}
	}

	public List<Father> getAllFathers() {
		logger.info("*****************Inside FatherService in getAllFathers method*********************");
		try {
			logger.info("***************Inside getAllFathers try**************");
			List<Father> fatherFromDb = fatherRepository.getAllFathers();
			logger.info("Father value:" + fatherFromDb);
			return fatherFromDb;
		} catch (final Exception e) {
			logger.error("*************Inside getAllFathers catch exception***************");
			throw new HibernateException("Sorry, No father found");
		}
	}

	public String deleteFatherById(Long fatherId) {
		logger.info("******************Inside FatherService in deleteFatherById method********************");
		try {
			logger.info("****************Inside deleteFatherById try***************");
			fatherRepository.deleteById(fatherId);
			logger.info("Father value: ");
			return "Successfully Deleted";
		} catch (final Exception e) {
			logger.error("*****************Inside deleteFatherById catch exception*******************");
			throw new HibernateException("Sorry, No father found");
		}
	}

	public Father getFatherByFatherIdOnly(Long id) {
		logger.info("************Inside FatherService in getFatherByFatherIdOnly method************** ");
		try {
			logger.info("************Inside getFatherByFatherIdOnly try*************** ");
			Father fatherFromDB = fatherRepository.fatherByIdOnly(id);
			logger.info("Father value: " + fatherFromDB);
			return fatherFromDB;
		} catch (final Exception e) {
			logger.error("************Inside getFatherByFatherIdOnly catch exception***************");
			throw new RuntimeException(e.getMessage());
		}

	}

	public Father updateFatherById(String father, Long id) throws ParseException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, JsonParseException, JsonMappingException, IOException {

		Father fatherFromDB = getFatherByFatherIdOnly(id);
		if (fatherFromDB == null) {
			return null;
		}

		Father fatherFromPayload = objectMapper.readValue(father, Father.class);
		LocalDateTime d = fatherFromPayload.getFdob();

		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(father);
		for (Iterator iterator = ((Map<String, String>) jsonObject).keySet().iterator(); iterator.hasNext();) {
			String prop = (String) iterator.next();

			if (prop.equals("f_dob")) {
				fatherFromDB.setFdob(d);

			} else {
				refUtil.getSetterMethod("Father", prop).invoke(fatherFromDB, jsonObject.get(prop));
			}

		}

		Father f = fatherRepository.save(fatherFromDB);
		return f;

	}

}
