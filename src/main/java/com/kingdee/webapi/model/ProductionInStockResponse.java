package com.kingdee.webapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * 生产入库月报表响应包装类
 *
 * @author Kingdee
 * @version 1.0.0
 */
@ApiModel(description = "生产入库月报表响应数据")
public class ProductionInStockResponse {

    @ApiModelProperty(value = "数据列表")
    private List<ProductionInStockData> list;

    public ProductionInStockResponse() {
    }

    public ProductionInStockResponse(List<ProductionInStockData> list) {
        this.list = list;
    }

    public List<ProductionInStockData> getList() {
        return list;
    }

    public void setList(List<ProductionInStockData> list) {
        this.list = list;
    }
}
