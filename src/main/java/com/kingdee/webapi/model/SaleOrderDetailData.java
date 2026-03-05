package com.kingdee.webapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 销售订单执行明细表数据映射
 */
@ApiModel(description = "销售订单执行明细表数据", value = "SaleOrderDetailData")
public class SaleOrderDetailData {

    @ApiModelProperty(value = "销售组织")
    private String FSALEORGNAME;

    @ApiModelProperty(value = "订单编号")
    private String FBILLNO;

    @ApiModelProperty(value = "单据类型")
    private String FBILLTYPE;

    @ApiModelProperty(value = "订单日期")
    private String FDate;

    @ApiModelProperty(value = "销售部门")
    private String FSALEDEPTID;

    @ApiModelProperty(value = "销售组")
    private String FSALEGROUP;

    @ApiModelProperty(value = "销售员")
    private String FSALES;

    @ApiModelProperty(value = "客户编码")
    private String FCUSTID;

    @ApiModelProperty(value = "客户名称")
    private String FCUSTOMERNAME;

    @ApiModelProperty(value = "客户分组")
    private String FCustGroupName;

    @ApiModelProperty(value = "地区")
    private String FPROVINCIAL;

    @ApiModelProperty(value = "省份")
    private String FPROVINCE;

    @ApiModelProperty(value = "城市")
    private String FCITY;

    @ApiModelProperty(value = "收款条件")
    private String FRecCondition;

    @ApiModelProperty(value = "订单行号")
    private String FORDERID;

    @ApiModelProperty(value = "要货日期")
    private String FDELIVERYDAY;

    @ApiModelProperty(value = "关联保证金")
    private String FAssociateMargin;

    @ApiModelProperty(value = "关联退款保证金")
    private String FASSREFUNDMARGIN;

    @ApiModelProperty(value = "物料编码")
    private String FMATERIALID;

    @ApiModelProperty(value = "物料名称")
    private String FMATERIALNAME;

    @ApiModelProperty(value = "客户物料编码")
    private String FMAPID;

    @ApiModelProperty(value = "客户物料名称")
    private String FMAPNAME;

    @ApiModelProperty(value = "规格型号")
    private String FSPECIFICATION;

    @ApiModelProperty(value = "物料属性")
    private String FERPCLSID;

    @ApiModelProperty(value = "物料分组")
    private String FMATERIALGROUPNAME;

    @ApiModelProperty(value = "存货类别")
    private String FCATEGORYID;

    @ApiModelProperty(value = "辅助属性")
    private String FAUXPROPID;

    @ApiModelProperty(value = "销售单位")
    private String FUNIT;

    @ApiModelProperty(value = "计价单位")
    private String FPRICEUNIT;

    @ApiModelProperty(value = "是否赠品")
    private String FIsFree;

    @ApiModelProperty(value = "币别")
    private String FCURRENCYID;

    @ApiModelProperty(value = "本位币别")
    private String FLOCALCURRID;

    @ApiModelProperty(value = "单价（销售单位）")
    private String FPrice;

    @ApiModelProperty(value = "单价(销售单位)(本位币)")
    private String FPRICE_LC;

    @ApiModelProperty(value = "单价（计价单位）")
    private String FCharge;

    @ApiModelProperty(value = "单价(计价单位)(本位币)")
    private String FCHARGE_LC;

    @ApiModelProperty(value = "订单数量")
    private String FORDERQTY;

    @ApiModelProperty(value = "订单数量（计价单位）")
    private String FOrderChargeQty;

    @ApiModelProperty(value = "订单金额")
    private String FORDERAMOUNT;

    @ApiModelProperty(value = "订单金额(本位币)")
    private String FORDERAMOUNT_LC;

    @ApiModelProperty(value = "发货通知单编号")
    private String FDELIBILLNO;

    @ApiModelProperty(value = "发货通知数量")
    private String FDELIQTY;

    @ApiModelProperty(value = "发货通知数量（计价单位）")
    private String FDELICHARGEQTY;

    @ApiModelProperty(value = "发货通知金额")
    private String FDELIAMOUNT;

    @ApiModelProperty(value = "发货通知金额(本位币)")
    private String FDELIAMOUNT_LC;

    @ApiModelProperty(value = "出库单编号")
    private String FOUTBILLNO;

    @ApiModelProperty(value = "出库日期")
    private String FOUTSTOCKDATE;

    @ApiModelProperty(value = "已出库数量")
    private String FOUTQTY;

    @ApiModelProperty(value = "已出库数量（计价单位）")
    private String FOUTCHARGEQTY;

    @ApiModelProperty(value = "已出库金额")
    private String FOUTAMOUNT;

    @ApiModelProperty(value = "已出库金额(本位币)")
    private String FOUTAMOUNT_LC;

