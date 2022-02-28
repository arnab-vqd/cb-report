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
		return new ResponseEntity<>(objSalesService.getAllReports(params.getStartDate(), params.getToDate(), params.getOutlet()), HttpStatus.OK);
	}

	@PostMapping("/getReportTotal")
	public ResponseEntity<Hashtable<String, KeyValue>> getReportTotal(@RequestBody SalesReportRequestParams params) {
		return new ResponseEntity<>(objSalesService.getReportTotal(params.getStartDate(), params.getToDate(), params.getOutlet()), HttpStatus.OK);
	}

	@PostMapping("/getReportFood")
	public ResponseEntity<Hashtable<String, KeyValue>> getReportFood(@RequestBody SalesReportRequestParams params) {
		return new ResponseEntity<>(objSalesService.getReportFood(params.getStartDate(), params.getToDate(), params.getOutlet()), HttpStatus.OK);
	}

	@PostMapping("/getReportBeverage")
	public ResponseEntity<Hashtable<String, KeyValue>> getReportBeverage(@RequestBody SalesReportRequestParams params) {
		return new ResponseEntity<>(objSalesService.getReportBeverage(params.getStartDate(), params.getToDate(), params.getOutlet()), HttpStatus.OK);
	}

	@PostMapping("/getReportHookah")
	public ResponseEntity<Hashtable<String, KeyValue>> getReportHookah(@RequestBody SalesReportRequestParams params) {
		return new ResponseEntity<>(objSalesService.getReportHookah(params.getStartDate(), params.getToDate(), params.getOutlet()), HttpStatus.OK);
	}

	@PostMapping("/getReportBuffet")
	public ResponseEntity<Hashtable<String, KeyValue>> getReportBuffet(@RequestBody SalesReportRequestParams params) {
		return new ResponseEntity<>(objSalesService.getReportBuffet(params.getStartDate(), params.getToDate(), params.getOutlet()), HttpStatus.OK);
	}

	@PostMapping("/getReportLiquor")
	public ResponseEntity<Hashtable<String, KeyValue>> getReportLiquor(@RequestBody SalesReportRequestParams params) {
		return new ResponseEntity<>(objSalesService.getReportLiquor(params.getStartDate(), params.getToDate(), params.getOutlet()), HttpStatus.OK);
	}


}






