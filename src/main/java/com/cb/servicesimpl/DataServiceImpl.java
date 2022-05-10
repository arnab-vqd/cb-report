package com.cb.servicesimpl;


import com.cb.dao.DatabaseHandler;
import com.cb.models.KeyValue;
import com.cb.services.DataService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
@Log
public class DataServiceImpl implements DataService {

    DatabaseHandler dbHandler;

    public List<KeyValue> getAllDepartments(){
        String query = "Select DepartmentID, DepartmentName from DepartmentMaster";
        return dbHandler.getKeyValueList(query);
    }

    public List<KeyValue> getAllDesignations(){
        String query = "select DesignationId, DesignationName from DesignationMaster where IsActive=1";
        return dbHandler.getKeyValueList(query);
    }

    public List<KeyValue> getAllUsers(){
        String query = "select UserID, UserName from UserMaster";
        return dbHandler.getKeyValueList(query);
    }

    public List<String> getCompanyList(){
        String query = "select distinct CompanyName from LocationMaster";
        return dbHandler.getStringList(query);
    }

    public List<KeyValue> getAllCities(String companies){
        String query = "select CityID, CityName from CityMaster where IsActive=1";
        if(companies!=null && !companies.equals("")){
            query += " and CityID in (select CityID from LocationMaster where CompanyName in ("+companies+"))";
        }
        return dbHandler.getKeyValueList(query);
    }



    public List<KeyValue> getAllLocations(String cityId,String companies){
        String query = "select LocationId, LocationName from LocationMaster where IsActive=1";
        if(cityId!=null && !cityId.equals("")){
            query += " and CityID = "+cityId;
        }
        if(cityId!=null && !cityId.equals("")){
            query += " and CompanyName in ("+companies+")";
        }
        return dbHandler.getKeyValueList(query);
    }

}
