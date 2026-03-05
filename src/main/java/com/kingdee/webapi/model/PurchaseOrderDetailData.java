package com.kingdee.webapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 采购订单执行明细表数据映射
 */
@ApiModel(description = "采购订单执行明细表数据", value = "PurchaseOrderDetailData")
public class PurchaseOrderDetailData {

    @ApiModelProperty(value = "采购组织")
    private String FPurchaseOrgId;

    @ApiModelProperty(value = "采购组")
    private String FPURCHASERGROUPID;

    @ApiModelProperty(value = "采购部门")
    private String FPURCHASEDEPTID;

    @ApiModelProperty(value = "采购员")
    private String FPURCHASERID;

    @ApiModelProperty(value = "订单编号")
    private String FBillNo;

    @ApiModelProperty(value = "日期")
    private String FDate;

    @ApiModelProperty(value = "供应商编码")
    private String FSUPPLIERID;

    @ApiModelProperty(value = "供应商名称")
    private String FSUPPLIERNAME;

    @ApiModelProperty(value = "供应商分组")
    private String FSupplierGroup;

    @ApiModelProperty(value = "订单行号")
    private String FBillSeqId;

    @ApiModelProperty(value = "物料编码")
    private String FMATERIALID;

    @ApiModelProperty(value = "物料名称")
    private String FMATERIALNAME;

    @ApiModelProperty(value = "计划跟踪号")
    private String FMTONO;

    @ApiModelProperty(value = "规格型号")
    private String FMaterialModel;

    @ApiModelProperty(value = "存货类别")
    private String FCategoryID;

    @ApiModelProperty(value = "物料属性")
    private String FErpClsID;

    @ApiModelProperty(value = "物料分组")
    private String FMaterialGroup;

    @ApiModelProperty(value = "辅助属性")
    private String FFLEX;

    @ApiModelProperty(value = "源单编号")
    private String FSrcBillNo;

    @ApiModelProperty(value = "源单类型")
    private String FSRCBILLTYPENAME;

    @ApiModelProperty(value = "交货日期")
    private String FDELIVERYDATE;

    @ApiModelProperty(value = "需求组织")
    private String FREQUIREORGID;

    @ApiModelProperty(value = "需求部门")
    private String FREQUIREDEPTID;

    @ApiModelProperty(value = "采购单位")
    private String FUnitId;

    @ApiModelProperty(value = "结算币别")
    private String FCurrencyId;

    @ApiModelProperty(value = "本位币")
    private String FCURRENCYID_LC;

    @ApiModelProperty(value = "计价单位")
    private String FPRICEUNITID;

    @ApiModelProperty(value = "订货数量")
    private String FOrderQty;

    @ApiModelProperty(value = "订货数量(计价单位)")
    private String FORDERPRICEQTY;

    @ApiModelProperty(value = "含税单价")
    private String FTAXPRICE;

    @ApiModelProperty(value = "含税单价(本位币)")
    private String FTAXPRICE_LC;

    @ApiModelProperty(value = "价税合计")
    private String FOrderAmount;

    @ApiModelProperty(value = "价税合计(本位币)")
    private String FORDERAMOUNT_LC;

    @ApiModelProperty(value = "收料单号")
    private String FReceiveNumber;

    @ApiModelProperty(value = "收料日期")
    private String FReceiveDate;

    @ApiModelProperty(value = "收料单行号")
    private String FRECEIVESEQID;

    @ApiModelProperty(value = "收料数量")
    private String FReceiveQty;

    @ApiModelProperty(value = "收料数量(计价单位)")
    private String FRECEIVEPRICEQTY;

    @ApiModelProperty(value = "收料金额")
    private String FReceiveAmount;

    @ApiModelProperty(value = "收料金额(本位币)")
    private String FRECEIVEAMOUNT_LC;

    @ApiModelProperty(value = "入库单号")
    private String FEnterNumber;

    @ApiModelProperty(value = "入库日期")
    private String FEnterDate;

    @ApiModelProperty(value = "入库单行号")
    private String FINSTOCKSEQID;

