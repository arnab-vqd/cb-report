package com.cb.services;


import com.cb.models.KeyValue;
import com.cb.models.SalesReportRequestParams;
import org.springframework.stereotype.Service;

import java.util.Hashtable;


@Service
public interface SalesService {

    Hashtable<String, KeyValue> getAllReports(SalesReportRequestParams params);

}