    @ApiModelProperty(value = "退货通知单编号")
    private String FRETURNPRDBILLNO;

    @ApiModelProperty(value = "退货通知数量")
    private String FRETURNPQTY;

    @ApiModelProperty(value = "退货通知数量（计价单位）")
    private String FRETURNPCHARGEQTY;

    @ApiModelProperty(value = "退货通知金额")
    private String FRETURNPAMOUNT;

    @ApiModelProperty(value = "退货通知金额(本位币)")
    private String FRETURNPAMOUNT_LC;

    @ApiModelProperty(value = "销售退货单编号")
    private String FRETURNBILLNO;

    @ApiModelProperty(value = "销售退货日期")
    private String FRETURNSTOCKDATE;

    @ApiModelProperty(value = "销售退货数量")
    private String FRETURNQTY;

    @ApiModelProperty(value = "销售退货数量（计价单位）")
    private String FRETURNCHARGEQTY;

    @ApiModelProperty(value = "销售退货金额")
    private String FRETURNAMOUNT;

    @ApiModelProperty(value = "销售退货金额(本位币)")
    private String FRETURNAMOUNT_LC;

    @ApiModelProperty(value = "应收单号")
    private String FRECEIVEBILLNO;

    @ApiModelProperty(value = "应收业务日期")
    private String FARDate;

    @ApiModelProperty(value = "应收立账类型")
    private String FSetAccountType;

    @ApiModelProperty(value = "应收数量")
    private String FRECQTY;

    @ApiModelProperty(value = "应收数量（计价单位）")
    private String FRECEIVECHARGEQTY;

    @ApiModelProperty(value = "应收金额")
    private String FRECAMOUNT;

    @ApiModelProperty(value = "应收金额(本位币)")
    private String FRECAMOUNT_LC;

    @ApiModelProperty(value = "调整金额")
    private String FWriteOffAmount;

    @ApiModelProperty(value = "调整金额(本位币)")
    private String FWRITEOFFAMOUNT_LC;

    @ApiModelProperty(value = "发票单据编号")
    private String FINVOECEBILLNO;

    @ApiModelProperty(value = "发票日期")
    private String FINVOECEDATE;

    @ApiModelProperty(value = "发票号码")
    private String FINVOECENO;

    @ApiModelProperty(value = "开票数量")
    private String FINVOECEQTY;

    @ApiModelProperty(value = "开票数量（计价单位）")
    private String FBILLCHARGEQTY;

    @ApiModelProperty(value = "开票金额")
    private String FINVOECEAMOUNT;

    @ApiModelProperty(value = "开票金额(本位币)")
    private String FINVOECEAMOUNT_LC;

    @ApiModelProperty(value = "预收单号")
    private String FADVANCEBILLNO;

    @ApiModelProperty(value = "预收单日期")
    private String FADVANCEBILLDATE;

    @ApiModelProperty(value = "预收金额")
    private String FADVANCEAMOUNT;

    @ApiModelProperty(value = "预收金额(本位币)")
    private String FADVANCEAMOUNT_LC;

    @ApiModelProperty(value = "预收超收金额")
    private String FOVERRECAMOUNT;

    @ApiModelProperty(value = "预收超收金额(本位币)")
    private String FOVERRECAMOUNT_LC;

    @ApiModelProperty(value = "收款单号")
    private String FRECBILLNO;

    @ApiModelProperty(value = "收款单日期")
    private String FRECBILLDATE;

    @ApiModelProperty(value = "结算金额")
    private String FRECEIPTAMOUNT;

    @ApiModelProperty(value = "结算金额(本位币)")
    private String FRECEIPTAMOUNT_LC;

    @ApiModelProperty(value = "结算调整金额")
    private String FJSWRITEOFFAMOUNT;

    @ApiModelProperty(value = "结算调整金额(本位币)")
    private String FJSWRITEOFFAMOUNT_LC;

    @ApiModelProperty(value = "特殊冲销金额")
    private String FChargeOffAmount;

    @ApiModelProperty(value = "特殊冲销金额冲销金额(本位币)")
    private String FCHARGEOFFAMOUNT_LC;

    @ApiModelProperty(value = "收款核销金额")
    private String FRECSKAMOUNT;

    @ApiModelProperty(value = "收款核销金额(本位币)")
    private String FRECSKAMOUNT_LC;

    @ApiModelProperty(value = "整单关闭")
    private String FCLOSESTATUS;

    @ApiModelProperty(value = "行业务关闭")
    private String FMRPCLOSESTATUS;

    @ApiModelProperty(value = "行业务终止")
    private String FMrpTerminateStatus;

    @ApiModelProperty(value = "行业务冻结")
    private String FMrpFreezeStatus;

