package com.cb.servicesimpl;


import com.cb.dao.DatabaseHandler;
import com.cb.models.KeyValue;
import com.cb.services.SalesService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Hashtable;


@Service
@AllArgsConstructor
@Log
public class SalesServiceImpl implements SalesService {

    DatabaseHandler dbHandler;

    public KeyValue getAllReportsHomeDelivery(String startDate, String toDate, String locations, String departmentIds) {

        String query = "select count(*), 'Home Delivery' as Tablename FROM SaleDetail as SD " +
                "INNER JOIN SaleHeader as SH ON SD.SerialNumber=SH.SerialNumber " +
                "INNER JOIN ProductMaster as PM ON PM.ProductID= Sd.ProductID " +
                "INNER JOIN ProductGroupMaster as PG ON PG.ProductGroupID=PM.ProductGroupID " +
                "where SH.Tablename = 'Home Delivery'"+
                "and CONVERT(DATE, DateTimeIn) between convert(date, '"+startDate+"') AND convert(date, '"+toDate+"') " ;
        if(locations!=null && locations.length()>0){
            query += "and SH.LocationId in ("+locations+") ";
        }
        if(departmentIds!=null){
            query += "and PG.DepartmentID in ("+departmentIds+") ";
        }

        return dbHandler.getKeyValue(query);
    }

    public KeyValue getAllReportsDineIn(String startDate,String toDate, String locations, String departmentIds) {

        String query = "select count(*), 'Dine In' as Tablename FROM SaleDetail as SD " +
                "INNER JOIN SaleHeader as SH ON SD.SerialNumber=SH.SerialNumber " +
                "INNER JOIN ProductMaster as PM ON PM.ProductID= Sd.ProductID " +
                "INNER JOIN ProductGroupMaster as PG ON PG.ProductGroupID=PM.ProductGroupID " +
                "where SH.Tablename != 'Home Delivery'"+
                "and CONVERT(DATE, DateTimeIn) between convert(date, '"+startDate+"') AND convert(date, '"+toDate+"') " ;
        if(locations!=null && locations.length()>0){
            query += "and SH.LocationId in ("+locations+") ";
        }
        if(departmentIds!=null){
            query += "and PG.DepartmentID in ("+departmentIds+") ";
        }

        return dbHandler.getKeyValue(query);
    }

    public Hashtable<String, KeyValue> getAllReports(String startDate, String toDate, String locations) {
        Hashtable<String, KeyValue> reports = new Hashtable<>();
        reports.put("Total Sale HD", this.getAllReportsHomeDelivery(startDate,toDate, locations,  null));
        reports.put("Total Sale DI",this.getAllReportsDineIn(startDate,toDate, locations, null));
        reports.put("Food Sale HD",this.getAllReportsHomeDelivery(startDate,toDate, locations, "116"));
        reports.put("Food Sale DI",this.getAllReportsDineIn(startDate,toDate, locations, "116"));
        reports.put("Beverage Sale HD",this.getAllReportsHomeDelivery(startDate,toDate, locations, "117"));
        reports.put("Beverage Sale DI",this.getAllReportsDineIn(startDate,toDate, locations, "117"));
        reports.put("Hookah Sale HD",this.getAllReportsHomeDelivery(startDate,toDate, locations, "121"));
        reports.put("Hookah Sale HD DI",this.getAllReportsDineIn(startDate,toDate, locations, "121"));
        reports.put("Buffet Sale HD",this.getAllReportsHomeDelivery(startDate,toDate, locations, "122"));
        reports.put("Buffet Sale DI",this.getAllReportsDineIn(startDate,toDate, locations, "122"));
        reports.put("Liquor Sale HD",this.getAllReportsHomeDelivery(startDate,toDate, locations, "118,119"));
        reports.put("Liquor Sale DI",this.getAllReportsDineIn(startDate,toDate, locations, "118,119"));
        log.info(String.valueOf(reports));
        return reports;
    }
}
