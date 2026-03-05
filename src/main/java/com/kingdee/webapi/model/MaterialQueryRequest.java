package com.kingdee.webapi.model;

import lombok.Data;

/**
 * 物料查询请求参数模型
 */
@Data
public class MaterialQueryRequest {
    private String filterString;    // 过滤条件，如 "FNumber='001'"
    private String fieldKeys;       // 查询字段，逗号分隔，如 "FMasterId,FNumber,FName,FSpecification"
    private Integer limit;          // 返回数量限制
    private Integer startRow;       // 开始行数
    private Boolean all;            // 是否获取全部数据（不分页）
}