    @ApiModelProperty(value = "是否手工关闭")
    private String FMANUALCLOSE;

    @ApiModelProperty(value = "是否手工行关闭")
    private String FMANUALROWCLOSE;

    @ApiModelProperty(value = "是否期初订单")
    private String FISINIT;

    @ApiModelProperty(value = "基本单位")
    private String FBaseUnitID;

    @ApiModelProperty(value = "订单数量(基本单位)")
    private String FOrderBaseQty;

    @ApiModelProperty(value = "单价(基本单位)")
    private String FBasePrice;

    @ApiModelProperty(value = "单价(基本单位)(本位币)")
    private String FBASEPRICE_LC;

    @ApiModelProperty(value = "发货通知数量(基本单位)")
    private String FDeliveryNoticeQty;

    @ApiModelProperty(value = "已出库数量(基本单位)")
    private String FDeliveryQty;

    @ApiModelProperty(value = "退货通知数量(基本单位)")
    private String FReturnNoticeQty;

    @ApiModelProperty(value = "销售退货数量(基本单位)")
    private String FReturnBaseQty;

    @ApiModelProperty(value = "应收数量(基本单位)")
    private String FReceiveQty;

    @ApiModelProperty(value = "开票数量(基本单位)")
    private String FBaseInvoiceQty;

    @ApiModelProperty(value = "预设基础资料1")
    private String FPRESETBASENAME1;

    @ApiModelProperty(value = "预设基础资料2")
    private String FPRESETBASENAME2;

    @ApiModelProperty(value = "预设辅助资料1")
    private String FPRESETASSISTANTNAME1;

    @ApiModelProperty(value = "预设辅助资料2")
    private String FPRESETASSISTANTNAME2;

    @ApiModelProperty(value = "订单预收未核销金额")
    private String FADVANCENOTMATCHAMOUNT;

    @ApiModelProperty(value = "订单预收未核销金额(本位币)")
    private String FADVANCENOTMATCHAMOUNT_LC;

    @ApiModelProperty(value = "订单收款金额字段")
    private String FALLMATCHAMOUNT;

    @ApiModelProperty(value = "订单收款金额字段(本位币)")
    private String FALLMATCHAMOUNT_LC;

    @ApiModelProperty(value = "订单数量")
    private String FORDERQTYTEMP;

    @ApiModelProperty(value = "订单金额")
    private String FORDERAMOUNTTEMP;

    // Getters and Setters
    public String getFSALEORGNAME() { return FSALEORGNAME; }
    public void setFSALEORGNAME(String FSALEORGNAME) { this.FSALEORGNAME = FSALEORGNAME; }

    public String getFBILLNO() { return FBILLNO; }
    public void setFBILLNO(String FBILLNO) { this.FBILLNO = FBILLNO; }

    public String getFBILLTYPE() { return FBILLTYPE; }
    public void setFBILLTYPE(String FBILLTYPE) { this.FBILLTYPE = FBILLTYPE; }

    public String getFDate() { return FDate; }
    public void setFDate(String FDate) { this.FDate = FDate; }

    public String getFSALEDEPTID() { return FSALEDEPTID; }
    public void setFSALEDEPTID(String FSALEDEPTID) { this.FSALEDEPTID = FSALEDEPTID; }

    public String getFSALEGROUP() { return FSALEGROUP; }
    public void setFSALEGROUP(String FSALEGROUP) { this.FSALEGROUP = FSALEGROUP; }

    public String getFSALES() { return FSALES; }
    public void setFSALES(String FSALES) { this.FSALES = FSALES; }

    public String getFCUSTID() { return FCUSTID; }
    public void setFCUSTID(String FCUSTID) { this.FCUSTID = FCUSTID; }

    public String getFCUSTOMERNAME() { return FCUSTOMERNAME; }
    public void setFCUSTOMERNAME(String FCUSTOMERNAME) { this.FCUSTOMERNAME = FCUSTOMERNAME; }

    public String getFCustGroupName() { return FCustGroupName; }
    public void setFCustGroupName(String FCustGroupName) { this.FCustGroupName = FCustGroupName; }

    public String getFPROVINCIAL() { return FPROVINCIAL; }
    public void setFPROVINCIAL(String FPROVINCIAL) { this.FPROVINCIAL = FPROVINCIAL; }

    public String getFPROVINCE() { return FPROVINCE; }
    public void setFPROVINCE(String FPROVINCE) { this.FPROVINCE = FPROVINCE; }

    public String getFCITY() { return FCITY; }
    public void setFCITY(String FCITY) { this.FCITY = FCITY; }

    public String getFRecCondition() { return FRecCondition; }
    public void setFRecCondition(String FRecCondition) { this.FRecCondition = FRecCondition; }

