package com.kingdee.webapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * 生产入库月报表请求参数映射
 */
@ApiModel(description = "生产入库月报表请求参数", value = "ProductionInStockRequest")
public class ProductionInStockRequest {

    @ApiModelProperty(value = "月份", notes = "月份（必填，格式：yyyy-MM-dd）", example = "2025-01-01", required = true)
    private String FPickMtrlMonth;

    @ApiModelProperty(value = "生产车间", notes = "生产车间（非必填，格式：[{\"FNumber\":\"WC001\"}]）", example = "[{\"FNumber\":\"\"}]")
    private List<Organization> FWorkShopId;

    @ApiModelProperty(value = "产品编码", notes = "产品编码（非必填，格式：[{\"FNumber\":\"P001\"}]）", example = "[{\"FNumber\":\"\"}]")
    private List<Organization> FProdNumId;

    @ApiModelProperty(value = "汇总依据", notes = "汇总依据（必填）", example = "")
    private String FSumBasis;

    @ApiModelProperty(value = "物料组", notes = "物料组（非必填，格式：[{\"FNumber\":\"T001\"}]）", example = "[{\"FNumber\":\"\"}]")
    private List<Organization> FProdTypeId;

    @ApiModelProperty(value = "生产组织", notes = "生产组织（必填）", example = "")
    private String FPrdOrgId;

    @ApiModelProperty(value = "期间从", notes = "期间从（必填，格式：yyyy-MM-dd）", example = "2025-01-01", required = true)
    private String FApprBeginDate;

    @ApiModelProperty(value = "至", notes = "至（必填，格式：yyyy-MM-dd）", example = "2025-01-31", required = true)
    private String FApprEndDate;

    @ApiModelProperty(value = "月份期间选择", notes = "月份期间选择（非必填）", example = "false")
    private Boolean FMonDateChk;

    @ApiModelProperty(value = "日期范围选择", notes = "日期范围选择（非必填）", example = "false")
    private Boolean FRangeDateChk;

    @ApiModelProperty(value = "单据类型", notes = "单据类型（非必填，格式：[{\"FNumber\":\"\"}]）", example = "[{\"FNumber\":\"\"}]")
    private List<Organization> FMoBillTypeId;

    @ApiModelProperty(value = "统计单位", notes = "统计单位（必填）", example = "")
    private String FSumUnit;

    @ApiModelProperty(value = "取数来源", notes = "取数来源（非必填）", example = "")
    private String FAccSource;

    @ApiModelProperty(value = "单据状态", notes = "单据状态（必填）", example = "")
    private String FDocStatus;

    // Getter and Setter
    public String getFPickMtrlMonth() {
        return FPickMtrlMonth;
    }

    public void setFPickMtrlMonth(String FPickMtrlMonth) {
        this.FPickMtrlMonth = FPickMtrlMonth;
    }

    public List<Organization> getFWorkShopId() {
        return FWorkShopId;
    }

    public void setFWorkShopId(List<Organization> FWorkShopId) {
        this.FWorkShopId = FWorkShopId;
    }

    public List<Organization> getFProdNumId() {
        return FProdNumId;
    }

    public void setFProdNumId(List<Organization> FProdNumId) {
        this.FProdNumId = FProdNumId;
    }

    public String getFSumBasis() {
        return FSumBasis;
    }

    public void setFSumBasis(String FSumBasis) {
        this.FSumBasis = FSumBasis;
    }

    public List<Organization> getFProdTypeId() {
        return FProdTypeId;
    }

    public void setFProdTypeId(List<Organization> FProdTypeId) {
        this.FProdTypeId = FProdTypeId;
    }

    public String getFPrdOrgId() {
        return FPrdOrgId;
    }

    public void setFPrdOrgId(String FPrdOrgId) {
        this.FPrdOrgId = FPrdOrgId;
    }

    public String getFApprBeginDate() {
        return FApprBeginDate;
    }

    public void setFApprBeginDate(String FApprBeginDate) {
        this.FApprBeginDate = FApprBeginDate;
    }

    public String getFApprEndDate() {
        return FApprEndDate;
    }

    public void setFApprEndDate(String FApprEndDate) {
        this.FApprEndDate = FApprEndDate;
    }

    public Boolean getFMonDateChk() {
        return FMonDateChk;
    }

    public void setFMonDateChk(Boolean FMonDateChk) {
        this.FMonDateChk = FMonDateChk;
    }

    public Boolean getFRangeDateChk() {
        return FRangeDateChk;
    }

    public void setFRangeDateChk(Boolean FRangeDateChk) {
        this.FRangeDateChk = FRangeDateChk;
    }

    public List<Organization> getFMoBillTypeId() {
        return FMoBillTypeId;
    }

    public void setFMoBillTypeId(List<Organization> FMoBillTypeId) {
        this.FMoBillTypeId = FMoBillTypeId;
    }

    public String getFSumUnit() {
        return FSumUnit;
    }

    public void setFSumUnit(String FSumUnit) {
        this.FSumUnit = FSumUnit;
    }

    public String getFAccSource() {
        return FAccSource;
    }

    public void setFAccSource(String FAccSource) {
        this.FAccSource = FAccSource;
    }

    public String getFDocStatus() {
        return FDocStatus;
    }

    public void setFDocStatus(String FDocStatus) {
        this.FDocStatus = FDocStatus;
    }
}
