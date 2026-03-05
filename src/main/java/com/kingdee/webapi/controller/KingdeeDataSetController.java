package com.kingdee.webapi.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingdee.webapi.model.Financial;
import com.kingdee.webapi.model.FinancialData;
import com.kingdee.webapi.model.MultiDailyReportData;
import com.kingdee.webapi.model.MultiDailyReportRequest;
import com.kingdee.webapi.model.Organization;
import com.kingdee.webapi.service.CacheService;
import com.kingdee.webapi.service.KingdeeDataSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map;

/**
 * 金蝶数据集控制器
 * 
 * @author Kingdee
 * @version 1.0.0
 */
@Api(tags = "K3Cloud报表接口")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/kingdeedataset")
public class KingdeeDataSetController {

    @Autowired
    private CacheService cacheService;
    
    @Autowired
    private KingdeeDataSetService kingdeeDataSetService;

    /**
     * 获取科目余额表数据(V2)
     * GET /kingdeedataset/account-balance-v2
     *
     * @param request 请求对象
     * @return 科目余额表数据
     * @throws Exception 异常
     */
    @ApiOperation(value = "科目余额表(V2)", notes = "查询科目余额表数据（通过数据库直接查询），兼容旧版接口")
    @GetMapping("/account-balance-v2")
    public Object getkmyedata(ServletRequest request) throws Exception {
        String kmyeb = cacheService.get("KMYEB");
        kmyeb = null; // 暂时禁用缓存，直接返回数据
        
        if (kmyeb != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<FinancialData> list1 = objectMapper.readValue(kmyeb, new TypeReference<List<FinancialData>>() {});
            Financial financial = new Financial();
            financial.setList(list1);
            return financial;
        } else {
            Financial financial = kingdeeDataSetService.getkmyedata();
           // cacheService.add("KMYEB", financial.getList());
            return financial;
        }
    }

    /**
     * 获取财务报表数据
     * GET /kingdeedataset/financial-statements
     *
     * @param request 请求对象
     * @return 财务报表数据（利润表、资产负债表、现金流量表）
     * @throws Exception 异常
     */
    @ApiOperation(value = "财务报表", notes = "查询利润表、资产负债表、现金流量表数据")
    @GetMapping("/financial-statements")
    public Object report(ServletRequest request) throws Exception {
        return   kingdeeDataSetService.report();
    }

