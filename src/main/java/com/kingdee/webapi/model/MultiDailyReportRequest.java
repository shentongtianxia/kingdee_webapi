package com.kingdee.webapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 多账簿日报表请求参数映射
 */
@ApiModel(description = "多账簿日报表请求参数", value = "MultiDailyReportRequest")
public class MultiDailyReportRequest {

    @ApiModelProperty(value = "起始日期", notes = "起始日期（必填，格式：yyyy-MM-dd）", example = "2025-01-01", required = true)
    @JsonProperty("FSTARTDATE")
    private String FStartDate;

    @ApiModelProperty(value = "结束日期", notes = "结束日期（必填，格式：yyyy-MM-dd）", example = "2025-01-31", required = true)
    @JsonProperty("FENDDATE")
    private String FEndDate;

    @ApiModelProperty(value = "币别", notes = "币别（必填）", example = "PRE001")
    @JsonProperty("FCURRENCYID")
    private String FCurrencyId;

    @ApiModelProperty(value = "账簿隶属关系", notes = "账簿隶属关系（必填，格式：{\"FNUMBER\":\"\"}）", example = "{\"FNUMBER\":\"\"}")
    @JsonProperty("FSCHEMEID")
    private Organization FSchemeID;

    @ApiModelProperty(value = "按账簿查询", notes = "按账簿查询（非必填）", example = "")
    @JsonProperty("FUSEACCTBOOKGROUP")
    private String FUseAcctBookGroup;

    @ApiModelProperty(value = "按账簿隶属关系查询", notes = "按账簿隶属关系查询（非必填）", example = "")
    @JsonProperty("FUSEACCTBOOKMERGEGROUP")
    private String FUseAcctBookMergeGroup;

    @ApiModelProperty(value = "科目编码", notes = "科目编码（非必填）", example = "")
    @JsonProperty("FBALANCE")
    private String FBalance;

    @ApiModelProperty(value = "起始科目编码", notes = "起始科目编码（非必填，格式：{\"FNumber\":\"1001\"}）", example = "{\"FNumber\":\"1001\"}")
    @JsonProperty("FSTARTBALANCE")
    private Organization FStartBalance;

    @ApiModelProperty(value = "结束科目编码", notes = "结束科目编码（非必填，格式：{\"FNumber\":\"1002\"}）", example = "{\"FNumber\":\"1002\"}")
    @JsonProperty("FENDBALANCE")
    private Organization FEndBalance;

    @ApiModelProperty(value = "非连续科目范围查询", notes = "非连续科目范围查询（非必填）", example = "")
    @JsonProperty("FNOCONBALANCE")
    private String FNoConBalance;

    @ApiModelProperty(value = "连续科目范围查询", notes = "连续科目范围查询（非必填）", example = "")
    @JsonProperty("FCONBALANCE")
    private String FConBalance;

    @ApiModelProperty(value = "科目级别（起始）", notes = "科目级别（起始）（非必填）", example = "")
    @JsonProperty("FSTARTBALANCELEVEL")
    private String FStartBalanceLevel;

    @ApiModelProperty(value = "科目级别（结束）", notes = "科目级别（结束）（非必填）", example = "")
    @JsonProperty("FENDBALANCELEVEL")
    private String FEndBalanceLevel;

    @ApiModelProperty(value = "显示核算维度明细", notes = "显示核算维度明细（非必填）", example = "false")
    @JsonProperty("FSHOWACCTITEMS")
    private Boolean FShowAcctItems;

    @ApiModelProperty(value = "包括未过账凭证", notes = "包括未过账凭证（非必填）", example = "false")
    @JsonProperty("FNOTPOSTVOUCHER")
    private Boolean FNotPostVoucher;

    @ApiModelProperty(value = "只显示明细项目", notes = "只显示明细项目（非必填）", example = "false")
    @JsonProperty("FDETAILBALANCE")
    private Boolean FDetailBalance;

    @ApiModelProperty(value = "无发生额不显示", notes = "无发生额不显示（非必填）", example = "false")
    @JsonProperty("FNOAMOUNT")
    private Boolean FNoAmount;

    @ApiModelProperty(value = "合并层级", notes = "合并层级（非必填）", example = "")
    @JsonProperty("FLEVEL")
    private String FLevel;

    @ApiModelProperty(value = "合并层级ID", notes = "合并层级ID（非必填）", example = "")
    @JsonProperty("FLEVELID")
    private String FLevelId;

    @ApiModelProperty(value = "余额为零且无发生额不显示", notes = "余额为零且无发生额不显示（非必填）", example = "false")
    @JsonProperty("FZEROANDNOBALANCE")
    private Boolean FZeroAndNoBalance;

