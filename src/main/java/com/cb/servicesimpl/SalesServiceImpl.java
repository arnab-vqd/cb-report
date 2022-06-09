package com.cb.servicesimpl;


import com.cb.dao.DatabaseHandler;
import com.cb.models.KeyValue;
import com.cb.models.SalesReportRequestParams;
import com.cb.services.SalesService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Log
public class SalesServiceImpl implements SalesService {

    private DatabaseHandler dbHandler;

    @Override
    public Hashtable<String, KeyValue> getAllReports(SalesReportRequestParams params) {
        Hashtable<String, KeyValue> reports = new Hashtable<>();
        reports.putAll(getStringKeyValueHashtable(params,"Total","116,117,118,119,121,122"));
        reports.putAll(getStringKeyValueHashtable(params,"Food","116,122"));
        reports.putAll(getStringKeyValueHashtable(params,"Beverage","117"));
        reports.putAll(getStringKeyValueHashtable(params,"Hookah","121"));
        reports.putAll(getStringKeyValueHashtable(params,"Buffet","122"));
        reports.putAll(getStringKeyValueHashtable(params,"Liquor","118"));
        log.info(String.valueOf(reports));
        return reports;
    }

    @Override
    public List<Integer> getTotalNumberOfPeople(SalesReportRequestParams params) {

        List<Integer> obj = new ArrayList<>();

        String startDate = params.getStartDate();
        String endDate = params.getToDate();

        String compareStartDate;
        String compareToDate;
        if(params.getCompareLastYear().equals("Same Last Year")){
            compareStartDate = Date.valueOf(params.getStartDate()).toLocalDate().minusYears(1).toString();
            compareToDate = Date.valueOf(params.getToDate()).toLocalDate().minusYears(1).toString();
        } else {
            compareStartDate = params.getCompareStartDate();
            compareToDate = params.getCompareToDate();
        }

        String query = "select sum(NoOfPax) from SaleHeader as sh " +
                "where CONVERT(DATE, VoucherDate) between convert(date, '"+startDate+"') AND convert(date, '"+endDate+"') " ;
        query = addOtherParams(params, query, "and SH.LocationId in (", "and DATEPART(WEEKDAY,VoucherDate) in (");
        obj.add(dbHandler.getInteger(query));

        String queryCmp = "select sum(NoOfPax) from SaleHeader as sh " +
                "where CONVERT(DATE, VoucherDate) between convert(date, '"+compareStartDate+"') AND convert(date, '"+compareToDate+"') " ;
        queryCmp = addOtherParams(params, queryCmp, "and SH.LocationId in (", "and DATEPART(WEEKDAY,VoucherDate) in (");
        obj.add(dbHandler.getInteger(queryCmp));

        return obj;
    }



    @Override
    public List<Integer> getTotalNumberOfBills(SalesReportRequestParams params) {

        List<Integer> obj = new ArrayList<>();

        String startDate = params.getStartDate();
        String endDate = params.getToDate();

        String compareStartDate;
        String compareToDate;
        if(params.getCompareLastYear().equals("Same Last Year")){
            compareStartDate = Date.valueOf(params.getStartDate()).toLocalDate().minusYears(1).toString();
            compareToDate = Date.valueOf(params.getToDate()).toLocalDate().minusYears(1).toString();
        } else {
            compareStartDate = params.getCompareStartDate();
            compareToDate = params.getCompareToDate();
        }

        String query = "select count(*) from SaleHeader as sh " +
                "where CONVERT(DATE, VoucherDate) between convert(date, '"+startDate+"') AND convert(date, '"+endDate+"') " ;
        query = addOtherParams(params, query, " and SH.LocationId in (", " and DATEPART(WEEKDAY,VoucherDate) in (");

        obj.add(dbHandler.getInteger(query));

        String queryCmp = "select count(*) from SaleHeader as sh " +
                "where CONVERT(DATE, VoucherDate) between convert(date, '"+compareStartDate+"') AND convert(date, '"+compareToDate+"') " ;
        queryCmp = addOtherParams(params, queryCmp, "and SH.LocationId in (", "and DATEPART(WEEKDAY,VoucherDate) in (");
        obj.add(dbHandler.getInteger(queryCmp));

        return obj;
    }

