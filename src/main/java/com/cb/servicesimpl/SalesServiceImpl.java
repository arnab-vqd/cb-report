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

    @Override
    public Hashtable<String, KeyValue> getReportTotal(String startDate, String toDate, String outlet, String valueType) {
        Hashtable<String, KeyValue> reports = new Hashtable<>();
        reports.put("Total Sale HD", this.getAllReportsHomeDelivery(startDate,toDate, outlet,  null, valueType));
        reports.put("Total Sale DI",this.getAllReportsDineIn(startDate,toDate, outlet, null, valueType));
        return reports;
    }

    @Override
    public Hashtable<String, KeyValue> getReportFood(String startDate, String toDate, String outlet, String valueType) {
        Hashtable<String, KeyValue> reports = new Hashtable<>();
        reports.put("Food Sale HD",this.getAllReportsHomeDelivery(startDate,toDate, outlet, "116", valueType));
        reports.put("Food Sale DI",this.getAllReportsDineIn(startDate,toDate, outlet, "116", valueType));
        return reports;
    }

    @Override
    public Hashtable<String, KeyValue> getReportBeverage(String startDate, String toDate, String outlet, String valueType) {
        Hashtable<String, KeyValue> reports = new Hashtable<>();
        reports.put("Beverage Sale HD",this.getAllReportsHomeDelivery(startDate,toDate, outlet, "117", valueType));
        reports.put("Beverage Sale DI",this.getAllReportsDineIn(startDate,toDate, outlet, "117", valueType));
        return reports;
    }

    @Override
    public Hashtable<String, KeyValue> getReportHookah(String startDate, String toDate, String outlet, String valueType) {
        Hashtable<String, KeyValue> reports = new Hashtable<>();
        reports.put("Hookah Sale HD",this.getAllReportsHomeDelivery(startDate,toDate, outlet, "121", valueType));
        reports.put("Hookah Sale DI",this.getAllReportsDineIn(startDate,toDate, outlet, "121", valueType));
        return reports;
    }

    @Override
    public Hashtable<String, KeyValue> getReportBuffet(String startDate, String toDate, String outlet, String valueType) {
        Hashtable<String, KeyValue> reports = new Hashtable<>();
        reports.put("Buffet Sale HD",this.getAllReportsHomeDelivery(startDate,toDate, outlet, "122", valueType));
        reports.put("Buffet Sale DI",this.getAllReportsDineIn(startDate,toDate, outlet, "122", valueType));
        return reports;
    }

    @Override
    public Hashtable<String, KeyValue> getReportLiquor(String startDate, String toDate, String outlet, String valueType) {
        Hashtable<String, KeyValue> reports = new Hashtable<>();
        reports.put("Liquor Sale HD",this.getAllReportsHomeDelivery(startDate,toDate, outlet, "118,119", valueType));
        reports.put("Liquor Sale DI",this.getAllReportsDineIn(startDate,toDate, outlet, "118,119", valueType));
        return reports;
    }

    @Override
    public Hashtable<String, KeyValue> getAllReports(String startDate, String toDate, String outlet, String valueType) {
        Hashtable<String, KeyValue> reports = new Hashtable<>();
        reports.putAll(this.getReportTotal(startDate,toDate, outlet, valueType));
        reports.putAll(this.getReportFood(startDate,toDate, outlet, valueType));
        reports.putAll(this.getReportBeverage(startDate,toDate, outlet, valueType));
        reports.putAll(this.getReportHookah(startDate,toDate, outlet, valueType));
        reports.putAll(this.getReportBuffet(startDate,toDate, outlet, valueType));
        reports.putAll(this.getReportLiquor(startDate,toDate, outlet, valueType));
        log.info(String.valueOf(reports));
        return reports;
    }


    private DatabaseHandler dbHandler;

    private KeyValue getAllReportsHomeDelivery(String startDate, String toDate, String locations, String departmentIds, String valueType) {

        String query = "select sum("+valueType+"), 'Home Delivery' as Tablename FROM SaleDetail as SD " +
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

    private KeyValue getAllReportsDineIn(String startDate,String toDate, String locations, String departmentIds, String valueType) {

        String query = "select sum("+valueType+"), 'Dine In' as Tablename FROM SaleDetail as SD " +
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
}
