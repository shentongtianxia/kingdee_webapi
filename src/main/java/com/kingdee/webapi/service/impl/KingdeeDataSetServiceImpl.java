package com.kingdee.webapi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.kingdee.bos.webapi.entity.RepoRet;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import com.kingdee.webapi.common.KdResult;
import com.kingdee.webapi.jdbc.JDBCUtils;
import com.kingdee.webapi.model.*;
import com.kingdee.webapi.service.KingdeeDataSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * 金蝶数据集服务实现类
 * 
 * @author Kingdee
 * @version 1.0.0
 */
@Service
public class KingdeeDataSetServiceImpl implements KingdeeDataSetService {


    
    @Override
    public Financial getkmyedata() throws Exception {
        Financial financial = new Financial();
        List<String> list = generateMonthYearList();

        // 查询组织信息列表
        List<Organization> organizations = getOrganizations();
        List<FinancialData> financialDatas = new ArrayList<>();
        for (Organization organization : organizations){
            List<FinancialData> financialData = processList(list, organization);
            financialDatas.addAll(financialData);
        }
        // 处理财务数据
        financial.setList(financialDatas);

        
        return financial;
    }
    
    /**
     * 查询组织信息列表
     * @return 组织信息列表
     */
    private List<Organization> getOrganizations() {
        try {
            // 查询组织编号和组织名称 - 排除编号为'100'的组织
            String sql = "SELECT top1 t2.fnumber, t1.FNAME FROM T_ORG_ORGANIZATIONS_l t1 " +
                        "LEFT JOIN T_ORG_Organizations t2 ON t1.forgid = t2.forgid " +
                        "WHERE t2.FNUMBER != '100' ORDER BY t2.FNUMBER";
            
            List<Organization> organizations = JDBCUtils.getJdbcTemplate().query(sql, new RowMapper<Organization>() {
                @Override
                public Organization mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Organization org = new Organization();
                    org.setFnumber(rs.getString("fnumber"));
                    org.setFname(rs.getString("FNAME"));
                    return org;
                }
            });
            
            // 过滤掉空值和空字符串
            organizations = organizations.stream()
                    .filter(org -> org.getFnumber() != null && !org.getFnumber().trim().isEmpty())
                    .collect(Collectors.toList());
            
            System.out.println("查询到组织数量: " + organizations.size());
            System.out.println("组织信息列表: " + organizations);
            
            return organizations;
        } catch (Exception e) {
            System.err.println("查询组织信息时发生错误: " + e.getMessage());
            e.printStackTrace();
            // 如果查询失败，返回空列表
            return new ArrayList<>();
        }
    }
    

    /**
     * 解析接口返回的数据并封装成ReportInfo对象列表
     * @param data 接口返回的数据字符串
     * @return ReportInfo对象列表
     */
    private List<ReportInfo> parseReportInfoFromJson(String data) {
        List<ReportInfo> reportInfoList = new ArrayList<>();
        
        try {
            System.out.println("原始数据: " + data);
            
            // 首先尝试使用简化的解析方法
            if (data != null && data.trim().startsWith("[[")) {
                reportInfoList = parseSimpleData(data);
                if (!reportInfoList.isEmpty()) {
                    System.out.println("简化解析成功，解析到 " + reportInfoList.size() + " 条数据");
                    return reportInfoList;
                }
            }
            
            // 如果简化解析失败，尝试JSON解析
            if (data.startsWith("[[")) {
                // 将数据转换为标准JSON格式
                String jsonData = convertToJsonFormat(data);
                System.out.println("转换后的JSON: " + jsonData);
                
                JSONArray jsonArray = JSON.parseArray(jsonData);
                
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONArray rowData = jsonArray.getJSONArray(i);
                    
                    // 确保数据有足够的元素
                    if (rowData.size() >= 5) {
                        String forgId = rowData.getString(4);
                        
                        // 过滤掉forgId为空的记录
                        if (forgId != null && !forgId.trim().isEmpty()) {
                            ReportInfo reportInfo = new ReportInfo();
                            reportInfo.setFnumber(rowData.getString(0));  // 报表编号
                            reportInfo.setFyear(rowData.getString(1));    // 年份
                            reportInfo.setFperiod(rowData.getString(2));  // 期间
                            reportInfo.setForgName(rowData.getString(3)); // 组织名称
                            reportInfo.setForgId(forgId);
                            
                            reportInfoList.add(reportInfo);
                        }
                    }
                }
                
                System.out.println("JSON解析成功，解析到 " + reportInfoList.size() + " 条数据");
            } else {
                System.out.println("数据格式不是数组格式，尝试手动解析");
                reportInfoList = parseDataManually(data);
            }
            
        } catch (Exception e) {
            System.err.println("解析数据时发生错误: " + e.getMessage());
            e.printStackTrace();
            
            // 如果所有解析方法都失败，尝试手动解析
            try {
                reportInfoList = parseDataManually(data);
                System.out.println("手动解析成功，解析到 " + reportInfoList.size() + " 条数据");
            } catch (Exception ex) {
                System.err.println("手动解析也失败: " + ex.getMessage());
            }
        }
        
        System.out.println("最终解析结果: " + reportInfoList.size() + " 条报表信息");
        return reportInfoList;
    }
    
    /**
     * 将非标准格式的数据转换为JSON格式
     * @param data 原始数据
     * @return JSON格式的字符串
     */
    private String convertToJsonFormat(String data) {
        // 移除开头和结尾的方括号
        String content = data.substring(1, data.length() - 1);
        
        // 分割每个数组元素
        String[] arrays = content.split("\\],\\s*\\[");
        
        StringBuilder jsonBuilder = new StringBuilder("[");
        for (int i = 0; i < arrays.length; i++) {
            String array = arrays[i];
            
            // 移除每个数组的开头和结尾方括号
            if (array.startsWith("[")) {
                array = array.substring(1);
            }
            if (array.endsWith("]")) {
                array = array.substring(0, array.length() - 1);
            }
            
            // 分割数组元素并添加引号
            String[] elements = array.split(",\\s*");
            StringBuilder arrayBuilder = new StringBuilder("[");
            for (int j = 0; j < elements.length; j++) {
                String element = elements[j].trim();
                // 如果元素不是数字，添加引号
                if (!element.matches("\\d+")) {
                    arrayBuilder.append("\"").append(element).append("\"");
                } else {
                    arrayBuilder.append(element);
                }
                if (j < elements.length - 1) {
                    arrayBuilder.append(",");
                }
            }
            arrayBuilder.append("]");
            
            jsonBuilder.append(arrayBuilder.toString());
            if (i < arrays.length - 1) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]");
        
        return jsonBuilder.toString();
    }
    
    /**
     * 手动解析数据
     * @param data 原始数据
     * @return ReportInfo对象列表
     */
    private List<ReportInfo> parseDataManually(String data) {
        List<ReportInfo> reportInfoList = new ArrayList<>();
        
        try {
            // 移除开头和结尾的方括号
            String content = data.substring(1, data.length() - 1);
            
            // 分割每个数组元素
            String[] arrays = content.split("\\],\\s*\\[");
            
            for (String array : arrays) {
                // 移除每个数组的开头和结尾方括号
                if (array.startsWith("[")) {
                    array = array.substring(1);
                }
                if (array.endsWith("]")) {
                    array = array.substring(0, array.length() - 1);
                }
                
                // 分割数组元素
                String[] elements = array.split(",\\s*");
                
                if (elements.length >= 5) {
                    String forgId = elements[4].trim();
                    
                    // 过滤掉forgId为空的记录
                    if (forgId != null && !forgId.isEmpty()) {
                        ReportInfo reportInfo = new ReportInfo();
                        reportInfo.setFnumber(elements[0].trim());  // 报表编号
                        reportInfo.setFyear(elements[1].trim());    // 年份
                        reportInfo.setFperiod(elements[2].trim());  // 期间
                        reportInfo.setForgName(elements[3].trim()); // 组织名称
                        reportInfo.setForgId(forgId);
                        
                        reportInfoList.add(reportInfo);
                    }
                }
            }
            
        } catch (Exception e) {
            System.err.println("手动解析数据时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
        
        return reportInfoList;
    }
    
    /**
     * 简化的数据解析方法，专门处理特定格式的数据
     * @param data 原始数据字符串
     * @return ReportInfo对象列表
     */
    private List<ReportInfo> parseSimpleData(String data) {
        List<ReportInfo> reportInfoList = new ArrayList<>();
        
        try {
            // 移除开头和结尾的方括号
            String content = data.trim();
            if (content.startsWith("[[") && content.endsWith("]]")) {
                content = content.substring(2, content.length() - 2);
            }
            
            // 使用正则表达式匹配每个数组
            String pattern = "\\[([^\\]]+)\\]";
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
            java.util.regex.Matcher m = p.matcher(content);
            
            while (m.find()) {
                String arrayContent = m.group(1);
                String[] elements = arrayContent.split(",\\s*");
                
                if (elements.length >= 5) {
                    String forgId = elements[4].trim();
                    
                    // 过滤掉forgId为空的记录
                    if (forgId != null && !forgId.isEmpty()) {
                        ReportInfo reportInfo = new ReportInfo();
                        reportInfo.setFnumber(elements[0].trim());  // 报表编号
                        reportInfo.setFyear(elements[1].trim());    // 年份
                        reportInfo.setFperiod(elements[2].trim());  // 期间
                        reportInfo.setForgName(elements[3].trim()); // 组织名称
                        reportInfo.setForgId(forgId);
                        
                        reportInfoList.add(reportInfo);
                    }
                }
            }
            
        } catch (Exception e) {
            System.err.println("简化解析数据时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
        
        return reportInfoList;
    }
    

    /**
     * 过滤掉forgId为空的ReportInfo记录
     * @param reportInfoList 原始报表信息列表
     * @return 过滤后的报表信息列表
     */
    public List<ReportInfo> filterReportInfoByForgId(List<ReportInfo> reportInfoList) {
        int originalSize = reportInfoList.size();
        List<ReportInfo> filteredList = reportInfoList.stream()
                .filter(info -> info.getForgId() != null && !info.getForgId().trim().isEmpty())
                .collect(Collectors.toList());
        
        int filteredSize = filteredList.size();
        System.out.println("过滤统计: 原始数据 " + originalSize + " 条，过滤后 " + filteredSize + " 条，过滤掉 " + (originalSize - filteredSize) + " 条");
        
        return filteredList;
    }
    

    @Override
    public FinancialStatements report() throws IOException {


        List<ReportInfo> reportInfoList=new ArrayList<>();
        K3CloudApi client = new K3CloudApi();
        //请求参数，要求为json字符串
        String jsonData1 = "{\n" +
                "    \"FormId\": \"KDS_Report\",\n" +
                "    \"FieldKeys\": \"FNUMBER,FYEAR,FPERIOD,FORGID.FNAME,FORGID.FNUMBER\",\n" +
                "    \"FilterString\": \"FNUMBER like 'GB00001%'\",\n" +
                "    \"OrderString\": \"\",\n" +
                "    \"TopRowCount\": 0,\n" +
                "    \"StartRow\": 0,\n" +
                "    \"Limit\": 2000,\n" +
                "    \"SubSystemId\": \"\"\n" +
                "}";


       // {"ReportType":17,"ReportNumber":"GB00001","AcctSystemNumber":"KJHSTX01_SYS","AcctPolicyNumber":"KJZC01_SYS","OrgNumber":"202","CurrencyNumber":"PRE001","CurrUnitNumber":"JEDW01_SYS","CycleType":4,"Year":2025,"Period":7,"DataType":"Json","ResultType":"0"}
        try {
            //调用接口
            String resultJson = String.valueOf(client.executeBillQuery(jsonData1));
            System.out.println("接口返回结果: " + resultJson);
            
            // 解析接口返回的数据并封装成对象
            reportInfoList = parseReportInfoFromJson(resultJson);
            System.out.println("解析后的报表信息: " + reportInfoList);
            
            // 过滤掉forgId为空的记录
            reportInfoList = filterReportInfoByForgId(reportInfoList);
            System.out.println("过滤后的报表信息数量: " + reportInfoList.size());
            
        } catch (Exception e) {
            System.err.println("调用接口时发生错误: " + e.getMessage());
            e.printStackTrace();
        }

        FinancialStatements financialStatements = new FinancialStatements();
        List<BalanceReport> lb=new ArrayList<>();
        List<IncomeStatementReport> lo=new ArrayList<>();
        List<IncomeStatementReport> lo1=new ArrayList<>();
        for (ReportInfo reportInfo:reportInfoList){

            try {
                System.err.println(reportInfo.getFyear()+"--"+reportInfo.getFyear());
                String jsonData = "[{\"ReportType\":17,\"ReportNumber\":\"" +reportInfo.getFnumber()+
                        "\",\"AcctSystemNumber\":\"KJHSTX01_SYS\",\"AcctPolicyNumber\":\"KJZC01_SYS\",\"OrgNumber\":\"" +reportInfo.getForgId()+
                        "\",\"CurrencyNumber\":\"PRE001\",\"CurrUnitNumber\":\"JEDW01_SYS\",\"CycleType\":4,\"Year\":" +reportInfo.getFyear()+
                        ",\"Period\":" +reportInfo.getFperiod()+
                        ",\"DataType\":\"Json\",\"ResultType\":\"0\"}]";



                System.err.println("jsonData:"+jsonData);

                Object[] parameters = new Object[] {
                        "66abb10fb96d85",//数据中心
                        "administrator",//用户名
                        "314364_RfeM7xGO6rgVxX8H217NS68s5vRbwPMu",//appId
                        "8938fb15cae94197a9086939ae55fe36",//appSec
                        "2052"//2052=中文
                };
                String loginResult = client.execute("Kingdee.BOS.WebApi.ServicesStub.AuthService.LoginByAppSecret", parameters);
                System.out.println(loginResult);

                //调用url
                String requestUrl = "Kingdee.BOS.KDS.ServiceFacade.ServicesStub.KDSReportAPIStub.GetReportData,Kingdee.BOS.KDS.ServiceFacade.ServicesStub";
                //自定义请求参数
                Gson gson = new Gson();
                Object[] paramInfo = gson.fromJson(jsonData,Object[].class);
                //调用接口
                String reportData = String.valueOf(client.execute(requestUrl, paramInfo));
                System.out.println(reportData);

               // String reportData = client.execute("Kingdee.BOS.KDS.ServiceFacade.ServicesStub.KDSReportAPIStub.GetReportData,Kingdee.BOS.KDS.ServiceFacade.ServicesStub", new String[]{apiParam});
                // 第一步：解析外层结构
                JSONObject outer = JSON.parseObject(reportData);
                String resultStr = outer.getString("result");
                // 第二步：解析result字段
                JSONObject result = JSON.parseObject(resultStr);
                // 第三步：获取items数组
                JSONArray items = result.getJSONArray("items");
                // 第四步：提取目标数据
                for (int i = 0; i < items.size(); i++) {
                    JSONObject item = items.getJSONObject(i);
                    JSONObject data = item.getJSONObject("data");
                    JSONArray targetData = data.getJSONArray("data");
                    JSONArray reports = JSON.parseArray(targetData.toJSONString());
                    int size = reports.size();
                    if(size==3){
                        // 分别存储三个报表 - 修正索引映射
                        // reports.getJSONArray(0) 是利润表
                        // reports.getJSONArray(1) 是资产负债表  
                        // reports.getJSONArray(2) 是现金流量表
                        List<ReportCell> incomeStatement = parseReport(reports.getJSONArray(0));
                        List<ReportCell> balanceSheet = parseReport(reports.getJSONArray(1));
                        List<ReportCell> cashFlow = parseReport(reports.getJSONArray(2));
                        List<BalanceReport> o = printReport(balanceSheet, 1,reportInfo.getFnumber(),reportInfo.getFyear(),reportInfo.getFperiod(),reportInfo.getForgName());
                        lb.addAll(o);
                        int index=1;
                        for (BalanceReport balanceReport : o) {
                            balanceReport.setIndex(index);
                            index++;
                        }
                        List<IncomeStatementReport> o1 = printReport1(incomeStatement, 2,reportInfo.getFnumber(),reportInfo.getFyear(),reportInfo.getFperiod(),reportInfo.getForgName());
                        lo.addAll(o1);
                        List<IncomeStatementReport> o2 = printReport1(cashFlow, 2,reportInfo.getFnumber(),reportInfo.getFyear(),reportInfo.getFperiod(),reportInfo.getForgName());
                        lo1.addAll(o2);
                    }

                    if(size==2){
                        // 修正索引映射
                        // reports.getJSONArray(0) 是利润表
                        // reports.getJSONArray(1) 是资产负债表
                        List<ReportCell> incomeStatement = parseReport(reports.getJSONArray(0));
                        List<ReportCell> balanceSheet = parseReport(reports.getJSONArray(1));
                        List<BalanceReport> o = printReport(balanceSheet, 1,reportInfo.getFnumber(),reportInfo.getFyear(),reportInfo.getFperiod(),reportInfo.getForgName());
                        lb.addAll(o);
                        int index=1;
                        for (BalanceReport balanceReport : o) {
                            balanceReport.setIndex(index);
                            index++;
                        }

                        List<IncomeStatementReport> o1 = printReport1(incomeStatement, 2,reportInfo.getFnumber(),reportInfo.getFyear(),reportInfo.getFperiod(),reportInfo.getForgName());
                        lo.addAll(o1);
                    }
                }
                //  return financialStatements;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        financialStatements.setBalanceReportList(lb);
        financialStatements.setIncomeStatementReport(lo);
        financialStatements.setIncomeStatementReport1(lo1);
        return financialStatements;
    }

    public List<String> generateMonthYearList() {
        List<String> monthYearList = new ArrayList<>();
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 设置起始日期为2023年5月
        LocalDate startDate = LocalDate.of(2025, Month.JANUARY, 1);
        // 循环生成年份和月份列表
        while (startDate.isBefore(currentDate) || startDate.isEqual(currentDate)) {
            int year = startDate.getYear();
            int monthValue = startDate.getMonthValue();
            String monthYear = year + "-" + monthValue; // 格式化月份为两位数
            monthYearList.add(monthYear);
            // 增加一个月
            startDate = startDate.plusMonths(1);
        }
        return monthYearList;
    }
    
    public List<FinancialData> processList(List<String> list,Organization organization) {
        List<CompletableFuture<List<FinancialData>>> futures = new ArrayList<>();
        for (String i : list) {
            CompletableFuture<List<FinancialData>> future = CompletableFuture.supplyAsync(() -> {
                try {
                    return getList(i,organization);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            futures.add(future);
        }
        // 使用CompletableFuture.allOf等待所有任务完成
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        try {
            // 等待所有任务完成
            allOf.get();
            // 收集结果
            return futures.stream()
                    .map(CompletableFuture::join) // 取得每个CompletableFuture的结果
                    .flatMap(List::stream) // 将结果列表展平成一个流
                    .collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            // 处理异常
        }
        return new ArrayList<>(); // 返回空列表或者适当的默认值
    }


    public   List<FinancialData> getList(String yearAdnmonth,Organization organization) throws Exception {
        String[] split = yearAdnmonth.split("-");
        K3CloudApi client = new K3CloudApi();
        String jsonData="{\n" +
                "    \"FieldKeys\": \"FBALANCEID,\n" +
                "FBALANCENAME,\n" +
                "FACCTTYPE,\n" +
                "FACCTGROUP,\n" +
                "FDETAILNUMBER,\n" +
                "FDETAILNAME,\n" +
                "FCyName,\n" +
                "FBEGINYEARDEBIT,\n" +
                "FBEGINYEARDEBITLOCAL,\n" +
                "FBEGINYEARCREDIT,\n" +
                "FBEGINYEARCREDITLOCAL,\n" +
                "FBEGINDEBIT,\n" +
                "FBEGINDEBITLOCAL,\n" +
                "FBEGINCREDIT,\n" +
                "FBEGINCREDITLOCAL,\n" +
                "FDEBIT,\n" +
                "FDEBITLOCAL,\n" +
                "FCREDIT,\n" +
                "FCREDITLOCAL,\n" +
                "FYTDDEBIT,\n" +
                "FYTDDEBITLOCAL,\n" +
                "FYTDCREDIT,\n" +
                "FYTDCREDITLOCAL,\n" +
                "FENDDEBIT,\n" +
                "FENDDEBITLOCAL,\n" +
                "FENDCREDIT,\n" +
                "FENDCREDITLOCAL,\n" +
                "FACCTDC,\n" +
                "FPROFIT,\n" +
                "FPROFITLOCAL,\n" +
                "FYTDPROFIT,\n" +
                "FYTDPROFITLOCAL\",\n" +
                "    \"SchemeId\": \"\",\n" +
                "    \"StartRow\": 0,\n" +
                "    \"Limit\": 10000,\n" +
                "    \"IsVerifyBaseDataField\": \"true\",\n" +
                "    \"Model\": {\n" +
                "        \"FACCTBOOKID\": {\n" +
                "            \"FNumber\": \"" +organization.getFnumber()+
                "\"\n" +
                "        },\n" +
                "        \"FCURRENCY\": \"1\",\n" +
                "        \"FSTARTYEAR\": \"" +split[0]+
                "\",\n" +
                "        \"FSTARTPERIOD\": \"" +split[1]+
                "\",\n" +
                "        \"FENDYEAR\": \"" +split[0]+
                "\",\n" +
                "        \"FBALANCELEVEL\": \"3\",\n" +
                "        \"FENDPERIOD\": \"" +split[1]+
                "\",\n" +
                "        \"FSHOWDETAIL\": true,\n" +
                "        \"FFORBIDBALANCE\": true,\n" +
                "        \"FNOTPOSTVOUCHER\": true,\n" +
                "        \"FDEBITORCREDIT\": false,\n" +
                "        \"FBALANCEZERO\": true,\n" +
                "        \"FNOBUSINESS\": false,\n" +
                "        \"FPERIODNOBALANCE\": true,\n" +
                "        \"FYEARNOBALANCE\": true,\n" +
                "        \"FSHOWFULLNAME\": false,\n" +
                "        \"FDETAILSHOWACCT\": false,\n" +
                "        \"FSHOWDETAILONLY\": false,\n" +
                "        \"FEXCLUDEADJUSTVCH\": false,\n" +
                "        \"FFLEXDEBITORCREDIT\": false,\n" +
                "        \"FSHOWFLEXBYCOL\": false\n" +
                "    }\n" +
                "}";

        //业务对象标识
        String formId = "GL_RPT_AccountBalance";
        //调用接口
        String resultJson = client.getSysReportData(formId,jsonData);
        KdResult result= JSONObject.parseObject(resultJson, KdResult.class);//JSON字符串转对象
        List<List<String>> rows = result.getResult().getRows();
        ArrayList<FinancialData> financialData1 = new ArrayList<>();
        for (List<String> s:rows){
            FinancialData financialData = new FinancialData();
            financialData.setFBalanceId(s.get(0));
            financialData.setFBalanceName(s.get(1));
            financialData.setFAcctType(s.get(2));
            financialData.setFAcctGroup(s.get(3));
            financialData.setFDetailNumber(s.get(4));
     /*     String value = s.get(4);
            String value1 = s.get(5);
           if(value!=null&&value.length()>2){
                String firstTwoDigits1 = value.trim().substring(0, 2);
                if(firstTwoDigits1.equals("FY")){
                    financialData.setFyxmNumber(value);
                    financialData.setFyxm(value1);
                }
            }
            if(value.contains("/")){
                String[] parts = value.split("/");
                String[] parts1 = value1.split("/");
                for (int i = 0; i < parts.length; i++) {
                    String part = parts[i];
                    System.out.println( "part:"+part);
                    String firstTwoDigits = part.trim().substring(0, 2);
                    if(firstTwoDigits.equals("SY")){
                        financialData.setSybNumber(part);
                        financialData.setSyb(parts1[i]);
                    }
                    if(firstTwoDigits.equals("BM")){
                        financialData.setBmNumber(part);
                        financialData.setBm(parts1[i]);
                    }
                    if(firstTwoDigits.equals("CU")){
                        financialData.setKhNumber(part);
                        financialData.setKh(parts1[i]);
                    }
                    if(firstTwoDigits.equals("QT")){
                        financialData.setCpNumber(part);
                        financialData.setCp(parts1[i]);
                    }

                }

            }*/
            financialData.setFDetailName(s.get(5));
            financialData.setFCyName(s.get(6));
            financialData.setFBeginYearDebit(s.get(7) != null ? s.get(7).replace(",", "") : "");
            financialData.setFBeginYearDebitLocal(s.get(8) != null ? s.get(8).replace(",", "") : "");
            financialData.setFBeginYearCredit(s.get(9) != null ? s.get(9).replace(",", "") : "");
            financialData.setFBeginYearCreditLocal(s.get(10) != null ? s.get(10).replace(",", "") : "");
            financialData.setFBeginDebit(s.get(11) != null ? s.get(11).replace(",", "") : "");
            financialData.setFBeginDebitLocal(s.get(12) != null ? s.get(12).replace(",", "") : "");
            financialData.setFBeginCredit(s.get(13) != null ? s.get(13).replace(",", "") : "");
            financialData.setFBeginCreditLocal(s.get(14) != null ? s.get(14).replace(",", "") : "");
            financialData.setFDebit(s.get(15) != null ? s.get(15).replace(",", "") : "");
            financialData.setFDebitLocal(s.get(16) != null ? s.get(16).replace(",", "") : "");
            financialData.setFCredit(s.get(17) != null ? s.get(17).replace(",", "") : "");
            financialData.setFCreditLocal(s.get(18) != null ? s.get(18).replace(",", "") : "");
            financialData.setFYtdDebit(s.get(19) != null ? s.get(19).replace(",", "") : "");
            financialData.setFYtdDebitLocal(s.get(20) != null ? s.get(20).replace(",", "") : "");
            financialData.setFYtdCredit(s.get(21) != null ? s.get(21).replace(",", "") : "");
            financialData.setFYtdCreditLocal(s.get(22) != null ? s.get(22).replace(",", "") : "");
            financialData.setFEndDebit(s.get(23) != null ? s.get(23).replace(",", "") : "");
            financialData.setFEndDebitLocal(s.get(24) != null ? s.get(24).replace(",", "") : "");
            financialData.setFEndCredit(s.get(25) != null ? s.get(25).replace(",", "") : "");
            financialData.setFEndCreditLocal(s.get(26) != null ? s.get(26).replace(",", "") : "");
            financialData.setFAcctDc(s.get(27));
            financialData.setFProfit(s.get(28) != null ? s.get(28).replace(",", "") : "");
            financialData.setFProfitLocal(s.get(29) != null ? s.get(29).replace(",", "") : "");
            financialData.setFYtdProfit(s.get(30) != null ? s.get(30).replace(",", "") : "");
            financialData.setFYtdProfitLocal(s.get(31) != null ? s.get(31).replace(",", "") : "");
            financialData.setOrgName(organization.getFname());
            financialData.setYear(split[0]);
            financialData.setMonth(split[1]+"");
            financialData1.add(financialData);

        }
        List<FinancialData> financialData = fillMissingBalanceIdsAndNames(financialData1);

        return financialData;

    }
    public List<FinancialData> fillMissingBalanceIdsAndNames(List<FinancialData> financialDataList) {
        String previousBalanceId = null;
        String previousBalanceName = null;

        for (FinancialData financialData : financialDataList) {
            String currentBalanceId = financialData.getFBalanceId();
            String currentBalanceName = financialData.getFBalanceName();

            // 如果当前的 fBalanceId 为空，则使用前一个非空值填充
            if (currentBalanceId == null || currentBalanceId.isEmpty()) {
                financialData.setFBalanceId(previousBalanceId);
                financialData.setFBalanceName(previousBalanceName);
            } else {
                // 更新前一个非空值
                previousBalanceId = currentBalanceId;
                previousBalanceName = currentBalanceName;
            }
        }
        return financialDataList;
    }



    /**
     * 格式化打印报表数据，以表格形式展示，并以列表形式输出
     * @param reportCells 报表单元格列表
     */
    @Override
    public String queryMaterials(MaterialQueryRequest request) throws Exception {
        K3CloudApi client = new K3CloudApi();
        
        // 默认查询字段，增加了组织名称、分类名称等
        String fieldKeys = request.getFieldKeys();
        if (fieldKeys == null || fieldKeys.isEmpty()) {
            fieldKeys = "FMasterId,FNumber,FName,FSpecification,FBaseUnitId.FName,FMaterialGroup.FName,FUseOrgId.FName,FCreateOrgId.FName,FDescription";
        }
        
        String filterString = request.getFilterString() != null ? request.getFilterString() : "";
        
        // 如果是全量查询 (all = true)
        if (Boolean.TRUE.equals(request.getAll())) {
            List<List<Object>> allResults = new ArrayList<>();
            int startRow = 0;
            int batchSize = 2000; // 金蝶单次建议最大 2000 条
            
            while (true) {
                JSONObject queryParam = new JSONObject();
                queryParam.put("FormId", "BD_MATERIAL");
                queryParam.put("FieldKeys", fieldKeys);
                queryParam.put("FilterString", filterString);
                queryParam.put("OrderString", "FNumber ASC");
                queryParam.put("StartRow", startRow);
                queryParam.put("Limit", batchSize);
                
                List<List<Object>> batch = client.executeBillQuery(queryParam.toJSONString());
                if (batch == null || batch.isEmpty()) {
                    break;
                }
                
                // 将数组转换为对象格式
                List<Map<String, Object>> objectBatch = convertToObjectFormat(batch, fieldKeys);
                // 这里我们需要累积对象而不是数组
                for (Map<String, Object> obj : objectBatch) {
                    allResults.add(convertMapToList(obj));
                }
                
                // 如果返回的数据少于 batchSize，说明已经是最后一页
                if (batch.size() < batchSize) {
                    break;
                }
                
                startRow += batchSize;
                
                // 安全限制：防止数据量过大导致 OOM，这里限制 50000 条，可根据实际情况调整
                if (allResults.size() >= 50000) {
                    break;
                }
            }
            
            JSONObject finalResponse = new JSONObject();
            finalResponse.put("totalCount", allResults.size());
            finalResponse.put("data", allResults);
            return finalResponse.toJSONString();
        } else {
            // 普通分页查询
            JSONObject queryParam = new JSONObject();
            queryParam.put("FormId", "BD_MATERIAL");
            queryParam.put("FieldKeys", fieldKeys);
            queryParam.put("FilterString", filterString);
            queryParam.put("OrderString", "FNumber ASC");
            queryParam.put("StartRow", request.getStartRow() != null ? request.getStartRow() : 0);
            queryParam.put("Limit", request.getLimit() != null ? request.getLimit() : 100);
            
            List<List<Object>> result = client.executeBillQuery(queryParam.toJSONString());
            
            // 将数组格式转换为对象格式
            List<Map<String, Object>> objectResult = convertToObjectFormat(result, fieldKeys);
            
            JSONObject finalResponse = new JSONObject();
            finalResponse.put("totalCount", objectResult != null ? objectResult.size() : 0);
            finalResponse.put("data", objectResult);
            return finalResponse.toJSONString();
        }
    }

    /**
     * 查询组织信息
     */
    public String queryOrganizations(MaterialQueryRequest request) throws Exception {
        K3CloudApi client = new K3CloudApi();
        String fieldKeys = request.getFieldKeys();
        if (fieldKeys == null || fieldKeys.isEmpty()) {
            fieldKeys = "FOrgId,FNumber,FName,FDescription,FOrgFunctions";
        }
        
        JSONObject queryParam = new JSONObject();
        queryParam.put("FormId", "ORG_Organizations");
        queryParam.put("FieldKeys", fieldKeys);
        queryParam.put("FilterString", request.getFilterString() != null ? request.getFilterString() : "");
        queryParam.put("Limit", request.getLimit() != null ? request.getLimit() : 100);
        
        java.util.List<java.util.List<Object>> result = client.executeBillQuery(queryParam.toJSONString());
        return JSON.toJSONString(result);
    }

    /**
     * 查询物料分类信息
     */
    public String queryMaterialGroups(MaterialQueryRequest request) throws Exception {
        K3CloudApi client = new K3CloudApi();
        String fieldKeys = request.getFieldKeys();
        if (fieldKeys == null || fieldKeys.isEmpty()) {
            fieldKeys = "FId,FNumber,FName,FDescription";
        }
        
        JSONObject queryParam = new JSONObject();
        queryParam.put("FormId", "BD_MATERIALGROUP");
        queryParam.put("FieldKeys", fieldKeys);
        queryParam.put("FilterString", request.getFilterString() != null ? request.getFilterString() : "");
        queryParam.put("Limit", request.getLimit() != null ? request.getLimit() : 100);
        
        java.util.List<java.util.List<Object>> result = client.executeBillQuery(queryParam.toJSONString());
        return JSON.toJSONString(result);
    }

    public List<BalanceReport>   printReport(List<ReportCell> reportCells,Integer type,String billNo,String year,String month,String org) {

        HashMap<Object, Object> map = new HashMap<>();
        int maxRow = 0;
        for (ReportCell cell : reportCells) {
            if (cell.getRow() > maxRow) {
                maxRow = cell.getRow();
            }
        }

        // 创建一个列表用于存储格式化后的数据
        List<Map<String, String>> formattedList = new ArrayList<>();

        // 逐行处理报表数据
        for (int row = 0; row <= maxRow; row++) {
            // 初始化每个单元格的数据
            String assetProject = "";
            String assetCurrentValue = "";
            String assetLastYearValue = "";
            String liabilityProject = "";
            String liabilityCurrentValue = "";
            String liabilityLastYearValue = "";

            // 查找资产项目（列0）
            for (ReportCell cell : reportCells) {
                if (cell.getRow() == row && cell.getCol() == 0) {
                    assetProject = cell.getProject();
                    break;
                }
            }

            // 查找资产期末余额（列1）
            for (ReportCell cell : reportCells) {
                if (cell.getRow() == row && cell.getCol() == 1) {
                    assetCurrentValue = cell.getProject().isEmpty() ? cell.getValue() : cell.getProject();
                    break;
                }
            }

            // 查找资产上年年末余额（列2）
            for (ReportCell cell : reportCells) {
                if (cell.getRow() == row && cell.getCol() == 2) {
                    assetLastYearValue = cell.getProject().isEmpty() ? cell.getValue() : cell.getProject();
                    break;
                }
            }

            // 查找负债和所有者权益项目（列3）
            for (ReportCell cell : reportCells) {
                if (cell.getRow() == row && cell.getCol() == 3) {
                    liabilityProject = cell.getProject();
                    break;
                }
            }

            // 查找负债和所有者权益期末余额（列4）
            for (ReportCell cell : reportCells) {
                if (cell.getRow() == row && cell.getCol() == 4) {
                    liabilityCurrentValue = cell.getProject().isEmpty() ? cell.getValue() : cell.getProject();
                    break;
                }
            }

            // 查找负债和所有者权益上年年末余额（列5）
            for (ReportCell cell : reportCells) {
                if (cell.getRow() == row && cell.getCol() == 5) {
                    liabilityLastYearValue = cell.getProject().isEmpty() ? cell.getValue() : cell.getProject();
                    break;
                }
            }

            // 打印该行数据（如果至少有一个资产或负债项目不为空）
            if (!assetProject.isEmpty() || !liabilityProject.isEmpty()) {
                Map<String, String> rowData = new HashMap<>();
                rowData.put("rowIndex", String.valueOf(row));
                rowData.put("assetProject", assetProject);
                rowData.put("assetCurrentValue", assetCurrentValue);
                rowData.put("assetLastYearValue", assetLastYearValue);
                rowData.put("liabilityProject", liabilityProject);
                rowData.put("liabilityCurrentValue", liabilityCurrentValue);
                rowData.put("liabilityLastYearValue", liabilityLastYearValue);
                formattedList.add(rowData);
            }
        }

        ArrayList<ReportData> list = new ArrayList<>();

        ArrayList<BalanceReport> list1 = new ArrayList<>();

        ArrayList<IncomeStatementReport> list2 = new ArrayList<>();


        for (int i = 0; i < formattedList.size(); i++) {
            Map<String, String> rowData = formattedList.get(i);

            if (type == 1) {
                if (i >= 4) {
                    String assetProject = rowData.get("assetProject");
                    if(!assetProject.equals("")){
                        BalanceReport balanceReport = new BalanceReport();
                        balanceReport.setType(1);
                        balanceReport.setProject(rowData.get("assetProject"));
                        balanceReport.setQmye(rowData.get("assetCurrentValue"));
                        balanceReport.setSnnmye(rowData.get("assetLastYearValue"));
                        balanceReport.setBillNO(billNo);
                        balanceReport.setYear(year);
                        balanceReport.setMonth(month);
                        balanceReport.setOrg(org);

                        list1.add(balanceReport);
                    }

                    String liabilityProject = rowData.get("liabilityProject");
                    if(!liabilityProject.equals("")){
                        BalanceReport balanceReport1 = new BalanceReport();
                        balanceReport1.setType(2);
                        balanceReport1.setProject(rowData.get("liabilityProject"));
                        balanceReport1.setQmye(rowData.get("liabilityCurrentValue"));
                        balanceReport1.setSnnmye(rowData.get("liabilityLastYearValue"));

                 /*     reportData.setLiabilityProject(rowData.get("liabilityProject"));
                        reportData.setLiabilityCurrentValue(rowData.get("liabilityCurrentValue"));
                        reportData.setLiabilityLastYearValue(rowData.get("liabilityLastYearValue"));*/
                        balanceReport1.setBillNO(billNo);
                        balanceReport1.setYear(year);
                        balanceReport1.setMonth(month);
                        balanceReport1.setOrg(org);
                        //   list.add(reportData);
                        list1.add(balanceReport1);
                        list1.sort(Comparator.comparing(BalanceReport::getType));


                    }



                }

            }
          /*  if (type == 2) {
                if (i >= 4) {
                    IncomeStatementReport incomeStatementReport = new IncomeStatementReport();

                    incomeStatementReport.setIndex(i - 3);
                    incomeStatementReport.setProject(rowData.get("assetProject"));
                    incomeStatementReport.setBqje(rowData.get("assetCurrentValue"));
                    incomeStatementReport.setBnje(rowData.get("assetLastYearValue"));
                    incomeStatementReport.setSqje(rowData.get("liabilityProject"));
                    list2.add(incomeStatementReport);

                }
            }*/


        }


        return list1;

    }



    /**
     * 格式化打印报表数据，以表格形式展示，并以列表形式输出
     * @param reportCells 报表单元格列表
     */
    public List<IncomeStatementReport>   printReport1(List<ReportCell> reportCells,Integer type,String billNo,String year,String month,String org) {

        HashMap<Object, Object> map = new HashMap<>();
        int maxRow = 0;
        for (ReportCell cell : reportCells) {
            if (cell.getRow() > maxRow) {
                maxRow = cell.getRow();
            }
        }

        // 创建一个列表用于存储格式化后的数据
        List<Map<String, String>> formattedList = new ArrayList<>();

        // 逐行处理报表数据
        for (int row = 0; row <= maxRow; row++) {
            // 初始化每个单元格的数据
            String assetProject = "";
            String assetCurrentValue = "";
            String assetLastYearValue = "";
            String liabilityProject = "";
            String liabilityCurrentValue = "";
            String liabilityLastYearValue = "";

            // 查找资产项目（列0）
            for (ReportCell cell : reportCells) {
                if (cell.getRow() == row && cell.getCol() == 0) {
                    assetProject = cell.getProject();
                    break;
                }
            }

            // 查找资产期末余额（列1）
            for (ReportCell cell : reportCells) {
                if (cell.getRow() == row && cell.getCol() == 1) {
                    assetCurrentValue = cell.getProject().isEmpty() ? cell.getValue() : cell.getProject();
                    break;
                }
            }

            // 查找资产上年年末余额（列2）
            for (ReportCell cell : reportCells) {
                if (cell.getRow() == row && cell.getCol() == 2) {
                    assetLastYearValue = cell.getProject().isEmpty() ? cell.getValue() : cell.getProject();
                    break;
                }
            }

            // 查找负债和所有者权益项目（列3）
            for (ReportCell cell : reportCells) {
                if (cell.getRow() == row && cell.getCol() == 3) {
                    liabilityProject = cell.getProject();
                    break;
                }
            }

            // 查找负债和所有者权益期末余额（列4）
            for (ReportCell cell : reportCells) {
                if (cell.getRow() == row && cell.getCol() == 4) {
                    liabilityCurrentValue = cell.getProject().isEmpty() ? cell.getValue() : cell.getProject();
                    break;
                }
            }

            // 查找负债和所有者权益上年年末余额（列5）
            for (ReportCell cell : reportCells) {
                if (cell.getRow() == row && cell.getCol() == 5) {
                    liabilityLastYearValue = cell.getProject().isEmpty() ? cell.getValue() : cell.getProject();
                    break;
                }
            }

            // 打印该行数据（如果至少有一个资产或负债项目不为空）
            if (!assetProject.isEmpty() || !liabilityProject.isEmpty()) {
                Map<String, String> rowData = new HashMap<>();
                rowData.put("rowIndex", String.valueOf(row));
                rowData.put("assetProject", assetProject);
                rowData.put("assetCurrentValue", assetCurrentValue);
                rowData.put("assetLastYearValue", assetLastYearValue);
                rowData.put("liabilityProject", liabilityProject);
                rowData.put("liabilityCurrentValue", liabilityCurrentValue);
                rowData.put("liabilityLastYearValue", liabilityLastYearValue);
                formattedList.add(rowData);
            }
        }

        ArrayList<ReportData> list = new ArrayList<>();

        ArrayList<BalanceReport> list1 = new ArrayList<>();

        ArrayList<IncomeStatementReport> list2 = new ArrayList<>();


        for (int i = 0; i < formattedList.size(); i++) {
            Map<String, String> rowData = formattedList.get(i);
            if (type == 2) {
                if (i >= 4) {
                    IncomeStatementReport incomeStatementReport = new IncomeStatementReport();

                    incomeStatementReport.setIndex(i - 3);
                    incomeStatementReport.setProject(rowData.get("assetProject"));
                    incomeStatementReport.setBqje(rowData.get("assetCurrentValue"));
                    incomeStatementReport.setBnje(rowData.get("assetLastYearValue"));
                    incomeStatementReport.setSqje(rowData.get("liabilityProject"));
                    incomeStatementReport.setBillNO(billNo);
                    incomeStatementReport.setYear(year);
                    incomeStatementReport.setMonth(month);
                    incomeStatementReport.setOrg(org);
                    list2.add(incomeStatementReport);

                }
            }


        }


        return list2;

    }

    private static List<ReportCell> parseReport(JSONArray reportArray) {
        List<ReportCell> cells = new ArrayList<>();
        for (int i = 0; i < reportArray.size(); i++) {
            JSONArray cellData = reportArray.getJSONArray(i);
            int row = cellData.getInteger(0);
            int col = cellData.getInteger(1);
            String project = cellData.getString(2);
            String valueFormula = cellData.getString(3);

            // 分离值和公式（根据数据特点处理）
            String value = valueFormula.isEmpty() ? project : "";
            String formula = valueFormula;

            cells.add(new ReportCell(row, col, project, value, formula));
        }
        return cells;
    }

    /**
     * 获取多账簿日报表数据
     *
     * @param request 请求参数
     * @return 多账簿日报表数据列表
     * @throws Exception 异常
     */
    @Override
    public List<MultiDailyReportData> getMultiDailyReportData(MultiDailyReportRequest request, String fieldKeys) throws Exception {
        List<MultiDailyReportData> resultList = new ArrayList<>();

        try {
            K3CloudApi client = new K3CloudApi();

            // ========== 【核心原则】前端传什么就用什么 ==========
            // 只有真正需要兜底的参数才给默认值，其他完全依赖前端传入

            // 1. 必填参数：日期必须有值，否则无法查询
            String startDate = request.getFStartDate();
            if (startDate == null || "null".equalsIgnoreCase(startDate) || startDate.trim().isEmpty()) {
                // 兜底默认值：查询当月第一天
                java.time.LocalDate now = java.time.LocalDate.now();
                startDate = now.getYear() + "-" + String.format("%02d", now.getMonthValue()) + "-01";
                System.out.println("【Service层】FStartDate未传，使用兜底默认值: " + startDate);
            }

            String endDate = request.getFEndDate();
            if (endDate == null || "null".equalsIgnoreCase(endDate) || endDate.trim().isEmpty()) {
                // 兜底默认值：查询当天
                java.time.LocalDate now = java.time.LocalDate.now();
                endDate = now.toString();
                System.out.println("【Service层】FEndDate未传，使用兜底默认值: " + endDate);
            }

            // 2. 币别：前端传什么就用什么，没传则传null让金蝶自行处理
            String currencyId = request.getFCurrencyId();
            System.out.println("【Service层】FCurrencyId: [" + currencyId + "] (前端传什么就用什么)");

            // 3. 科目级别：前端传什么就用什么
            String startBalanceLevel = request.getFStartBalanceLevel();
            String endBalanceLevel = request.getFEndBalanceLevel();
            System.out.println("【Service层】FSTARTBALANCELEVEL: " + startBalanceLevel + " (前端传什么就用什么)");
            System.out.println("【Service层】FENDBALANCELEVEL: " + endBalanceLevel + " (前端传什么就用什么)");

            // 4. 所有Boolean参数：前端传什么就用什么，不给任何默认值
            System.out.println("【Service层】Boolean参数(前端传什么就用什么):");
            System.out.println("  FSHOWACCTITEMS: " + request.getFShowAcctItems());
            System.out.println("  FNOTPOSTVOUCHER: " + request.getFNotPostVoucher());
            System.out.println("  FDetailBalance: " + request.getFDetailBalance());
            System.out.println("  FNoAmount: " + request.getFNoAmount());
            System.out.println("  FZeroAndNoBalance: " + request.getFZeroAndNoBalance());
            System.out.println("  FTotalByDay: " + request.getFTotalByDay());
            System.out.println("  FShowChildDetail: " + request.getFShowChildDetail());
            // ========== 【核心原则】参数处理完毕 ==========

            // 构建请求JSON字符串 - 完全参照测试数据格式
            StringBuilder jsonBuilder = new StringBuilder();
            
            // 使用用户提供的FieldKeys（如果提供的话），否则使用默认值
            String finalFieldKeys;
            if (fieldKeys != null && !fieldKeys.trim().isEmpty()) {
                finalFieldKeys = fieldKeys;
                System.out.println("使用用户提供的FieldKeys: " + finalFieldKeys);
            } else {
                finalFieldKeys = "FDate,FAccountNumber,FAccountName,FYDayDebitAmount,FYDayCreditAmount,FDebitAmount,FCreditAmount,FBalDebitAmount,FBalCreditAmount,FDebitCount,FCreditCount";
                System.out.println("使用默认FieldKeys: " + finalFieldKeys);
            }
            
            // ========== 构建账簿JSON ==========
            StringBuilder acctBookJson = new StringBuilder();
            int acctBookCount = 0;
            if (request.getFMultiAcctBookId() != null && !request.getFMultiAcctBookId().isEmpty()) {
                List<Organization> acctBookList = request.getFMultiAcctBookId();
                acctBookCount = acctBookList.size();
                for (int i = 0; i < acctBookList.size(); i++) {
                    Organization org = acctBookList.get(i);
                    if (org != null && org.getFnumber() != null) {
                        acctBookJson.append("{\"FNumber\":\"").append(org.getFnumber()).append("\"}");
                        if (i < acctBookList.size() - 1) {
                            acctBookJson.append(",");
                        }
                    }
                }
            }
            System.out.println("【Service层】FMULTITACCTBOOKID: " + acctBookCount + " 个账簿");
            System.out.println("【Service层】账簿JSON: " + acctBookJson.toString());

            // 构建JSON - 参照测试数据格式
            jsonBuilder.append("{");
            jsonBuilder.append("\"FieldKeys\":\"").append(finalFieldKeys).append("\",");
            jsonBuilder.append("\"SchemeId\":\"\",");
            jsonBuilder.append("\"StartRow\":0,");
            jsonBuilder.append("\"Limit\":2000,");
            jsonBuilder.append("\"IsVerifyBaseDataField\":\"true\",");  // 注意：是字符串"true"，不是布尔值
            jsonBuilder.append("\"FilterString\":[],");
            jsonBuilder.append("\"Model\":{");
            jsonBuilder.append("\"FCURRENCYID\":").append(stringToJsonValue(currencyId)).append(",");
            jsonBuilder.append("\"FSTARTDATE\":\"").append(startDate).append("\",");
            jsonBuilder.append("\"FENDDATE\":\"").append(endDate).append("\",");
            jsonBuilder.append("\"FSTARTBALANCELEVEL\":").append(stringToJsonValue(startBalanceLevel)).append(",");
            jsonBuilder.append("\"FENDBALANCELEVEL\":").append(stringToJsonValue(endBalanceLevel)).append(",");
            jsonBuilder.append("\"FSHOWACCTITEMS\":").append(boolToJsonValue(request.getFShowAcctItems())).append(",");
            jsonBuilder.append("\"FNOTPOSTVOUCHER\":").append(boolToJsonValue(request.getFNotPostVoucher())).append(",");
            jsonBuilder.append("\"FDETAILBALANCE\":").append(boolToJsonValue(request.getFDetailBalance())).append(",");
            jsonBuilder.append("\"FNOAMOUNT\":").append(boolToJsonValue(request.getFNoAmount())).append(",");
            jsonBuilder.append("\"FZEROANDNOBALANCE\":").append(boolToJsonValue(request.getFZeroAndNoBalance())).append(",");
            jsonBuilder.append("\"FMULTITACCTBOOKID\":[").append(acctBookJson.toString()).append("],");
            jsonBuilder.append("\"FSHOWACCTONEVERYCOL\":").append(boolToJsonValue(request.getFShowAcctOnEveryCol())).append(",");
            jsonBuilder.append("\"FSHOWZEROBALANCE\":").append(boolToJsonValue(request.getFShowZeroBalance())).append(",");
            jsonBuilder.append("\"FSAMEDIRECTION\":").append(boolToJsonValue(request.getFSameDirection())).append(",");
            jsonBuilder.append("\"FEXCLUDEADJUSTVCH\":").append(boolToJsonValue(request.getFExcludeAdjustVch())).append(",");
            jsonBuilder.append("\"FFORBIDBALANCE\":").append(boolToJsonValue(request.getFForbidBalance())).append(",");
            jsonBuilder.append("\"FSHOWTOTAL\":").append(boolToJsonValue(request.getFShowTotal())).append(",");
            jsonBuilder.append("\"FSHOWFULLNAME\":").append(boolToJsonValue(request.getFShowFullName())).append(",");
            jsonBuilder.append("\"FSHOWQTY\":").append(boolToJsonValue(request.getFShowQty())).append(",");
            System.out.println("【Service层】Boolean参数(前端传什么就用什么)");
            jsonBuilder.append("\"FTOTALBYDAY\":").append(boolToJsonValue(request.getFTotalByDay())).append(",");
            jsonBuilder.append("\"FCALBYDEFAULTUNIT\":").append(boolToJsonValue(request.getFCalByDefaultUnit())).append(",");
            jsonBuilder.append("\"FEXCLUDEPROFITVCH\":").append(boolToJsonValue(request.getFExcludeProfitVch())).append(",");
            jsonBuilder.append("\"FSHOWCHILDDETAIL\":").append(boolToJsonValue(request.getFShowChildDetail())).append(",");
            jsonBuilder.append("\"FBalanceRadioGrp\":\"0\",");
            jsonBuilder.append("\"FACCTBOOKRadioGrp\":\"1\"");
            jsonBuilder.append("}");
            jsonBuilder.append("}");

            String jsonData = jsonBuilder.toString();
            System.out.println("========================================");
            System.out.println("【多账簿日报表】最终发送给金蝶的请求参数:");
            System.out.println(jsonData);
            System.out.println("========================================");

            // 业务对象标识
            String formId = "GL_RPT_MultiDailyReport";

            // 调用接口
            String resultJson = client.getSysReportData(formId, jsonData);
            System.out.println("【多账簿日报表】金蝶API返回结果:");
            System.out.println(resultJson);

            // 检查金蝶API返回的错误
            if (resultJson.contains("\"IsSuccess\":false") || resultJson.contains("ResponseStatus")) {
                // 金蝶返回错误，抛出异常并附带错误信息
                String errorMsg = parseKdError(resultJson);
                throw new Exception("金蝶API调用失败: " + errorMsg);
            }

            KdResult result = JSONObject.parseObject(resultJson, KdResult.class);
            List<List<String>> rows = result.getResult().getRows();

            // 解析结果
            if (rows != null && !rows.isEmpty()) {
                // 解析FieldKeys为列索引映射
                Map<String, Integer> fieldIndexMap = new HashMap<>();
                String[] fieldArray = finalFieldKeys.split(",");
                for (int i = 0; i < fieldArray.length; i++) {
                    fieldIndexMap.put(fieldArray[i].trim(), i);
                }
                System.out.println("字段索引映射: " + fieldIndexMap);
                
                for (List<String> row : rows) {
                    MultiDailyReportData data = new MultiDailyReportData();

                    // 根据FieldKeys动态解析每一行数据
                    if (fieldIndexMap.containsKey("FDate") && row.size() > fieldIndexMap.get("FDate")) {
                        data.setFDate(getValue(row, fieldIndexMap.get("FDate")));
                    }
                    if (fieldIndexMap.containsKey("FAccountNumber") && row.size() > fieldIndexMap.get("FAccountNumber")) {
                        data.setFAccountNumber(getValue(row, fieldIndexMap.get("FAccountNumber")));
                    }
                    if (fieldIndexMap.containsKey("FAccountName") && row.size() > fieldIndexMap.get("FAccountName")) {
                        data.setFAccountName(getValue(row, fieldIndexMap.get("FAccountName")));
                    }
                    if (fieldIndexMap.containsKey("FYDayDebitAmount") && row.size() > fieldIndexMap.get("FYDayDebitAmount")) {
                        data.setFYDayDebitAmount(getValue(row, fieldIndexMap.get("FYDayDebitAmount")));
                    }
                    if (fieldIndexMap.containsKey("FYDayCreditAmount") && row.size() > fieldIndexMap.get("FYDayCreditAmount")) {
                        data.setFYDayCreditAmount(getValue(row, fieldIndexMap.get("FYDayCreditAmount")));
                    }
                    if (fieldIndexMap.containsKey("FDebitAmount") && row.size() > fieldIndexMap.get("FDebitAmount")) {
                        data.setFDebitAmount(getValue(row, fieldIndexMap.get("FDebitAmount")));
                    }
                    if (fieldIndexMap.containsKey("FCreditAmount") && row.size() > fieldIndexMap.get("FCreditAmount")) {
                        data.setFCreditAmount(getValue(row, fieldIndexMap.get("FCreditAmount")));
                    }
                    if (fieldIndexMap.containsKey("FBalDebitAmount") && row.size() > fieldIndexMap.get("FBalDebitAmount")) {
                        data.setFBalDebitAmount(getValue(row, fieldIndexMap.get("FBalDebitAmount")));
                    }
                    if (fieldIndexMap.containsKey("FBalCreditAmount") && row.size() > fieldIndexMap.get("FBalCreditAmount")) {
                        data.setFBalCreditAmount(getValue(row, fieldIndexMap.get("FBalCreditAmount")));
                    }
                    if (fieldIndexMap.containsKey("FDebitCount") && row.size() > fieldIndexMap.get("FDebitCount")) {
                        data.setFDebitCount(getValue(row, fieldIndexMap.get("FDebitCount")));
                    }
                    if (fieldIndexMap.containsKey("FCreditCount") && row.size() > fieldIndexMap.get("FCreditCount")) {
                        data.setFCreditCount(getValue(row, fieldIndexMap.get("FCreditCount")));
                    }

                    resultList.add(data);
                }
            }

            System.out.println("多账簿日报表数据解析完成，共 " + resultList.size() + " 条数据");

        } catch (Exception e) {
            System.err.println("获取多账簿日报表数据时发生错误: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("获取多账簿日报表数据失败", e);
        }

        return resultList;
    }

    /**
     * 安全获取列表元素值
     *
     * @param list  列表
     * @param index 索引
     * @return 值或空字符串
     */
    private String getValue(List<String> list, int index) {
        if (list != null && index >= 0 && index < list.size()) {
            String value = list.get(index);
            return value != null ? value : "";
        }
        return "";
    }

    /**
     * Boolean转换为JSON字符串
     * @param value Boolean值（可能为null）
     * @return "true"、"false" 或 "null"
     */
    private String boolToJsonValue(Boolean value) {
        if (value != null) {
            return value ? "true" : "false";
        }
        return "null";
    }

    /**
     * String转换为JSON字符串值
     * @param value 字符串值（可能为null或空）
     * @return 格式化的JSON字符串值
     */
    private String stringToJsonValue(String value) {
        if (value == null) {
            return "null";
        }
        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            return "null";
        }
        return "\"" + escapedJsonString(trimmed) + "\"";
    }

    /**
     * JSON字符串转义处理
     */
    private String escapedJsonString(String value) {
        return value.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
    }

    /**
     * 解析金蝶API返回的错误信息
     * 
     * @param resultJson 金蝶返回的JSON
     * @return 错误信息
     */
    private String parseKdError(String resultJson) {
        try {
            JSONObject result = JSONObject.parseObject(resultJson);
            
            // 尝试解析 Result -> ResponseStatus 格式（金蝶常见格式）
            if (result.containsKey("Result")) {
                JSONObject resultObj = result.getJSONObject("Result");
                if (resultObj != null) {
                    // 检查是否有 ResponseStatus
                    if (resultObj.containsKey("ResponseStatus")) {
                        JSONObject responseStatus = resultObj.getJSONObject("ResponseStatus");
                        if (responseStatus != null) {
                            String msgCode = responseStatus.getString("MsgCode");
                            JSONArray errors = responseStatus.getJSONArray("Errors");
                            if (errors != null && !errors.isEmpty()) {
                                JSONObject error = errors.getJSONObject(0);
                                String message = error.getString("Message");
                                return message + " (错误代码: " + msgCode + ")";
                            }
                            String msg = responseStatus.getString("Message");
                            if (msg != null && !msg.isEmpty()) {
                                return msg + " (错误代码: " + msgCode + ")";
                            }
                        }
                    }
                    
                    // 检查 Result 级别的错误信息
                    String msgCode = resultObj.getString("MsgCode");
                    String message = resultObj.getString("Message");
                    if (message != null && !message.isEmpty()) {
                        return message + " (错误代码: " + msgCode + ")";
                    }
                }
            }
            
            // 尝试直接解析 ResponseStatus 格式
            if (result.containsKey("ResponseStatus")) {
                JSONObject responseStatus = result.getJSONObject("ResponseStatus");
                if (responseStatus != null) {
                    String msgCode = responseStatus.getString("MsgCode");
                    JSONArray errors = responseStatus.getJSONArray("Errors");
                    if (errors != null && !errors.isEmpty()) {
                        JSONObject error = errors.getJSONObject(0);
                        String message = error.getString("Message");
                        return message + " (错误代码: " + msgCode + ")";
                    }
                }
            }
            
            return "未知错误";
        } catch (Exception e) {
            return "解析错误失败: " + e.getMessage();
        }
    }

    /**
     * 将数组格式的数据转换为对象格式
     * @param arrayData 数组格式的数据
     * @param fieldKeys 字段键，逗号分隔
     * @return 对象格式的数据列表
     */
    private List<Map<String, Object>> convertToObjectFormat(List<List<Object>> arrayData, String fieldKeys) {
        if (arrayData == null || arrayData.isEmpty()) {
            return new ArrayList<>();
        }
        
        // 解析字段键
        String[] fields = fieldKeys.split(",");
        List<String> fieldList = new ArrayList<>();
        for (String field : fields) {
            fieldList.add(field.trim());
        }
        
        List<Map<String, Object>> result = new ArrayList<>();
        
        for (List<Object> row : arrayData) {
            Map<String, Object> objectRow = new LinkedHashMap<>();
            
            for (int i = 0; i < fieldList.size() && i < row.size(); i++) {
                String fieldName = fieldList.get(i);
                Object value = row.get(i);
                
                // 简化字段名
                String simplifiedName = fieldName.replaceAll("^F", "").toLowerCase();
                simplifiedName = camelToSnake(simplifiedName);
                
                objectRow.put(simplifiedName, value);
            }
            
            result.add(objectRow);
        }
        
        return result;
    }
    
    /**
     * 将驼峰式命名转换为下划线式命名
     */
    private String camelToSnake(String str) {
        return str.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }
    
    /**
     * 将 Map 转换回 List（用于兼容旧格式）
     */
    private List<Object> convertMapToList(Map<String, Object> map) {
        return new ArrayList<>(map.values());
    }
}
