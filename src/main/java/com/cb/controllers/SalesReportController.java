package com.cb.controllers;

import com.cb.annotations.WizController;
import com.cb.models.KeyValue;
import com.cb.models.SalesReportRequestParams;
import com.cb.services.SalesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.List;

@WizController
@RequestMapping(path="/sales")
@AllArgsConstructor
public class SalesReportController {


	SalesService objSalesService;

	@PostMapping("/getReport")
	public ResponseEntity<Hashtable<String, KeyValue>> getReport(@RequestBody SalesReportRequestParams params) {
		return new ResponseEntity<>(objSalesService.getAllReports(params), HttpStatus.OK);
	}

	@PostMapping("/getPax")
	public ResponseEntity<Integer> getPax(@RequestBody SalesReportRequestParams params) {
		return new ResponseEntity<>(objSalesService.getTotalNumberOfPeople(params), HttpStatus.OK);
	}

}






