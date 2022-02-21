package com.cb.controllers;

import com.cb.annotations.WizController;
import com.cb.models.KeyValue;
import com.cb.report.salesreport.SalesReportParams;
import com.cb.services.SalesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@WizController
@RequestMapping(path="/sales")
@AllArgsConstructor
public class SalesReportController {


	SalesService objSalesService;

	@PostMapping("/getReport")
	public ResponseEntity<List<KeyValue>> getReport(@RequestBody SalesReportParams params) {
		return new ResponseEntity<>(objSalesService.getAllReports(params.getStartDate(), params.getToDate()), HttpStatus.OK);
	}


}