    @Override
    public List<Integer> getTotalNumberOfCustomers(SalesReportRequestParams params) {

        List<Integer> obj = new ArrayList<>();

        String startDate = params.getStartDate();
        String endDate = params.getToDate();
        String compareStartDate;
        String compareToDate;
        if(params.getCompareLastYear().equals("Same Last Year")){
            compareStartDate = Date.valueOf(params.getStartDate()).toLocalDate().minusYears(1).toString();
            compareToDate = Date.valueOf(params.getToDate()).toLocalDate().minusYears(1).toString();
        } else {
            compareStartDate = params.getCompareStartDate();
            compareToDate = params.getCompareToDate();
        }

        String query = "select sum(NoOfPax) from SaleHeader as sh " +
                "where CONVERT(DATE, VoucherDate) between convert(date, '"+startDate+"') AND convert(date, '"+endDate+"') " ;
        query = addOtherParams(params, query, " and SH.LocationId in (", " and DATEPART(WEEKDAY,VoucherDate) in (");
        obj.add(dbHandler.getInteger(query));

        String queryCmp = "select sum(NoOfPax) from SaleHeader as sh " +
                "where CONVERT(DATE, VoucherDate) between convert(date, '"+compareStartDate+"') AND convert(date, '"+compareToDate+"') " ;
        queryCmp = addOtherParams(params, queryCmp, "and SH.LocationId in (", "and DATEPART(WEEKDAY,VoucherDate) in (");
        obj.add(dbHandler.getInteger(queryCmp));

        String totalBill = "select sum(SubTotal) from SaleHeader as sh " +
                "where CONVERT(DATE, VoucherDate) between convert(date, '"+startDate+"') AND convert(date, '"+endDate+"') " ;
        totalBill = addOtherParams(params, totalBill, "and SH.LocationId in (", "and DATEPART(WEEKDAY,VoucherDate) in (");
        obj.add(dbHandler.getInteger(totalBill));

        String totalBillCmp = "select sum(SubTotal) from SaleHeader as sh " +
                "where CONVERT(DATE, VoucherDate) between convert(date, '"+compareStartDate+"') AND convert(date, '"+compareToDate+"') " ;
        totalBillCmp = addOtherParams(params, totalBillCmp, "and SH.LocationId in (", "and DATEPART(WEEKDAY,VoucherDate) in (");
        obj.add(dbHandler.getInteger(totalBillCmp));

        return obj;
    }


    private String addOtherParams(SalesReportRequestParams params, String query, String s, String s2) {
        if (params.getOutlet() != null && params.getOutlet().length() > 0) {
            query += s + params.getOutlet() + ") ";
        }

        if (params.getSaleMode() != null && !params.getSaleMode().equals("total") && !params.getSaleMode().equals("average")) {
            query += s2 + params.getSaleMode() + ") ";
        }

        if (params.getSaleType() != null && params.getSaleType().equals("deliverySale")) {
            query += " and TableName='Home Delivery' ";
        }
        if (params.getSaleType() != null && params.getSaleType().equals("dineInSale")) {
            query += " and TableName!='Home Delivery' ";
        }
        return query;
    }




    private KeyValue getAllReportsHomeDelivery(String startDate, String toDate, String locations, String departmentIds, String saleMode) {

        String query = "select sum(FinalSaleAmount-TaxAmount), 'Home Delivery' as Tablename FROM SaleDetail as SD " +
                "JOIN SaleHeader as SH ON SD.SerialNumber=SH.SerialNumber " +
                "JOIN ProductMaster as PM ON PM.ProductID= Sd.ProductID " +
                "JOIN ProductGroupMaster as PG ON PG.ProductGroupID=PM.ProductGroupID " +
                "where SH.Tablename = 'Home Delivery'"+
                "and CONVERT(DATE, VoucherDate) between convert(date, '"+startDate+"') AND convert(date, '"+toDate+"') " ;
        if(locations!=null && locations.length()>0){
            query += "and SH.LocationId in ("+locations+") ";
        }
        if(departmentIds!=null){
            query += "and PG.DepartmentID in ("+departmentIds+") ";
            if(departmentIds.equals("122")){
                query += "and PM.SubGroupID = 309 ";
            } else if(departmentIds.equals("116,122")){
                query += "and PM.SubGroupID != 309 ";
            }
        }
        if(saleMode!=null && !saleMode.equals("total") && !saleMode.equals("average")){
            query += "and DATEPART(WEEKDAY,VoucherDate) in ("+saleMode+") ";
        }

        return dbHandler.getKeyValue(query);
    }

    private KeyValue getAllReportsDineIn(String startDate,String toDate, String locations, String departmentIds, String saleMode) {

        String query = "select sum(FinalSaleAmount-TaxAmount), 'Dine In' as Tablename FROM SaleDetail as SD " +
                "JOIN SaleHeader as SH ON SD.SerialNumber=SH.SerialNumber " +
                "JOIN ProductMaster as PM ON PM.ProductID= Sd.ProductID " +
                "JOIN ProductGroupMaster as PG ON PG.ProductGroupID=PM.ProductGroupID " +
                "where SH.Tablename != 'Home Delivery'"+
                "and CONVERT(DATE, VoucherDate) between convert(date, '"+startDate+"') AND convert(date, '"+toDate+"') " ;
        if(locations!=null && locations.length()>0){
            query += "and SH.LocationId in ("+locations+") ";
        }
        if(departmentIds!=null){
            query += "and PG.DepartmentID in ("+departmentIds+") ";
            if(departmentIds.equals("122")){
                query += "and PM.SubGroupID = 309 ";
            } else if(departmentIds.equals("116,122")){
                query += "and PM.SubGroupID != 309 ";
            }
        }
        if(saleMode!=null && !saleMode.equals("total") && !saleMode.equals("average")){
            query += "and DATEPART(WEEKDAY,VoucherDate) in ("+saleMode+") ";
        }

        return dbHandler.getKeyValue(query);
    }