    public String getFORDERID() { return FORDERID; }
    public void setFORDERID(String FORDERID) { this.FORDERID = FORDERID; }

    public String getFDELIVERYDAY() { return FDELIVERYDAY; }
    public void setFDELIVERYDAY(String FDELIVERYDAY) { this.FDELIVERYDAY = FDELIVERYDAY; }

    public String getFAssociateMargin() { return FAssociateMargin; }
    public void setFAssociateMargin(String FAssociateMargin) { this.FAssociateMargin = FAssociateMargin; }

    public String getFASSREFUNDMARGIN() { return FASSREFUNDMARGIN; }
    public void setFASSREFUNDMARGIN(String FASSREFUNDMARGIN) { this.FASSREFUNDMARGIN = FASSREFUNDMARGIN; }

    public String getFMATERIALID() { return FMATERIALID; }
    public void setFMATERIALID(String FMATERIALID) { this.FMATERIALID = FMATERIALID; }

    public String getFMATERIALNAME() { return FMATERIALNAME; }
    public void setFMATERIALNAME(String FMATERIALNAME) { this.FMATERIALNAME = FMATERIALNAME; }

    public String getFMAPID() { return FMAPID; }
    public void setFMAPID(String FMAPID) { this.FMAPID = FMAPID; }

    public String getFMAPNAME() { return FMAPNAME; }
    public void setFMAPNAME(String FMAPNAME) { this.FMAPNAME = FMAPNAME; }

    public String getFSPECIFICATION() { return FSPECIFICATION; }
    public void setFSPECIFICATION(String FSPECIFICATION) { this.FSPECIFICATION = FSPECIFICATION; }

    public String getFERPCLSID() { return FERPCLSID; }
    public void setFERPCLSID(String FERPCLSID) { this.FERPCLSID = FERPCLSID; }

    public String getFMATERIALGROUPNAME() { return FMATERIALGROUPNAME; }
    public void setFMATERIALGROUPNAME(String FMATERIALGROUPNAME) { this.FMATERIALGROUPNAME = FMATERIALGROUPNAME; }

    public String getFCATEGORYID() { return FCATEGORYID; }
    public void setFCATEGORYID(String FCATEGORYID) { this.FCATEGORYID = FCATEGORYID; }

    public String getFAUXPROPID() { return FAUXPROPID; }
    public void setFAUXPROPID(String FAUXPROPID) { this.FAUXPROPID = FAUXPROPID; }

    public String getFUNIT() { return FUNIT; }
    public void setFUNIT(String FUNIT) { this.FUNIT = FUNIT; }

    public String getFPRICEUNIT() { return FPRICEUNIT; }
    public void setFPRICEUNIT(String FPRICEUNIT) { this.FPRICEUNIT = FPRICEUNIT; }

    public String getFIsFree() { return FIsFree; }
    public void setFIsFree(String FIsFree) { this.FIsFree = FIsFree; }

    public String getFCURRENCYID() { return FCURRENCYID; }
    public void setFCURRENCYID(String FCURRENCYID) { this.FCURRENCYID = FCURRENCYID; }

    public String getFLOCALCURRID() { return FLOCALCURRID; }
    public void setFLOCALCURRID(String FLOCALCURRID) { this.FLOCALCURRID = FLOCALCURRID; }

    public String getFPrice() { return FPrice; }
    public void setFPrice(String FPrice) { this.FPrice = FPrice; }

    public String getFPRICE_LC() { return FPRICE_LC; }
    public void setFPRICE_LC(String FPRICE_LC) { this.FPRICE_LC = FPRICE_LC; }

    public String getFCharge() { return FCharge; }
    public void setFCharge(String FCharge) { this.FCharge = FCharge; }

    public String getFCHARGE_LC() { return FCHARGE_LC; }
    public void setFCHARGE_LC(String FCHARGE_LC) { this.FCHARGE_LC = FCHARGE_LC; }

    public String getFORDERQTY() { return FORDERQTY; }
    public void setFORDERQTY(String FORDERQTY) { this.FORDERQTY = FORDERQTY; }

    public String getFOrderChargeQty() { return FOrderChargeQty; }
    public void setFOrderChargeQty(String FOrderChargeQty) { this.FOrderChargeQty = FOrderChargeQty; }

    public String getFORDERAMOUNT() { return FORDERAMOUNT; }
    public void setFORDERAMOUNT(String FORDERAMOUNT) { this.FORDERAMOUNT = FORDERAMOUNT; }

    public String getFORDERAMOUNT_LC() { return FORDERAMOUNT_LC; }
    public void setFORDERAMOUNT_LC(String FORDERAMOUNT_LC) { this.FORDERAMOUNT_LC = FORDERAMOUNT_LC; }

