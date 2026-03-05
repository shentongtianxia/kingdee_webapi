package com.kingdee.webapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 现金流量表数据映射
 */
@ApiModel(description = "现金流量表数据", value = "CashFlowData")
public class CashFlowData {

    @ApiModelProperty(value = "项目类别")
    private String FItemTypeDesc;

    @ApiModelProperty(value = "现金流量项目")
    private String FCashItem;

    @ApiModelProperty(value = "行次")
    private String FRowNumber;

    @ApiModelProperty(value = "金额")
    private String FAmount;

    @ApiModelProperty(value = "比重")
    private String FPercent;

    public String getFItemTypeDesc() {
        return FItemTypeDesc;
    }

    public void setFItemTypeDesc(String FItemTypeDesc) {
        this.FItemTypeDesc = FItemTypeDesc;
    }

    public String getFCashItem() {
        return FCashItem;
    }

    public void setFCashItem(String FCashItem) {
        this.FCashItem = FCashItem;
    }

    public String getFRowNumber() {
        return FRowNumber;
    }

    public void setFRowNumber(String FRowNumber) {
        this.FRowNumber = FRowNumber;
    }

    public String getFAmount() {
        return FAmount;
    }

    public void setFAmount(String FAmount) {
        this.FAmount = FAmount;
    }

    public String getFPercent() {
        return FPercent;
    }

    public void setFPercent(String FPercent) {
        this.FPercent = FPercent;
    }
}
