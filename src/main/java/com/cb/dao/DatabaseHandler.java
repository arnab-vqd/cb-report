package com.cb.dao;

import com.cb.models.KeyValue;
import com.cb.services.DataService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Log
public class DatabaseHandler {

    JdbcTemplate jdbcTemplate;

    public List<KeyValue> getKeyValueList(String query){
        List<KeyValue> sr = new ArrayList<>();
        jdbcTemplate.query(
                query,
                (rs, rowNum) -> sr.add(new KeyValue(rs.getString(1),rs.getString(2))
                ));
        return sr;
    }

    public KeyValue getKeyValue(String query){
        return jdbcTemplate.queryForObject(
                query,
                (rs, rowNum) -> new KeyValue(rs.getString(1),rs.getString(2))
                );
    }

    public int getInteger(String query){
        return jdbcTemplate.queryForObject(
                query,
                (rs, rowNum) -> rs.getInt(1)
        );
    }

    public List<String> getStringList(String query){
        List<String> sr = new ArrayList<>();
        jdbcTemplate.query(
                query,
                (rs, rowNum) -> sr.add(rs.getString(1))
                );
        return sr;
    }

}