    @ApiModelProperty(value = "入库数量")
    private String FImportQty;

    @ApiModelProperty(value = "入库数量(计价单位)")
    private String FIMPORTPRICEQTY;

    @ApiModelProperty(value = "入库金额")
    private String FImportAmount;

    @ApiModelProperty(value = "入库金额(本位币)")
    private String FIMPORTAMOUNT_LC;

    @ApiModelProperty(value = "入库不含税金额")
    private String FIMPORTNOTAXAMOUNT;

    @ApiModelProperty(value = "入库不含税金额(本位币)")
    private String FIMPORTNOTAXAMOUNT_LC;

    @ApiModelProperty(value = "退料单号")
    private String FReturnNumber;

    @ApiModelProperty(value = "退料日期")
    private String FReturnDate;

    @ApiModelProperty(value = "退料单行号")
    private String FRETURNSEQID;

    @ApiModelProperty(value = "退料数量")
    private String FReturnQty;

    @ApiModelProperty(value = "退料数量(计价单位)")
    private String FRETURNPRICEQTY;

    @ApiModelProperty(value = "退料金额")
    private String FReturnAmount;

    @ApiModelProperty(value = "退料金额(本位币)")
    private String FRETURNAMOUNT_LC;

    @ApiModelProperty(value = "应付单号")
    private String FPAYNUMBER;

    @ApiModelProperty(value = "应付日期")
    private String FPAYDATE;

    @ApiModelProperty(value = "应付单行号")
    private String FPAYSEQID;

    @ApiModelProperty(value = "立账类型")
    private String FSETACCOUNTTYPE;

    @ApiModelProperty(value = "应付数量")
    private String FPAYQTY;

    @ApiModelProperty(value = "应付数量(计价单位)")
    private String FPAYPRICEQTY;

    @ApiModelProperty(value = "应付金额")
    private String FPAYAMOUNT;

    @ApiModelProperty(value = "应付不含税金额")
    private String FPAYEXTAXAMOUNT;

    @ApiModelProperty(value = "应付金额(本位币)")
    private String FPAYAMOUNT_LC;

    @ApiModelProperty(value = "调整金额")
    private String FPAYWOFFAMOUNT;

    @ApiModelProperty(value = "应付调整金额(本位币)")
    private String FPAYWOFFAMOUNT_LC;

    @ApiModelProperty(value = "开票单号")
    private String FINVOICENUMBER;

    @ApiModelProperty(value = "开票数量")
    private String FINVOICEQTY;

    @ApiModelProperty(value = "开票金额")
    private String FINVOICEAMOUNT;

    @ApiModelProperty(value = "开票金额(本位币)")
    private String FINVOICEAMOUNT_LC;

    @ApiModelProperty(value = "预付单号")
    private String FRECPAYBILLNUMBER;

    @ApiModelProperty(value = "预付金额")
    private String FRECPAYBILLAMOUNT;

    @ApiModelProperty(value = "预付金额(本位币)")
    private String FRECPAYBILLAMOUNT_LC;

    @ApiModelProperty(value = "行业务终止")
    private String FMRPTERMINATESTATUS;

    @ApiModelProperty(value = "付款单号")
    private String FPAYBILLNUMBER;

    @ApiModelProperty(value = "付款日期")
    private String FPAYBILLDATE;

    @ApiModelProperty(value = "已结算金额")
    private String FPAYBILLAMOUNT;

    @ApiModelProperty(value = "已结算金额(本位币)")
    private String FPAYBILLAMOUNT_LC;

    @ApiModelProperty(value = "关联保证金")
    private String FRELATEDEPOSIT;

    @ApiModelProperty(value = "行业务关闭")
    private String FMRPCLOSESTATUS;

    @ApiModelProperty(value = "关联申请保证金")
    private String FAPPLYRELATEDEPOSIT;

    @ApiModelProperty(value = "关联退款保证金")
    private String FRELATEREFUNDDEPOSIT;

    @ApiModelProperty(value = "整单关闭")
    private String FCLOSESTATUS;

