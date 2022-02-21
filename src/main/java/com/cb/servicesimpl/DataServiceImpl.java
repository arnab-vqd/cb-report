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

    public List<KeyValue> getAllLocations(){
        String query = "select LocationId, LocationName from LocationMaster where IsActive=1";
        return dbHandler.getKeyValueList(query);
    }

}
