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

import com.example.demo.model.Father;
import com.example.demo.service.FatherService;
import com.google.gson.Gson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(path = "/api/object/father")
@Api(value = "Father Crud Operations", description = "This controller is for crud operation of father Instance")
public class FatherController {

	private static final Logger logger = LoggerFactory.getLogger(FatherController.class);

	@Autowired
	private FatherService fatherService;

	@RequestMapping(path = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "/create", notes = "Save Father Data To The Database", response = Father.class)
	public ResponseEntity<?> createInstance(@Valid @RequestBody Father father, BindingResult bindingResult) {
		logger.info("***********Inside FatherController Create Instance method*************");
		if (bindingResult.hasErrors() == true) {
			logger.info("***********Inside if block of Father createInstance*****************");
			Map<String, String> error = new LinkedHashMap<String, String>();
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				logger.info("************Inside for block of Father createInstance***************");
				error.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return new ResponseEntity<Map<String, String>>(error, HttpStatus.BAD_REQUEST);
		}

		Father fatherToDb = fatherService.registerFather(father);
		logger.info("Father value: " + fatherToDb);
		Gson gson = new Gson();
		String gsonString = gson.toJson(fatherToDb);
		return new ResponseEntity<String>(gsonString, HttpStatus.CREATED);
	}

	@RequestMapping(path = "/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "/get", notes = "Get Father Resource By ID", response = Father.class)
	public ResponseEntity<?> getFatherByID(
			@ApiParam(value = "id", required = true) @RequestParam(value = "id", required = true) Long id) {
		logger.info("***************Inside FatherController getFatherByID method*************** ");
		Father fatherFromDB = fatherService.getFatherByFatherIdOnly(id);
		logger.info("Father value: " + fatherFromDB );
		if (fatherFromDB == null) {
			logger.info("**************Inside if block of father getFatherByID***************");
			return new ResponseEntity<String>("Sorry Could Not Retrieve Data From Father Table For id:- " + id,
					HttpStatus.BAD_REQUEST);
		}
		Gson gson = new Gson();
		String gsonString = gson.toJson(fatherFromDB);
		return new ResponseEntity<String>(gsonString, HttpStatus.OK);
	}

	@RequestMapping(path = "/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "/get", notes = "Get Father Resource By Id and Name", response = Father.class)
	public ResponseEntity<?> getFatherByIdAndName(
			@ApiParam(value = "id", required = true) @RequestParam(value = "id", required = true) Long id,
			@ApiParam(value = "name", required = true) @RequestParam(value = "name", required = true) String name) {
		logger.info("***************Inside FatherController getFatherByIdAndName method*************** ");
		Father fatherFromDB = fatherService.getFatherById(id, name);
		logger.info("Father value: " + fatherFromDB );
		if (fatherFromDB == null) {
			logger.info("**************Inside if block of father getFatherByIdAndName***************");
			return new ResponseEntity<String>("Sorry No Data Found", HttpStatus.BAD_REQUEST);
		}

		Gson gson = new Gson();
		String gsonString = gson.toJson(fatherFromDB);
		return new ResponseEntity<String>(gsonString, HttpStatus.OK);

	}

	@RequestMapping(path = "/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "/get", notes = "Get All Father Resources", response = Father.class, responseContainer = "LIST")
	public ResponseEntity<?> getAllFatherDetails() {
		logger.info("***************Inside FatherController getAllFatherDetails method*************** ");
		List<Father> fatherList = fatherService.getAllFathers();
		logger.info("Father value: " + fatherList );
		if (fatherList.size() == 0 || fatherList == null) {
			logger.info("**************Inside if block of father getAllFatherDetails***************");
			return new ResponseEntity<String>("Sorry No Data Found For Father", HttpStatus.BAD_REQUEST);
		}
		Gson gson = new Gson();
		String gsonString = gson.toJson(fatherList);
		return new ResponseEntity<String>(gsonString, HttpStatus.OK);

	}

	@RequestMapping(path = "/delete", method = RequestMethod.DELETE, produces = "text/plain")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "/delete", notes = "Remove Father Resouce BY ID", response = String.class)
	public ResponseEntity<?> deleteFatherByID(
			@ApiParam(value = "id", required = true) @RequestParam(value = "id", required = true) Long id) {
		logger.info("***************Inside FatherController deleteFatherByID method*************** ");
		String response = fatherService.deleteFatherById(id);
		logger.info("Father value: " + response );
		if (response == null) {
			logger.info("**************Inside if block of father deleteFatherByID***************");
			return new ResponseEntity<String>("Sorry No Data Found To Delete", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

}