    public String getFDELIBILLNO() { return FDELIBILLNO; }
    public void setFDELIBILLNO(String FDELIBILLNO) { this.FDELIBILLNO = FDELIBILLNO; }

    public String getFDELIQTY() { return FDELIQTY; }
    public void setFDELIQTY(String FDELIQTY) { this.FDELIQTY = FDELIQTY; }

    public String getFDELICHARGEQTY() { return FDELICHARGEQTY; }
    public void setFDELICHARGEQTY(String FDELICHARGEQTY) { this.FDELICHARGEQTY = FDELICHARGEQTY; }

    public String getFDELIAMOUNT() { return FDELIAMOUNT; }
    public void setFDELIAMOUNT(String FDELIAMOUNT) { this.FDELIAMOUNT = FDELIAMOUNT; }

    public String getFDELIAMOUNT_LC() { return FDELIAMOUNT_LC; }
    public void setFDELIAMOUNT_LC(String FDELIAMOUNT_LC) { this.FDELIAMOUNT_LC = FDELIAMOUNT_LC; }

    public String getFOUTBILLNO() { return FOUTBILLNO; }
    public void setFOUTBILLNO(String FOUTBILLNO) { this.FOUTBILLNO = FOUTBILLNO; }

    public String getFOUTSTOCKDATE() { return FOUTSTOCKDATE; }
    public void setFOUTSTOCKDATE(String FOUTSTOCKDATE) { this.FOUTSTOCKDATE = FOUTSTOCKDATE; }

    public String getFOUTQTY() { return FOUTQTY; }
    public void setFOUTQTY(String FOUTQTY) { this.FOUTQTY = FOUTQTY; }

    public String getFOUTCHARGEQTY() { return FOUTCHARGEQTY; }
    public void setFOUTCHARGEQTY(String FOUTCHARGEQTY) { this.FOUTCHARGEQTY = FOUTCHARGEQTY; }

    public String getFOUTAMOUNT() { return FOUTAMOUNT; }
    public void setFOUTAMOUNT(String FOUTAMOUNT) { this.FOUTAMOUNT = FOUTAMOUNT; }

    public String getFOUTAMOUNT_LC() { return FOUTAMOUNT_LC; }
    public void setFOUTAMOUNT_LC(String FOUTAMOUNT_LC) { this.FOUTAMOUNT_LC = FOUTAMOUNT_LC; }

    public String getFRETURNPRDBILLNO() { return FRETURNPRDBILLNO; }
    public void setFRETURNPRDBILLNO(String FRETURNPRDBILLNO) { this.FRETURNPRDBILLNO = FRETURNPRDBILLNO; }

    public String getFRETURNPQTY() { return FRETURNPQTY; }
    public void setFRETURNPQTY(String FRETURNPQTY) { this.FRETURNPQTY = FRETURNPQTY; }

    public String getFRETURNPCHARGEQTY() { return FRETURNPCHARGEQTY; }
    public void setFRETURNPCHARGEQTY(String FRETURNPCHARGEQTY) { this.FRETURNPCHARGEQTY = FRETURNPCHARGEQTY; }

    public String getFRETURNPAMOUNT() { return FRETURNPAMOUNT; }
    public void setFRETURNPAMOUNT(String FRETURNPAMOUNT) { this.FRETURNPAMOUNT = FRETURNPAMOUNT; }

    public String getFRETURNPAMOUNT_LC() { return FRETURNPAMOUNT_LC; }
    public void setFRETURNPAMOUNT_LC(String FRETURNPAMOUNT_LC) { this.FRETURNPAMOUNT_LC = FRETURNPAMOUNT_LC; }

    public String getFRETURNBILLNO() { return FRETURNBILLNO; }
    public void setFRETURNBILLNO(String FRETURNBILLNO) { this.FRETURNBILLNO = FRETURNBILLNO; }

    public String getFRETURNSTOCKDATE() { return FRETURNSTOCKDATE; }
    public void setFRETURNSTOCKDATE(String FRETURNSTOCKDATE) { this.FRETURNSTOCKDATE = FRETURNSTOCKDATE; }

    public String getFRETURNQTY() { return FRETURNQTY; }
    public void setFRETURNQTY(String FRETURNQTY) { this.FRETURNQTY = FRETURNQTY; }

    public String getFRETURNCHARGEQTY() { return FRETURNCHARGEQTY; }
    public void setFRETURNCHARGEQTY(String FRETURNCHARGEQTY) { this.FRETURNCHARGEQTY = FRETURNCHARGEQTY; }

    public String getFRETURNAMOUNT() { return FRETURNAMOUNT; }
    public void setFRETURNAMOUNT(String FRETURNAMOUNT) { this.FRETURNAMOUNT = FRETURNAMOUNT; }

