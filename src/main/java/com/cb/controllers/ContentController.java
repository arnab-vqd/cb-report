package com.cb.controllers;

import com.cb.annotations.WizController;
import com.cb.models.KeyValue;
import com.cb.services.DataService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@WizController
@RequestMapping(path="/sales")
@AllArgsConstructor
public class ContentController {

	private DataService dataService;

	@GetMapping("/getAllDepartments")
	public ResponseEntity<List<KeyValue>> getAllDepartments() {
		return new ResponseEntity<>(dataService.getAllDepartments(), HttpStatus.OK);
	}

	@GetMapping("/getAllDesignations")
	public ResponseEntity<List<KeyValue>> getAllDesignations() {
		return new ResponseEntity<>(dataService.getAllDesignations(), HttpStatus.OK);
	}

	@GetMapping("/getAllUsers")
	public ResponseEntity<List<KeyValue>> getAllUsers() {
		return new ResponseEntity<>(dataService.getAllUsers(), HttpStatus.OK);
	}

	@GetMapping("/getCompanyList")
	public ResponseEntity<List<String>> getCompanyList() {
		return new ResponseEntity<>(dataService.getCompanyList(), HttpStatus.OK);
	}

	@GetMapping("/getAllLocations")
	public ResponseEntity<List<KeyValue>> getAllLocations(@RequestParam("cityID") String cityID) {
		return new ResponseEntity<>(dataService.getAllLocations(cityID), HttpStatus.OK);
	}

	@GetMapping("/getAllCities")
	public ResponseEntity<List<KeyValue>> getAllCities(@RequestParam("companies") String companies) {
		return new ResponseEntity<>(dataService.getAllCities(companies), HttpStatus.OK);
	}



}






