package com.cb.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesReportRequestParams {
    private String outlet;
    private String saleType;
    private String saleMode;
    private String startDate="YYYY-mm-dd";
    private String toDate="YYYY-mm-dd";
    private String compareLastYear="Same Last Year";
    private String compareStartDate="YYYY-mm-dd";
    private String compareToDate="YYYY-mm-dd";
}
