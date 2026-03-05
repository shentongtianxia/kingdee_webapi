package com.kingdee.webapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * 销售订单执行明细表响应对象
 */
@ApiModel(description = "销售订单执行明细表响应", value = "SaleOrderDetailResponse")
public class SaleOrderDetailResponse {

    @ApiModelProperty(value = "数据列表")
    private List<SaleOrderDetailData> list;

    public List<SaleOrderDetailData> getList() {
        return list;
    }

    public void setList(List<SaleOrderDetailData> list) {
        this.list = list;
    }
}
