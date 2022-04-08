package com.cb.servicesimpl;


import com.cb.dao.DatabaseHandler;
import com.cb.models.KeyValue;
import com.cb.models.SalesReportRequestParams;
import com.cb.services.SalesService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.Hashtable;


@Service
@AllArgsConstructor
@Log
public class SalesServiceImpl implements SalesService {

    private DatabaseHandler dbHandler;

    @Override
    public Hashtable<String, KeyValue> getAllReports(SalesReportRequestParams params) {
        Hashtable<String, KeyValue> reports = new Hashtable<>();
        reports.putAll(getStringKeyValueHashtable(params,"Total",null));
        reports.putAll(getStringKeyValueHashtable(params,"Food","116"));
        reports.putAll(getStringKeyValueHashtable(params,"Beverage","117"));
        reports.putAll(getStringKeyValueHashtable(params,"Hookah","121"));
        reports.putAll(getStringKeyValueHashtable(params,"Buffet","122"));
        reports.putAll(getStringKeyValueHashtable(params,"Liquor","118,119"));
        log.info(String.valueOf(reports));
        return reports;
    }

    private KeyValue getAllReportsHomeDelivery(String startDate, String toDate, String locations, String departmentIds, String saleMode) {

        String query = "select sum(mrp), 'Home Delivery' as Tablename FROM SaleDetail as SD " +
                "JOIN SaleHeader as SH ON SD.SerialNumber=SH.SerialNumber " +
                "JOIN ProductMaster as PM ON PM.ProductID= Sd.ProductID " +
                "JOIN ProductGroupMaster as PG ON PG.ProductGroupID=PM.ProductGroupID " +
                "where SH.Tablename = 'Home Delivery'"+
                "and CONVERT(DATE, DateTimeIn) between convert(date, '"+startDate+"') AND convert(date, '"+toDate+"') " ;
        if(locations!=null && locations.length()>0){
            query += "and SH.LocationId in ("+locations+") ";
        }
        if(departmentIds!=null){
            query += "and PG.DepartmentID in ("+departmentIds+") ";
        }
        if(saleMode!=null && !saleMode.equals("total") && !saleMode.equals("average")){
            query += "and DATEPART(WEEKDAY,DateTimeIn) in ("+saleMode+") ";
        }

        return dbHandler.getKeyValue(query);
    }

    private KeyValue getAllReportsDineIn(String startDate,String toDate, String locations, String departmentIds, String saleMode) {

        String query = "select sum(mrp), 'Dine In' as Tablename FROM SaleDetail as SD " +
                "JOIN SaleHeader as SH ON SD.SerialNumber=SH.SerialNumber " +
                "JOIN ProductMaster as PM ON PM.ProductID= Sd.ProductID " +
                "JOIN ProductGroupMaster as PG ON PG.ProductGroupID=PM.ProductGroupID " +
                "where SH.Tablename != 'Home Delivery'"+
                "and CONVERT(DATE, DateTimeIn) between convert(date, '"+startDate+"') AND convert(date, '"+toDate+"') " ;
        if(locations!=null && locations.length()>0){
            query += "and SH.LocationId in ("+locations+") ";
        }
        if(departmentIds!=null){
            query += "and PG.DepartmentID in ("+departmentIds+") ";
        }
        if(saleMode!=null && !saleMode.equals("total") && !saleMode.equals("average")){
            query += "and DATEPART(WEEKDAY,DateTimeIn) in ("+saleMode+") ";
        }

        return dbHandler.getKeyValue(query);
    }

