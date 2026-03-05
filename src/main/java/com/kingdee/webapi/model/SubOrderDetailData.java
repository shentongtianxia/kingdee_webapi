package com.kingdee.webapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 委外订单执行明细表数据映射
 */
@ApiModel(description = "委外订单执行明细表数据", value = "SubOrderDetailData")
public class SubOrderDetailData {

    @ApiModelProperty(value = "供应商")
    private String FSUPPLIERID;

    @ApiModelProperty(value = "单据类型")
    private String FROBILLTYPEID;

    @ApiModelProperty(value = "委外订单编号")
    private String FROBILLNO;

    @ApiModelProperty(value = "委外订单行号")
    private String FROENTRYSEQ;

    @ApiModelProperty(value = "产品编码")
    private String FMATERIALID;

    @ApiModelProperty(value = "产品名称")
    private String FMATERIALNAME;

    @ApiModelProperty(value = "规格型号")
    private String FMATERIALMODEL;

    @ApiModelProperty(value = "产品类型")
    private String FPRODUCTTYPENAME;

    @ApiModelProperty(value = "辅助属性")
    private String FAUXPROP;

    @ApiModelProperty(value = "BOM版本")
    private String FBOMID;

    @ApiModelProperty(value = "计划跟踪号")
    private String FMTONO;

    @ApiModelProperty(value = "需求来源")
    private String FREQSRC;

    @ApiModelProperty(value = "需求单据")
    private String FSaleOrderNo;

    @ApiModelProperty(value = "需求单据行号")
    private String FSALEORDERENTRYSEQ;

    @ApiModelProperty(value = "入库货主")
    private String FInStockOwnerName;

    @ApiModelProperty(value = "计划开工日期")
    private String FPLANSTARTDATE;

    @ApiModelProperty(value = "计划完工日期")
    private String FPLANFINISHDATE;

    @ApiModelProperty(value = "下达日期")
    private String FCONVEYDATE;

    @ApiModelProperty(value = "完工日期")
    private String FFINISHDATE;

    @ApiModelProperty(value = "委外单位")
    private String FPRDUNITID;

    @ApiModelProperty(value = "计划生产数量")
    private String FPLANQTY;

    @ApiModelProperty(value = "采购执行数量")
    private String FPURQTY;

    @ApiModelProperty(value = "入库数量")
    private String FSTOCKINQTY;

    @ApiModelProperty(value = "未入库数量")
    private String FNOSTOCKINQTY;

    @ApiModelProperty(value = "计划完成百分比")
    private String FPLANFINISHPERCENT;

    @ApiModelProperty(value = "已领套数")
    private String FPICKEDQTY;

    @ApiModelProperty(value = "业务状态")
    private String FSTATUSNAME;

    @ApiModelProperty(value = "结案类型")
    private String FCLOSETYPENAME;

    // Getters and Setters

    public String getFSUPPLIERID() { return FSUPPLIERID; }
    public void setFSUPPLIERID(String FSUPPLIERID) { this.FSUPPLIERID = FSUPPLIERID; }

    public String getFROBILLTYPEID() { return FROBILLTYPEID; }
    public void setFROBILLTYPEID(String FROBILLTYPEID) { this.FROBILLTYPEID = FROBILLTYPEID; }

    public String getFROBILLNO() { return FROBILLNO; }
    public void setFROBILLNO(String FROBILLNO) { this.FROBILLNO = FROBILLNO; }

    public String getFROENTRYSEQ() { return FROENTRYSEQ; }
    public void setFROENTRYSEQ(String FROENTRYSEQ) { this.FROENTRYSEQ = FROENTRYSEQ; }

    public String getFMATERIALID() { return FMATERIALID; }
    public void setFMATERIALID(String FMATERIALID) { this.FMATERIALID = FMATERIALID; }

    public String getFMATERIALNAME() { return FMATERIALNAME; }
    public void setFMATERIALNAME(String FMATERIALNAME) { this.FMATERIALNAME = FMATERIALNAME; }

    public String getFMATERIALMODEL() { return FMATERIALMODEL; }
    public void setFMATERIALMODEL(String FMATERIALMODEL) { this.FMATERIALMODEL = FMATERIALMODEL; }

