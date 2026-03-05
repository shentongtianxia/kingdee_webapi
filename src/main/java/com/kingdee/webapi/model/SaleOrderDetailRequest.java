package com.kingdee.webapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 销售订单执行明细表请求参数映射
 */
@ApiModel(description = "销售订单执行明细表请求参数", value = "SaleOrderDetailRequest")
public class SaleOrderDetailRequest {

    @ApiModelProperty(value = "销售组织", notes = "组织（必填）", example = "")
    private String FSaleOrgList;

    @ApiModelProperty(value = "币别", notes = "币别（格式：{\"FNumber\":\"\"}）", example = "{\"FNumber\":\"\"}")
    private Organization FMoneyType;

    @ApiModelProperty(value = "订单日期（起始）", notes = "订单日期（起始，必填，格式：yyyy-MM-dd）", example = "1900-01-01", required = true)
    private String FSoFromDate;

    @ApiModelProperty(value = "订单日期（结束）", notes = "订单日期（结束，必填，格式：yyyy-MM-dd）", example = "1900-01-01", required = true)
    private String FSoToDate;

    @ApiModelProperty(value = "客户编码（起始）", notes = "客户编码（起始，格式：{\"FNumber\":\"\"}）", example = "{\"FNumber\":\"\"}")
    private Organization FCustomerFrom;

    @ApiModelProperty(value = "客户编码（结束）", notes = "客户编码（结束，格式：{\"FNumber\":\"\"}）", example = "{\"FNumber\":\"\"}")
    private Organization FCustomerTo;

    @ApiModelProperty(value = "销售员编码（起始）", notes = "销售员编码（起始，格式：{\"FNumber\":\"\"}）", example = "{\"FNumber\":\"\"}")
    private Organization FSalesFrom;

    @ApiModelProperty(value = "销售员编码（结束）", notes = "销售员编码（结束，格式：{\"FNumber\":\"\"}）", example = "{\"FNumber\":\"\"}")
    private Organization FSalesTo;

    @ApiModelProperty(value = "物料编码（起始）", notes = "物料编码（起始，格式：{\"FNumber\":\"\"}）", example = "{\"FNumber\":\"\"}")
    private Organization FMaterialFrom;

    @ApiModelProperty(value = "物料编码（结束）", notes = "物料编码（结束，格式：{\"FNumber\":\"\"}）", example = "{\"FNumber\":\"\"}")
    private Organization FMaterialTo;

    @ApiModelProperty(value = "销售订单（起始）", notes = "销售订单（起始）", example = "")
    private String FSaleOFrom;

    @ApiModelProperty(value = "销售订单（结束）", notes = "销售订单（结束）", example = "")
    private String FSaleOTo;

    @ApiModelProperty(value = "要货日期（起始）", notes = "要货日期（起始，格式：yyyy-MM-dd）", example = "1900-01-01")
    private String FDelliveryDateFrom;

    @ApiModelProperty(value = "要货日期（结束）", notes = "要货日期（结束，格式：yyyy-MM-dd）", example = "1900-01-01")
    private String FDelliveryDateTo;

    @ApiModelProperty(value = "整单关闭状态", notes = "整单关闭状态（必填）", example = "")
    private String FFormCloseStatus;

    @ApiModelProperty(value = "单据状态", notes = "单据状态（必填）", example = "")
    private String FFormStatus;

    @ApiModelProperty(value = "单价来源", notes = "单价来源（必填）", example = "")
    private String FPriceFrom;

    @ApiModelProperty(value = "行业务关闭状态", notes = "行业务关闭状态（必填）", example = "")
    private String FBusCloseStatus;

    @ApiModelProperty(value = "查询时不合并展示订单的表头信息", notes = "查询时不合并展示订单的表头信息", example = "false")
    private Boolean FMergingSOHeader;

    @ApiModelProperty(value = "包含未执行的销售订单", notes = "包含未执行的销售订单", example = "false")
    private Boolean FIncludedUnfilledOrders;

    @ApiModelProperty(value = "按物料明细收款方式查询", notes = "按物料明细收款方式查询", example = "false")
    private Boolean FIsRecWithMat;

    @ApiModelProperty(value = "客户分组", notes = "客户分组（格式：{\"FNumber\":\"\"}）", example = "{\"FNumber\":\"\"}")
    private Organization FCustGroup;

    @ApiModelProperty(value = "统计套件", notes = "统计套件（必填）", example = "")
    private String FSuite;

    @ApiModelProperty(value = "立账类型", notes = "立账类型（必填）", example = "")
    private String FSetAccountType;

    @ApiModelProperty(value = "集团客户", notes = "集团客户", example = "false")
    private Boolean FIsGroup;

    @ApiModelProperty(value = "包含赠品", notes = "包含赠品", example = "false")
    private Boolean FIncludedFree;

    // Getter and Setter
    public String getFSaleOrgList() {
        return FSaleOrgList;
    }

    public void setFSaleOrgList(String FSaleOrgList) {
        this.FSaleOrgList = FSaleOrgList;
    }

