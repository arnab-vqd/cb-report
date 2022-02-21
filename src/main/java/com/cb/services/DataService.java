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

    public List<KeyValue> getAllDepartments();

    public List<KeyValue> getAllDesignations();

    public List<KeyValue> getAllUsers();

    public List<KeyValue> getAllLocations();
}
