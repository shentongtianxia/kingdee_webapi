package com.kingdee.webapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * 销售出库明细表请求参数映射
 */
@ApiModel(description = "销售出库明细表请求参数", value = "SalesOutboundRequest")
public class SalesOutboundRequest {

    @ApiModelProperty(value = "销售组织", notes = "销售组织（必填，格式：{\"FNumber\":\"100\"}）", example = "{\"FNumber\":\"100\"}")
    private Organization FSaleOrgId;

    @ApiModelProperty(value = "结算组织", notes = "结算组织（必填，格式：{\"FNumber\":\"100\"}）", example = "{\"FNumber\":\"100\"}")
    private Organization FSettleOrgId;

    @ApiModelProperty(value = "起始日期", notes = "起始日期（必填，格式：yyyy-MM-dd）", example = "2025-01-01", required = true)
    private String FStartDate;

    @ApiModelProperty(value = "结束日期", notes = "结束日期（必填，格式：yyyy-MM-dd）", example = "2025-01-31", required = true)
    private String FEndDate;

    @ApiModelProperty(value = "起始物料", notes = "起始物料（非必填，格式：{\"FNUMBER\":\"M001\"}）", example = "{\"FNUMBER\":\"M001\"}")
    private Organization FStartMaterial;

    @ApiModelProperty(value = "结束物料", notes = "结束物料（非必填，格式：{\"FNUMBER\":\"M999\"}）", example = "{\"FNUMBER\":\"M999\"}")
    private Organization FEndMaterial;

    @ApiModelProperty(value = "起始客户", notes = "起始客户（非必填，格式：{\"FNumber\":\"C001\"}）", example = "{\"FNumber\":\"C001\"}")
    private Organization FStartCustomer;

    @ApiModelProperty(value = "结束客户", notes = "结束客户（非必填，格式：{\"FNumber\":\"C999\"}）", example = "{\"FNumber\":\"C999\"}")
    private Organization FEndCustomer;

    @ApiModelProperty(value = "起始销售部门", notes = "起始销售部门（非必填，格式：{\"FNUMBER\":\"D001\"}）", example = "{\"FNUMBER\":\"D001\"}")
    private Organization FStartDepartment;

    @ApiModelProperty(value = "结束销售部门", notes = "结束销售部门（非必填，格式：{\"FNUMBER\":\"D999\"}）", example = "{\"FNUMBER\":\"D999\"}")
    private Organization FEndDepartment;

    @ApiModelProperty(value = "单据状态", notes = "单据状态（非必填），可选值：A(保存)、B(审核中)、C(已审核)、D(重新审核)", example = "C")
    private String FBillStatus;

    @ApiModelProperty(value = "统计套件", notes = "统计套件（非必填，格式：{\"FNumber\":\"SUITE001\"}）", example = "{\"FNumber\":\"\"}")
    private Organization FSuite;

    @ApiModelProperty(value = "包含跨组织结算单据", notes = "包含跨组织结算单据（非必填）", example = "false")
    private Boolean FIsIncludeInnerExchangeBill;

    @ApiModelProperty(value = "包含服务类物料", notes = "包含服务类物料（非必填）", example = "false")
    private Boolean FIsIncludeSerMat;

    @ApiModelProperty(value = "集团客户", notes = "集团客户（非必填）", example = "false")
    private Boolean FIsGroupCust;

    @ApiModelProperty(value = "仅取出库退货单数据分析", notes = "仅取出库退货单数据分析（非必填）", example = "false")
    private Boolean FOnlyOutReturnData;

    @ApiModelProperty(value = "包含仅退款不退货", notes = "包含仅退款不退货（非必填）", example = "false")
    private Boolean FIncludeRefundNoGoods;

    // Getter and Setter
    public Organization getFSaleOrgId() {
        return FSaleOrgId;
    }

    public void setFSaleOrgId(Organization FSaleOrgId) {
        this.FSaleOrgId = FSaleOrgId;
    }

    public Organization getFSettleOrgId() {
        return FSettleOrgId;
    }

    public void setFSettleOrgId(Organization FSettleOrgId) {
        this.FSettleOrgId = FSettleOrgId;
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

    public Organization getFStartMaterial() {
        return FStartMaterial;
    }

    public void setFStartMaterial(Organization FStartMaterial) {
        this.FStartMaterial = FStartMaterial;
    }

    public Organization getFEndMaterial() {
        return FEndMaterial;
    }

    public void setFEndMaterial(Organization FEndMaterial) {
        this.FEndMaterial = FEndMaterial;
    }

    public Organization getFStartCustomer() {
        return FStartCustomer;
    }

    public void setFStartCustomer(Organization FStartCustomer) {
        this.FStartCustomer = FStartCustomer;
    }

    public Organization getFEndCustomer() {
        return FEndCustomer;
    }

    public void setFEndCustomer(Organization FEndCustomer) {
        this.FEndCustomer = FEndCustomer;
    }

    public Organization getFStartDepartment() {
        return FStartDepartment;
    }

    public void setFStartDepartment(Organization FStartDepartment) {
        this.FStartDepartment = FStartDepartment;
    }

    public Organization getFEndDepartment() {
        return FEndDepartment;
    }

    public void setFEndDepartment(Organization FEndDepartment) {
        this.FEndDepartment = FEndDepartment;
    }

    public String getFBillStatus() {
        return FBillStatus;
    }

    public void setFBillStatus(String FBillStatus) {
        this.FBillStatus = FBillStatus;
    }

    public Organization getFSuite() {
        return FSuite;
    }

    public void setFSuite(Organization FSuite) {
        this.FSuite = FSuite;
    }

    public Boolean getFIsIncludeInnerExchangeBill() {
        return FIsIncludeInnerExchangeBill;
    }

    public void setFIsIncludeInnerExchangeBill(Boolean FIsIncludeInnerExchangeBill) {
        this.FIsIncludeInnerExchangeBill = FIsIncludeInnerExchangeBill;
    }

    public Boolean getFIsIncludeSerMat() {
        return FIsIncludeSerMat;
    }

    public void setFIsIncludeSerMat(Boolean FIsIncludeSerMat) {
        this.FIsIncludeSerMat = FIsIncludeSerMat;
    }

    public Boolean getFIsGroupCust() {
        return FIsGroupCust;
    }

    public void setFIsGroupCust(Boolean FIsGroupCust) {
        this.FIsGroupCust = FIsGroupCust;
    }

    public Boolean getFOnlyOutReturnData() {
        return FOnlyOutReturnData;
    }

    public void setFOnlyOutReturnData(Boolean FOnlyOutReturnData) {
        this.FOnlyOutReturnData = FOnlyOutReturnData;
    }

    public Boolean getFIncludeRefundNoGoods() {
        return FIncludeRefundNoGoods;
    }

    public void setFIncludeRefundNoGoods(Boolean FIncludeRefundNoGoods) {
        this.FIncludeRefundNoGoods = FIncludeRefundNoGoods;
    }
}

