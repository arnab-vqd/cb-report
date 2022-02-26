package com.cb.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesReportRequestParams {
    private Integer[] outlet;
    private String viewType;
    private String valueType;
    private String days;
    private String quarter;
    private String startDate="YYYY-mm-dd";
    private String toDate="YYYY-mm-dd";
}