    private KeyValue getAllReportsTotal(String startDate, String toDate, String locations, String departmentIds, String saleMode) {

        String query = "select sum(FinalSaleAmount-TaxAmount), 'Total' as Tablename FROM SaleHeader as SH  " +
                "left JOIN SaleDetail as SD ON SD.SerialNumber=SH.SerialNumber " +
                "JOIN ProductMaster as PM ON PM.ProductID= Sd.ProductID " +
                "JOIN ProductGroupMaster as PG ON PG.ProductGroupID=PM.ProductGroupID " +
                "where CONVERT(DATE, VoucherDate) between convert(date, '"+startDate+"') AND convert(date, '"+toDate+"') " ;
        if(locations!=null && locations.length()>0){
            query += "and SH.LocationId in ("+locations+") ";
        }
        if(departmentIds!=null){
            query += "and PG.DepartmentID in ("+departmentIds+") ";
            if(departmentIds.equals("122")){
                query += "and PM.SubGroupID = 309 ";
            } else if(departmentIds.equals("116,122")){
                query += "and PM.SubGroupID != 309 ";
            }
        }
        if(saleMode!=null && !saleMode.equals("total") && !saleMode.equals("average")){
            query += "and DATEPART(WEEKDAY,VoucherDate) in ("+saleMode+") ";
        }
        System.out.println(query);
        return dbHandler.getKeyValue(query);
    }


    private Hashtable<String, KeyValue> getStringKeyValueHashtable(SalesReportRequestParams params, String name, String departmentId) {
        Hashtable<String, KeyValue> reports = new Hashtable<>();
        String startDate = params.getStartDate();
        String endDate = params.getToDate();
        computeReports(params, name, departmentId, reports, startDate, endDate, "");

        String compareStartDate;
        String compareToDate;
        if(params.getCompareLastYear().equals("Same Last Year")){
            compareStartDate = Date.valueOf(params.getStartDate()).toLocalDate().minusYears(1).toString();
            compareToDate = Date.valueOf(params.getToDate()).toLocalDate().minusYears(1).toString();
        } else {
            compareStartDate = params.getCompareStartDate();
            compareToDate = params.getCompareToDate();
        }
        computeReports(params, name, departmentId, reports, compareStartDate, compareToDate,"LY");
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

//        if(params.getSaleMode()!=null && !params.getSaleMode().equals("total")){
//            int weekDays[] = this.getWeekDays(startDate, endDate);
//            KeyValue saleData = reports.get(name + " Sale"+type);
//            if(params.getSaleMode().equals("average")){
//                saleData.setKey(String.valueOf(getDoubleValue(saleData) /(weekDays[0]+weekDays[1])));
//                reports.put(name + " Sale"+type,saleData);
//            } else if(params.getSaleMode().equals("1,7")){
//                saleData.setKey(String.valueOf(getDoubleValue(saleData) /weekDays[0]));
//                reports.put(name + " Sale"+type,saleData);
//            } else if(params.getSaleMode().equals("2,3,4,5,6")){
//                saleData.setKey(String.valueOf(getDoubleValue(saleData) /weekDays[1]));
//                reports.put(name + " Sale"+type,saleData);
//            }
//        }

        if(params.getSaleMode()!=null && !params.getSaleMode().equals("total")){
            List<Integer> objList = Arrays.stream(params.getSaleMode().split(",")).map(Integer::valueOf).collect(Collectors.toList());
            int weekDays = this.getSelectedDaysCount(startDate, endDate, objList);
            KeyValue saleData = reports.get(name + " Sale"+type);
                saleData.setKey(String.valueOf(getDoubleValue(saleData)/weekDays));
                reports.put(name + " Sale"+type,saleData);
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


    private int getSelectedDaysCount(String startDate, String endDate, List<Integer> selectedDays){
        Calendar start = Calendar.getInstance();
        start.setTime(Date.valueOf(startDate));
        Calendar end = Calendar.getInstance();
        end.setTime(Date.valueOf(endDate));

        int days = 0;
        while (start.before(end)) {
            if (selectedDays.contains(start.get(Calendar.DAY_OF_WEEK))) {
                days++;
            }
            start.add(Calendar.DATE, 1);
        }
        return days;
    }
}
