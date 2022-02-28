package com.cb.services;


import com.cb.models.KeyValue;
import org.springframework.stereotype.Service;

import java.util.Hashtable;


@Service
public interface SalesService {

    Hashtable<String, KeyValue> getAllReports(String startDate, String toDate, String locations);
}
