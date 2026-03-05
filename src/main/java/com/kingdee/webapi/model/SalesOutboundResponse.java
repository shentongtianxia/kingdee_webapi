package com.kingdee.webapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * 销售出库明细表响应包装类
 *
 * @author Kingdee
 * @version 1.0.0
 */
@ApiModel(description = "销售出库明细表响应数据")
public class SalesOutboundResponse {

    @ApiModelProperty(value = "数据列表")
    private List<SalesOutboundData> list;

    public SalesOutboundResponse() {
    }

    public SalesOutboundResponse(List<SalesOutboundData> list) {
        this.list = list;
    }

    public List<SalesOutboundData> getList() {
        return list;
    }

    public void setList(List<SalesOutboundData> list) {
        this.list = list;
    }
}