    @ApiModelProperty(value = "行业务冻结")
    private String FMRPFREEZESTATUS;

    @ApiModelProperty(value = "发票号")
    private String FINVOICENO;

    @ApiModelProperty(value = "发票日期")
    private String FINVOICEDATE;

    @ApiModelProperty(value = "结算调整金额")
    private String FSETADJAMOUNT;

    @ApiModelProperty(value = "付款核销金额")
    private String FPAYWRITEOFFAMOUNT;

    @ApiModelProperty(value = "特殊冲销金额")
    private String FSPEWOFFAMOUNT;

    @ApiModelProperty(value = "已付款金额(接口)")
    private String FINTEPAYAMOUNT;

    @ApiModelProperty(value = "结算调整金(本位币)")
    private String FSETADJAMOUNT_LC;

    @ApiModelProperty(value = "付款核销金额(本位币)")
    private String FPAYWRITEOFFAMOUNT_LC;

    @ApiModelProperty(value = "特殊冲销金额(本位币)")
    private String FSPEWOFFAMOUNT_LC;

    @ApiModelProperty(value = "已付款金额(接口)(本位币)")
    private String FINTEPAYAMOUNT_LC;

    @ApiModelProperty(value = "先开票单号")
    private String FPREINVOCEBILLNO;

    @ApiModelProperty(value = "先开票发票日期")
    private String FPREINVOICEDATE;

    @ApiModelProperty(value = "先开票数量")
    private String FPREINVOICEQTY;

    @ApiModelProperty(value = "先开票金额")
    private String FPREINVOICEAMOUNT;

    @ApiModelProperty(value = "先开票发票号")
    private String FPREINVOICENO;

    @ApiModelProperty(value = "先开票金额(本位币)")
    private String FPREINVOICEAMOUNT_LC;

    // Getters and Setters

    public String getFPurchaseOrgId() { return FPurchaseOrgId; }
    public void setFPurchaseOrgId(String FPurchaseOrgId) { this.FPurchaseOrgId = FPurchaseOrgId; }

    public String getFPURCHASERGROUPID() { return FPURCHASERGROUPID; }
    public void setFPURCHASERGROUPID(String FPURCHASERGROUPID) { this.FPURCHASERGROUPID = FPURCHASERGROUPID; }

    public String getFPURCHASEDEPTID() { return FPURCHASEDEPTID; }
    public void setFPURCHASEDEPTID(String FPURCHASEDEPTID) { this.FPURCHASEDEPTID = FPURCHASEDEPTID; }

    public String getFPURCHASERID() { return FPURCHASERID; }
    public void setFPURCHASERID(String FPURCHASERID) { this.FPURCHASERID = FPURCHASERID; }

    public String getFBillNo() { return FBillNo; }
    public void setFBillNo(String FBillNo) { this.FBillNo = FBillNo; }

    public String getFDate() { return FDate; }
    public void setFDate(String FDate) { this.FDate = FDate; }

    public String getFSUPPLIERID() { return FSUPPLIERID; }
    public void setFSUPPLIERID(String FSUPPLIERID) { this.FSUPPLIERID = FSUPPLIERID; }

    public String getFSUPPLIERNAME() { return FSUPPLIERNAME; }
    public void setFSUPPLIERNAME(String FSUPPLIERNAME) { this.FSUPPLIERNAME = FSUPPLIERNAME; }

    public String getFSupplierGroup() { return FSupplierGroup; }
    public void setFSupplierGroup(String FSupplierGroup) { this.FSupplierGroup = FSupplierGroup; }

    public String getFBillSeqId() { return FBillSeqId; }
    public void setFBillSeqId(String FBillSeqId) { this.FBillSeqId = FBillSeqId; }

    public String getFMATERIALID() { return FMATERIALID; }
    public void setFMATERIALID(String FMATERIALID) { this.FMATERIALID = FMATERIALID; }

    public String getFMATERIALNAME() { return FMATERIALNAME; }
    public void setFMATERIALNAME(String FMATERIALNAME) { this.FMATERIALNAME = FMATERIALNAME; }

