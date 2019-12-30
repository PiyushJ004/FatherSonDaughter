package com.example.demo.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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

import com.example.demo.model.Daughter;
import com.example.demo.service.DaughterService;
import com.google.gson.Gson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping
@Api(value = "Daughter Crud Operation", description = "This controller is for crud operation of daughter instance")
public class DaughterController {
	
	private static final Logger logger = LoggerFactory.getLogger(Daughter.class);
	
	@Autowired
	private DaughterService daughterService;
	
	@RequestMapping(path = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "/get", notes = "Get Daughter resource by id", response = Daughter.class)
	public ResponseEntity<?> createInstance(@Valid @RequestBody Daughter daughter, BindingResult bindingResult){
		logger.info("***************Inside DaughterController Instance method****************");
		if(bindingResult.hasErrors() == true) {
			logger.info("**************Inside if block of daughter createInstance");
			Map<String, String> error = new LinkedHashMap<String, String>();
			for(FieldError fieldError : bindingResult.getFieldErrors()) {
				logger.info("*************Inside for block of daughter createInstance***************");
				error.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return new ResponseEntity<Map<String, String>>(error, HttpStatus.BAD_REQUEST);
		}
		
		Daughter daughterToDb = daughterService.registerDaughter(daughter);
		logger.info("Daughter value: " + daughterToDb);
		Gson gson = new Gson();
		String gsonString = gson.toJson(daughterToDb);
		return new ResponseEntity<String>(gsonString, HttpStatus.CREATED);
	}
	
	@RequestMapping(path = "/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "/get", notes = "Get daughter resource by id", response = Daughter.class)
	public ResponseEntity<?> getDaughterByID(
			@ApiParam(value = "/get", required = true) @RequestParam(value = "id", required = true) Long id){
		logger.info("*************Inside DaughterController getDaughterByID*****************");
		Daughter daughterFromDB = daughterService.getDaughterByDaughterIdOnly(id);
		logger.info("Daughter value :" + daughterFromDB);
		if(daughterFromDB == null) {
			logger.info("*********Inside if block of daughter getDaughterByID**************");
			return new ResponseEntity<String>("Sorry, could not retrieve data from daughter table for id: " + id,
					HttpStatus.BAD_REQUEST);
		}
		Gson gson = new Gson();
		String gsonString = gson.toJson(daughterFromDB);
		return new ResponseEntity<String>(gsonString, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "/get", notes = "Get Daughter resource by id and name", response = Daughter.class)
	public ResponseEntity<?> getDaughterIdAndName(
			@ApiParam(value = "id", required = true)@RequestParam(value = "id", required = true)Long id,
			@ApiParam(value = "name", required = true)@RequestParam(value = "name", required = true)String name){
		logger.info("***************Inside DaughterController getDaughterIdAndName method**************");
		Daughter daughterFromDB = daughterService.getDaughterById(id, name);
		logger.info("Daughter value: " + daughterFromDB);
		if(daughterFromDB == null) {
			logger.info("***************Inside if block of daughter getDaughterIdAndName**************");
			return new ResponseEntity<String>("Sorry, No data found", HttpStatus.OK);
		}
		
		Gson gson = new Gson();
		String gsonString = gson.toJson(daughterFromDB);
		return new ResponseEntity<String>(gsonString, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "/get", notes = "Get all daughter resource", response = Daughter.class, responseContainer = "List")
	public ResponseEntity<?> getAllDaughters(){
		logger.info("************Inside DaughterController getAllDaughters method***************");
		List<Daughter> daughterList = daughterService.getAllDaughters();
		logger.info("Daughter value: " + daughterList);
		if(daughterList.size() == 0 || daughterList == null) {
			logger.info("***************Insode if block of daughter getAllDaughters ***************");
			return new ResponseEntity<String>("Sorry, No data found for daughter", HttpStatus.BAD_REQUEST);
		}
		Gson gson = new Gson();
		String gsonString = gson.toJson(daughterList);
		return new ResponseEntity<String>(gsonString, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/delete", method = RequestMethod.DELETE, produces = "test/plain")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "/delete", notes = "Remove daughter resource by ID", response = String.class)
	public ResponseEntity<?> deleteDaughterByID(
			@ApiParam(value = "/get", required = true) @RequestParam(value = "id", required = true) Long id){
		logger.info("************Inside DaughterController deleteDaughterByID***************");
		String response = daughterService.deleteSonById(id);
		logger.info("Daughter value: " + response);
		if(response == null) {
			logger.info("*****************Inside if block of daughter deleteDaughterByID**************");
			return new ResponseEntity<String>("Sorry, No data found", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}

