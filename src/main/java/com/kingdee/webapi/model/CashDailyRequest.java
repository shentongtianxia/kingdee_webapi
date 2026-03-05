package com.kingdee.webapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * 现金日报表请求参数映射
 */
@ApiModel(description = "现金日报表请求参数", value = "CashDailyRequest")
public class CashDailyRequest {

    @ApiModelProperty(value = "收付组织", notes = "收付组织（非必填，不填则查询所有组织）", example = "[{\"FNumber\":\"100\"}]")
    private List<Organization> FOrgID;

    @ApiModelProperty(value = "币别", notes = "币别（非必填，不填则查询所有币别）", example = "1")
    private String FCurrencyIds;

    @ApiModelProperty(value = "起始日期", notes = "起始日期（必填项，格式：yyyy-MM-dd）", example = "2025-01-01", required = true)
    private String FStartDate;

    @ApiModelProperty(value = "结束日期", notes = "结束日期（必填项，格式：yyyy-MM-dd）", example = "2025-01-31", required = true)
    private String FEndDate;

    @ApiModelProperty(value = "包含未审核单据", notes = "包含未审核单据（非必填）", example = "false")
    private Boolean FNotAudit;

    @ApiModelProperty(value = "显示本位币", notes = "显示本位币（非必填）", example = "false")
    private Boolean FMyCurrency;

    @ApiModelProperty(value = "按收付组织分项显示", notes = "按收付组织分项显示（非必填）", example = "false")
    private Boolean FMyPayOrg;

    @ApiModelProperty(value = "隶属方案", notes = "隶属方案（非必填）", example = "{\"FNumber\":\"\"}")
    private Organization FAffiliation;

    @ApiModelProperty(value = "所有账号", notes = "所有账号（非必填）", example = "false")
    private Boolean FAllAcct;

    @ApiModelProperty(value = "显示本位币合计", notes = "显示本位币合计（非必填）", example = "false")
    private Boolean FMyCurrencySum;

    @ApiModelProperty(value = "现金账号列表", notes = "现金账号列表（非必填）", example = "[{\"FNumber\":\"CASH1\"}]")
    private List<Organization> FCASHACCTID;

    @ApiModelProperty(value = "显示币别小计", notes = "显示币别小计（非必填）", example = "false")
    private Boolean FCurrencySubTotal;

    public List<Organization> getFOrgID() {
        return FOrgID;
    }

    public void setFOrgID(List<Organization> FOrgID) {
        this.FOrgID = FOrgID;
    }

    public String getFCurrencyIds() {
        return FCurrencyIds;
    }

    public void setFCurrencyIds(String FCurrencyIds) {
        this.FCurrencyIds = FCurrencyIds;
    }

    public String getFStartDate() {
        return FStartDate;
    }

    public void setFStartDate(String FStartDate) {
        this.FStartDate = FStartDate;
    }

    public String getFEndDate() {
        return FEndDate;
    }

    public void setFEndDate(String FEndDate) {
        this.FEndDate = FEndDate;
    }

    public Boolean getFNotAudit() {
        return FNotAudit;
    }

    public void setFNotAudit(Boolean FNotAudit) {
        this.FNotAudit = FNotAudit;
    }

    public Boolean getFMyCurrency() {
        return FMyCurrency;
    }

    public void setFMyCurrency(Boolean FMyCurrency) {
        this.FMyCurrency = FMyCurrency;
    }

    public Boolean getFMyPayOrg() {
        return FMyPayOrg;
    }

    public void setFMyPayOrg(Boolean FMyPayOrg) {
        this.FMyPayOrg = FMyPayOrg;
    }

    public Organization getFAffiliation() {
        return FAffiliation;
    }

    public void setFAffiliation(Organization FAffiliation) {
        this.FAffiliation = FAffiliation;
    }

    public Boolean getFAllAcct() {
        return FAllAcct;
    }

    public void setFAllAcct(Boolean FAllAcct) {
        this.FAllAcct = FAllAcct;
    }

    public Boolean getFMyCurrencySum() {
        return FMyCurrencySum;
    }

    public void setFMyCurrencySum(Boolean FMyCurrencySum) {
        this.FMyCurrencySum = FMyCurrencySum;
    }

    public List<Organization> getFCASHACCTID() {
        return FCASHACCTID;
    }

    public void setFCASHACCTID(List<Organization> FCASHACCTID) {
        this.FCASHACCTID = FCASHACCTID;
    }

    public Boolean getFCurrencySubTotal() {
        return FCurrencySubTotal;
    }

    public void setFCurrencySubTotal(Boolean FCurrencySubTotal) {
        this.FCurrencySubTotal = FCurrencySubTotal;
    }
}


