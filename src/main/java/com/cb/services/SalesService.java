package com.cb.services;


import com.cb.models.KeyValue;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
@Log
public class SalesService {

    JdbcTemplate jdbcTemplate;

    public List<KeyValue> getAllReportsHomeDelivery(String startDate, String toDate){

        String query = "select count(*), 'Home Delivery' as Tablename FROM SaleDetail as SD " +
                "INNER JOIN SaleHeader as SH ON SD.SerialNumber=SH.SerialNumber " +
                "INNER JOIN ProductMaster as PM ON PM.ProductID= Sd.ProductID " +
                "INNER JOIN ProductGroupMaster as PG ON PG.ProductGroupID=PM.ProductGroupID " +
                "where CONVERT(DATE, DateTimeIn) between convert(date, '"+startDate+"') AND convert(date, '"+toDate+"') " +
                "and PG.DepartmentID = 116 " +
                "and SH.Tablename = 'Home Delivery'";
        List<KeyValue> sr = new ArrayList<>();
        jdbcTemplate.query(
                query,
                (rs, rowNum) -> sr.add(new KeyValue(rs.getString(1),rs.getString(2))
        ));
        return sr;
    }

    public List<KeyValue> getAllReportsDineIn(String startDate,String toDate){

        String query = "select count(*), 'Dine In' as Tablename FROM SaleDetail as SD " +
                "INNER JOIN SaleHeader as SH ON SD.SerialNumber=SH.SerialNumber " +
                "INNER JOIN ProductMaster as PM ON PM.ProductID= Sd.ProductID " +
                "INNER JOIN ProductGroupMaster as PG ON PG.ProductGroupID=PM.ProductGroupID " +
                "where CONVERT(DATE, DateTimeIn) between convert(date, '"+startDate+"') AND convert(date, '"+toDate+"') " +
                "and PG.DepartmentID = 116 " +
                "and SH.Tablename != 'Home Delivery'";

        List<KeyValue> sr = new ArrayList<>();
        jdbcTemplate.query(
                query,
                (rs, rowNum) -> sr.add(new KeyValue(rs.getString(1),rs.getString(2))
                ));
        return sr;
    }

    public List<KeyValue> getAllReports(String startDate, String toDate) {
        List<KeyValue> reports = this.getAllReportsHomeDelivery(startDate,toDate);
        reports.addAll(this.getAllReportsDineIn(startDate,toDate));
        log.info(String.valueOf(reports));
        return reports;
    }
}
