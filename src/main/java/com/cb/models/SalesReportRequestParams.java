package com.cb.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesReportRequestParams {
    private String outlet;
    private String valueType;
    private String startDate="YYYY-mm-dd";
    private String toDate="YYYY-mm-dd";
    private boolean compareLastYear=false;
}