    public Organization getFMoneyType() {
        return FMoneyType;
    }

    public void setFMoneyType(Organization FMoneyType) {
        this.FMoneyType = FMoneyType;
    }

    public String getFSoFromDate() {
        return FSoFromDate;
    }

    public void setFSoFromDate(String FSoFromDate) {
        this.FSoFromDate = FSoFromDate;
    }

    public String getFSoToDate() {
        return FSoToDate;
    }

    public void setFSoToDate(String FSoToDate) {
        this.FSoToDate = FSoToDate;
    }

    public Organization getFCustomerFrom() {
        return FCustomerFrom;
    }

    public void setFCustomerFrom(Organization FCustomerFrom) {
        this.FCustomerFrom = FCustomerFrom;
    }

    public Organization getFCustomerTo() {
        return FCustomerTo;
    }

    public void setFCustomerTo(Organization FCustomerTo) {
        this.FCustomerTo = FCustomerTo;
    }

    public Organization getFSalesFrom() {
        return FSalesFrom;
    }

    public void setFSalesFrom(Organization FSalesFrom) {
        this.FSalesFrom = FSalesFrom;
    }

    public Organization getFSalesTo() {
        return FSalesTo;
    }

    public void setFSalesTo(Organization FSalesTo) {
        this.FSalesTo = FSalesTo;
    }

    public Organization getFMaterialFrom() {
        return FMaterialFrom;
    }

    public void setFMaterialFrom(Organization FMaterialFrom) {
        this.FMaterialFrom = FMaterialFrom;
    }

    public Organization getFMaterialTo() {
        return FMaterialTo;
    }

    public void setFMaterialTo(Organization FMaterialTo) {
        this.FMaterialTo = FMaterialTo;
    }

    public String getFSaleOFrom() {
        return FSaleOFrom;
    }

    public void setFSaleOFrom(String FSaleOFrom) {
        this.FSaleOFrom = FSaleOFrom;
    }

    public String getFSaleOTo() {
        return FSaleOTo;
    }

    public void setFSaleOTo(String FSaleOTo) {
        this.FSaleOTo = FSaleOTo;
    }

    public String getFDelliveryDateFrom() {
        return FDelliveryDateFrom;
    }

    public void setFDelliveryDateFrom(String FDelliveryDateFrom) {
        this.FDelliveryDateFrom = FDelliveryDateFrom;
    }

    public String getFDelliveryDateTo() {
        return FDelliveryDateTo;
    }

    public void setFDelliveryDateTo(String FDelliveryDateTo) {
        this.FDelliveryDateTo = FDelliveryDateTo;
    }

    public String getFFormCloseStatus() {
        return FFormCloseStatus;
    }

    public void setFFormCloseStatus(String FFormCloseStatus) {
        this.FFormCloseStatus = FFormCloseStatus;
    }

    public String getFFormStatus() {
        return FFormStatus;
    }

    public void setFFormStatus(String FFormStatus) {
        this.FFormStatus = FFormStatus;
    }

    public String getFPriceFrom() {
        return FPriceFrom;
    }

    public void setFPriceFrom(String FPriceFrom) {
        this.FPriceFrom = FPriceFrom;
    }

    public String getFBusCloseStatus() {
        return FBusCloseStatus;
    }

    public void setFBusCloseStatus(String FBusCloseStatus) {
        this.FBusCloseStatus = FBusCloseStatus;
    }

    public Boolean getFMergingSOHeader() {
        return FMergingSOHeader;
    }

    public void setFMergingSOHeader(Boolean FMergingSOHeader) {
        this.FMergingSOHeader = FMergingSOHeader;
    }

    public Boolean getFIncludedUnfilledOrders() {
        return FIncludedUnfilledOrders;
    }

    public void setFIncludedUnfilledOrders(Boolean FIncludedUnfilledOrders) {
        this.FIncludedUnfilledOrders = FIncludedUnfilledOrders;
    }

    public Boolean getFIsRecWithMat() {
        return FIsRecWithMat;
    }

    public void setFIsRecWithMat(Boolean FIsRecWithMat) {
        this.FIsRecWithMat = FIsRecWithMat;
    }

    public Organization getFCustGroup() {
        return FCustGroup;
    }

    public void setFCustGroup(Organization FCustGroup) {
        this.FCustGroup = FCustGroup;
    }

    public String getFSuite() {
        return FSuite;
    }

    public void setFSuite(String FSuite) {
        this.FSuite = FSuite;
    }

    public String getFSetAccountType() {
        return FSetAccountType;
    }

    public void setFSetAccountType(String FSetAccountType) {
        this.FSetAccountType = FSetAccountType;
    }

    public Boolean getFIsGroup() {
        return FIsGroup;
    }

    public void setFIsGroup(Boolean FIsGroup) {
        this.FIsGroup = FIsGroup;
    }

    public Boolean getFIncludedFree() {
        return FIncludedFree;
    }

    public void setFIncludedFree(Boolean FIncludedFree) {
        this.FIncludedFree = FIncludedFree;
    }
}
