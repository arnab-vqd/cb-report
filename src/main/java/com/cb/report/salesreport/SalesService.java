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


//    AccountRepo accountRepo;
//    BusinessUnitRepo businessUnitRepo;

    public List<SalesReport> getAllAccounts(){
        List<SalesReport> sr = new ArrayList<>();
        jdbcTemplate.query(
                "SELECT * FROM account WHERE active = ?", new Object[] { 1 },
                (rs, rowNum) -> sr.add(new SalesReport(rs.getString("id")))
        );
        return sr;
    }
//
//    @SneakyThrows
//    public void validateAccount(AccountRequest account) {
//        if(account.getName()==null || account.getName().trim().equals("")){
//            throw new FinWizException("Account Name should not be blank", HttpStatus.PARTIAL_CONTENT);
//        }
//        if(accountRepo.findByName(account.getName())!=null){
//            throw new FinWizException("Account Name Already Exists", HttpStatus.CONFLICT);
//        }
//    }
//
//    public Account addNewAccount(Account account) {
//        return accountRepo.save(account);
//    }
//
//    @SneakyThrows
//    public Account addBusinessUnit(AccountRequest accountRequest){
//        BusinessUnit businessUnit = businessUnitRepo.findByBusinessUnit(accountRequest.getBusinessUnitId());
//        if(businessUnit==null){
//            throw new FinWizException("No Business account found", HttpStatus.FAILED_DEPENDENCY);
//        } else {
//            return Account.builder().name(accountRequest.getName()).businessUnit(businessUnit).active(true).build();
//        }
//    }


}