    public String getFRETURNAMOUNT_LC() { return FRETURNAMOUNT_LC; }
    public void setFRETURNAMOUNT_LC(String FRETURNAMOUNT_LC) { this.FRETURNAMOUNT_LC = FRETURNAMOUNT_LC; }

    public String getFRECEIVEBILLNO() { return FRECEIVEBILLNO; }
    public void setFRECEIVEBILLNO(String FRECEIVEBILLNO) { this.FRECEIVEBILLNO = FRECEIVEBILLNO; }

    public String getFARDate() { return FARDate; }
    public void setFARDate(String FARDate) { this.FARDate = FARDate; }

    public String getFSetAccountType() { return FSetAccountType; }
    public void setFSetAccountType(String FSetAccountType) { this.FSetAccountType = FSetAccountType; }

    public String getFRECQTY() { return FRECQTY; }
    public void setFRECQTY(String FRECQTY) { this.FRECQTY = FRECQTY; }

    public String getFRECEIVECHARGEQTY() { return FRECEIVECHARGEQTY; }
    public void setFRECEIVECHARGEQTY(String FRECEIVECHARGEQTY) { this.FRECEIVECHARGEQTY = FRECEIVECHARGEQTY; }

    public String getFRECAMOUNT() { return FRECAMOUNT; }
    public void setFRECAMOUNT(String FRECAMOUNT) { this.FRECAMOUNT = FRECAMOUNT; }

    public String getFRECAMOUNT_LC() { return FRECAMOUNT_LC; }
    public void setFRECAMOUNT_LC(String FRECAMOUNT_LC) { this.FRECAMOUNT_LC = FRECAMOUNT_LC; }

    public String getFWriteOffAmount() { return FWriteOffAmount; }
    public void setFWriteOffAmount(String FWriteOffAmount) { this.FWriteOffAmount = FWriteOffAmount; }

    public String getFWRITEOFFAMOUNT_LC() { return FWRITEOFFAMOUNT_LC; }
    public void setFWRITEOFFAMOUNT_LC(String FWRITEOFFAMOUNT_LC) { this.FWRITEOFFAMOUNT_LC = FWRITEOFFAMOUNT_LC; }

    public String getFINVOECEBILLNO() { return FINVOECEBILLNO; }
    public void setFINVOECEBILLNO(String FINVOECEBILLNO) { this.FINVOECEBILLNO = FINVOECEBILLNO; }

    public String getFINVOECEDATE() { return FINVOECEDATE; }
    public void setFINVOECEDATE(String FINVOECEDATE) { this.FINVOECEDATE = FINVOECEDATE; }

    public String getFINVOECENO() { return FINVOECENO; }
    public void setFINVOECENO(String FINVOECENO) { this.FINVOECENO = FINVOECENO; }

    public String getFINVOECEQTY() { return FINVOECEQTY; }
    public void setFINVOECEQTY(String FINVOECEQTY) { this.FINVOECEQTY = FINVOECEQTY; }

    public String getFBILLCHARGEQTY() { return FBILLCHARGEQTY; }
    public void setFBILLCHARGEQTY(String FBILLCHARGEQTY) { this.FBILLCHARGEQTY = FBILLCHARGEQTY; }

    public String getFINVOECEAMOUNT() { return FINVOECEAMOUNT; }
    public void setFINVOECEAMOUNT(String FINVOECEAMOUNT) { this.FINVOECEAMOUNT = FINVOECEAMOUNT; }

    public String getFINVOECEAMOUNT_LC() { return FINVOECEAMOUNT_LC; }
    public void setFINVOECEAMOUNT_LC(String FINVOECEAMOUNT_LC) { this.FINVOECEAMOUNT_LC = FINVOECEAMOUNT_LC; }

    public String getFADVANCEBILLNO() { return FADVANCEBILLNO; }
    public void setFADVANCEBILLNO(String FADVANCEBILLNO) { this.FADVANCEBILLNO = FADVANCEBILLNO; }

    public String getFADVANCEBILLDATE() { return FADVANCEBILLDATE; }
    public void setFADVANCEBILLDATE(String FADVANCEBILLDATE) { this.FADVANCEBILLDATE = FADVANCEBILLDATE; }

    public String getFADVANCEAMOUNT() { return FADVANCEAMOUNT; }
    public void setFADVANCEAMOUNT(String FADVANCEAMOUNT) { this.FADVANCEAMOUNT = FADVANCEAMOUNT; }

    public String getFADVANCEAMOUNT_LC() { return FADVANCEAMOUNT_LC; }
    public void setFADVANCEAMOUNT_LC(String FADVANCEAMOUNT_LC) { this.FADVANCEAMOUNT_LC = FADVANCEAMOUNT_LC; }

