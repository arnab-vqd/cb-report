package com.cb.services;


import com.cb.models.KeyValue;
import com.cb.models.SalesReportRequestParams;
import org.springframework.stereotype.Service;

import java.util.Hashtable;


@Service
public interface SalesService {

    Hashtable<String, KeyValue> getAllReports(SalesReportRequestParams params);

    Hashtable<String, KeyValue> getReportTotal(SalesReportRequestParams params);

    Hashtable<String, KeyValue> getReportFood(SalesReportRequestParams params);

    Hashtable<String, KeyValue> getReportBeverage(SalesReportRequestParams params);

    Hashtable<String, KeyValue> getReportHookah(SalesReportRequestParams params);

    Hashtable<String, KeyValue> getReportBuffet(SalesReportRequestParams params);

    Hashtable<String, KeyValue> getReportLiquor(SalesReportRequestParams params);
}