    private KeyValue getAllReportsTotal(String startDate, String toDate, String locations, String departmentIds, String saleMode) {

        String query = "select sum(mrp), 'Total' as Tablename FROM SaleDetail as SD " +
                "JOIN SaleHeader as SH ON SD.SerialNumber=SH.SerialNumber " +
                "JOIN ProductMaster as PM ON PM.ProductID= Sd.ProductID " +
                "JOIN ProductGroupMaster as PG ON PG.ProductGroupID=PM.ProductGroupID " +
                "where CONVERT(DATE, DateTimeIn) between convert(date, '"+startDate+"') AND convert(date, '"+toDate+"') " ;
        if(locations!=null && locations.length()>0){
            query += "and SH.LocationId in ("+locations+") ";
        }
        if(departmentIds!=null){
            query += "and PG.DepartmentID in ("+departmentIds+") ";
        }
        if(saleMode!=null && !saleMode.equals("total") && !saleMode.equals("average")){
            query += "and DATEPART(WEEKDAY,DateTimeIn) in ("+saleMode+") ";
        }

        return dbHandler.getKeyValue(query);
    }


    private Hashtable<String, KeyValue> getStringKeyValueHashtable(SalesReportRequestParams params, String name, String departmentId) {
        Hashtable<String, KeyValue> reports = new Hashtable<>();
        String startDate = params.getStartDate();
        String endDate = params.getToDate();
        computeReports(params, name, departmentId, reports, startDate, endDate, "");
        if(params.getCompareLastYear().equals("Same Last Year")){
            String compareStartDate = Date.valueOf(params.getStartDate()).toLocalDate().minusYears(1).toString();
            String compareToDate = Date.valueOf(params.getToDate()).toLocalDate().minusYears(1).toString();
            computeReports(params, name, departmentId, reports, compareStartDate, compareToDate,"LY");
        } else {
            String compareStartDate = params.getCompareStartDate();
            String compareToDate = params.getCompareToDate();
            computeReports(params, name, departmentId, reports, compareStartDate, compareToDate, "LY");
        }
        return reports;
    }

    private void computeReports(SalesReportRequestParams params, String name, String departmentId, Hashtable<String, KeyValue> reports, String startDate, String endDate, String type) {


        if(params.getSaleType()!=null && params.getSaleType().equals("totalSale")){
            reports.put(name +" Sale"+type,this.getAllReportsTotal(startDate, endDate, params.getOutlet(), departmentId,params.getSaleMode()));
        } else if(params.getSaleType()!=null && params.getSaleType().equals("deliverySale")) {
            reports.put(name + " Sale"+type, this.getAllReportsHomeDelivery(startDate, endDate, params.getOutlet(), departmentId, params.getSaleMode()));
        } else if(params.getSaleType()!=null && params.getSaleType().equals("dineInSale")) {
            reports.put(name + " Sale"+type, this.getAllReportsDineIn(startDate, endDate, params.getOutlet(), departmentId, params.getSaleMode()));
        }

        if(params.getSaleMode()!=null && !params.getSaleMode().equals("total")){
            int weekDays[] = this.getWeekDays(startDate, endDate);
            KeyValue saleData = reports.get(name + " Sale"+type);
            if(params.getSaleMode().equals("average")){
                saleData.setKey(String.valueOf(getDoubleValue(saleData) /(weekDays[0]+weekDays[1])));
                reports.put(name + " Sale"+type,saleData);
            } else if(params.getSaleMode().equals("1,7")){
                saleData.setKey(String.valueOf(getDoubleValue(saleData) /weekDays[0]));
                reports.put(name + " Sale"+type,saleData);
            } else if(params.getSaleMode().equals("2,3,4,5,6")){
                saleData.setKey(String.valueOf(getDoubleValue(saleData) /weekDays[1]));
                reports.put(name + " Sale"+type,saleData);
            }
        }

    }

    private Double getDoubleValue(KeyValue saleData) {
        if(saleData.getKey()==null){
            return 0.0;
        }
        return Double.valueOf(saleData.getKey());
    }


    private int[] getWeekDays(String startDate, String endDate){
        Calendar start = Calendar.getInstance();
        start.setTime(Date.valueOf(startDate));
        Calendar end = Calendar.getInstance();
        end.setTime(Date.valueOf(endDate));

        int weekEnds = 0;
        int weekDays = 0;
        while (start.before(end)) {
            if (start.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || start.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                weekEnds++;
            } else {
                weekDays++;
            }
            start.add(Calendar.DATE, 1);
        }
        return new int[]{weekEnds, weekDays};
    }
}
