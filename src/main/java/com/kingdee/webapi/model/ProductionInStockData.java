package com.kingdee.webapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 生产入库月报表响应数据映射
 */
@Data
@ApiModel(description = "生产入库月报表数据项")
public class ProductionInStockData {

    @JsonProperty("月份/期间")
    @ApiModelProperty(value = "月份/期间")
    private String FPickMtrlMonth;

    @JsonProperty("生产车间")
    @ApiModelProperty(value = "生产车间")
    private String FWorkShopId;

    @JsonProperty("物料组")
    @ApiModelProperty(value = "物料组")
    private String FProdTypeId;

    @JsonProperty("生产组织")
    @ApiModelProperty(value = "生产组织")
    private String FPrdOrgId;

    @JsonProperty("产品编码")
    @ApiModelProperty(value = "产品编码")
    private String FMATERIALID;

    @JsonProperty("产品名称")
    @ApiModelProperty(value = "产品名称")
    private String FProdName;

    @JsonProperty("规格型号")
    @ApiModelProperty(value = "规格型号")
    private String FSpecModel;

    @JsonProperty("单位")
    @ApiModelProperty(value = "单位")
    private String FUnitId;

    @JsonProperty("合格品入库数量")
    @ApiModelProperty(value = "合格品入库数量")
    private String FPASSINSTOCKQTY;

    @JsonProperty("不合格品入库数量")
    @ApiModelProperty(value = "不合格品入库数量")
    private String FFAILINSTOCKQTY;

    @JsonProperty("报废品入库数量")
    @ApiModelProperty(value = "报废品入库数量")
    private String FSCRAPINSTOCKQTY;

    @JsonProperty("返工品入库数量")
    @ApiModelProperty(value = "返工品入库数量")
    private String FRETURNINSTOCKQTY;

    @JsonProperty("退库数量")
    @ApiModelProperty(value = "退库数量")
    private String FOUTSTOCKQTY;

    @JsonProperty("实际入库数量")
    @ApiModelProperty(value = "实际入库数量")
    private String FACTUALINSTOCKQTY;

    @JsonProperty("库存辅单位")
    @ApiModelProperty(value = "库存辅单位")
    private String FAUXUNITID;

    @JsonProperty("合格品入库数量（辅）")
    @ApiModelProperty(value = "合格品入库数量（辅）")
    private String FSECPASSINSTOCKQTY;

    @JsonProperty("不合格品入库数量（辅）")
    @ApiModelProperty(value = "不合格品入库数量（辅）")
    private String FSECFAILINSTOCKQTY;

    @JsonProperty("报废品入库数量（辅）")
    @ApiModelProperty(value = "报废品入库数量（辅）")
    private String FSECSCRAPINSTOCKQTY;

    @JsonProperty("返工品入库数量（辅）")
    @ApiModelProperty(value = "返工品入库数量（辅）")
    private String FSECRETURNINSTOCKQTY;

    @JsonProperty("退库数量（辅）")
    @ApiModelProperty(value = "退库数量（辅）")
    private String FSECOUTSTOCKQTY;

    @JsonProperty("实际入库数量（辅）")
    @ApiModelProperty(value = "实际入库数量（辅）")
    private String FSECACTUALINSTOCKQTY;
}