    public String getFOVERRECAMOUNT() { return FOVERRECAMOUNT; }
    public void setFOVERRECAMOUNT(String FOVERRECAMOUNT) { this.FOVERRECAMOUNT = FOVERRECAMOUNT; }

    public String getFOVERRECAMOUNT_LC() { return FOVERRECAMOUNT_LC; }
    public void setFOVERRECAMOUNT_LC(String FOVERRECAMOUNT_LC) { this.FOVERRECAMOUNT_LC = FOVERRECAMOUNT_LC; }

    public String getFRECBILLNO() { return FRECBILLNO; }
    public void setFRECBILLNO(String FRECBILLNO) { this.FRECBILLNO = FRECBILLNO; }

    public String getFRECBILLDATE() { return FRECBILLDATE; }
    public void setFRECBILLDATE(String FRECBILLDATE) { this.FRECBILLDATE = FRECBILLDATE; }

    public String getFRECEIPTAMOUNT() { return FRECEIPTAMOUNT; }
    public void setFRECEIPTAMOUNT(String FRECEIPTAMOUNT) { this.FRECEIPTAMOUNT = FRECEIPTAMOUNT; }

    public String getFRECEIPTAMOUNT_LC() { return FRECEIPTAMOUNT_LC; }
    public void setFRECEIPTAMOUNT_LC(String FRECEIPTAMOUNT_LC) { this.FRECEIPTAMOUNT_LC = FRECEIPTAMOUNT_LC; }

    public String getFJSWRITEOFFAMOUNT() { return FJSWRITEOFFAMOUNT; }
    public void setFJSWRITEOFFAMOUNT(String FJSWRITEOFFAMOUNT) { this.FJSWRITEOFFAMOUNT = FJSWRITEOFFAMOUNT; }

    public String getFJSWRITEOFFAMOUNT_LC() { return FJSWRITEOFFAMOUNT_LC; }
    public void setFJSWRITEOFFAMOUNT_LC(String FJSWRITEOFFAMOUNT_LC) { this.FJSWRITEOFFAMOUNT_LC = FJSWRITEOFFAMOUNT_LC; }

    public String getFChargeOffAmount() { return FChargeOffAmount; }
    public void setFChargeOffAmount(String FChargeOffAmount) { this.FChargeOffAmount = FChargeOffAmount; }

    public String getFCHARGEOFFAMOUNT_LC() { return FCHARGEOFFAMOUNT_LC; }
    public void setFCHARGEOFFAMOUNT_LC(String FCHARGEOFFAMOUNT_LC) { this.FCHARGEOFFAMOUNT_LC = FCHARGEOFFAMOUNT_LC; }

    public String getFRECSKAMOUNT() { return FRECSKAMOUNT; }
    public void setFRECSKAMOUNT(String FRECSKAMOUNT) { this.FRECSKAMOUNT = FRECSKAMOUNT; }

    public String getFRECSKAMOUNT_LC() { return FRECSKAMOUNT_LC; }
    public void setFRECSKAMOUNT_LC(String FRECSKAMOUNT_LC) { this.FRECSKAMOUNT_LC = FRECSKAMOUNT_LC; }

    public String getFCLOSESTATUS() { return FCLOSESTATUS; }
    public void setFCLOSESTATUS(String FCLOSESTATUS) { this.FCLOSESTATUS = FCLOSESTATUS; }

    public String getFMRPCLOSESTATUS() { return FMRPCLOSESTATUS; }
    public void setFMRPCLOSESTATUS(String FMRPCLOSESTATUS) { this.FMRPCLOSESTATUS = FMRPCLOSESTATUS; }

    public String getFMrpTerminateStatus() { return FMrpTerminateStatus; }
    public void setFMrpTerminateStatus(String FMrpTerminateStatus) { this.FMrpTerminateStatus = FMrpTerminateStatus; }

    public String getFMrpFreezeStatus() { return FMrpFreezeStatus; }
    public void setFMrpFreezeStatus(String FMrpFreezeStatus) { this.FMrpFreezeStatus = FMrpFreezeStatus; }

    public String getFMANUALCLOSE() { return FMANUALCLOSE; }
    public void setFMANUALCLOSE(String FMANUALCLOSE) { this.FMANUALCLOSE = FMANUALCLOSE; }

    public String getFMANUALROWCLOSE() { return FMANUALROWCLOSE; }
    public void setFMANUALROWCLOSE(String FMANUALROWCLOSE) { this.FMANUALROWCLOSE = FMANUALROWCLOSE; }

    public String getFISINIT() { return FISINIT; }
    public void setFISINIT(String FISINIT) { this.FISINIT = FISINIT; }

