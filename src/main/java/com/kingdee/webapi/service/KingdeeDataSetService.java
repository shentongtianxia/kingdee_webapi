package com.kingdee.webapi.service;

import com.kingdee.webapi.model.Financial;
import com.kingdee.webapi.model.FinancialStatements;
import com.kingdee.webapi.model.MultiDailyReportData;
import com.kingdee.webapi.model.MultiDailyReportRequest;

import java.io.IOException;
import java.util.List;

/**
 * 金蝶数据集服务接口
 * 
 * @author Kingdee
 * @version 1.0.0
 */
public interface KingdeeDataSetService {
    
    /**
     * 获取科目余额表数据
     * 
     * @return 科目余额表数据
     * @throws Exception 异常
     */
    Financial getkmyedata() throws Exception;

    FinancialStatements report() throws IOException;

    /**
     * 获取多账簿日报表数据
     *
     * @param request 请求参数
     * @param fieldKeys 字段列表（可选，为null时使用默认值）
     * @return 多账簿日报表数据
     * @throws Exception 异常
     */
    List<MultiDailyReportData> getMultiDailyReportData(MultiDailyReportRequest request, String fieldKeys) throws Exception;

    /**
     * 查询物料基本信息
     * @param request 查询请求
     * @return 物料数据列表（JSON格式）
     * @throws Exception 异常
     */
    String queryMaterials(com.kingdee.webapi.model.MaterialQueryRequest request) throws Exception;

    /**
     * 查询组织信息
     * @param request 查询请求
     * @return 组织数据列表（JSON格式）
     * @throws Exception 异常
     */
    String queryOrganizations(com.kingdee.webapi.model.MaterialQueryRequest request) throws Exception;

    /**
     * 查询物料分类信息
     * @param request 查询请求
     * @return 物料分类数据列表（JSON格式）
     * @throws Exception 异常
     */
    String queryMaterialGroups(com.kingdee.webapi.model.MaterialQueryRequest request) throws Exception;
} 