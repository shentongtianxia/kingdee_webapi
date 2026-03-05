package com.kingdee.webapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 科目余额表请求参数映射
 */
@ApiModel(description = "科目余额表请求参数")
public class AccountBalanceRequest {

    @ApiModelProperty(value = "账簿编码（必填）", example = "001")
    private String acctBookNumber;
    @ApiModelProperty(value = "币别（必填）", example = "1")
    private String currency;
    @ApiModelProperty(value = "开始年度（必填）", example = "2025")
    private String startYear;
    @ApiModelProperty(value = "开始期间（必填）", example = "1")
    private String startPeriod;
    @ApiModelProperty(value = "结束年度（必填）", example = "2025")
    private String endYear;
    @ApiModelProperty(value = "结束期间（必填）", example = "1")
    private String endPeriod;
    @ApiModelProperty(value = "余额级次", example = "1")
    private String balanceLevel;
    @ApiModelProperty(value = "显示明细", example = "false")
    private Boolean showDetail;
    @ApiModelProperty(value = "显示禁用科目", example = "false")
    private Boolean forbidBalance;
    @ApiModelProperty(value = "包括未过账凭证", example = "false")
    private Boolean notPostVoucher;
    @ApiModelProperty(value = "余额按借贷分别小计", example = "false")
    private Boolean debitOrCredit;
    @ApiModelProperty(value = "包含余额为零的科目", example = "true")
    private Boolean balanceZero;
    @ApiModelProperty(value = "包括没有业务发生的科目", example = "false")
    private Boolean noBusiness;
    @ApiModelProperty(value = "包括本期没有发生额的科目", example = "true")
    private Boolean periodNoBalance;
    @ApiModelProperty(value = "包括本年没有发生额的科目", example = "true")
    private Boolean yearNoBalance;
    @ApiModelProperty(value = "显示科目全名", example = "false")
    private Boolean showFullName;
    @ApiModelProperty(value = "核算维度明细行显示科目信息", example = "false")
    private Boolean detailShowAcct;
    @ApiModelProperty(value = "只显示明细科目", example = "false")
    private Boolean showDetailOnly;
    @ApiModelProperty(value = "不包含调整期凭证", example = "false")
    private Boolean excludeAdjustVch;
    @ApiModelProperty(value = "核算维度余额按借贷分别小计", example = "false")
    private Boolean flexDebitOrCredit;
    @ApiModelProperty(value = "核算维度分列显示", example = "true")
    private Boolean showFlexByCol;

    public String getAcctBookNumber() {
        return acctBookNumber;
    }

    public void setAcctBookNumber(String acctBookNumber) {
        this.acctBookNumber = acctBookNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(String startPeriod) {
        this.startPeriod = startPeriod;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public String getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(String endPeriod) {
        this.endPeriod = endPeriod;
    }

    public String getBalanceLevel() {
        return balanceLevel;
    }

    public void setBalanceLevel(String balanceLevel) {
        this.balanceLevel = balanceLevel;
    }

    public Boolean getShowDetail() {
        return showDetail;
    }

    public void setShowDetail(Boolean showDetail) {
        this.showDetail = showDetail;
    }

    public Boolean getForbidBalance() {
        return forbidBalance;
    }

    public void setForbidBalance(Boolean forbidBalance) {
        this.forbidBalance = forbidBalance;
    }

    public Boolean getNotPostVoucher() {
        return notPostVoucher;
    }

    public void setNotPostVoucher(Boolean notPostVoucher) {
        this.notPostVoucher = notPostVoucher;
    }

    public Boolean getDebitOrCredit() {
        return debitOrCredit;
    }

    public void setDebitOrCredit(Boolean debitOrCredit) {
        this.debitOrCredit = debitOrCredit;
    }

    public Boolean getBalanceZero() {
        return balanceZero;
    }

    public void setBalanceZero(Boolean balanceZero) {
        this.balanceZero = balanceZero;
    }

    public Boolean getNoBusiness() {
        return noBusiness;
    }

    public void setNoBusiness(Boolean noBusiness) {
        this.noBusiness = noBusiness;
    }

    public Boolean getPeriodNoBalance() {
        return periodNoBalance;
    }

    public void setPeriodNoBalance(Boolean periodNoBalance) {
        this.periodNoBalance = periodNoBalance;
    }

    public Boolean getYearNoBalance() {
        return yearNoBalance;
    }

    public void setYearNoBalance(Boolean yearNoBalance) {
        this.yearNoBalance = yearNoBalance;
    }

    public Boolean getShowFullName() {
        return showFullName;
    }

    public void setShowFullName(Boolean showFullName) {
        this.showFullName = showFullName;
    }

    public Boolean getDetailShowAcct() {
        return detailShowAcct;
    }

    public void setDetailShowAcct(Boolean detailShowAcct) {
        this.detailShowAcct = detailShowAcct;
    }

    public Boolean getShowDetailOnly() {
        return showDetailOnly;
    }

    public void setShowDetailOnly(Boolean showDetailOnly) {
        this.showDetailOnly = showDetailOnly;
    }

    public Boolean getExcludeAdjustVch() {
        return excludeAdjustVch;
    }

    public void setExcludeAdjustVch(Boolean excludeAdjustVch) {
        this.excludeAdjustVch = excludeAdjustVch;
    }

    public Boolean getFlexDebitOrCredit() {
        return flexDebitOrCredit;
    }

    public void setFlexDebitOrCredit(Boolean flexDebitOrCredit) {
        this.flexDebitOrCredit = flexDebitOrCredit;
    }

    public Boolean getShowFlexByCol() {
        return showFlexByCol;
    }

    public void setShowFlexByCol(Boolean showFlexByCol) {
        this.showFlexByCol = showFlexByCol;
    }
}


