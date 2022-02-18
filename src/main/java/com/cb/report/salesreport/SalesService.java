package com.cb.report.salesreport;

//import com.finwiz.exceptions.FinWizException;
//import com.finwiz.models.Account;
//import com.finwiz.models.BusinessUnit;
//import com.finwiz.repositories.AccountRepo;
//import com.finwiz.repositories.BusinessUnitRepo;
//import com.finwiz.request.AccountRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
@Log
public class SalesService {

    JdbcTemplate jdbcTemplate;

    public List<SalesReport> getAllReports(){

        String query = "select count(*), (CASE WHEN sh.Tablename != 'Home Delivery' THEN 'Dine In' ELSE sh.Tablename END) as Tablename FROM SaleDetail as SD " +
                "INNER JOIN SaleHeader as SH ON SD.SerialNumber=SH.SerialNumber " +
                "INNER JOIN ProductMaster as PM ON PM.ProductID= Sd.ProductID " +
                "INNER JOIN ProductGroupMaster as PG ON PG.ProductGroupID=PM.ProductGroupID " +
                "where CONVERT(DATE, DateTimeIn) between convert(date, '2022-01-01') AND convert(date, getdate()) " +
                "and PG.DepartmentID = 116 " +
                "group by (CASE WHEN sh.Tablename != 'Home Delivery' THEN 'Dine In' ELSE sh.Tablename END)";

        List<SalesReport> sr = new ArrayList<>();
        jdbcTemplate.query(
                query,
                (rs, rowNum) -> sr.add(new SalesReport(rs.getString(1),rs.getString(2))
        ));
        return sr;
    }

    public List<SalesReport> getAllDepartments(){
        String query = "Select * from DepartmentMaster";
        List<SalesReport> sr = new ArrayList<>();
        jdbcTemplate.query(
                query,
                (rs, rowNum) -> sr.add(new SalesReport(rs.getString(1),rs.getString(2))
                ));
        return sr;
    }
}
