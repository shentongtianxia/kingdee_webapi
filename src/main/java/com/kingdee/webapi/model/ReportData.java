package com.kingdee.webapi.model;

import lombok.Data;

@Data
public class ReportData {
    private  Integer rowIndex;
    private  String assetProject;
    private  String assetCurrentValue;
    private  String assetLastYearValue;
    private  String liabilityProject;
    private  String liabilityCurrentValue;
    private  String liabilityLastYearValue;


}