    public String getFMTONO() { return FMTONO; }
    public void setFMTONO(String FMTONO) { this.FMTONO = FMTONO; }

    public String getFMaterialModel() { return FMaterialModel; }
    public void setFMaterialModel(String FMaterialModel) { this.FMaterialModel = FMaterialModel; }

    public String getFCategoryID() { return FCategoryID; }
    public void setFCategoryID(String FCategoryID) { this.FCategoryID = FCategoryID; }

    public String getFErpClsID() { return FErpClsID; }
    public void setFErpClsID(String FErpClsID) { this.FErpClsID = FErpClsID; }

    public String getFMaterialGroup() { return FMaterialGroup; }
    public void setFMaterialGroup(String FMaterialGroup) { this.FMaterialGroup = FMaterialGroup; }

    public String getFFLEX() { return FFLEX; }
    public void setFFLEX(String FFLEX) { this.FFLEX = FFLEX; }

    public String getFSrcBillNo() { return FSrcBillNo; }
    public void setFSrcBillNo(String FSrcBillNo) { this.FSrcBillNo = FSrcBillNo; }

    public String getFSRCBILLTYPENAME() { return FSRCBILLTYPENAME; }
    public void setFSRCBILLTYPENAME(String FSRCBILLTYPENAME) { this.FSRCBILLTYPENAME = FSRCBILLTYPENAME; }

    public String getFDELIVERYDATE() { return FDELIVERYDATE; }
    public void setFDELIVERYDATE(String FDELIVERYDATE) { this.FDELIVERYDATE = FDELIVERYDATE; }

    public String getFREQUIREORGID() { return FREQUIREORGID; }
    public void setFREQUIREORGID(String FREQUIREORGID) { this.FREQUIREORGID = FREQUIREORGID; }

    public String getFREQUIREDEPTID() { return FREQUIREDEPTID; }
    public void setFREQUIREDEPTID(String FREQUIREDEPTID) { this.FREQUIREDEPTID = FREQUIREDEPTID; }

    public String getFUnitId() { return FUnitId; }
    public void setFUnitId(String FUnitId) { this.FUnitId = FUnitId; }

    public String getFCurrencyId() { return FCurrencyId; }
    public void setFCurrencyId(String FCurrencyId) { this.FCurrencyId = FCurrencyId; }

    public String getFCURRENCYID_LC() { return FCURRENCYID_LC; }
    public void setFCURRENCYID_LC(String FCURRENCYID_LC) { this.FCURRENCYID_LC = FCURRENCYID_LC; }

    public String getFPRICEUNITID() { return FPRICEUNITID; }
    public void setFPRICEUNITID(String FPRICEUNITID) { this.FPRICEUNITID = FPRICEUNITID; }

    public String getFOrderQty() { return FOrderQty; }
    public void setFOrderQty(String FOrderQty) { this.FOrderQty = FOrderQty; }

    public String getFORDERPRICEQTY() { return FORDERPRICEQTY; }
    public void setFORDERPRICEQTY(String FORDERPRICEQTY) { this.FORDERPRICEQTY = FORDERPRICEQTY; }

    public String getFTAXPRICE() { return FTAXPRICE; }
    public void setFTAXPRICE(String FTAXPRICE) { this.FTAXPRICE = FTAXPRICE; }

    public String getFTAXPRICE_LC() { return FTAXPRICE_LC; }
    public void setFTAXPRICE_LC(String FTAXPRICE_LC) { this.FTAXPRICE_LC = FTAXPRICE_LC; }

    public String getFOrderAmount() { return FOrderAmount; }
    public void setFOrderAmount(String FOrderAmount) { this.FOrderAmount = FOrderAmount; }

    public String getFORDERAMOUNT_LC() { return FORDERAMOUNT_LC; }
    public void setFORDERAMOUNT_LC(String FORDERAMOUNT_LC) { this.FORDERAMOUNT_LC = FORDERAMOUNT_LC; }

    public String getFReceiveNumber() { return FReceiveNumber; }
    public void setFReceiveNumber(String FReceiveNumber) { this.FReceiveNumber = FReceiveNumber; }

