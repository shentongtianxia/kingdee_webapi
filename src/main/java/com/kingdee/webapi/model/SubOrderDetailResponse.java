package com.kingdee.webapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * 委外订单执行明细表响应对象
 */
@ApiModel(description = "委外订单执行明细表响应", value = "SubOrderDetailResponse")
public class SubOrderDetailResponse {

    @ApiModelProperty(value = "数据列表")
    private List<SubOrderDetailData> list;

    public List<SubOrderDetailData> getList() {
        return list;
    }

    public void setList(List<SubOrderDetailData> list) {
        this.list = list;
    }
}
