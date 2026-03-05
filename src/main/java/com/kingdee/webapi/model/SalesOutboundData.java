package com.kingdee.webapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 销售出库明细表响应数据映射
 */
@Data
@ApiModel(description = "销售出库明细表数据项")
public class SalesOutboundData {

    @JsonProperty("单据名称")
    @ApiModelProperty(value = "单据名称")
    private String FBILLNAME;

    @JsonProperty("单据类型")
    @ApiModelProperty(value = "单据类型")
    private String FBILLTYPENAME;

    @JsonProperty("单据编码")
    @ApiModelProperty(value = "单据编码")
    private String FBILLNUMBER;

    @JsonProperty("日期")
    @ApiModelProperty(value = "日期")
    private String FBILLDATE;

    @JsonProperty("审核日期")
    @ApiModelProperty(value = "审核日期")
    private String FApproveDate;

    @JsonProperty("销售组织")
    @ApiModelProperty(value = "销售组织")
    private String FSALEORGNAME;

    @JsonProperty("结算组织")
    @ApiModelProperty(value = "结算组织")
    private String FSettleOrgName;

    @JsonProperty("销售部门编码")
    @ApiModelProperty(value = "销售部门编码")
    private String FDEPTNUMBER;

    @JsonProperty("销售部门名称")
    @ApiModelProperty(value = "销售部门名称")
    private String FDEPTNAME;

    @JsonProperty("销售员编码")
    @ApiModelProperty(value = "销售员编码")
    private String FSALERNUMBER;

    @JsonProperty("销售员")
    @ApiModelProperty(value = "销售员")
    private String FSALERNAME;

    @JsonProperty("销售组")
    @ApiModelProperty(value = "销售组")
    private String FSalGroupName;

    @JsonProperty("结算方式")
    @ApiModelProperty(value = "结算方式")
    private String FSETTLETYPENAME;

    @JsonProperty("客户编码")
    @ApiModelProperty(value = "客户编码")
    private String FCUSTOMERNUMBER;

    @JsonProperty("客户")
    @ApiModelProperty(value = "客户")
    private String FCUSTOMERID;

    @JsonProperty("客户分组")
    @ApiModelProperty(value = "客户分组")
    private String FCustGroupName;

    @JsonProperty("物料编码")
    @ApiModelProperty(value = "物料编码")
    private String FMATERIALNUMBER;

    @JsonProperty("物料")
    @ApiModelProperty(value = "物料")
    private String FMATERIALID;

    @JsonProperty("客户物料编码")
    @ApiModelProperty(value = "客户物料编码")
    private String FMAPID;

    @JsonProperty("客户物料名称")
    @ApiModelProperty(value = "客户物料名称")
    private String FMAPNAME;

    @JsonProperty("物料分组")
    @ApiModelProperty(value = "物料分组")
    private String FMATERIALGROUP;

    @JsonProperty("物料属性")
    @ApiModelProperty(value = "物料属性")
    private String FERPCLSID;

    @JsonProperty("存货类别")
    @ApiModelProperty(value = "存货类别")
    private String FCATEGORYID;

    @JsonProperty("规格型号")
    @ApiModelProperty(value = "规格型号")
    private String FMATERIALMODEL;

    @JsonProperty("辅助属性")
    @ApiModelProperty(value = "辅助属性")
    private String FMATERIALAUXID;

    @JsonProperty("仓库")
    @ApiModelProperty(value = "仓库")
    private String FSTOCKID;

    @JsonProperty("仓位")
    @ApiModelProperty(value = "仓位")
    private String FStockLocId;

    @JsonProperty("批号")
    @ApiModelProperty(value = "批号")
    private String FLOTNAME;

    @JsonProperty("订单单号")
    @ApiModelProperty(value = "订单单号")
    private String FSOORDERNO;

    @JsonProperty("单位")
    @ApiModelProperty(value = "单位")
    private String FUNITNAME;

    @JsonProperty("结算币别")
    @ApiModelProperty(value = "结算币别")
    private String FCURRENCYNAME;

    @JsonProperty("币别")
    @ApiModelProperty(value = "币别")
    private String FLCCURRENCYNAME;

    @JsonProperty("出库数量")
    @ApiModelProperty(value = "出库数量")
    private String FOUTSTOCKQTY;

    @JsonProperty("出库单价")
    @ApiModelProperty(value = "出库单价")
    private String FLCNOTAXOUTSTOCKPRICE;

    @JsonProperty("出库含税单价")
    @ApiModelProperty(value = "出库含税单价")
    private String FLCTAXOUTSTOCKPRICE;

    @JsonProperty("出库净价")
    @ApiModelProperty(value = "出库净价")
    private String FLCTAXNETPRICE;

    @JsonProperty("出库金额")
    @ApiModelProperty(value = "出库金额")
    private String FLCNOTAXOUTSTOCKAMOUNT;

    @JsonProperty("出库价税合计")
    @ApiModelProperty(value = "出库价税合计")
    private String FLCTAXOUTSTOCKAMOUNT;

    @JsonProperty("出库成本")
    @ApiModelProperty(value = "出库成本")
    private String FCOSTPRICE;

    @JsonProperty("出库总成本")
    @ApiModelProperty(value = "出库总成本")
    private String FLCOUTSTOCKTATALCOSTAMOUNT;

    @JsonProperty("应收数量")
    @ApiModelProperty(value = "应收数量")
    private String FRECQTY;

    @JsonProperty("应收金额")
    @ApiModelProperty(value = "应收金额")
    private String FLCNOTAXRECAMOUNT;

    @JsonProperty("应收价税合计")
    @ApiModelProperty(value = "应收价税合计")
    private String FLCTAXRECAMOUNT;

    @JsonProperty("是否赠品")
    @ApiModelProperty(value = "是否赠品")
    private String FISFREE;

    @JsonProperty("备注")
    @ApiModelProperty(value = "备注")
    private String FENTRYNOTE;
}