    public String getFReceiveDate() { return FReceiveDate; }
    public void setFReceiveDate(String FReceiveDate) { this.FReceiveDate = FReceiveDate; }

    public String getFRECEIVESEQID() { return FRECEIVESEQID; }
    public void setFRECEIVESEQID(String FRECEIVESEQID) { this.FRECEIVESEQID = FRECEIVESEQID; }

    public String getFReceiveQty() { return FReceiveQty; }
    public void setFReceiveQty(String FReceiveQty) { this.FReceiveQty = FReceiveQty; }

    public String getFRECEIVEPRICEQTY() { return FRECEIVEPRICEQTY; }
    public void setFRECEIVEPRICEQTY(String FRECEIVEPRICEQTY) { this.FRECEIVEPRICEQTY = FRECEIVEPRICEQTY; }

    public String getFReceiveAmount() { return FReceiveAmount; }
    public void setFReceiveAmount(String FReceiveAmount) { this.FReceiveAmount = FReceiveAmount; }

    public String getFRECEIVEAMOUNT_LC() { return FRECEIVEAMOUNT_LC; }
    public void setFRECEIVEAMOUNT_LC(String FRECEIVEAMOUNT_LC) { this.FRECEIVEAMOUNT_LC = FRECEIVEAMOUNT_LC; }

    public String getFEnterNumber() { return FEnterNumber; }
    public void setFEnterNumber(String FEnterNumber) { this.FEnterNumber = FEnterNumber; }

    public String getFEnterDate() { return FEnterDate; }
    public void setFEnterDate(String FEnterDate) { this.FEnterDate = FEnterDate; }

    public String getFINSTOCKSEQID() { return FINSTOCKSEQID; }
    public void setFINSTOCKSEQID(String FINSTOCKSEQID) { this.FINSTOCKSEQID = FINSTOCKSEQID; }

    public String getFImportQty() { return FImportQty; }
    public void setFImportQty(String FImportQty) { this.FImportQty = FImportQty; }

    public String getFIMPORTPRICEQTY() { return FIMPORTPRICEQTY; }
    public void setFIMPORTPRICEQTY(String FIMPORTPRICEQTY) { this.FIMPORTPRICEQTY = FIMPORTPRICEQTY; }

    public String getFImportAmount() { return FImportAmount; }
    public void setFImportAmount(String FImportAmount) { this.FImportAmount = FImportAmount; }

    public String getFIMPORTAMOUNT_LC() { return FIMPORTAMOUNT_LC; }
    public void setFIMPORTAMOUNT_LC(String FIMPORTAMOUNT_LC) { this.FIMPORTAMOUNT_LC = FIMPORTAMOUNT_LC; }

    public String getFIMPORTNOTAXAMOUNT() { return FIMPORTNOTAXAMOUNT; }
    public void setFIMPORTNOTAXAMOUNT(String FIMPORTNOTAXAMOUNT) { this.FIMPORTNOTAXAMOUNT = FIMPORTNOTAXAMOUNT; }

    public String getFIMPORTNOTAXAMOUNT_LC() { return FIMPORTNOTAXAMOUNT_LC; }
    public void setFIMPORTNOTAXAMOUNT_LC(String FIMPORTNOTAXAMOUNT_LC) { this.FIMPORTNOTAXAMOUNT_LC = FIMPORTNOTAXAMOUNT_LC; }

    public String getFReturnNumber() { return FReturnNumber; }
    public void setFReturnNumber(String FReturnNumber) { this.FReturnNumber = FReturnNumber; }

    public String getFReturnDate() { return FReturnDate; }
    public void setFReturnDate(String FReturnDate) { this.FReturnDate = FReturnDate; }

    public String getFRETURNSEQID() { return FRETURNSEQID; }
    public void setFRETURNSEQID(String FRETURNSEQID) { this.FRETURNSEQID = FRETURNSEQID; }

    public String getFReturnQty() { return FReturnQty; }
    public void setFReturnQty(String FReturnQty) { this.FReturnQty = FReturnQty; }