    public String getFBaseUnitID() { return FBaseUnitID; }
    public void setFBaseUnitID(String FBaseUnitID) { this.FBaseUnitID = FBaseUnitID; }

    public String getFOrderBaseQty() { return FOrderBaseQty; }
    public void setFOrderBaseQty(String FOrderBaseQty) { this.FOrderBaseQty = FOrderBaseQty; }

    public String getFBasePrice() { return FBasePrice; }
    public void setFBasePrice(String FBasePrice) { this.FBasePrice = FBasePrice; }

    public String getFBASEPRICE_LC() { return FBASEPRICE_LC; }
    public void setFBASEPRICE_LC(String FBASEPRICE_LC) { this.FBASEPRICE_LC = FBASEPRICE_LC; }

    public String getFDeliveryNoticeQty() { return FDeliveryNoticeQty; }
    public void setFDeliveryNoticeQty(String FDeliveryNoticeQty) { this.FDeliveryNoticeQty = FDeliveryNoticeQty; }

    public String getFDeliveryQty() { return FDeliveryQty; }
    public void setFDeliveryQty(String FDeliveryQty) { this.FDeliveryQty = FDeliveryQty; }

    public String getFReturnNoticeQty() { return FReturnNoticeQty; }
    public void setFReturnNoticeQty(String FReturnNoticeQty) { this.FReturnNoticeQty = FReturnNoticeQty; }

    public String getFReturnBaseQty() { return FReturnBaseQty; }
    public void setFReturnBaseQty(String FReturnBaseQty) { this.FReturnBaseQty = FReturnBaseQty; }

    public String getFReceiveQty() { return FReceiveQty; }
    public void setFReceiveQty(String FReceiveQty) { this.FReceiveQty = FReceiveQty; }

    public String getFBaseInvoiceQty() { return FBaseInvoiceQty; }
    public void setFBaseInvoiceQty(String FBaseInvoiceQty) { this.FBaseInvoiceQty = FBaseInvoiceQty; }

    public String getFPRESETBASENAME1() { return FPRESETBASENAME1; }
    public void setFPRESETBASENAME1(String FPRESETBASENAME1) { this.FPRESETBASENAME1 = FPRESETBASENAME1; }

    public String getFPRESETBASENAME2() { return FPRESETBASENAME2; }
    public void setFPRESETBASENAME2(String FPRESETBASENAME2) { this.FPRESETBASENAME2 = FPRESETBASENAME2; }

    public String getFPRESETASSISTANTNAME1() { return FPRESETASSISTANTNAME1; }
    public void setFPRESETASSISTANTNAME1(String FPRESETASSISTANTNAME1) { this.FPRESETASSISTANTNAME1 = FPRESETASSISTANTNAME1; }

    public String getFPRESETASSISTANTNAME2() { return FPRESETASSISTANTNAME2; }
    public void setFPRESETASSISTANTNAME2(String FPRESETASSISTANTNAME2) { this.FPRESETASSISTANTNAME2 = FPRESETASSISTANTNAME2; }

    public String getFADVANCENOTMATCHAMOUNT() { return FADVANCENOTMATCHAMOUNT; }
    public void setFADVANCENOTMATCHAMOUNT(String FADVANCENOTMATCHAMOUNT) { this.FADVANCENOTMATCHAMOUNT = FADVANCENOTMATCHAMOUNT; }

    public String getFADVANCENOTMATCHAMOUNT_LC() { return FADVANCENOTMATCHAMOUNT_LC; }
    public void setFADVANCENOTMATCHAMOUNT_LC(String FADVANCENOTMATCHAMOUNT_LC) { this.FADVANCENOTMATCHAMOUNT_LC = FADVANCENOTMATCHAMOUNT_LC; }

    public String getFALLMATCHAMOUNT() { return FALLMATCHAMOUNT; }
    public void setFALLMATCHAMOUNT(String FALLMATCHAMOUNT) { this.FALLMATCHAMOUNT = FALLMATCHAMOUNT; }

    public String getFALLMATCHAMOUNT_LC() { return FALLMATCHAMOUNT_LC; }
    public void setFALLMATCHAMOUNT_LC(String FALLMATCHAMOUNT_LC) { this.FALLMATCHAMOUNT_LC = FALLMATCHAMOUNT_LC; }

    public String getFORDERQTYTEMP() { return FORDERQTYTEMP; }
    public void setFORDERQTYTEMP(String FORDERQTYTEMP) { this.FORDERQTYTEMP = FORDERQTYTEMP; }

    public String getFORDERAMOUNTTEMP() { return FORDERAMOUNTTEMP; }
    public void setFORDERAMOUNTTEMP(String FORDERAMOUNTTEMP) { this.FORDERAMOUNTTEMP = FORDERAMOUNTTEMP; }
}
