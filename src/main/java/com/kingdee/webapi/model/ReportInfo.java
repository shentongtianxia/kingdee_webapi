package com.kingdee.webapi.model;

import lombok.Data;

/**
 * 报表信息模型类
 * 用于封装接口返回的报表数据
 * 
 * @author Kingdee
 * @version 1.0.0
 */
@Data
public class ReportInfo {
    
    private String fnumber;    // 报表编号
    private String fyear;      // 年份
    private String fperiod;    // 期间
    private String forgName;   // 组织名称
    private String forgId;

} 