    public String getFRETURNPRICEQTY() { return FRETURNPRICEQTY; }
    public void setFRETURNPRICEQTY(String FRETURNPRICEQTY) { this.FRETURNPRICEQTY = FRETURNPRICEQTY; }

    public String getFReturnAmount() { return FReturnAmount; }
    public void setFReturnAmount(String FReturnAmount) { this.FReturnAmount = FReturnAmount; }

    public String getFRETURNAMOUNT_LC() { return FRETURNAMOUNT_LC; }
    public void setFRETURNAMOUNT_LC(String FRETURNAMOUNT_LC) { this.FRETURNAMOUNT_LC = FRETURNAMOUNT_LC; }

    public String getFPAYNUMBER() { return FPAYNUMBER; }
    public void setFPAYNUMBER(String FPAYNUMBER) { this.FPAYNUMBER = FPAYNUMBER; }

    public String getFPAYDATE() { return FPAYDATE; }
    public void setFPAYDATE(String FPAYDATE) { this.FPAYDATE = FPAYDATE; }

    public String getFPAYSEQID() { return FPAYSEQID; }
    public void setFPAYSEQID(String FPAYSEQID) { this.FPAYSEQID = FPAYSEQID; }

    public String getFSETACCOUNTTYPE() { return FSETACCOUNTTYPE; }
    public void setFSETACCOUNTTYPE(String FSETACCOUNTTYPE) { this.FSETACCOUNTTYPE = FSETACCOUNTTYPE; }

    public String getFPAYQTY() { return FPAYQTY; }
    public void setFPAYQTY(String FPAYQTY) { this.FPAYQTY = FPAYQTY; }

    public String getFPAYPRICEQTY() { return FPAYPRICEQTY; }
    public void setFPAYPRICEQTY(String FPAYPRICEQTY) { this.FPAYPRICEQTY = FPAYPRICEQTY; }

    public String getFPAYAMOUNT() { return FPAYAMOUNT; }
    public void setFPAYAMOUNT(String FPAYAMOUNT) { this.FPAYAMOUNT = FPAYAMOUNT; }

    public String getFPAYEXTAXAMOUNT() { return FPAYEXTAXAMOUNT; }
    public void setFPAYEXTAXAMOUNT(String FPAYEXTAXAMOUNT) { this.FPAYEXTAXAMOUNT = FPAYEXTAXAMOUNT; }

    public String getFPAYAMOUNT_LC() { return FPAYAMOUNT_LC; }
    public void setFPAYAMOUNT_LC(String FPAYAMOUNT_LC) { this.FPAYAMOUNT_LC = FPAYAMOUNT_LC; }

    public String getFPAYWOFFAMOUNT() { return FPAYWOFFAMOUNT; }
    public void setFPAYWOFFAMOUNT(String FPAYWOFFAMOUNT) { this.FPAYWOFFAMOUNT = FPAYWOFFAMOUNT; }

    public String getFPAYWOFFAMOUNT_LC() { return FPAYWOFFAMOUNT_LC; }
    public void setFPAYWOFFAMOUNT_LC(String FPAYWOFFAMOUNT_LC) { this.FPAYWOFFAMOUNT_LC = FPAYWOFFAMOUNT_LC; }

    public String getFINVOICENUMBER() { return FINVOICENUMBER; }
    public void setFINVOICENUMBER(String FINVOICENUMBER) { this.FINVOICENUMBER = FINVOICENUMBER; }

    public String getFINVOICEQTY() { return FINVOICEQTY; }
    public void setFINVOICEQTY(String FINVOICEQTY) { this.FINVOICEQTY = FINVOICEQTY; }

    public String getFINVOICEAMOUNT() { return FINVOICEAMOUNT; }
    public void setFINVOICEAMOUNT(String FINVOICEAMOUNT) { this.FINVOICEAMOUNT = FINVOICEAMOUNT; }

    public String getFINVOICEAMOUNT_LC() { return FINVOICEAMOUNT_LC; }
    public void setFINVOICEAMOUNT_LC(String FINVOICEAMOUNT_LC) { this.FINVOICEAMOUNT_LC = FINVOICEAMOUNT_LC; }

