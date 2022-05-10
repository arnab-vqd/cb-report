package com.cb.services;


import com.cb.models.KeyValue;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public interface DataService {

    List<KeyValue> getAllDepartments();

    List<KeyValue> getAllDesignations();

    List<KeyValue> getAllUsers();

    List<String> getCompanyList();

    List<KeyValue> getAllCities(String companies);

    List<KeyValue> getAllLocations(String cityId,String companies);
}
