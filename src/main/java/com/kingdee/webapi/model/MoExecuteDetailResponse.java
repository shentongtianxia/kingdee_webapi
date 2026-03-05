package com.kingdee.webapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * 生产订单执行明细表响应对象
 */
@ApiModel(description = "生产订单执行明细表响应", value = "MoExecuteDetailResponse")
public class MoExecuteDetailResponse {

    @ApiModelProperty(value = "数据列表")
    private List<MoExecuteDetailData> list;

    public List<MoExecuteDetailData> getList() {
        return list;
    }

    public void setList(List<MoExecuteDetailData> list) {
        this.list = list;
    }
}