    public String getFRECPAYBILLNUMBER() { return FRECPAYBILLNUMBER; }
    public void setFRECPAYBILLNUMBER(String FRECPAYBILLNUMBER) { this.FRECPAYBILLNUMBER = FRECPAYBILLNUMBER; }

    public String getFRECPAYBILLAMOUNT() { return FRECPAYBILLAMOUNT; }
    public void setFRECPAYBILLAMOUNT(String FRECPAYBILLAMOUNT) { this.FRECPAYBILLAMOUNT = FRECPAYBILLAMOUNT; }

    public String getFRECPAYBILLAMOUNT_LC() { return FRECPAYBILLAMOUNT_LC; }
    public void setFRECPAYBILLAMOUNT_LC(String FRECPAYBILLAMOUNT_LC) { this.FRECPAYBILLAMOUNT_LC = FRECPAYBILLAMOUNT_LC; }

    public String getFMRPTERMINATESTATUS() { return FMRPTERMINATESTATUS; }
    public void setFMRPTERMINATESTATUS(String FMRPTERMINATESTATUS) { this.FMRPTERMINATESTATUS = FMRPTERMINATESTATUS; }

    public String getFPAYBILLNUMBER() { return FPAYBILLNUMBER; }
    public void setFPAYBILLNUMBER(String FPAYBILLNUMBER) { this.FPAYBILLNUMBER = FPAYBILLNUMBER; }

    public String getFPAYBILLDATE() { return FPAYBILLDATE; }
    public void setFPAYBILLDATE(String FPAYBILLDATE) { this.FPAYBILLDATE = FPAYBILLDATE; }

    public String getFPAYBILLAMOUNT() { return FPAYBILLAMOUNT; }
    public void setFPAYBILLAMOUNT(String FPAYBILLAMOUNT) { this.FPAYBILLAMOUNT = FPAYBILLAMOUNT; }

    public String getFPAYBILLAMOUNT_LC() { return FPAYBILLAMOUNT_LC; }
    public void setFPAYBILLAMOUNT_LC(String FPAYBILLAMOUNT_LC) { this.FPAYBILLAMOUNT_LC = FPAYBILLAMOUNT_LC; }

    public String getFRELATEDEPOSIT() { return FRELATEDEPOSIT; }
    public void setFRELATEDEPOSIT(String FRELATEDEPOSIT) { this.FRELATEDEPOSIT = FRELATEDEPOSIT; }

    public String getFMRPCLOSESTATUS() { return FMRPCLOSESTATUS; }
    public void setFMRPCLOSESTATUS(String FMRPCLOSESTATUS) { this.FMRPCLOSESTATUS = FMRPCLOSESTATUS; }

    public String getFAPPLYRELATEDEPOSIT() { return FAPPLYRELATEDEPOSIT; }
    public void setFAPPLYRELATEDEPOSIT(String FAPPLYRELATEDEPOSIT) { this.FAPPLYRELATEDEPOSIT = FAPPLYRELATEDEPOSIT; }

    public String getFRELATEREFUNDDEPOSIT() { return FRELATEREFUNDDEPOSIT; }
    public void setFRELATEREFUNDDEPOSIT(String FRELATEREFUNDDEPOSIT) { this.FRELATEREFUNDDEPOSIT = FRELATEREFUNDDEPOSIT; }

    public String getFCLOSESTATUS() { return FCLOSESTATUS; }
    public void setFCLOSESTATUS(String FCLOSESTATUS) { this.FCLOSESTATUS = FCLOSESTATUS; }

    public String getFMRPFREEZESTATUS() { return FMRPFREEZESTATUS; }
    public void setFMRPFREEZESTATUS(String FMRPFREEZESTATUS) { this.FMRPFREEZESTATUS = FMRPFREEZESTATUS; }

    public String getFINVOICENO() { return FINVOICENO; }
    public void setFINVOICENO(String FINVOICENO) { this.FINVOICENO = FINVOICENO; }

