package com.cb.services;


import com.cb.models.KeyValue;
import com.cb.models.SalesReportRequestParams;
import org.springframework.stereotype.Service;

import java.util.Hashtable;
import java.util.List;


@Service
public interface SalesService {

    Hashtable<String, KeyValue> getAllReports(SalesReportRequestParams params);

    List<Integer> getTotalNumberOfPeople(SalesReportRequestParams params);

    List<Integer> getTotalNumberOfBills(SalesReportRequestParams params);

    List<Integer> getTotalNumberOfCustomers(SalesReportRequestParams params);
}
