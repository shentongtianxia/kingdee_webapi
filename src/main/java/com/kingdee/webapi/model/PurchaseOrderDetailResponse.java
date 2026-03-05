package com.kingdee.webapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * 采购订单执行明细表响应对象
 */
@ApiModel(description = "采购订单执行明细表响应", value = "PurchaseOrderDetailResponse")
public class PurchaseOrderDetailResponse {

    @ApiModelProperty(value = "数据列表")
    private List<PurchaseOrderDetailData> list;

    public List<PurchaseOrderDetailData> getList() {
        return list;
    }

    public void setList(List<PurchaseOrderDetailData> list) {
        this.list = list;
    }
}
