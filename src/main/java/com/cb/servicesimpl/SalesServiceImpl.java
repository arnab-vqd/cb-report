package com.cb.servicesimpl;


import com.cb.dao.DatabaseHandler;
import com.cb.models.KeyValue;
import com.cb.models.SalesReportRequestParams;
import com.cb.services.SalesService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Hashtable;


@Service
@AllArgsConstructor
@Log
public class SalesServiceImpl implements SalesService {

    @Override
    public Hashtable<String, KeyValue> getReportTotal(SalesReportRequestParams params) {
        return getStringKeyValueHashtable(params,"Total",null);
    }

    @Override
    public Hashtable<String, KeyValue> getReportFood(SalesReportRequestParams params) {
        return getStringKeyValueHashtable(params,"Food","116");
    }

    @Override
    public Hashtable<String, KeyValue> getReportBeverage(SalesReportRequestParams params) {
        return getStringKeyValueHashtable(params,"Beverage","117");
    }

    @Override
    public Hashtable<String, KeyValue> getReportHookah(SalesReportRequestParams params) {
        return getStringKeyValueHashtable(params,"Hookah","121");
    }

    @Override
    public Hashtable<String, KeyValue> getReportBuffet(SalesReportRequestParams params) {
        return getStringKeyValueHashtable(params,"Buffet","122");
    }

    @Override
    public Hashtable<String, KeyValue> getReportLiquor(SalesReportRequestParams params) {
        return getStringKeyValueHashtable(params,"Liquor","116");
    }

    @Override
    public Hashtable<String, KeyValue> getAllReports(SalesReportRequestParams params) {
        Hashtable<String, KeyValue> reports = new Hashtable<>();
        reports.putAll(this.getReportTotal(params));
        reports.putAll(this.getReportFood(params));
        reports.putAll(this.getReportBeverage(params));
        reports.putAll(this.getReportHookah(params));
        reports.putAll(this.getReportBuffet(params));
        reports.putAll(this.getReportLiquor(params));
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

    private Hashtable<String, KeyValue> getStringKeyValueHashtable(SalesReportRequestParams params, String name, String departmentId) {
        Hashtable<String, KeyValue> reports = new Hashtable<>();
        reports.put(name+" Sale HD", this.getAllReportsHomeDelivery(params.getStartDate(), params.getToDate(), params.getOutlet(),  departmentId, params.getValueType()));
        reports.put(name+" Sale DI",this.getAllReportsDineIn(params.getStartDate(), params.getToDate(), params.getOutlet(), departmentId, params.getValueType()));
        if(params.isCompareLastYear()){
            String startDate = Date.valueOf(params.getStartDate()).toLocalDate().minusYears(1).toString();
            String toDate = Date.valueOf(params.getToDate()).toLocalDate().minusYears(1).toString();
            reports.put(name+" Sale HD LY", this.getAllReportsHomeDelivery(startDate,toDate, params.getOutlet(),  departmentId, params.getValueType()));
            reports.put(name+" Sale DI LY",this.getAllReportsDineIn(startDate,toDate, params.getOutlet(), departmentId, params.getValueType()));
        }
        return reports;
    }
}