    @ApiModelProperty(value = "账簿", notes = "账簿（非必填）", example = "[{\"FNumber\":\"\"}]")
    @JsonProperty("FMULTITACCTBOOKID")
    private java.util.List<Organization> FMultiAcctBookId;

    @ApiModelProperty(value = "核算维度明细行显示科目信息", notes = "核算维度明细行显示科目信息（非必填）", example = "false")
    @JsonProperty("FSHOWACCTONEVERYCOL")
    private Boolean FShowAcctOnEveryCol;

    @ApiModelProperty(value = "余额为零不显示", notes = "余额为零不显示（非必填）", example = "false")
    @JsonProperty("FSHOWZEROBALANCE")
    private Boolean FShowZeroBalance;

    @ApiModelProperty(value = "余额方向与科目余额方向一致", notes = "余额方向与科目余额方向一致（非必填）", example = "false")
    @JsonProperty("FSAMEDIRECTION")
    private Boolean FSameDirection;

    @ApiModelProperty(value = "不包含调整期凭证", notes = "不包含调整期凭证（非必填）", example = "false")
    @JsonProperty("FEXCLUDEADJUSTVCH")
    private Boolean FExcludeAdjustVch;

    @ApiModelProperty(value = "显示禁用科目", notes = "显示禁用科目（非必填）", example = "false")
    @JsonProperty("FFORBIDBALANCE")
    private Boolean FForbidBalance;

    @ApiModelProperty(value = "显示合计", notes = "显示合计（非必填）", example = "false")
    @JsonProperty("FSHOWTOTAL")
    private Boolean FShowTotal;

    @ApiModelProperty(value = "显示科目全名", notes = "显示科目全名（非必填）", example = "false")
    @JsonProperty("FSHOWFULLNAME")
    private Boolean FShowFullName;

    @ApiModelProperty(value = "显示数量单价", notes = "显示数量单价（非必填）", example = "false")
    @JsonProperty("FSHOWQTY")
    private Boolean FShowQty;

    @ApiModelProperty(value = "按日显示", notes = "按日显示（非必填）", example = "false")
    @JsonProperty("FTOTALBYDAY")
    private Boolean FTotalByDay;

    @ApiModelProperty(value = "按计量单位组默认单位核算", notes = "按计量单位组默认单位核算（非必填）", example = "false")
    @JsonProperty("FCALBYDEFAULTUNIT")
    private Boolean FCalByDefaultUnit;

    @ApiModelProperty(value = "不包含结转损益凭证", notes = "不包含结转损益凭证（非必填）", example = "false")
    @JsonProperty("FEXCLUDEPROFITVCH")
    private Boolean FExcludeProfitVch;

    @ApiModelProperty(value = "展开下级明细", notes = "展开下级明细（非必填）", example = "false")
    @JsonProperty("FSHOWCHILDDETAIL")
    private Boolean FShowChildDetail;

    // Getter and Setter
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

    public String getFCurrencyId() {
        return FCurrencyId;
    }

    public void setFCurrencyId(String FCurrencyId) {
        this.FCurrencyId = FCurrencyId;
    }

    public Organization getFSchemeID() {
        return FSchemeID;
    }

    public void setFSchemeID(Organization FSchemeID) {
        this.FSchemeID = FSchemeID;
    }

    public String getFUseAcctBookGroup() {
        return FUseAcctBookGroup;
    }

    public void setFUseAcctBookGroup(String FUseAcctBookGroup) {
        this.FUseAcctBookGroup = FUseAcctBookGroup;
    }

    public String getFUseAcctBookMergeGroup() {
        return FUseAcctBookMergeGroup;
    }

    public void setFUseAcctBookMergeGroup(String FUseAcctBookMergeGroup) {
        this.FUseAcctBookMergeGroup = FUseAcctBookMergeGroup;
    }

    public String getFBalance() {
        return FBalance;
    }

    public void setFBalance(String FBalance) {
        this.FBalance = FBalance;
    }

    public Organization getFStartBalance() {
        return FStartBalance;
    }

    public void setFStartBalance(Organization FStartBalance) {
        this.FStartBalance = FStartBalance;
    }

    public Organization getFEndBalance() {
        return FEndBalance;
    }

    public void setFEndBalance(Organization FEndBalance) {
        this.FEndBalance = FEndBalance;
    }

    public String getFNoConBalance() {
        return FNoConBalance;
    }

    public void setFNoConBalance(String FNoConBalance) {
        this.FNoConBalance = FNoConBalance;
    }

    public String getFConBalance() {
        return FConBalance;
    }

    public void setFConBalance(String FConBalance) {
        this.FConBalance = FConBalance;
    }

    public String getFStartBalanceLevel() {
        return FStartBalanceLevel;
    }