    public String getFPRODUCTTYPENAME() { return FPRODUCTTYPENAME; }
    public void setFPRODUCTTYPENAME(String FPRODUCTTYPENAME) { this.FPRODUCTTYPENAME = FPRODUCTTYPENAME; }

    public String getFAUXPROP() { return FAUXPROP; }
    public void setFAUXPROP(String FAUXPROP) { this.FAUXPROP = FAUXPROP; }

    public String getFBOMID() { return FBOMID; }
    public void setFBOMID(String FBOMID) { this.FBOMID = FBOMID; }

    public String getFMTONO() { return FMTONO; }
    public void setFMTONO(String FMTONO) { this.FMTONO = FMTONO; }

    public String getFREQSRC() { return FREQSRC; }
    public void setFREQSRC(String FREQSRC) { this.FREQSRC = FREQSRC; }

    public String getFSaleOrderNo() { return FSaleOrderNo; }
    public void setFSaleOrderNo(String FSaleOrderNo) { this.FSaleOrderNo = FSaleOrderNo; }

    public String getFSALEORDERENTRYSEQ() { return FSALEORDERENTRYSEQ; }
    public void setFSALEORDERENTRYSEQ(String FSALEORDERENTRYSEQ) { this.FSALEORDERENTRYSEQ = FSALEORDERENTRYSEQ; }

    public String getFInStockOwnerName() { return FInStockOwnerName; }
    public void setFInStockOwnerName(String FInStockOwnerName) { this.FInStockOwnerName = FInStockOwnerName; }

    public String getFPLANSTARTDATE() { return FPLANSTARTDATE; }
    public void setFPLANSTARTDATE(String FPLANSTARTDATE) { this.FPLANSTARTDATE = FPLANSTARTDATE; }

    public String getFPLANFINISHDATE() { return FPLANFINISHDATE; }
    public void setFPLANFINISHDATE(String FPLANFINISHDATE) { this.FPLANFINISHDATE = FPLANFINISHDATE; }

    public String getFCONVEYDATE() { return FCONVEYDATE; }
    public void setFCONVEYDATE(String FCONVEYDATE) { this.FCONVEYDATE = FCONVEYDATE; }

    public String getFFINISHDATE() { return FFINISHDATE; }
    public void setFFINISHDATE(String FFINISHDATE) { this.FFINISHDATE = FFINISHDATE; }

    public String getFPRDUNITID() { return FPRDUNITID; }
    public void setFPRDUNITID(String FPRDUNITID) { this.FPRDUNITID = FPRDUNITID; }

    public String getFPLANQTY() { return FPLANQTY; }
    public void setFPLANQTY(String FPLANQTY) { this.FPLANQTY = FPLANQTY; }

    public String getFPURQTY() { return FPURQTY; }
    public void setFPURQTY(String FPURQTY) { this.FPURQTY = FPURQTY; }

    public String getFSTOCKINQTY() { return FSTOCKINQTY; }
    public void setFSTOCKINQTY(String FSTOCKINQTY) { this.FSTOCKINQTY = FSTOCKINQTY; }

    public String getFNOSTOCKINQTY() { return FNOSTOCKINQTY; }
    public void setFNOSTOCKINQTY(String FNOSTOCKINQTY) { this.FNOSTOCKINQTY = FNOSTOCKINQTY; }

    public String getFPLANFINISHPERCENT() { return FPLANFINISHPERCENT; }
    public void setFPLANFINISHPERCENT(String FPLANFINISHPERCENT) { this.FPLANFINISHPERCENT = FPLANFINISHPERCENT; }

    public String getFPICKEDQTY() { return FPICKEDQTY; }
    public void setFPICKEDQTY(String FPICKEDQTY) { this.FPICKEDQTY = FPICKEDQTY; }

    public String getFSTATUSNAME() { return FSTATUSNAME; }
    public void setFSTATUSNAME(String FSTATUSNAME) { this.FSTATUSNAME = FSTATUSNAME; }

    public String getFCLOSETYPENAME() { return FCLOSETYPENAME; }
    public void setFCLOSETYPENAME(String FCLOSETYPENAME) { this.FCLOSETYPENAME = FCLOSETYPENAME; }
}