    /**
     * 获取多账簿日报表数据（使用原始JSON字符串接收）
     * POST /kingdeedataset/multi-daily
     */
    @ApiOperation(value = "多账簿日报表", notes = "查询多账簿的日报表数据，支持按日期、科目、账簿等条件筛选")
    @PostMapping(value = "/multi-daily", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getMultiDailyReportRaw(@RequestBody String rawJson) throws Exception {
        System.out.println("===== 【原始JSON端点】多账簿日报表请求开始 =====");
        System.out.println("原始JSON: " + rawJson);
        
        // 手动解析JSON（使用大写下划线格式）
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(rawJson);
        
        // 创建请求对象
        MultiDailyReportRequest request = new MultiDailyReportRequest();
        
        // 检查是否有嵌套的Model节点（金蝶API的完整格式）
        JsonNode modelNode = root.has("Model") ? root.get("Model") : root;
        JsonNode rootNode = root.has("Model") ? root : null;  // 用于获取FieldKeys
        
        System.out.println("解析的节点: " + (root.has("Model") ? "Model" : "Root"));
        
        // 提取FieldKeys（从根节点获取）
        String fieldKeys = null;
        if (rootNode != null && rootNode.has("FieldKeys")) {
            fieldKeys = rootNode.get("FieldKeys").asText();
        } else if (root.has("FieldKeys")) {
            fieldKeys = root.get("FieldKeys").asText();
        }
        System.out.println("FieldKeys: " + fieldKeys);
        
        // 解析所有字段（从Model节点中获取）
        if (modelNode.has("FSTARTDATE")) {
            request.setFStartDate(modelNode.get("FSTARTDATE").asText());
        }
        if (modelNode.has("FENDDATE")) {
            request.setFEndDate(modelNode.get("FENDDATE").asText());
        }
        if (modelNode.has("FCURRENCYID")) {
            request.setFCurrencyId(modelNode.get("FCURRENCYID").asText());
        }
        
        // 解析FSchemeID
        if (modelNode.has("FSCHEMEID") && !modelNode.get("FSCHEMEID").isNull()) {
            JsonNode schemeNode = modelNode.get("FSCHEMEID");
            if (schemeNode.has("FNUMBER")) {
                Organization org = new Organization();
                org.setFnumber(schemeNode.get("FNUMBER").asText());
                request.setFSchemeID(org);
            }
        }
        
        // 解析FMultiAcctBookId列表
        if (modelNode.has("FMULTITACCTBOOKID") && modelNode.get("FMULTITACCTBOOKID").isArray()) {
            JsonNode acctBookArray = modelNode.get("FMULTITACCTBOOKID");
            List<Organization> acctBookList = mapper.readValue(
                acctBookArray.toString(), 
                new TypeReference<List<Organization>>() {}
            );
            request.setFMultiAcctBookId(acctBookList);
        }
        
        // 解析Boolean类型字段
        if (modelNode.has("FUSEACCTBOOKGROUP")) {
            request.setFUseAcctBookGroup(modelNode.get("FUSEACCTBOOKGROUP").asText());
        }
        if (modelNode.has("FUSEACCTBOOKMERGEGROUP")) {
            request.setFUseAcctBookMergeGroup(modelNode.get("FUSEACCTBOOKMERGEGROUP").asText());
        }
        if (modelNode.has("FBALANCE")) {
            request.setFBalance(modelNode.get("FBALANCE").asText());
        }
        if (modelNode.has("FSTARTBALANCE") && !modelNode.get("FSTARTBALANCE").isNull()) {
            JsonNode node = modelNode.get("FSTARTBALANCE");
            if (node.has("FNUMBER")) {
                Organization org = new Organization();
                org.setFnumber(node.get("FNUMBER").asText());
                request.setFStartBalance(org);
            }
        }
        if (modelNode.has("FENDBALANCE") && !modelNode.get("FENDBALANCE").isNull()) {
            JsonNode node = modelNode.get("FENDBALANCE");
            if (node.has("FNUMBER")) {
                Organization org = new Organization();
                org.setFnumber(node.get("FNUMBER").asText());
                request.setFEndBalance(org);
            }
        }
        if (modelNode.has("FNOCONBALANCE")) {
            request.setFNoConBalance(modelNode.get("FNOCONBALANCE").asText());
        }
        if (modelNode.has("FCONBALANCE")) {
            request.setFConBalance(modelNode.get("FCONBALANCE").asText());
        }
        if (modelNode.has("FSTARTBALANCELEVEL")) {
            request.setFStartBalanceLevel(modelNode.get("FSTARTBALANCELEVEL").asText());
        }
        if (modelNode.has("FENDBALANCELEVEL")) {
            request.setFEndBalanceLevel(modelNode.get("FENDBALANCELEVEL").asText());
        }
        if (modelNode.has("FSHOWACCTITEMS")) {
            request.setFShowAcctItems(modelNode.get("FSHOWACCTITEMS").asBoolean());
        }
        if (modelNode.has("FNOTPOSTVOUCHER")) {
            request.setFNotPostVoucher(modelNode.get("FNOTPOSTVOUCHER").asBoolean());
        }
        if (modelNode.has("FDETAILBALANCE")) {
            request.setFDetailBalance(modelNode.get("FDETAILBALANCE").asBoolean());
        }
        if (modelNode.has("FNOAMOUNT")) {
            request.setFNoAmount(modelNode.get("FNOAMOUNT").asBoolean());
        }
        if (modelNode.has("FLEVEL")) {
            request.setFLevel(modelNode.get("FLEVEL").asText());
        }
        if (modelNode.has("FLEVELID")) {
            request.setFLevelId(modelNode.get("FLEVELID").asText());
        }
        if (modelNode.has("FZEROANDNOBALANCE")) {
            request.setFZeroAndNoBalance(modelNode.get("FZEROANDNOBALANCE").asBoolean());
        }
        if (modelNode.has("FSHOWACCTONEVERYCOL")) {
            request.setFShowAcctOnEveryCol(modelNode.get("FSHOWACCTONEVERYCOL").asBoolean());
        }
        if (modelNode.has("FSHOWZEROBALANCE")) {
            request.setFShowZeroBalance(modelNode.get("FSHOWZEROBALANCE").asBoolean());
        }
        if (modelNode.has("FSAMEDIRECTION")) {
            request.setFSameDirection(modelNode.get("FSAMEDIRECTION").asBoolean());
        }
        if (modelNode.has("FEXCLUDEADJUSTVCH")) {
            request.setFExcludeAdjustVch(modelNode.get("FEXCLUDEADJUSTVCH").asBoolean());
        }
        if (modelNode.has("FFORBIDBALANCE")) {
            request.setFForbidBalance(modelNode.get("FFORBIDBALANCE").asBoolean());
        }
        if (modelNode.has("FSHOWTOTAL")) {
            request.setFShowTotal(modelNode.get("FSHOWTOTAL").asBoolean());
        }
        if (modelNode.has("FSHOWFULLNAME")) {
            request.setFShowFullName(modelNode.get("FSHOWFULLNAME").asBoolean());
        }
        if (modelNode.has("FSHOWQTY")) {
            request.setFShowQty(modelNode.get("FSHOWQTY").asBoolean());
        }
        if (modelNode.has("FTOTALBYDAY")) {
            request.setFTotalByDay(modelNode.get("FTOTALBYDAY").asBoolean());
        }
        if (modelNode.has("FCALBYDEFAULTUNIT")) {
            request.setFCalByDefaultUnit(modelNode.get("FCALBYDEFAULTUNIT").asBoolean());
        }
        if (modelNode.has("FEXCLUDEPROFITVCH")) {
            request.setFExcludeProfitVch(modelNode.get("FEXCLUDEPROFITVCH").asBoolean());
        }
        if (modelNode.has("FSHOWCHILDDETAIL")) {
            request.setFShowChildDetail(modelNode.get("FSHOWCHILDDETAIL").asBoolean());
        }
        
        System.out.println("解析后的请求对象: " + request);
        System.out.println("FStartDate: " + request.getFStartDate());
        System.out.println("FEndDate: " + request.getFEndDate());
        System.out.println("FCurrencyId: " + request.getFCurrencyId());
        System.out.println("FMultiAcctBookId列表大小: " + (request.getFMultiAcctBookId() != null ? request.getFMultiAcctBookId().size() : "null"));
        if (request.getFMultiAcctBookId() != null && !request.getFMultiAcctBookId().isEmpty()) {
            System.out.println("账簿编号: " + request.getFMultiAcctBookId().get(0).getFnumber());
        }
        System.out.println("===== 【原始JSON端点】请求参数打印完毕 =====");
        
        // 将FieldKeys传递给Service
        return kingdeeDataSetService.getMultiDailyReportData(request, fieldKeys);
    }
}