    public void setFStartBalanceLevel(String FStartBalanceLevel) {
        this.FStartBalanceLevel = FStartBalanceLevel;
    }

    public String getFEndBalanceLevel() {
        return FEndBalanceLevel;
    }

    public void setFEndBalanceLevel(String FEndBalanceLevel) {
        this.FEndBalanceLevel = FEndBalanceLevel;
    }

    public Boolean getFShowAcctItems() {
        return FShowAcctItems;
    }

    public void setFShowAcctItems(Boolean FShowAcctItems) {
        this.FShowAcctItems = FShowAcctItems;
    }

    public Boolean getFNotPostVoucher() {
        return FNotPostVoucher;
    }

    public void setFNotPostVoucher(Boolean FNotPostVoucher) {
        this.FNotPostVoucher = FNotPostVoucher;
    }

    public Boolean getFDetailBalance() {
        return FDetailBalance;
    }

    public void setFDetailBalance(Boolean FDetailBalance) {
        this.FDetailBalance = FDetailBalance;
    }

    public Boolean getFNoAmount() {
        return FNoAmount;
    }

    public void setFNoAmount(Boolean FNoAmount) {
        this.FNoAmount = FNoAmount;
    }

    public String getFLevel() {
        return FLevel;
    }

    public void setFLevel(String FLevel) {
        this.FLevel = FLevel;
    }

    public String getFLevelId() {
        return FLevelId;
    }

    public void setFLevelId(String FLevelId) {
        this.FLevelId = FLevelId;
    }

    public Boolean getFZeroAndNoBalance() {
        return FZeroAndNoBalance;
    }

    public void setFZeroAndNoBalance(Boolean FZeroAndNoBalance) {
        this.FZeroAndNoBalance = FZeroAndNoBalance;
    }

    public java.util.List<Organization> getFMultiAcctBookId() {
        return FMultiAcctBookId;
    }

    public void setFMultiAcctBookId(java.util.List<Organization> FMultiAcctBookId) {
        this.FMultiAcctBookId = FMultiAcctBookId;
    }

    public Boolean getFShowAcctOnEveryCol() {
        return FShowAcctOnEveryCol;
    }

    public void setFShowAcctOnEveryCol(Boolean FShowAcctOnEveryCol) {
        this.FShowAcctOnEveryCol = FShowAcctOnEveryCol;
    }

    public Boolean getFShowZeroBalance() {
        return FShowZeroBalance;
    }

    public void setFShowZeroBalance(Boolean FShowZeroBalance) {
        this.FShowZeroBalance = FShowZeroBalance;
    }

    public Boolean getFSameDirection() {
        return FSameDirection;
    }

    public void setFSameDirection(Boolean FSameDirection) {
        this.FSameDirection = FSameDirection;
    }

    public Boolean getFExcludeAdjustVch() {
        return FExcludeAdjustVch;
    }

    public void setFExcludeAdjustVch(Boolean FExcludeAdjustVch) {
        this.FExcludeAdjustVch = FExcludeAdjustVch;
    }

    public Boolean getFForbidBalance() {
        return FForbidBalance;
    }

    public void setFForbidBalance(Boolean FForbidBalance) {
        this.FForbidBalance = FForbidBalance;
    }

    public Boolean getFShowTotal() {
        return FShowTotal;
    }

    public void setFShowTotal(Boolean FShowTotal) {
        this.FShowTotal = FShowTotal;
    }

    public Boolean getFShowFullName() {
        return FShowFullName;
    }

    public void setFShowFullName(Boolean FShowFullName) {
        this.FShowFullName = FShowFullName;
    }

    public Boolean getFShowQty() {
        return FShowQty;
    }

    public void setFShowQty(Boolean FShowQty) {
        this.FShowQty = FShowQty;
    }

    public Boolean getFTotalByDay() {
        return FTotalByDay;
    }

    public void setFTotalByDay(Boolean FTotalByDay) {
        this.FTotalByDay = FTotalByDay;
    }

    public Boolean getFCalByDefaultUnit() {
        return FCalByDefaultUnit;
    }

    public void setFCalByDefaultUnit(Boolean FCalByDefaultUnit) {
        this.FCalByDefaultUnit = FCalByDefaultUnit;
    }

    public Boolean getFExcludeProfitVch() {
        return FExcludeProfitVch;
    }

    public void setFExcludeProfitVch(Boolean FExcludeProfitVch) {
        this.FExcludeProfitVch = FExcludeProfitVch;
    }

    public Boolean getFShowChildDetail() {
        return FShowChildDetail;
    }

    public void setFShowChildDetail(Boolean FShowChildDetail) {
        this.FShowChildDetail = FShowChildDetail;
    }
}
