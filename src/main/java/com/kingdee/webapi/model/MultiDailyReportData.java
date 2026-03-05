package com.kingdee.webapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 多账簿日报表响应数据映射
 */
@Data
@ApiModel(description = "多账簿日报表数据项")
public class MultiDailyReportData {

    @JsonProperty("账簿编码")
    @ApiModelProperty(value = "账簿编码")
    private String FAccountBookNumber;

    @JsonProperty("账簿名称")
    @ApiModelProperty(value = "账簿名称")
    private String FAccountBookName;

    @JsonProperty("日期")
    @ApiModelProperty(value = "日期")
    private String FDate;

    @JsonProperty("科目编码")
    @ApiModelProperty(value = "科目编码")
    private String FAccountNumber;

    @JsonProperty("科目名称")
    @ApiModelProperty(value = "科目名称")
    private String FAccountName;

    @JsonProperty("会计要素")
    @ApiModelProperty(value = "会计要素")
    private String FAcctGroup;

    @JsonProperty("科目类别")
    @ApiModelProperty(value = "科目类别")
    private String FAccountGroup;

    @JsonProperty("核算维度编码")
    @ApiModelProperty(value = "核算维度编码")
    private String FFlexNumber;

    @JsonProperty("核算维度名称")
    @ApiModelProperty(value = "核算维度名称")
    private String FFlexName;

    @JsonProperty("币别")
    @ApiModelProperty(value = "币别")
    private String Fcurrency;

    @JsonProperty("计量单位")
    @ApiModelProperty(value = "计量单位")
    private String FUnitName;

    @JsonProperty("上日余额-数量(借)")
    @ApiModelProperty(value = "上日余额-数量(借)")
    private String FYDayDebitQty;

    @JsonProperty("上日余额-单价(借)")
    @ApiModelProperty(value = "上日余额-单价(借)")
    private String FYDayDebitPrice;

    @JsonProperty("上日余额-原币(借)")
    @ApiModelProperty(value = "上日余额-原币(借)")
    private String FYDayDebitAmountFor;

    @JsonProperty("上日余额-本位币(借)")
    @ApiModelProperty(value = "上日余额-本位币(借)")
    private String FYDayDebitAmount;

    @JsonProperty("上日余额-数量(贷)")
    @ApiModelProperty(value = "上日余额-数量(贷)")
    private String FYDayCreditQty;

    @JsonProperty("上日余额-单价(贷)")
    @ApiModelProperty(value = "上日余额-单价(贷)")
    private String FYDayCreditPrice;

    @JsonProperty("上日余额-原币(贷)")
    @ApiModelProperty(value = "上日余额-原币(贷)")
    private String FYDayCreditAmountFor;

    @JsonProperty("上日余额-本位币(贷)")
    @ApiModelProperty(value = "上日余额-本位币(贷)")
    private String FYDayCreditAmount;

    @JsonProperty("本日发生额-数量(借)")
    @ApiModelProperty(value = "本日发生额-数量(借)")
    private String FDebitQty;

    @JsonProperty("本日发生额-单价(借)")
    @ApiModelProperty(value = "本日发生额-单价(借)")
    private String FDebitPrice;

    @JsonProperty("本日发生额-原币(借)")
    @ApiModelProperty(value = "本日发生额-原币(借)")
    private String FDebitAmountFor;

    @JsonProperty("本日发生额-本位币(借)")
    @ApiModelProperty(value = "本日发生额-本位币(借)")
    private String FDebitAmount;

    @JsonProperty("本日发生额-数量(贷)")
    @ApiModelProperty(value = "本日发生额-数量(贷)")
    private String FCreditQty;

    @JsonProperty("本日发生额-单价(贷)")
    @ApiModelProperty(value = "本日发生额-单价(贷)")
    private String FCreditPrice;

    @JsonProperty("本日发生额-原币(贷)")
    @ApiModelProperty(value = "本日发生额-原币(贷)")
    private String FCreditAmountFor;

    @JsonProperty("本日发生额-本位币(贷)")
    @ApiModelProperty(value = "本日发生额-本位币(贷)")
    private String FCreditAmount;

    @JsonProperty("本日余额-数量(借)")
    @ApiModelProperty(value = "本日余额-数量(借)")
    private String FBalDebitQty;

    @JsonProperty("本日余额-单价(借)")
    @ApiModelProperty(value = "本日余额-单价(借)")
    private String FBalDebitPrice;

    @JsonProperty("本日余额-原币(借)")
    @ApiModelProperty(value = "本日余额-原币(借)")
    private String FBalDebitAmountFor;

    @JsonProperty("本日余额-本位币(借)")
    @ApiModelProperty(value = "本日余额-本位币(借)")
    private String FBalDebitAmount;

    @JsonProperty("本日余额-数量(贷)")
    @ApiModelProperty(value = "本日余额-数量(贷)")
    private String FBalCreditQty;

    @JsonProperty("本日余额-单价(贷)")
    @ApiModelProperty(value = "本日余额-单价(贷)")
    private String FBalCreditPrice;

    @JsonProperty("本日余额-原币(贷)")
    @ApiModelProperty(value = "本日余额-原币(贷)")
    private String FBalCreditAmountFor;

    @JsonProperty("本日余额-本位币(贷)")
    @ApiModelProperty(value = "本日余额-本位币(贷)")
    private String FBalCreditAmount;

    @JsonProperty("借方笔数")
    @ApiModelProperty(value = "借方笔数")
    private String FDebitCount;

    @JsonProperty("贷方笔数")
    @ApiModelProperty(value = "贷方笔数")
    private String FCreditCount;
}
