package com.cb.report.salesreport;

import com.cb.report.annotations.WizController;
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
	public ResponseEntity<List<SalesReport>> getReport(@RequestBody SalesReportParams params) {
//		accountCreated = accountService.addNewAccount(accountCreated);
		return new ResponseEntity<>(objSalesService.getAllAccounts(), HttpStatus.CREATED);
	}

}






