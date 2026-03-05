package com.kingdee.webapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * 科目余额表包装类
 * 
 * @author Kingdee
 * @version 1.0.0
 */
@ApiModel(description = "科目余额表响应数据")
public class Financial {
    
    @ApiModelProperty(value = "数据列表")
    private List<FinancialData> list;
    
    public Financial() {
    }
    
    public Financial(List<FinancialData> list) {
        this.list = list;
    }
    
    public List<FinancialData> getList() {
        return list;
    }
    
    public void setList(List<FinancialData> list) {
        this.list = list;
    }
    

} 