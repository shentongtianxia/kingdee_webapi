package com.kingdee.webapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 资产负债表报告模型
 * 
 * @author Kingdee
 * @version 1.0.0
 */
@Data
public class BalanceReport {

    @JsonProperty("序号")
    private Integer index;
    @JsonProperty("类型")
    private Integer type;
    @JsonProperty("项目")
    private String project;
    @JsonProperty("期末余额")
    private String qmye;
    @JsonProperty("上年年末余额")
    private String snnmye;

    @JsonProperty("单据编号")
    private String billNO;
    @JsonProperty("年")
    private String year;
    @JsonProperty("月")
    private String month;
    @JsonProperty("组织")
    private String org;

}
