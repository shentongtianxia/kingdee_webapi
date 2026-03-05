package com.kingdee.webapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 利润表报告模型
 * 
 * @author Kingdee
 * @version 1.0.0
 */
@Data
public class IncomeStatementReport {

    @JsonProperty("序号")
    private Integer index;
    @JsonProperty("项目")
    private String project;
    @JsonProperty("本期金额")
    private String bqje;
    @JsonProperty("本年金额")
    private String bnje;
    @JsonProperty("上期金额")
    private String sqje;

    @JsonProperty("单据编号")
    private String billNO;
    @JsonProperty("年")
    private String year;
    @JsonProperty("月")
    private String month;
    @JsonProperty("组织")
    private String org;

}
