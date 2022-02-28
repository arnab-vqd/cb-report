package com.cb.services;


import com.cb.models.KeyValue;
import org.springframework.stereotype.Service;

import java.util.Hashtable;


@Service
public interface SalesService {

    Hashtable<String, KeyValue> getAllReports(String startDate, String toDate, String locations);

    Hashtable<String, KeyValue> getReportTotal(String startDate, String toDate, String outlet);

    Hashtable<String, KeyValue> getReportFood(String startDate, String toDate, String outlet);

    Hashtable<String, KeyValue> getReportBeverage(String startDate, String toDate, String outlet);

    Hashtable<String, KeyValue> getReportHookah(String startDate, String toDate, String outlet);

    Hashtable<String, KeyValue> getReportBuffet(String startDate, String toDate, String outlet);

    Hashtable<String, KeyValue> getReportLiquor(String startDate, String toDate, String outlet);
}