    public String getFINVOICEDATE() { return FINVOICEDATE; }
    public void setFINVOICEDATE(String FINVOICEDATE) { this.FINVOICEDATE = FINVOICEDATE; }

    public String getFSETADJAMOUNT() { return FSETADJAMOUNT; }
    public void setFSETADJAMOUNT(String FSETADJAMOUNT) { this.FSETADJAMOUNT = FSETADJAMOUNT; }

    public String getFPAYWRITEOFFAMOUNT() { return FPAYWRITEOFFAMOUNT; }
    public void setFPAYWRITEOFFAMOUNT(String FPAYWRITEOFFAMOUNT) { this.FPAYWRITEOFFAMOUNT = FPAYWRITEOFFAMOUNT; }

    public String getFSPEWOFFAMOUNT() { return FSPEWOFFAMOUNT; }
    public void setFSPEWOFFAMOUNT(String FSPEWOFFAMOUNT) { this.FSPEWOFFAMOUNT = FSPEWOFFAMOUNT; }

    public String getFINTEPAYAMOUNT() { return FINTEPAYAMOUNT; }
    public void setFINTEPAYAMOUNT(String FINTEPAYAMOUNT) { this.FINTEPAYAMOUNT = FINTEPAYAMOUNT; }

    public String getFSETADJAMOUNT_LC() { return FSETADJAMOUNT_LC; }
    public void setFSETADJAMOUNT_LC(String FSETADJAMOUNT_LC) { this.FSETADJAMOUNT_LC = FSETADJAMOUNT_LC; }

    public String getFPAYWRITEOFFAMOUNT_LC() { return FPAYWRITEOFFAMOUNT_LC; }
    public void setFPAYWRITEOFFAMOUNT_LC(String FPAYWRITEOFFAMOUNT_LC) { this.FPAYWRITEOFFAMOUNT_LC = FPAYWRITEOFFAMOUNT_LC; }

    public String getFSPEWOFFAMOUNT_LC() { return FSPEWOFFAMOUNT_LC; }
    public void setFSPEWOFFAMOUNT_LC(String FSPEWOFFAMOUNT_LC) { this.FSPEWOFFAMOUNT_LC = FSPEWOFFAMOUNT_LC; }

    public String getFINTEPAYAMOUNT_LC() { return FINTEPAYAMOUNT_LC; }
    public void setFINTEPAYAMOUNT_LC(String FINTEPAYAMOUNT_LC) { this.FINTEPAYAMOUNT_LC = FINTEPAYAMOUNT_LC; }

    public String getFPREINVOCEBILLNO() { return FPREINVOCEBILLNO; }
    public void setFPREINVOCEBILLNO(String FPREINVOCEBILLNO) { this.FPREINVOCEBILLNO = FPREINVOCEBILLNO; }

    public String getFPREINVOICEDATE() { return FPREINVOICEDATE; }
    public void setFPREINVOICEDATE(String FPREINVOICEDATE) { this.FPREINVOICEDATE = FPREINVOICEDATE; }

    public String getFPREINVOICEQTY() { return FPREINVOICEQTY; }
    public void setFPREINVOICEQTY(String FPREINVOICEQTY) { this.FPREINVOICEQTY = FPREINVOICEQTY; }

    public String getFPREINVOICEAMOUNT() { return FPREINVOICEAMOUNT; }
    public void setFPREINVOICEAMOUNT(String FPREINVOICEAMOUNT) { this.FPREINVOICEAMOUNT = FPREINVOICEAMOUNT; }

    public String getFPREINVOICENO() { return FPREINVOICENO; }
    public void setFPREINVOICENO(String FPREINVOICENO) { this.FPREINVOICENO = FPREINVOICENO; }

    public String getFPREINVOICEAMOUNT_LC() { return FPREINVOICEAMOUNT_LC; }
    public void setFPREINVOICEAMOUNT_LC(String FPREINVOICEAMOUNT_LC) { this.FPREINVOICEAMOUNT_LC = FPREINVOICEAMOUNT_LC; }
}
