package com.example.demo.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Son;
import com.example.demo.service.SonService;
import com.example.demo.util.ReflectionUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(path = "/api/object/son")
@Api(value = "Son Crud Operations", description = "This controller is for crud operation of son instance")
public class SonController {

	private static final Logger logger = LoggerFactory.getLogger(SonController.class);
	
	ReflectionUtil refUtil = ReflectionUtil.getInstance();

	@Autowired
	private SonService sonService;

	@RequestMapping(path = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "/create", notes = "Save Son Data To The Database", response = Son.class)
	public ResponseEntity<?> createInstance(@Valid @RequestBody Son son, BindingResult bindingResult) {
		logger.info("***********Inside SonController Create Instance method*************");
		if (bindingResult.hasErrors() == true) {
			logger.info("***********Inside if block of Son createInstance*****************");
			Map<String, String> error = new LinkedHashMap<String, String>();
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				logger.info("************Inside for block of Son createInstance***************");
				error.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return new ResponseEntity<Map<String, String>>(error, HttpStatus.BAD_REQUEST);
		}

		Son sonToDb = sonService.registerSon(son);
		logger.info("Son value: " + sonToDb);
		Gson gson = new Gson();
		String gsonString = gson.toJson(sonToDb);
		return new ResponseEntity<String>(gsonString, HttpStatus.CREATED);
	}

	@RequestMapping(path = "/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "/get", notes = "Get Son Resource By ID", response = Son.class)
	public ResponseEntity<?> getSonByID(
			@ApiParam(value = "id", required = true) @RequestParam(value = "id", required = true) Long id) {
		logger.info("*************Inside SonController getSonByID method**************");
		Son sonFromDB = sonService.getSonBySonIdOnly(id);
		logger.info("Son value: " + sonFromDB);
		if (sonFromDB == null) {
			logger.info("*****************Inside if block of son getSonByID****************");
			return new ResponseEntity<String>("Sorry, Could not retrive data from son table for id:- " + id,
					HttpStatus.BAD_REQUEST);
		}

		/*
		 * Gson gson = new Gson(); String gsonString = gson.toJson(sonFromDB);
		 */
		return new ResponseEntity<Son>(sonFromDB, HttpStatus.OK);

	}

	@RequestMapping(path = "/get1", method = RequestMethod.GET, produces = "applucation/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "/get", notes = "Get Son Resource by id and name", response = Son.class)
	public ResponseEntity<?> getSonByIdAndName(
			@ApiParam(value = "id", required = true) @RequestParam(value = "id", required = true) Long id,
			@ApiParam(value = "name", required = true) @RequestParam(value = "name", required = true) String name) {
		logger.info("****************Inside SonController getSonByIdAndName method**************");
		Son sonFromDB = sonService.getSonById(id, name);
		logger.info("Son value: " + sonFromDB);
		if (sonFromDB == null) {
			logger.info("***********Inside if block of son getSonByIdAndName***************");
			return new ResponseEntity<String>("Sorry No data found", HttpStatus.BAD_REQUEST);
		}

		/*
		 * Gson gson = new Gson(); String gsonString = gson.toJson(sonFromDB);
		 */
		return new ResponseEntity<Son>(sonFromDB, HttpStatus.OK);
	}

	@RequestMapping(path = "/get2", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "/get", notes = "Get all son resources", response = Son.class, responseContainer = "LIST")
	public ResponseEntity<?> getAllSonDetails() {
		logger.info("************Inside SonController getAllSonDetails method************");
		List<Son> sonList = sonService.getAllSons();
		logger.info("Father alue : " + sonList);
		if (sonList.size() == 0 || sonList == null) {
			logger.info("***************Inside if block of son getAllSonDetails***********");
			return new ResponseEntity<String>("Sorry, No data found for son", HttpStatus.BAD_REQUEST);
		}
		/*
		 * Gson gson = new Gson(); String gsonString = gson.toJson(sonList);
		 */
		return new ResponseEntity <List<Son>>(sonList, HttpStatus.OK);

	}

	@RequestMapping(path = "/delete", method = RequestMethod.DELETE, produces = "text/plain")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "/delete", notes = "Remove Son Resouce BY ID", response = String.class)
	public ResponseEntity<?> deleteSonByID(
			@ApiParam(value = "id", required = true) @RequestParam(value = "id", required = true) Long id) {
		logger.info("************Inside SonController deleteSonByID***************");
		String response = sonService.deleteSonById(id);
		logger.info("Son value: " + response);
		if (response == null) {
			logger.info("************Inside if block of son deleteSonByID*************");
			return new ResponseEntity<String>("Sorry, No data found", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/update", method = RequestMethod.PATCH/* , consumes = "text/plain" */, produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "/update", notes = "Update Son Resource by Id", response = String.class)
	public ResponseEntity<?> updateSonByID(@RequestBody String son,
			@RequestParam(value = "id", required = true) Long id) throws JsonParseException, JsonMappingException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, ParseException{
		
		Son updateSon = sonService.updateSonById(son, id);
		if(updateSon == null) {
			return new ResponseEntity<String>("Sorry No data exist for id:-  " + id, HttpStatus.BAD_REQUEST);
			
		}
		
		
		return new ResponseEntity<Son>(updateSon, HttpStatus.OK);
	}
	
	
}
