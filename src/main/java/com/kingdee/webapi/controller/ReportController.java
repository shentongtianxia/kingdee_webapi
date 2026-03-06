package com.kingdee.webapi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import com.kingdee.webapi.common.KdResult;
import com.kingdee.webapi.model.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;

/**
 * 报表相关接口
 */
@RestController
@RequestMapping("/api/k3cloud/reports")
@Api(tags = "K3Cloud报表接口")
public class ReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @PostConstruct
    public void init() {
        logger.info("ReportController initialized and Swagger tag registered: K3Cloud报表接口");
    }

    @ApiOperation(value = "查询科目余额表", notes = "根据请求参数查询科目余额表并返回数据列表\n\n示例请求:\n{\n  \"acctBookNumber\": \"001\",\n  \"currency\": \"1\",\n  \"startYear\": \"2025\",\n  \"startPeriod\": \"1\",\n  \"endYear\": \"2025\",\n  \"endPeriod\": \"1\",\n  \"balanceLevel\": \"1\",\n  \"showDetail\": false,\n  \"balanceZero\": true,\n  \"showFlexByCol\": true\n}\n\n示例响应:\n{\n  \"list\": [\n    {\n      \"科目编码\": \"1001\",\n      \"科目名称\": \"现金\",\n      \"币别\": \"CNY\",\n      \"年初余额-原币（借）\": \"1000.00\",\n      \"期末余额-原币（借）\": \"1200.00\",\n      \"年份\": \"2025\",\n      \"月份\": \"1\",\n      \"组织名字\": \"总公司\"\n    }\n  ]\n}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Financial.class),
            @ApiResponse(code = 500, message = "服务器内部错误")
    })
    @PostMapping(value = "/account-balance", consumes = "application/json", produces = "application/json")
    public Object accountBalance(@RequestBody String rawJson) throws Exception {
        K3CloudApi client = new K3CloudApi();

        System.err.println("===== 科目余额表原始JSON =====");
        System.err.println(rawJson);

        // 直接使用原始JSON发送给金蝶（和多账簿日报表一样）
        String formId = "GL_RPT_AccountBalance";
        String resultJson = client.getSysReportData(formId, rawJson);
        System.err.println("===== 金蝶返回结果 =====");
        System.err.println(resultJson);

        // 解析请求JSON获取FieldKeys
        JSONObject requestObj = JSON.parseObject(rawJson);
        String fieldKeysStr = requestObj.getString("FieldKeys");

        // 建立FieldKey到中文字段名的映射
        HashMap<String, String> fieldKeyToChinese = getAccountBalanceFieldKeyMapping();

        // 解析FieldKeys
        String[] fieldKeys = fieldKeysStr != null ? fieldKeysStr.split(",") : new String[0];

        KdResult kdResult = JSON.parseObject(resultJson, KdResult.class);
        List<List<String>> rows = (kdResult.getResult() != null && kdResult.getResult().getRows() != null)
                ? kdResult.getResult().getRows()
                : new ArrayList<>();

        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        for (List<String> r : rows) {
            HashMap<String, String> data = new HashMap<>();
            // 动态根据FieldKeys顺序映射
            for (int i = 0; i < r.size() && i < fieldKeys.length; i++) {
                String fk = fieldKeys[i].trim();
                String chineseName = fieldKeyToChinese.get(fk);
                if (chineseName != null) {
                    data.put(chineseName, getSafe(r, i));
                } else {
                    // 如果没有映射，使用原始FieldKey
                    data.put(fk, getSafe(r, i));
                }
            }
            list.add(data);
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("list", list);
        return result;
    }

    /**
     * 科目余额表FieldKey到中文字段名的映射
     */
    private HashMap<String, String> getAccountBalanceFieldKeyMapping() {
        HashMap<String, String> map = new HashMap<>();
        // 科目基本信息
        map.put("FBALANCEID", "科目编码");
        map.put("FBALANCENAME", "科目名称");
        map.put("FACCTTYPE", "科目类别");
        map.put("FACCTGROUP", "会计要素");
        map.put("FDETAILNUMBER", "核算维度编码");
        map.put("FDETAILNAME", "核算维度名称");
        map.put("FCyName", "币别");
        // 年初余额
        map.put("FBEGINYEARDEBIT", "年初余额-原币（借）");
        map.put("FBEGINYEARDEBITLOCAL", "年初余额-本位币（借）");
        map.put("FBEGINYEARCREDIT", "年初余额-原币（贷）");
        map.put("FBEGINYEARCREDITLOCAL", "年初余额-本位币（贷）");
        // 期初余额
        map.put("FBEGINDEBIT", "期初余额-原币（借）");
        map.put("FBEGINDEBITLOCAL", "期初余额-本位币（借）");
        map.put("FBEGINCREDIT", "期初余额-原币（贷）");
        map.put("FBEGINCREDITLOCAL", "期初余额-本位币（贷）");
        // 本期发生
        map.put("FDEBIT", "本期发生-原币（借）");
        map.put("FDEBITLOCAL", "本期发生-本位币（借）");
        map.put("FCREDIT", "本期发生-原币（贷）");
        map.put("FCREDITLOCAL", "本期发生-本位币（贷）");
        // 本年累计
        map.put("FYTDDEBIT", "本年累计-原币（借）");
        map.put("FYTDDEBITLOCAL", "本年累计-本位币（借）");
        map.put("FYTDCREDIT", "本年累计-原币（贷）");
        map.put("FYTDCREDITLOCAL", "本年累计-本位币（贷）");
        // 期末余额
        map.put("FENDDEBIT", "期末余额-原币（借）");
        map.put("FENDDEBITLOCAL", "期末余额-本位币（借）");
        map.put("FENDCREDIT", "期末余额-原币（贷）");
        map.put("FENDCREDITLOCAL", "期末余额-本位币（贷）");
        // 其他
        map.put("FACCTDC", "方向");
        map.put("FPROFIT", "本期实际损益发生额-原币");
        map.put("FPROFITLOCAL", "本期实际损益发生额-本位币");
        map.put("FYTDPROFIT", "本年累计实际损益发生额-原币");
        map.put("FYTDPROFITLOCAL", "本年累计实际损益发生额-本位币");
        return map;
    }

    @ApiOperation(value = "现金日报表", notes = "根据请求参数查询现金日报表并返回数据列表\n\n" +
            "【请求参数说明】\n\n" +
            "FOrgID: 收付组织列表，示例: [{\"FNumber\":\"100\"}]\n" +
            "FCurrencyIds: 币别，示例: \"1\"\n" +
            "FStartDate: 起始日期（必填，格式：yyyy-MM-dd），示例: \"2025-01-01\"\n" +
            "FEndDate: 结束日期（必填，格式：yyyy-MM-dd），示例: \"2025-01-31\"\n" +
            "FNotAudit: 是否包含未审核单据，示例: false\n" +
            "FMyCurrency: 是否显示本位币，示例: false\n" +
            "FMyPayOrg: 是否按收付组织分项显示，示例: false\n" +
            "FAffiliation: 隶属方案，示例: {\"FNumber\":\"\"}\n" +
            "FAllAcct: 是否所有账号，示例: false\n" +
            "FMyCurrencySum: 是否显示本位币合计，示例: false\n" +
            "FCASHACCTID: 现金账号列表，示例: [{\"FNumber\":\"CASH1\"}]\n" +
            "FCurrencySubTotal: 是否显示币别小计，示例: false\n\n" +
            "【响应字段说明】\n\n" +
            "FDate: 日期\n" +
            "FPAYORGNAME: 收付组织\n" +
            "FBankAcctName: 账户名称\n" +
            "FBankAcctId: 现金账号\n" +
            "FForCurrencyName: 币别（原币）\n" +
            "FForLastBal: 昨日余额（原币）\n" +
            "FForTodayIn: 今日收入（原币）\n" +
            "FForTodayOut: 今日支出（原币）\n" +
            "FForTodayBal: 今日余额（原币）\n" +
            "FLocalCurrencyName: 币别（本位币）\n" +
            "FLocalLastBal: 昨日余额（本位币）\n" +
            "FLocalTodayIn: 今日收入（本位币）\n" +
            "FLocalTodayOut: 今日支出（本位币）\n" +
            "FLocalTodayBal: 今日余额（本位币）\n" +
            "FInCount: 收入笔数\n" +
            "FOutCount: 支出笔数\n\n" +
            "【示例请求】\n" +
            "{\n" +
            "  \"FOrgID\": [{\"FNumber\":\"100\"}],\n" +
            "  \"FCurrencyIds\": \"1\",\n" +
            "  \"FStartDate\": \"2025-01-01\",\n" +
            "  \"FEndDate\": \"2025-01-31\",\n" +
            "  \"FNotAudit\": false,\n" +
            "  \"FMyCurrency\": false,\n" +
            "  \"FMyPayOrg\": false,\n" +
            "  \"FAffiliation\": {\"FNumber\":\"\"},\n" +
            "  \"FAllAcct\": false,\n" +
            "  \"FMyCurrencySum\": false,\n" +
            "  \"FCASHACCTID\": [{\"FNumber\":\"CASH1\"}],\n" +
            "  \"FCurrencySubTotal\": false\n" +
            "}\n\n" +
            "【示例响应】\n" +
            "{\n" +
            "  \"list\": [\n" +
            "    {\n" +
            "      \"FDate\": \"2025-01-15\",\n" +
            "      \"FPAYORGNAME\": \"总公司\",\n" +
            "      \"FBankAcctName\": \"库存现金\",\n" +
            "      \"FBankAcctId\": \"CASH1\",\n" +
            "      \"FForCurrencyName\": \"人民币\",\n" +
            "      \"FForLastBal\": \"1000.00\",\n" +
            "      \"FForTodayIn\": \"500.00\",\n" +
            "      \"FForTodayOut\": \"200.00\",\n" +
            "      \"FForTodayBal\": \"1300.00\",\n" +
            "      \"FLocalCurrencyName\": \"人民币\",\n" +
            "      \"FLocalLastBal\": \"1000.00\",\n" +
            "      \"FLocalTodayIn\": \"500.00\",\n" +
            "      \"FLocalTodayOut\": \"200.00\",\n" +
            "      \"FLocalTodayBal\": \"1300.00\",\n" +
            "      \"FInCount\": 2,\n" +
            "      \"FOutCount\": 1\n" +
            "    }\n" +
            "  ]\n" +
            "}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Financial.class),
            @ApiResponse(code = 500, message = "服务器内部错误")
    })
    @PostMapping(value = "/cash-daily", consumes = "application/json", produces = "application/json")
    public Object cashDaily(@RequestBody String rawJson) throws Exception {
        K3CloudApi client = new K3CloudApi();

        System.err.println("===== 现金日报表原始JSON =====");
        System.err.println(rawJson);

        // 直接使用原始JSON发送给金蝶（和多账簿日报表一样）
        String formId = "CN_CashDailyReport";
        String resultJson = client.getSysReportData(formId, rawJson);
        System.err.println("===== 金蝶返回结果 =====");
        System.err.println(resultJson);

        // 解析请求JSON获取FieldKeys
        JSONObject requestObj = JSON.parseObject(rawJson);
        String fieldKeysStr = requestObj.getString("FieldKeys");

        // 建立FieldKey到中文字段名的映射
        HashMap<String, String> fieldKeyToChinese = getCashDailyFieldKeyMapping();

        // 解析FieldKeys
        String[] fieldKeys = fieldKeysStr != null ? fieldKeysStr.split(",") : new String[0];

        KdResult kdResult = JSON.parseObject(resultJson, KdResult.class);
        List<List<String>> rows = (kdResult.getResult() != null && kdResult.getResult().getRows() != null)
                ? kdResult.getResult().getRows()
                : new ArrayList<>();

        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        for (List<String> r : rows) {
            HashMap<String, String> data = new HashMap<>();
            // 动态根据FieldKeys顺序映射
            for (int i = 0; i < r.size() && i < fieldKeys.length; i++) {
                String fk = fieldKeys[i].trim();
                String chineseName = fieldKeyToChinese.get(fk);
                if (chineseName != null) {
                    data.put(chineseName, getSafe(r, i));
                } else {
                    // 如果没有映射，使用原始FieldKey
                    data.put(fk, getSafe(r, i));
                }
            }
            list.add(data);
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("list", list);
        return result;
    }

    /**
     * 现金日报表FieldKey到中文字段名的映射
     */
    private HashMap<String, String> getCashDailyFieldKeyMapping() {
        HashMap<String, String> map = new HashMap<>();
        // 日期和基本信息
        map.put("FDate", "日期");
        map.put("FPAYORGNAME", "收付组织");
        map.put("FBankAcctName", "账户名称");
        map.put("FBankAcctId", "现金账号");
        // 原币信息
        map.put("FForCurrencyName", "币别（原币）");
        map.put("FForLastBal", "昨日余额（原币）");
        map.put("FForTodayIn", "今日收入（原币）");
        map.put("FForTodayOut", "今日支出（原币）");
        map.put("FForTodayBal", "今日余额（原币）");
        // 本位币信息
        map.put("FLocalCurrencyName", "币别（本位币）");
        map.put("FLocalLastBal", "昨日余额（本位币）");
        map.put("FLocalTodayIn", "今日收入（本位币）");
        map.put("FLocalTodayOut", "今日支出（本位币）");
        map.put("FLocalTodayBal", "今日余额（本位币）");
        // 笔数
        map.put("FInCount", "收入笔数");
        map.put("FOutCount", "支出笔数");
        return map;
    }

    /**
     * 销售订单执行明细表FieldKey到中文字段名的映射
     */
    private HashMap<String, String> getSaleOrderDetailFieldKeyMapping() {
        HashMap<String, String> map = new HashMap<>();
        // 基本信息
        map.put("FSALEORGNAME", "销售组织");
        map.put("FBILLNO", "销售订单编号");
        map.put("FBILLTYPE", "单据类型");
        map.put("FDate", "日期");
        map.put("FSALEDEPTID", "销售部门");
        map.put("FSALEGROUP", "销售组");
        map.put("FSALES", "销售员");
        map.put("FCUSTID", "客户编码");
        map.put("FCUSTOMERNAME", "客户名称");
        map.put("FCustGroupName", "客户分组");
        map.put("FRecCondition", "收款条件");
        map.put("FORDERID", "订单编号");
        map.put("FDELIVERYDAY", "交货日期");
        // 物料信息
        map.put("FMATERIALID", "产品编码");
        map.put("FMATERIALNAME", "产品名称");
        map.put("FMAPID", "套件编号");
        map.put("FMAPNAME", "套件名称");
        map.put("FSPECIFICATION", "规格型号");
        map.put("FERPCLSID", "产品分类");
        map.put("FMATERIALGROUPNAME", "物料组");
        map.put("FCATEGORYID", "存货类别");
        map.put("FAUXPROPID", "辅助属性");
        map.put("FUNIT", "单位");
        map.put("FPRICEUNIT", "计价单位");
        map.put("FIsFree", "是否赠品");
        map.put("FCURRENCYID", "币别");
        map.put("FLOCALCURRID", "本位币");
        // 金额字段
        map.put("FPrice", "单价");
        map.put("FPRICE_LC", "单价（本位币）");
        map.put("FCharge", "含税单价");
        map.put("FCHARGE_LC", "含税单价（本位币）");
        map.put("FORDERQTY", "订单数量");
        map.put("FOrderChargeQty", "订单含税数量");
        map.put("FORDERAMOUNT", "订单金额");
        map.put("FORDERAMOUNT_LC", "订单金额（本位币）");
        // 销售出库
        map.put("FDELIBILLNO", "发货通知单编号");
        map.put("FDELIQTY", "发货数量");
        map.put("FDELICHARGEQTY", "发货含税数量");
        map.put("FDELIAMOUNT", "发货金额");
        map.put("FDELIAMOUNT_LC", "发货金额（本位币）");
        map.put("FOUTBILLNO", "销售出库单编号");
        map.put("FOUTSTOCKDATE", "出库日期");
        map.put("FOUTQTY", "出库数量");
        map.put("FOUTCHARGEQTY", "出库含税数量");
        map.put("FOUTAMOUNT", "出库金额");
        map.put("FOUTAMOUNT_LC", "出库金额（本位币）");
        // 销售退货
        map.put("FRETURNPRDBILLNO", "退货通知单编号");
        map.put("FRETURNPQTY", "退货数量");
        map.put("FRETURNPCHARGEQTY", "退货含税数量");
        map.put("FRETURNPAMOUNT", "退货金额");
        map.put("FRETURNPAMOUNT_LC", "退货金额（本位币）");
        map.put("FRETURNBILLNO", "销售退货单编号");
        map.put("FRETURNSTOCKDATE", "退货日期");
        map.put("FRETURNQTY", "退货数量");
        map.put("FRETURNAMOUNT", "退货金额");
        map.put("FRETURNAMOUNT_LC", "退货金额（本位币）");
        // 应收
        map.put("FRECEIVEBILLNO", "应收单编号");
        map.put("FARDate", "应收日期");
        map.put("FSetAccountType", "应收类型");
        map.put("FRECQTY", "应收数量");
        map.put("FRECAMOUNT", "应收金额");
        map.put("FWriteOffAmount", "核销金额");
        map.put("FINVOECEBILLNO", "发票编号");
        map.put("FINVOECEQTY", "开票数量");
        map.put("FINVOECEAMOUNT", "开票金额");
        map.put("FRECEIPTAMOUNT", "收款金额");
        map.put("FJSWRITEOFFAMOUNT", "即征即退核销金额");
        map.put("FChargeOffAmount", "扣减核销金额");
        map.put("FALLMATCHAMOUNT", "完全核销金额");
        map.put("FORDERQTYTEMP", "订单数量（含容器）");
        map.put("FORDERAMOUNTTEMP", "订单金额（含容器）");
        return map;
    }

    @ApiOperation(value = "销售出库明细表", notes = "根据请求参数查询销售出库明细表并返回数据列表\n\n" +
            "【请求参数说明】\n\n" +
            "FSaleOrgId: 销售组织（必填，格式：{\"FNumber\":\"100\"}）\n" +
            "FSettleOrgId: 结算组织（必填，格式：{\"FNumber\":\"100\"}）\n" +
            "FStartDate: 起始日期（必填，格式：yyyy-MM-dd），示例: \"2025-01-01\"\n" +
            "FEndDate: 结束日期（必填，格式：yyyy-MM-dd），示例: \"2025-01-31\"\n" +
            "FStartMaterial: 起始物料（非必填，格式：{\"FNUMBER\":\"M001\"}）\n" +
            "FEndMaterial: 结束物料（非必填，格式：{\"FNUMBER\":\"M999\"}）\n" +
            "FStartCustomer: 起始客户（非必填，格式：{\"FNumber\":\"C001\"}）\n" +
            "FEndCustomer: 结束客户（非必填，格式：{\"FNumber\":\"C999\"}）\n" +
            "FStartDepartment: 起始销售部门（非必填，格式：{\"FNUMBER\":\"D001\"}）\n" +
            "FEndDepartment: 结束销售部门（非必填，格式：{\"FNUMBER\":\"D999\"}）\n" +
            "FBillStatus: 单据状态（非必填），可选值：A(保存)、B(审核中)、C(已审核)、D(重新审核)\n" +
            "FSuite: 统计套件（非必填，格式：{\"FNumber\":\"SUITE001\"}）\n" +
            "FIsIncludeInnerExchangeBill: 包含跨组织结算单据（非必填）\n" +
            "FIsIncludeSerMat: 包含服务类物料（非必填）\n" +
            "FIsGroupCust: 集团客户（非必填）\n" +
            "FOnlyOutReturnData: 仅取出库退货单数据分析（非必填）\n" +
            "FIncludeRefundNoGoods: 包含仅退款不退货（非必填）\n\n" +
            "【响应字段说明】\n\n" +
            "FBILLNAME: 单据名称\n" +
            "FBILLTYPENAME: 单据类型\n" +
            "FBILLNUMBER: 单据编码\n" +
            "FBILLDATE: 日期\n" +
            "FApproveDate: 审核日期\n" +
            "FSALEORGNAME: 销售组织\n" +
            "FSettleOrgName: 结算组织\n" +
            "FDEPTNUMBER: 销售部门编码\n" +
            "FDEPTNAME: 销售部门名称\n" +
            "FSALERNUMBER: 销售员编码\n" +
            "FSALERNAME: 销售员\n" +
            "FSalGroupName: 销售组\n" +
            "FSETTLETYPENAME: 结算方式\n" +
            "FCUSTOMERNUMBER: 客户编码\n" +
            "FCUSTOMERID: 客户\n" +
            "FCustGroupName: 客户分组\n" +
            "FMATERIALNUMBER: 物料编码\n" +
            "FMATERIALID: 物料\n" +
            "FMAPID: 客户物料编码\n" +
            "FMAPNAME: 客户物料名称\n" +
            "FMATERIALGROUP: 物料分组\n" +
            "FERPCLSID: 物料属性\n" +
            "FCATEGORYID: 存货类别\n" +
            "FMATERIALMODEL: 规格型号\n" +
            "FSTOCKID: 仓库\n" +
            "FLOTNAME: 批号\n" +
            "FSOORDERNO: 订单单号\n" +
            "FUNITNAME: 单位\n" +
            "FOUTSTOCKQTY: 出库数量\n" +
            "FLCNOTAXOUTSTOCKPRICE: 出库单价\n" +
            "FLCTAXOUTSTOCKPRICE: 出库含税单价\n" +
            "FLCNOTAXOUTSTOCKAMOUNT: 出库金额\n" +
            "FLCTAXOUTSTOCKAMOUNT: 出库价税合计\n" +
            "FCOSTPRICE: 出库成本\n" +
            "FLCOUTSTOCKTATALCOSTAMOUNT: 出库总成本\n" +
            "FRECQTY: 应收数量\n" +
            "FLCNOTAXRECAMOUNT: 应收金额\n" +
            "FLCTAXRECAMOUNT: 应收价税合计\n" +
            "FISFREE: 是否赠品\n" +
            "FENTRYNOTE: 备注\n\n" +
            "【示例请求】\n" +
            "{\n" +
            "  \"FSaleOrgId\": {\"FNumber\": \"100\"},\n" +
            "  \"FSettleOrgId\": {\"FNumber\": \"100\"},\n" +
            "  \"FStartDate\": \"2025-01-01\",\n" +
            "  \"FEndDate\": \"2025-01-31\",\n" +
            "  \"FStartMaterial\": {\"FNUMBER\": \"\"},\n" +
            "  \"FEndMaterial\": {\"FNUMBER\": \"\"},\n" +
            "  \"FStartCustomer\": {\"FNumber\": \"\"},\n" +
            "  \"FEndCustomer\": {\"FNumber\": \"\"},\n" +
            "  \"FStartDepartment\": {\"FNUMBER\": \"\"},\n" +
            "  \"FEndDepartment\": {\"FNUMBER\": \"\"},\n" +
            "  \"FBillStatus\": \"\",\n" +
            "  \"FIsIncludeInnerExchangeBill\": false,\n" +
            "  \"FIsIncludeSerMat\": false,\n" +
            "  \"FSuite\": {\"FNumber\": \"\"},\n" +
            "  \"FIsGroupCust\": false,\n" +
            "  \"FOnlyOutReturnData\": false,\n" +
            "  \"FIncludeRefundNoGoods\": false\n" +
            "}\n\n" +
            "【示例响应】\n" +
            "{\n" +
            "  \"list\": [\n" +
            "    {\n" +
            "      \"FBILLNAME\": \"销售出库单\",\n" +
            "      \"FBILLTYPENAME\": \"标准销售出库单\",\n" +
            "      \"FBILLNUMBER\": \"XSCKD00100001\",\n" +
            "      \"FBILLDATE\": \"2025-01-15\",\n" +
            "      \"FSALEORGNAME\": \"总公司\",\n" +
            "      \"FDEPTNAME\": \"销售部\",\n" +
            "      \"FSALERNAME\": \"张三\",\n" +
            "      \"FCUSTOMERID\": \"ABC公司\",\n" +
            "      \"FMATERIALID\": \"产品A\",\n" +
            "      \"FMATERIALNUMBER\": \"A001\",\n" +
            "      \"FMATERIALMODEL\": \"规格1\",\n" +
            "      \"FUNITNAME\": \"个\",\n" +
            "      \"FOUTSTOCKQTY\": \"100.00\",\n" +
            "      \"FLCNOTAXOUTSTOCKPRICE\": \"50.00\",\n" +
            "      \"FLCNOTAXOUTSTOCKAMOUNT\": \"5000.00\",\n" +
            "      \"FLCTAXOUTSTOCKPRICE\": \"56.50\",\n" +
            "      \"FLCTAXOUTSTOCKAMOUNT\": \"5650.00\",\n" +
            "      \"FISFREE\": \"否\"\n" +
            "    }\n" +
            "  ]\n" +
            "}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = SalesOutboundResponse.class),
            @ApiResponse(code = 500, message = "服务器内部错误")
    })
    @PostMapping(value = "/sales-outbound", consumes = "application/json", produces = "application/json")
    public Object salesOutbound(@ApiParam(value = "查询条件", required = true) @RequestBody SalesOutboundRequest req) throws Exception {
        K3CloudApi client = new K3CloudApi();

        // 构建 FieldKeys（常用列）
        String fieldKeys = "FBILLNAME,FBILLTYPENAME,FBILLNUMBER,FBILLDATE,FApproveDate,FSALEORGNAME,FSettleOrgName,"
                + "FDEPTNUMBER,FDEPTNAME,FSALERNUMBER,FSALERNAME,FSalGroupName,FSETTLETYPENAME,"
                + "FCUSTOMERNUMBER,FCUSTOMERID,FCustGroupName,FMATERIALNUMBER,FMATERIALID,FMAPID,FMAPNAME,"
                + "FMATERIALGROUP,FERPCLSID,FCATEGORYID,FMATERIALMODEL,FMATERIALAUXID,FSTOCKID,FStockLocId,"
                + "FLOTNAME,FSOORDERNO,FUNITNAME,FCURRENCYNAME,FLCCURRENCYNAME,"
                + "FOUTSTOCKQTY,FLCNOTAXOUTSTOCKPRICE,FLCTAXOUTSTOCKPRICE,FLCTAXNETPRICE,"
                + "FLCNOTAXOUTSTOCKAMOUNT,FLCTAXOUTSTOCKAMOUNT,FCOSTPRICE,FLCOUTSTOCKTATALCOSTAMOUNT,"
                + "FRECQTY,FLCNOTAXRECAMOUNT,FLCTAXRECAMOUNT,FISFREE,FENTRYNOTE";

        // 构建 Model JSON
        JSONObject model = new JSONObject();
        model.put("FSaleOrgId", req.getFSaleOrgId() != null ? orgToJson(req.getFSaleOrgId()) : new JSONObject());
        model.put("FSettleOrgId", req.getFSettleOrgId() != null ? orgToJson(req.getFSettleOrgId()) : new JSONObject());
        model.put("FStartDate", safe(req.getFStartDate()));
        model.put("FEndDate", safe(req.getFEndDate()));
        model.put("FStartMaterial", req.getFStartMaterial() != null ? orgToJson(req.getFStartMaterial()) : new JSONObject());
        model.put("FEndMaterial", req.getFEndMaterial() != null ? orgToJson(req.getFEndMaterial()) : new JSONObject());
        model.put("FStartCustomer", req.getFStartCustomer() != null ? orgToJson(req.getFStartCustomer()) : new JSONObject());
        model.put("FEndCustomer", req.getFEndCustomer() != null ? orgToJson(req.getFEndCustomer()) : new JSONObject());
        model.put("FStartDepartment", req.getFStartDepartment() != null ? orgToJson(req.getFStartDepartment()) : new JSONObject());
        model.put("FEndDepartment", req.getFEndDepartment() != null ? orgToJson(req.getFEndDepartment()) : new JSONObject());
        model.put("FBillStatus", safe(req.getFBillStatus()));
        model.put("FSuite", req.getFSuite() != null ? orgToJson(req.getFSuite()) : new JSONObject());
        model.put("FIsIncludeInnerExchangeBill", boolToString(req.getFIsIncludeInnerExchangeBill()));
        model.put("FIsIncludeSerMat", boolToString(req.getFIsIncludeSerMat()));
        model.put("FIsGroupCust", boolToString(req.getFIsGroupCust()));
        model.put("FOnlyOutReturnData", boolToString(req.getFOnlyOutReturnData()));
        model.put("FIncludeRefundNoGoods", boolToString(req.getFIncludeRefundNoGoods()));

        String jsonData = "{"
                + "\"FieldKeys\":\"" + fieldKeys + "\","
                + "\"SchemeId\":\"\","
                + "\"StartRow\":0,"
                + "\"Limit\":10000,"
                + "\"IsVerifyBaseDataField\":\"true\","
                + "\"FilterString\":[],"
                + "\"Model\":" + model.toJSONString()
                + "}";

        String formId = "Sal_OutStockDetailRpt";
        String resultJson = client.getSysReportData(formId, jsonData);

        KdResult kdResult = JSON.parseObject(resultJson, KdResult.class);
        List<List<String>> rows = (kdResult.getResult() != null && kdResult.getResult().getRows() != null)
                ? kdResult.getResult().getRows()
                : new ArrayList<>();

        ArrayList<SalesOutboundData> list = new ArrayList<>();
        for (List<String> r : rows) {
            SalesOutboundData data = new SalesOutboundData();
            if (r.size() > 0) data.setFBILLNAME(getSafe(r,0));
            if (r.size() > 1) data.setFBILLTYPENAME(getSafe(r,1));
            if (r.size() > 2) data.setFBILLNUMBER(getSafe(r,2));
            if (r.size() > 3) data.setFBILLDATE(getSafe(r,3));
            if (r.size() > 4) data.setFApproveDate(getSafe(r,4));
            if (r.size() > 5) data.setFSALEORGNAME(getSafe(r,5));
            if (r.size() > 6) data.setFSettleOrgName(getSafe(r,6));
            if (r.size() > 7) data.setFDEPTNUMBER(getSafe(r,7));
            if (r.size() > 8) data.setFDEPTNAME(getSafe(r,8));
            if (r.size() > 9) data.setFSALERNUMBER(getSafe(r,9));
            if (r.size() > 10) data.setFSALERNAME(getSafe(r,10));
            if (r.size() > 11) data.setFSalGroupName(getSafe(r,11));
            if (r.size() > 12) data.setFSETTLETYPENAME(getSafe(r,12));
            if (r.size() > 13) data.setFCUSTOMERNUMBER(getSafe(r,13));
            if (r.size() > 14) data.setFCUSTOMERID(getSafe(r,14));
            if (r.size() > 15) data.setFCustGroupName(getSafe(r,15));
            if (r.size() > 16) data.setFMATERIALNUMBER(getSafe(r,16));
            if (r.size() > 17) data.setFMATERIALID(getSafe(r,17));
            if (r.size() > 18) data.setFMAPID(getSafe(r,18));
            if (r.size() > 19) data.setFMAPNAME(getSafe(r,19));
            if (r.size() > 20) data.setFMATERIALGROUP(getSafe(r,20));
            if (r.size() > 21) data.setFERPCLSID(getSafe(r,21));
            if (r.size() > 22) data.setFCATEGORYID(getSafe(r,22));
            if (r.size() > 23) data.setFMATERIALMODEL(getSafe(r,23));
            if (r.size() > 24) data.setFMATERIALAUXID(getSafe(r,24));
            if (r.size() > 25) data.setFSTOCKID(getSafe(r,25));
            if (r.size() > 26) data.setFStockLocId(getSafe(r,26));
            if (r.size() > 27) data.setFLOTNAME(getSafe(r,27));
            if (r.size() > 28) data.setFSOORDERNO(getSafe(r,28));
            if (r.size() > 29) data.setFUNITNAME(getSafe(r,29));
            if (r.size() > 30) data.setFCURRENCYNAME(getSafe(r,30));
            if (r.size() > 31) data.setFLCCURRENCYNAME(getSafe(r,31));
            if (r.size() > 32) data.setFOUTSTOCKQTY(getSafe(r,32));
            if (r.size() > 33) data.setFLCNOTAXOUTSTOCKPRICE(getSafe(r,33));
            if (r.size() > 34) data.setFLCTAXOUTSTOCKPRICE(getSafe(r,34));
            if (r.size() > 35) data.setFLCTAXNETPRICE(getSafe(r,35));
            if (r.size() > 36) data.setFLCNOTAXOUTSTOCKAMOUNT(getSafe(r,36));
            if (r.size() > 37) data.setFLCTAXOUTSTOCKAMOUNT(getSafe(r,37));
            if (r.size() > 38) data.setFCOSTPRICE(getSafe(r,38));
            if (r.size() > 39) data.setFLCOUTSTOCKTATALCOSTAMOUNT(getSafe(r,39));
            if (r.size() > 40) data.setFRECQTY(getSafe(r,40));
            if (r.size() > 41) data.setFLCNOTAXRECAMOUNT(getSafe(r,41));
            if (r.size() > 42) data.setFLCTAXRECAMOUNT(getSafe(r,42));
            if (r.size() > 43) data.setFISFREE(getSafe(r,43));
            if (r.size() > 44) data.setFENTRYNOTE(getSafe(r,44));

            list.add(data);
        }

        SalesOutboundResponse response = new SalesOutboundResponse();
        response.setList(list);
        return response;
    }

    private JSONObject orgToJson(Organization org) {
        JSONObject json = new JSONObject();
        if (org != null) {
            json.put("FNUMBER", org.getFnumber() != null ? org.getFnumber() : "");
        }
        return json;
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }

    private String boolToString(Boolean b) {
        return (b != null && b) ? "true" : "false";
    }

    private String getSafe(List<String> row, int idx) {
        try {
            String v = row.get(idx);
            return v != null ? v.replace(",", "") : "";
        } catch (Exception e) {
            return "";
        }
    }

    @ApiOperation(value = "生产入库月报表", notes = "根据请求参数查询生产入库月报表并返回数据列表\n\n" +
            "【请求参数说明】\n\n" +
            "FPickMtrlMonth: 月份（必填，格式：yyyy-MM-dd），示例: \"2025-01-01\"\n" +
            "FWorkShopId: 生产车间（非必填，格式：[{\"FNumber\":\"WC001\"}]）\n" +
            "FProdNumId: 产品编码（非必填，格式：[{\"FNumber\":\"P001\"}]）\n" +
            "FSumBasis: 汇总依据（必填），示例: \"\"\n" +
            "FProdTypeId: 物料组（非必填，格式：[{\"FNumber\":\"T001\"}]）\n" +
            "FPrdOrgId: 生产组织（必填），示例: \"\"\n" +
            "FApprBeginDate: 期间从（必填，格式：yyyy-MM-dd），示例: \"2025-01-01\"\n" +
            "FApprEndDate: 至（必填，格式：yyyy-MM-dd），示例: \"2025-01-31\"\n" +
            "FMonDateChk: 月份期间选择（非必填），示例: false\n" +
            "FRangeDateChk: 日期范围选择（非必填），示例: false\n" +
            "FMoBillTypeId: 单据类型（非必填，格式：[{\"FNumber\":\"\"}]）\n" +
            "FSumUnit: 统计单位（必填），示例: \"\"\n" +
            "FAccSource: 取数来源（非必填），示例: \"\"\n" +
            "FDocStatus: 单据状态（必填），示例: \"\"\n\n" +
            "【响应字段说明】\n\n" +
            "FPickMtrlMonth: 月份/期间\n" +
            "FWorkShopId: 生产车间\n" +
            "FProdTypeId: 物料组\n" +
            "FPrdOrgId: 生产组织\n" +
            "FMATERIALID: 产品编码\n" +
            "FProdName: 产品名称\n" +
            "FSpecModel: 规格型号\n" +
            "FUnitId: 单位\n" +
            "FPASSINSTOCKQTY: 合格品入库数量\n" +
            "FFAILINSTOCKQTY: 不合格品入库数量\n" +
            "FSCRAPINSTOCKQTY: 报废品入库数量\n" +
            "FRETURNINSTOCKQTY: 返工品入库数量\n" +
            "FOUTSTOCKQTY: 退库数量\n" +
            "FACTUALINSTOCKQTY: 实际入库数量\n" +
            "FAUXUNITID: 库存辅单位\n" +
            "FSECPASSINSTOCKQTY: 合格品入库数量（辅）\n" +
            "FSECFAILINSTOCKQTY: 不合格品入库数量（辅）\n" +
            "FSECSCRAPINSTOCKQTY: 报废品入库数量（辅）\n" +
            "FSECRETURNINSTOCKQTY: 返工品入库数量（辅）\n" +
            "FSECOUTSTOCKQTY: 退库数量（辅）\n" +
            "FSECACTUALINSTOCKQTY: 实际入库数量（辅）\n\n" +
            "【示例请求】\n" +
            "{\n" +
            "  \"FPickMtrlMonth\": \"2025-01-01\",\n" +
            "  \"FWorkShopId\": [{\"FNumber\":\"\"}],\n" +
            "  \"FProdNumId\": [{\"FNumber\":\"\"}],\n" +
            "  \"FSumBasis\": \"\",\n" +
            "  \"FProdTypeId\": [{\"FNumber\":\"\"}],\n" +
            "  \"FPrdOrgId\": \"\",\n" +
            "  \"FApprBeginDate\": \"2025-01-01\",\n" +
            "  \"FApprEndDate\": \"2025-01-31\",\n" +
            "  \"FMonDateChk\": false,\n" +
            "  \"FRangeDateChk\": false,\n" +
            "  \"FMoBillTypeId\": [{\"FNumber\":\"\"}],\n" +
            "  \"FSumUnit\": \"\",\n" +
            "  \"FAccSource\": \"\",\n" +
            "  \"FDocStatus\": \"\"\n" +
            "}\n\n" +
            "【示例响应】\n" +
            "{\n" +
            "  \"list\": [\n" +
            "    {\n" +
            "      \"FPickMtrlMonth\": \"2025-01\",\n" +
            "      \"FWorkShopId\": \"一车间\",\n" +
            "      \"FProdTypeId\": \"电子产品\",\n" +
            "      \"FPrdOrgId\": \"生产部\",\n" +
            "      \"FMATERIALID\": \"P001\",\n" +
            "      \"FProdName\": \"产品A\",\n" +
            "      \"FSpecModel\": \"规格1\",\n" +
            "      \"FUnitId\": \"个\",\n" +
            "      \"FPASSINSTOCKQTY\": \"10000.00\",\n" +
            "      \"FFAILINSTOCKQTY\": \"100.00\",\n" +
            "      \"FSCRAPINSTOCKQTY\": \"50.00\",\n" +
            "      \"FRETURNINSTOCKQTY\": \"30.00\",\n" +
            "      \"FOUTSTOCKQTY\": \"20.00\",\n" +
            "      \"FACTUALINSTOCKQTY\": \"9860.00\",\n" +
            "      \"FAUXUNITID\": \"箱\",\n" +
            "      \"FSECPASSINSTOCKQTY\": \"1000.00\",\n" +
            "      \"FSECFAILINSTOCKQTY\": \"10.00\",\n" +
            "      \"FSECSCRAPINSTOCKQTY\": \"5.00\",\n" +
            "      \"FSECRETURNINSTOCKQTY\": \"3.00\",\n" +
            "      \"FSECOUTSTOCKQTY\": \"2.00\",\n" +
            "      \"FSECACTUALINSTOCKQTY\": \"986.00\"\n" +
            "    }\n" +
            "  ]\n" +
            "}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = ProductionInStockResponse.class),
            @ApiResponse(code = 500, message = "服务器内部错误")
    })
    @PostMapping(value = "/production-inbound-monthly", consumes = "application/json", produces = "application/json")
    public Object productionInboundMonthly(@ApiParam(value = "查询条件", required = true) @RequestBody ProductionInStockRequest req) throws Exception {
        K3CloudApi client = new K3CloudApi();

        // 构建 FieldKeys（常用列）
        String fieldKeys = "FPickMtrlMonth,FWorkShopId,FProdTypeId,FPrdOrgId,FMATERIALID,FProdName,FSpecModel,FUnitId,"
                + "FPASSINSTOCKQTY,FFAILINSTOCKQTY,FSCRAPINSTOCKQTY,FRETURNINSTOCKQTY,FOUTSTOCKQTY,FACTUALINSTOCKQTY,"
                + "FAUXUNITID,FSECPASSINSTOCKQTY,FSECFAILINSTOCKQTY,FSECSCRAPINSTOCKQTY,FSECRETURNINSTOCKQTY,FSECOUTSTOCKQTY,FSECACTUALINSTOCKQTY";

        // 构建 Model JSON
        JSONObject model = new JSONObject();
        model.put("FPickMtrlMonth", safe(req.getFPickMtrlMonth()));
        model.put("FWorkShopId", req.getFWorkShopId() != null ? req.getFWorkShopId() : new ArrayList<>());
        model.put("FProdNumId", req.getFProdNumId() != null ? req.getFProdNumId() : new ArrayList<>());
        model.put("FSumBasis", safe(req.getFSumBasis()));
        model.put("FProdTypeId", req.getFProdTypeId() != null ? req.getFProdTypeId() : new ArrayList<>());
        model.put("FPrdOrgId", safe(req.getFPrdOrgId()));
        model.put("FApprBeginDate", safe(req.getFApprBeginDate()));
        model.put("FApprEndDate", safe(req.getFApprEndDate()));
        model.put("FMonDateChk", boolToString(req.getFMonDateChk()));
        model.put("FRangeDateChk", boolToString(req.getFRangeDateChk()));
        model.put("FMoBillTypeId", req.getFMoBillTypeId() != null ? req.getFMoBillTypeId() : new ArrayList<>());
        model.put("FSumUnit", safe(req.getFSumUnit()));
        model.put("FAccSource", safe(req.getFAccSource()));
        model.put("FDocStatus", safe(req.getFDocStatus()));

        String jsonData = "{"
                + "\"FieldKeys\":\"" + fieldKeys + "\","
                + "\"SchemeId\":\"\","
                + "\"StartRow\":0,"
                + "\"Limit\":10000,"
                + "\"IsVerifyBaseDataField\":\"true\","
                + "\"FilterString\":[],"
                + "\"Model\":" + model.toJSONString()
                + "}";

        String formId = "PRD_InStockMonthRpt";
        String resultJson = client.getSysReportData(formId, jsonData);

        KdResult kdResult = JSON.parseObject(resultJson, KdResult.class);
        List<List<String>> rows = (kdResult.getResult() != null && kdResult.getResult().getRows() != null)
                ? kdResult.getResult().getRows()
                : new ArrayList<>();

        ArrayList<ProductionInStockData> list = new ArrayList<>();
        for (List<String> r : rows) {
            ProductionInStockData data = new ProductionInStockData();
            if (r.size() > 0) data.setFPickMtrlMonth(getSafe(r,0));
            if (r.size() > 1) data.setFWorkShopId(getSafe(r,1));
            if (r.size() > 2) data.setFProdTypeId(getSafe(r,2));
            if (r.size() > 3) data.setFPrdOrgId(getSafe(r,3));
            if (r.size() > 4) data.setFMATERIALID(getSafe(r,4));
            if (r.size() > 5) data.setFProdName(getSafe(r,5));
            if (r.size() > 6) data.setFSpecModel(getSafe(r,6));
            if (r.size() > 7) data.setFUnitId(getSafe(r,7));
            if (r.size() > 8) data.setFPASSINSTOCKQTY(getSafe(r,8));
            if (r.size() > 9) data.setFFAILINSTOCKQTY(getSafe(r,9));
            if (r.size() > 10) data.setFSCRAPINSTOCKQTY(getSafe(r,10));
            if (r.size() > 11) data.setFRETURNINSTOCKQTY(getSafe(r,11));
            if (r.size() > 12) data.setFOUTSTOCKQTY(getSafe(r,12));
            if (r.size() > 13) data.setFACTUALINSTOCKQTY(getSafe(r,13));
            if (r.size() > 14) data.setFAUXUNITID(getSafe(r,14));
            if (r.size() > 15) data.setFSECPASSINSTOCKQTY(getSafe(r,15));
            if (r.size() > 16) data.setFSECFAILINSTOCKQTY(getSafe(r,16));
            if (r.size() > 17) data.setFSECSCRAPINSTOCKQTY(getSafe(r,17));
            if (r.size() > 18) data.setFSECRETURNINSTOCKQTY(getSafe(r,18));
            if (r.size() > 19) data.setFSECOUTSTOCKQTY(getSafe(r,19));
            if (r.size() > 20) data.setFSECACTUALINSTOCKQTY(getSafe(r,20));

            list.add(data);
        }

        ProductionInStockResponse response = new ProductionInStockResponse();
        response.setList(list);
        return response;
    }

    @ApiOperation(value = "销售订单执行明细表", notes = "根据请求参数查询销售订单执行明细表并返回数据列表\n\n" +
            "【请求参数说明】\n\n" +
            "FSaleOrgList: 销售组织（必填），示例: \"\"\n" +
            "FMoneyType: 币别（必填，格式：{\"FNumber\":\"\"}），示例: {\"FNumber\":\"\"}\n" +
            "FSoFromDate: 订单日期（起始，必填，格式：yyyy-MM-dd），示例: \"2025-01-01\"\n" +
            "FSoToDate: 订单日期（结束，必填，格式：yyyy-MM-dd），示例: \"2025-01-31\"\n" +
            "FCustomerFrom: 客户编码（起始，非必填，格式：{\"FNumber\":\"\"}），示例: {\"FNumber\":\"\"}\n" +
            "FCustomerTo: 客户编码（结束，非必填，格式：{\"FNumber\":\"\"}），示例: {\"FNumber\":\"\"}\n" +
            "FSalesFrom: 销售员编码（起始，非必填，格式：{\"FNumber\":\"\"}），示例: {\"FNumber\":\"\"}\n" +
            "FSalesTo: 销售员编码（结束，非必填，格式：{\"FNumber\":\"\"}），示例: {\"FNumber\":\"\"}\n" +
            "FMaterialFrom: 物料编码（起始，非必填，格式：{\"FNumber\":\"\"}），示例: {\"FNumber\":\"\"}\n" +
            "FMaterialTo: 物料编码（结束，非必填，格式：{\"FNumber\":\"\"}），示例: {\"FNumber\":\"\"}\n" +
            "FSaleOFrom: 销售订单（起始），示例: \"\"\n" +
            "FSaleOTo: 销售订单（结束），示例: \"\"\n" +
            "FDelliveryDateFrom: 要货日期（起始，格式：yyyy-MM-dd），示例: \"1900-01-01\"\n" +
            "FDelliveryDateTo: 要货日期（结束，格式：yyyy-MM-dd），示例: \"1900-01-01\"\n" +
            "FFormCloseStatus: 整单关闭状态（必填），示例: \"\"\n" +
            "FFormStatus: 单据状态（必填），示例: \"\"\n" +
            "FPriceFrom: 单价来源（必填），示例: \"\"\n" +
            "FBusCloseStatus: 行业务关闭状态（必填），示例: \"\"\n" +
            "FMergingSOHeader: 查询时不合并展示订单的表头信息，示例: false\n" +
            "FIncludedUnfilledOrders: 包含未执行的销售订单，示例: false\n" +
            "FIsRecWithMat: 按物料明细收款方式查询，示例: false\n" +
            "FCustGroup: 客户分组（格式：{\"FNumber\":\"\"}），示例: {\"FNumber\":\"\"}\n" +
            "FSuite: 统计套件（必填），示例: \"\"\n" +
            "FSetAccountType: 立账类型（必填），示例: \"\"\n" +
            "FIsGroup: 集团客户，示例: false\n" +
            "FIncludedFree: 包含赠品，示例: false\n\n" +
            "【响应字段说明】\n\n" +
            "FSALEORGNAME: 销售组织\n" +
            "FBILLNO: 订单编号\n" +
            "FBILLTYPE: 单据类型\n" +
            "FDate: 订单日期\n" +
            "FSALEDEPTID: 销售部门\n" +
            "FSALEGROUP: 销售组\n" +
            "FSALES: 销售员\n" +
            "FCUSTID: 客户编码\n" +
            "FCUSTOMERNAME: 客户名称\n" +
            "FCustGroupName: 客户分组\n" +
            "FORDERID: 订单行号\n" +
            "FMATERIALID: 物料编码\n" +
            "FMATERIALNAME: 物料名称\n" +
            "FSPECIFICATION: 规格型号\n" +
            "FIsFree: 是否赠品\n" +
            "FORDERQTY: 订单数量\n" +
            "FORDERAMOUNT: 订单金额\n" +
            "FDELIBILLNO: 发货通知单编号\n" +
            "FDELIQTY: 发货通知数量\n" +
            "FOUTBILLNO: 出库单编号\n" +
            "FOUTQTY: 已出库数量\n" +
            "FRETURNBILLNO: 销售退货单编号\n" +
            "FRETURNQTY: 销售退货数量\n" +
            "FRECAMOUNT: 应收金额\n" +
            "FINVOECEAMOUNT: 开票金额\n" +
            "FCLOSESTATUS: 整单关闭\n\n" +
            "【示例请求】\n" +
            "{\n" +
            "  \"FSaleOrgList\": \"\",\n" +
            "  \"FMoneyType\": {\"FNumber\":\"\"},\n" +
            "  \"FSoFromDate\": \"2025-01-01\",\n" +
            "  \"FSoToDate\": \"2025-01-31\",\n" +
            "  \"FCustomerFrom\": {\"FNumber\":\"\"},\n" +
            "  \"FCustomerTo\": {\"FNumber\":\"\"},\n" +
            "  \"FSalesFrom\": {\"FNumber\":\"\"},\n" +
            "  \"FSalesTo\": {\"FNumber\":\"\"},\n" +
            "  \"FMaterialFrom\": {\"FNumber\":\"\"},\n" +
            "  \"FMaterialTo\": {\"FNumber\":\"\"},\n" +
            "  \"FSaleOFrom\": \"\",\n" +
            "  \"FSaleOTo\": \"\",\n" +
            "  \"FDelliveryDateFrom\": \"1900-01-01\",\n" +
            "  \"FDelliveryDateTo\": \"1900-01-01\",\n" +
            "  \"FFormCloseStatus\": \"\",\n" +
            "  \"FFormStatus\": \"\",\n" +
            "  \"FPriceFrom\": \"\",\n" +
            "  \"FBusCloseStatus\": \"\",\n" +
            "  \"FMergingSOHeader\": false,\n" +
            "  \"FIncludedUnfilledOrders\": false,\n" +
            "  \"FIsRecWithMat\": false,\n" +
            "  \"FCustGroup\": {\"FNumber\":\"\"},\n" +
            "  \"FSuite\": \"\",\n" +
            "  \"FSetAccountType\": \"\",\n" +
            "  \"FIsGroup\": false,\n" +
            "  \"FIncludedFree\": false\n" +
            "}\n\n" +
            "【示例响应】\n" +
            "{\n" +
            "  \"list\": [\n" +
            "    {\n" +
            "      \"FSALEORGNAME\": \"华东销售部\",\n" +
            "      \"FBILLNO\": \"SO202501150001\",\n" +
            "      \"FBILLTYPE\": \"标准销售订单\",\n" +
            "      \"FDate\": \"2025-01-15\",\n" +
            "      \"FSALEDEPTID\": \"XS001\",\n" +
            "      \"FSALEGROUP\": \"华东一组\",\n" +
            "      \"FSALES\": \"张三\",\n" +
            "      \"FCUSTID\": \"CUST001\",\n" +
            "      \"FCUSTOMERNAME\": \"某某科技有限公司\",\n" +
            "      \"FCustGroupName\": \"大客户\",\n" +
            "      \"FORDERID\": \"0001\",\n" +
            "      \"FMATERIALID\": \"MAT001\",\n" +
            "      \"FMATERIALNAME\": \"某产品A\",\n" +
            "      \"FSPECIFICATION\": \"规格1\",\n" +
            "      \"FIsFree\": \"否\",\n" +
            "      \"FORDERQTY\": \"100.00\",\n" +
            "      \"FORDERAMOUNT\": \"50000.00\",\n" +
            "      \"FDELIBILLNO\": \"DN202501160001\",\n" +
            "      \"FDELIQTY\": \"100.00\",\n" +
            "      \"FOUTBILLNO\": \"CK202501170001\",\n" +
            "      \"FOUTQTY\": \"100.00\",\n" +
            "      \"FRETURNBILLNO\": \"\",\n" +
            "      \"FRETURNQTY\": \"\",\n" +
            "      \"FRECAMOUNT\": \"50000.00\",\n" +
            "      \"FINVOECEAMOUNT\": \"50000.00\",\n" +
            "      \"FCLOSESTATUS\": \"否\"\n" +
            "    }\n" +
            "  ]\n" +
            "}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = SaleOrderDetailResponse.class),
            @ApiResponse(code = 500, message = "服务器内部错误")
    })
    @PostMapping(value = "/sale-order-detail", consumes = "application/json", produces = "application/json")
    public Object saleOrderDetail(@RequestBody String rawJson) throws Exception {
        K3CloudApi client = new K3CloudApi();

        System.err.println("===== 销售订单执行明细表原始JSON =====");
        System.err.println(rawJson);

        // 解析FieldKeys
        JSONObject requestJson = JSON.parseObject(rawJson);
        String fieldKeysStr = requestJson.getString("FieldKeys");

        // 获取字段Key到中文名称的映射
        HashMap<String, String> fieldKeyToChinese = getSaleOrderDetailFieldKeyMapping();

        String formId = "SAL_DetailReport";
        String resultJson = client.getSysReportData(formId, rawJson);
        System.err.println("===== 金蝶返回结果 =====");
        System.err.println(resultJson);

        KdResult kdResult = JSON.parseObject(resultJson, KdResult.class);
        List<List<String>> rows = (kdResult.getResult() != null && kdResult.getResult().getRows() != null)
                ? kdResult.getResult().getRows()
                : new ArrayList<>();

        // 解析FieldKeys
        String[] fieldKeys = fieldKeysStr != null ? fieldKeysStr.split(",") : new String[0];

        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        for (List<String> r : rows) {
            HashMap<String, String> data = new HashMap<>();
            // 动态根据FieldKeys顺序映射
            for (int i = 0; i < r.size() && i < fieldKeys.length; i++) {
                String fk = fieldKeys[i].trim();
                String chineseName = fieldKeyToChinese.get(fk);
                if (chineseName != null) {
                    data.put(chineseName, getSafe(r, i));
                } else {
                    // 如果没有映射，使用原始FieldKey
                    data.put(fk, getSafe(r, i));
                }
            }
            list.add(data);
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("list", list);
        return result;
    }

    /**
            if (r.size() > 58) data.setFRETURNQTY(getSafe(r,58));
            if (r.size() > 59) data.setFRETURNCHARGEQTY(getSafe(r,59));
            if (r.size() > 60) data.setFRETURNAMOUNT(getSafe(r,60));
            if (r.size() > 61) data.setFRETURNAMOUNT_LC(getSafe(r,61));
            if (r.size() > 62) data.setFRECEIVEBILLNO(getSafe(r,62));
            if (r.size() > 63) data.setFARDate(getSafe(r,63));
            if (r.size() > 64) data.setFSetAccountType(getSafe(r,64));
            if (r.size() > 65) data.setFRECQTY(getSafe(r,65));
            if (r.size() > 66) data.setFRECEIVECHARGEQTY(getSafe(r,66));
            if (r.size() > 67) data.setFRECAMOUNT(getSafe(r,67));
            if (r.size() > 68) data.setFRECAMOUNT_LC(getSafe(r,68));
            if (r.size() > 69) data.setFWriteOffAmount(getSafe(r,69));
            if (r.size() > 70) data.setFWRITEOFFAMOUNT_LC(getSafe(r,70));
            if (r.size() > 71) data.setFINVOECEBILLNO(getSafe(r,71));
            if (r.size() > 72) data.setFINVOECEDATE(getSafe(r,72));
            if (r.size() > 73) data.setFINVOECENO(getSafe(r,73));
            if (r.size() > 74) data.setFINVOECEQTY(getSafe(r,74));
            if (r.size() > 75) data.setFBILLCHARGEQTY(getSafe(r,75));
            if (r.size() > 76) data.setFINVOECEAMOUNT(getSafe(r,76));
            if (r.size() > 77) data.setFINVOECEAMOUNT_LC(getSafe(r,77));
            if (r.size() > 78) data.setFADVANCEBILLNO(getSafe(r,78));
            if (r.size() > 79) data.setFADVANCEBILLDATE(getSafe(r,79));
            if (r.size() > 80) data.setFADVANCEAMOUNT(getSafe(r,80));
            if (r.size() > 81) data.setFADVANCEAMOUNT_LC(getSafe(r,81));
            if (r.size() > 82) data.setFOVERRECAMOUNT(getSafe(r,82));
            if (r.size() > 83) data.setFOVERRECAMOUNT_LC(getSafe(r,83));
            if (r.size() > 84) data.setFRECBILLNO(getSafe(r,84));
            if (r.size() > 85) data.setFRECBILLDATE(getSafe(r,85));
            if (r.size() > 86) data.setFRECEIPTAMOUNT(getSafe(r,86));
            if (r.size() > 87) data.setFRECEIPTAMOUNT_LC(getSafe(r,87));
            if (r.size() > 88) data.setFJSWRITEOFFAMOUNT(getSafe(r,88));
            if (r.size() > 89) data.setFJSWRITEOFFAMOUNT_LC(getSafe(r,89));
            if (r.size() > 90) data.setFChargeOffAmount(getSafe(r,90));
            if (r.size() > 91) data.setFCHARGEOFFAMOUNT_LC(getSafe(r,91));
            if (r.size() > 92) data.setFRECSKAMOUNT(getSafe(r,92));
            if (r.size() > 93) data.setFRECSKAMOUNT_LC(getSafe(r,93));
            if (r.size() > 94) data.setFCLOSESTATUS(getSafe(r,94));
            if (r.size() > 95) data.setFMRPCLOSESTATUS(getSafe(r,95));
            if (r.size() > 96) data.setFMrpTerminateStatus(getSafe(r,96));
            if (r.size() > 97) data.setFMrpFreezeStatus(getSafe(r,97));
            if (r.size() > 98) data.setFMANUALCLOSE(getSafe(r,98));
            if (r.size() > 99) data.setFMANUALROWCLOSE(getSafe(r,99));
            if (r.size() > 100) data.setFISINIT(getSafe(r,100));
            if (r.size() > 101) data.setFBaseUnitID(getSafe(r,101));
            if (r.size() > 102) data.setFOrderBaseQty(getSafe(r,102));
            if (r.size() > 103) data.setFBasePrice(getSafe(r,103));
            if (r.size() > 104) data.setFBASEPRICE_LC(getSafe(r,104));
            if (r.size() > 105) data.setFDeliveryNoticeQty(getSafe(r,105));
            if (r.size() > 106) data.setFDeliveryQty(getSafe(r,106));
            if (r.size() > 107) data.setFReturnNoticeQty(getSafe(r,107));
            if (r.size() > 108) data.setFReturnBaseQty(getSafe(r,108));
            if (r.size() > 109) data.setFReceiveQty(getSafe(r,109));
            if (r.size() > 110) data.setFBaseInvoiceQty(getSafe(r,110));
            if (r.size() > 111) data.setFPRESETBASENAME1(getSafe(r,111));
            if (r.size() > 112) data.setFPRESETBASENAME2(getSafe(r,112));
            if (r.size() > 113) data.setFPRESETASSISTANTNAME1(getSafe(r,113));
            if (r.size() > 114) data.setFPRESETASSISTANTNAME2(getSafe(r,114));
            if (r.size() > 115) data.setFADVANCENOTMATCHAMOUNT(getSafe(r,115));
            if (r.size() > 116) data.setFADVANCENOTMATCHAMOUNT_LC(getSafe(r,116));
            if (r.size() > 117) data.setFALLMATCHAMOUNT(getSafe(r,117));
            if (r.size() > 118) data.setFALLMATCHAMOUNT_LC(getSafe(r,118));
            if (r.size() > 119) data.setFORDERQTYTEMP(getSafe(r,119));
            if (r.size() > 120) data.setFORDERAMOUNTTEMP(getSafe(r,120));

            list.add(data);
        }

        SaleOrderDetailResponse response = new SaleOrderDetailResponse();
        response.setList(list);
        return response;
    }

    // ==================== 采购订单执行明细表 ====================
    @ApiOperation(value = "采购订单执行明细表", notes = "" +
            "{\n" +
            "  \"Result\": {\n" +
            "    \"IsSuccess\": true,\n" +
            "    \"RowCount\": 2,\n" +
            "    \"Rows\": []\n" +
            "  }\n" +
            "}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = PurchaseOrderDetailResponse.class),
            @ApiResponse(code = 500, message = "服务器内部错误")
    })
    @PostMapping(value = "/purchase-order-detail", consumes = "application/json", produces = "application/json")
    public Object purchaseOrderDetail(@RequestBody String rawJson) throws Exception {
        K3CloudApi client = new K3CloudApi();

        System.err.println("===== 采购订单执行明细表原始JSON =====");
        System.err.println(rawJson);

        // 解析请求JSON获取FieldKeys
        JSONObject requestObj = JSON.parseObject(rawJson);
        String fieldKeysStr = requestObj.getString("FieldKeys");

        // 建立FieldKey到中文字段名的映射
        HashMap<String, String> fieldKeyToChinese = getPurchaseOrderDetailFieldKeyMapping();

        // 解析FieldKeys
        String[] fieldKeys = fieldKeysStr != null ? fieldKeysStr.split(",") : new String[0];

        // 直接使用原始JSON发送给金蝶
        String formId = "PUR_PurchaseOrderDetailRpt";
        String resultJson = client.getSysReportData(formId, rawJson);
        System.err.println("===== 金蝶返回结果 =====");
        System.err.println(resultJson);

        KdResult kdResult = JSON.parseObject(resultJson, KdResult.class);
        List<List<String>> rows = (kdResult.getResult() != null && kdResult.getResult().getRows() != null)
                ? kdResult.getResult().getRows()
                : new ArrayList<>();

        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        for (List<String> r : rows) {
            HashMap<String, String> data = new HashMap<>();
            // 动态根据FieldKeys顺序映射
            for (int i = 0; i < r.size() && i < fieldKeys.length; i++) {
                String fk = fieldKeys[i].trim();
                String chineseName = fieldKeyToChinese.get(fk);
                if (chineseName != null) {
                    data.put(chineseName, getSafe(r, i));
                } else {
                    // 如果没有映射，使用原始FieldKey
                    data.put(fk, getSafe(r, i));
                }
            }
            list.add(data);
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("list", list);
        return result;
    }

    /**
     * 采购订单执行明细表FieldKey到中文字段名的映射
     */
    private HashMap<String, String> getPurchaseOrderDetailFieldKeyMapping() {
        HashMap<String, String> map = new HashMap<>();
        map.put("FPurchaseOrgId", "采购组织");
        map.put("FPURCHASERGROUPID", "采购组");
        map.put("FPURCHASEDEPTID", "采购部门");
        map.put("FPURCHASERID", "采购员");
        map.put("FBillNo", "订单编号");
        map.put("FDate", "日期");
        map.put("FSUPPLIERID", "供应商编码");
        map.put("FSUPPLIERNAME", "供应商名称");
        map.put("FSupplierGroup", "供应商分组");
        map.put("FBillSeqId", "订单行号");
        map.put("FMATERIALID", "物料编码");
        map.put("FMATERIALNAME", "物料名称");
        map.put("FMTONO", "计划跟踪号");
        map.put("FMaterialModel", "规格型号");
        map.put("FCategoryID", "存货类别");
        map.put("FErpClsID", "物料属性");
        map.put("FMaterialGroup", "物料分组");
        map.put("FFLEX", "辅助属性");
        map.put("FSrcBillNo", "源单编号");
        map.put("FSRCBILLTYPENAME", "源单类型");
        map.put("FDELIVERYDATE", "交货日期");
        map.put("FREQUIREORGID", "需求组织");
        map.put("FREQUIREDEPTID", "需求部门");
        map.put("FUnitId", "采购单位");
        map.put("FCurrencyId", "结算币别");
        map.put("FCURRENCYID_LC", "本位币");
        map.put("FPRICEUNITID", "计价单位");
        map.put("FOrderQty", "订货数量");
        map.put("FORDERPRICEQTY", "订货数量(计价单位)");
        map.put("FTAXPRICE", "含税单价");
        map.put("FTAXPRICE_LC", "含税单价(本位币)");
        map.put("FOrderAmount", "价税合计");
        map.put("FORDERAMOUNT_LC", "价税合计(本位币)");
        map.put("FReceiveNumber", "收料单号");
        map.put("FReceiveDate", "收料日期");
        map.put("FRECEIVESEQID", "收料单行号");
        map.put("FReceiveQty", "收料数量");
        map.put("FRECEIVEPRICEQTY", "收料数量(计价单位)");
        map.put("FReceiveAmount", "收料金额");
        map.put("FRECEIVEAMOUNT_LC", "收料金额(本位币)");
        map.put("FEnterNumber", "入库单号");
        map.put("FEnterDate", "入库日期");
        map.put("FINSTOCKSEQID", "入库单行号");
        map.put("FImportQty", "入库数量");
        map.put("FIMPORTPRICEQTY", "入库数量(计价单位)");
        map.put("FImportAmount", "入库金额");
        map.put("FIMPORTAMOUNT_LC", "入库金额(本位币)");
        map.put("FIMPORTNOTAXAMOUNT", "入库不含税金额");
        map.put("FIMPORTNOTAXAMOUNT_LC", "入库不含税金额(本位币)");
        map.put("FReturnNumber", "退料单号");
        map.put("FReturnDate", "退料日期");
        map.put("FRETURNSEQID", "退料单行号");
        map.put("FReturnQty", "退料数量");
        map.put("FRETURNPRICEQTY", "退料数量(计价单位)");
        map.put("FReturnAmount", "退料金额");
        map.put("FRETURNAMOUNT_LC", "退料金额(本位币)");
        map.put("FPAYNUMBER", "应付单号");
        map.put("FPAYDATE", "应付日期");
        map.put("FPAYSEQID", "应付单行号");
        map.put("FSETACCOUNTTYPE", "立账类型");
        map.put("FPAYQTY", "应付数量");
        map.put("FPAYPRICEQTY", "应付数量(计价单位)");
        map.put("FPAYAMOUNT", "应付金额");
        map.put("FPAYEXTAXAMOUNT", "应付不含税金额");
        map.put("FPAYAMOUNT_LC", "应付金额(本位币)");
        map.put("FPAYWOFFAMOUNT", "调整金额");
        map.put("FPAYWOFFAMOUNT_LC", "应付调整金额(本位币)");
        map.put("FINVOICENUMBER", "开票单号");
        map.put("FINVOICEQTY", "开票数量");
        map.put("FINVOICEAMOUNT", "开票金额");
        map.put("FINVOICEAMOUNT_LC", "开票金额(本位币)");
        map.put("FRECPAYBILLNUMBER", "预付单号");
        map.put("FRECPAYBILLAMOUNT", "预付金额");
        map.put("FRECPAYBILLAMOUNT_LC", "预付金额(本位币)");
        map.put("FMRPTERMINATESTATUS", "行业务终止");
        map.put("FPAYBILLNUMBER", "付款单号");
        map.put("FPAYBILLDATE", "付款日期");
        map.put("FPAYBILLAMOUNT", "已结算金额");
        map.put("FPAYBILLAMOUNT_LC", "已结算金额(本位币)");
        map.put("FRELATEDEPOSIT", "关联保证金");
        map.put("FMRPCLOSESTATUS", "行业务关闭");
        map.put("FAPPLYRELATEDEPOSIT", "关联申请保证金");
        map.put("FRELATEREFUNDDEPOSIT", "关联退款保证金");
        map.put("FCLOSESTATUS", "整单关闭");
        map.put("FMRPFREEZESTATUS", "行业务冻结");
        map.put("FINVOICENO", "发票号");
        map.put("FINVOICEDATE", "发票日期");
        map.put("FSETADJAMOUNT", "结算调整金额");
        map.put("FPAYWRITOFFAMOUNT", "付款核销金额");
        map.put("FSPEWOFFAMOUNT", "特殊冲销金额");
        map.put("FINTEPAYAMOUNT", "已付款金额(接口)");
        map.put("FSETADJAMOUNT_LC", "结算调整金(本位币)");
        map.put("FPAYWRITOFFAMOUNT_LC", "付款核销金额(本位币)");
        map.put("FSPEWOFFAMOUNT_LC", "特殊冲销金额(本位币)");
        map.put("FINTEPAYAMOUNT_LC", "已付款金额(接口)(本位币)");
        map.put("FPREINVOCEBILLNO", "先开票单号");
        map.put("FPREINVOICEDATE", "先开票发票日期");
        map.put("FPREINVOICEQTY", "先开票数量");
        map.put("FPREINVOICEAMOUNT", "先开票金额");
        map.put("FPREINVOICENO", "先开票发票号");
        map.put("FPREINVOICEAMOUNT_LC", "先开票金额(本位币)");
        return map;
    }

    // ==================== 生产订单执行明细表 ====================
    @ApiOperation(value = "生产订单执行明细表", notes = "" +
            "{\n" +
            "  \"Result\": {\n" +
            "    \"IsSuccess\": true,\n" +
            "    \"RowCount\": 2,\n" +
            "    \"Rows\": []\n" +
            "  }\n" +
            "}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = MoExecuteDetailResponse.class),
            @ApiResponse(code = 500, message = "服务器内部错误")
    })
    @PostMapping(value = "/mo-execute-detail", consumes = "application/json", produces = "application/json")
    public Object moExecuteDetail(@RequestBody String rawJson) throws Exception {
        K3CloudApi client = new K3CloudApi();

        System.err.println("===== 生产订单执行明细表原始JSON =====");
        System.err.println(rawJson);

        // 解析请求JSON获取FieldKeys
        JSONObject requestObj = JSON.parseObject(rawJson);
        String fieldKeysStr = requestObj.getString("FieldKeys");

        // 建立FieldKey到中文字段名的映射
        HashMap<String, String> fieldKeyToChinese = getMoExecuteDetailFieldKeyMapping();

        // 解析FieldKeys
        String[] fieldKeys = fieldKeysStr != null ? fieldKeysStr.split(",") : new String[0];

        // 直接使用原始JSON发送给金蝶
        String formId = "PRD_MOExecuteDetailRpt";
        String resultJson = client.getSysReportData(formId, rawJson);
        System.err.println("===== 金蝶返回结果 =====");
        System.err.println(resultJson);

        KdResult kdResult = JSON.parseObject(resultJson, KdResult.class);
        List<List<String>> rows = (kdResult.getResult() != null && kdResult.getResult().getRows() != null)
                ? kdResult.getResult().getRows()
                : new ArrayList<>();

        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        for (List<String> r : rows) {
            HashMap<String, String> data = new HashMap<>();
            // 动态根据FieldKeys顺序映射
            for (int i = 0; i < r.size() && i < fieldKeys.length; i++) {
                String fk = fieldKeys[i].trim();
                String chineseName = fieldKeyToChinese.get(fk);
                if (chineseName != null) {
                    data.put(chineseName, getSafe(r, i));
                } else {
                    // 如果没有映射，使用原始FieldKey
                    data.put(fk, getSafe(r, i));
                }
            }
            list.add(data);
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("list", list);
        return result;
    }

    /**
     * 生产订单执行明细表FieldKey到中文字段名的映射
     */
    private HashMap<String, String> getMoExecuteDetailFieldKeyMapping() {
        HashMap<String, String> map = new HashMap<>();
        map.put("FWORKSHOPID", "生产车间");
        map.put("FMOBILLTYPEID", "单据类型");
        map.put("FMOBILLNO", "生产订单编号");
        map.put("FMOENTRYSEQ", "生产订单行号");
        map.put("FMATERIALID", "产品编码");
        map.put("FMATERIALNAME", "产品名称");
        map.put("FMATERIALMODEL", "规格型号");
        map.put("FPRODUCTTYPENAME", "产品类型");
        map.put("FAUXPROP", "辅助属性");
        map.put("FBOMID", "BOM版本");
        map.put("FMTONO", "计划跟踪号");
        map.put("FPRDUNITID", "生产单位");
        map.put("FPLANSTARTDATE", "计划开工日期");
        map.put("FPLANFINISHDATE", "计划完工日期");
        map.put("FREQSRC", "需求来源");
        map.put("FSaleOrderNo", "需求单据");
        map.put("FSALEORDERENTRYSEQ", "需求单据行号");
        map.put("FCONVEYDATE", "下达日期");
        map.put("FSTARTDATE", "开工日期");
        map.put("FFINISHDATE", "完工日期");
        map.put("FPLANQTY", "计划生产数量");
        map.put("FREPORTQTY", "汇报数量");
        map.put("FAPPINSPECTQTY", "请检数量");
        map.put("FINSPECTQTY", "检验数量");
        map.put("FFINISHQTY", "完工数量");
        map.put("FSTOCKINQUAQTY", "合格品入库数量");
        map.put("FSTOCKINFAILQTY", "不合格品入库数量");
        map.put("FStockInScrapQty", "报废品入库数量");
        map.put("FSTOCKINREMADEQTY", "返工品入库数量");
        map.put("FReStkQty", "退库数量");
        map.put("FNOSTOCKINQTY", "未入库数量");
        map.put("FPLANFINISHPERCENT", "计划完成率%");
        map.put("FQUAPERCENT", "合格率%");
        map.put("FInStockOwnerName", "入库货主");
        map.put("FPICKEDQTY", "材料套数");
        map.put("FSTATUSNAME", "业务状态");
        map.put("FCLOSETYPENAME", "结案类型");
        return map;
    }

    /**
     * 委外订单执行明细表FieldKey到中文字段名的映射
     */
    private HashMap<String, String> getSubOrderDetailFieldKeyMapping() {
        HashMap<String, String> map = new HashMap<>();
        map.put("FSUPPLIERID", "供应商");
        map.put("FROBILLTYPEID", "单据类型");
        map.put("FROBILLNO", "委外订单编号");
        map.put("FROENTRYSEQ", "委外订单行号");
        map.put("FMATERIALID", "产品编码");
        map.put("FMATERIALNAME", "产品名称");
        map.put("FMATERIALMODEL", "规格型号");
        map.put("FPRODUCTTYPENAME", "产品类型");
        map.put("FAUXPROP", "辅助属性");
        map.put("FBOMID", "BOM版本");
        map.put("FMTONO", "计划跟踪号");
        map.put("FPLANSTARTDATE", "计划开工日期");
        map.put("FPLANFINISHDATE", "计划完工日期");
        map.put("FREQSRC", "需求来源");
        map.put("FSaleOrderNo", "需求单据");
        map.put("FSALEORDERENTRYSEQ", "需求单据行号");
        map.put("FCONVEYDATE", "下达日期");
        map.put("FFINISHDATE", "完工日期");
        map.put("FPRDUNITID", "委外单位");
        map.put("FPLANQTY", "计划生产数量");
        map.put("FPURQTY", "采购执行数量");
        map.put("FSTOCKINQTY", "入库数量");
        map.put("FNOSTOCKINQTY", "未入库数量");
        map.put("FPLANFINISHPERCENT", "计划完成百分比");
        map.put("FInStockOwnerName", "入库货主");
        map.put("FPICKEDQTY", "已领套数");
        map.put("FSTATUSNAME", "业务状态");
        map.put("FCLOSETYPENAME", "结案类型");
        return map;
    }

    /**
     * 委外订单执行明细表
     * POST /api/k3cloud/reports/sub-order-detail
     */
    @ApiOperation(value = "委外订单执行明细表", notes = "查询委外订单执行明细数据")
    @PostMapping(value = "/sub-order-detail", consumes = "application/json", produces = "application/json")
    public Object subOrderDetail(@RequestBody String rawJson) throws Exception {
        K3CloudApi client = new K3CloudApi();

        System.err.println("===== 委外订单执行明细表原始JSON =====");
        System.err.println(rawJson);

        // 解析FieldKeys
        JSONObject requestJson = JSON.parseObject(rawJson);
        String fieldKeysStr = requestJson.getString("FieldKeys");

        // 获取字段Key到中文名称的映射
        HashMap<String, String> fieldKeyToChinese = getSubOrderDetailFieldKeyMapping();

        String formId = "SUB_ROExecuteDetailRpt";
        String resultJson = client.getSysReportData(formId, rawJson);
        System.err.println("===== 金蝶返回结果 =====");
        System.err.println(resultJson);

        KdResult kdResult = JSON.parseObject(resultJson, KdResult.class);
        List<List<String>> rows = (kdResult.getResult() != null && kdResult.getResult().getRows() != null)
                ? kdResult.getResult().getRows()
                : new ArrayList<>();

        // 解析FieldKeys
        String[] fieldKeys = fieldKeysStr != null ? fieldKeysStr.split(",") : new String[0];

        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        for (List<String> r : rows) {
            HashMap<String, String> data = new HashMap<>();
            // 动态根据FieldKeys顺序映射
            for (int i = 0; i < r.size() && i < fieldKeys.length; i++) {
                String fk = fieldKeys[i].trim();
                String chineseName = fieldKeyToChinese.get(fk);
                if (chineseName != null) {
                    data.put(chineseName, getSafe(r, i));
                } else {
                    // 如果没有映射，使用原始FieldKey
                    data.put(fk, getSafe(r, i));
                }
            }
            list.add(data);
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("list", list);
        return result;
    }

    @ApiOperation(value = "现金流量表", notes = "根据请求参数查询现金流量表并返回数据列表\n\n" +
            "【请求参数说明】\n\n" +
            "FACCTBOOKID: 账簿，示例: {\"FNumber\":\"001\"}\n" +
            "FCURRENCYID: 币别，示例: \"1\"\n" +
            "FByPeriod: 按期间查询，示例: \"1\"\n" +
            "FByDate: 按日期查询，示例: \"1\"\n" +
            "FSTARTYEAR: 会计年度（起始），示例: \"2026\"\n" +
            "FSTARTPERIOD: 会计期间（起始），示例: \"1\"\n" +
            "FENDYEAR: 会计年度（结束），示例: \"2026\"\n" +
            "FENDPERIOD: 会计期间（结束），示例: \"12\"\n" +
            "FSTARTDATE: 开始日期，示例: \"2026-01-01\"\n" +
            "FENDDATE: 结束日期，示例: \"2026-12-31\"\n" +
            "FIncNotPost: 包括未过账凭证，示例: false\n\n" +
            "【响应字段说明】\n\n" +
            "FItemTypeDesc: 项目类别\n" +
            "FCashItem: 现金流量项目\n" +
            "FRowNumber: 行次\n" +
            "FAmount: 金额\n" +
            "FPercent: 比重\n\n" +
            "【示例请求】\n" +
            "{\n" +
            "  \"FieldKeys\": \"FItemTypeDesc,FCashItem,FRowNumber,FAmount,FPercent\",\n" +
            "  \"SchemeId\": \"\",\n" +
            "  \"StartRow\": 0,\n" +
            "  \"Limit\": 2000,\n" +
            "  \"IsVerifyBaseDataField\": \"true\",\n" +
            "  \"FilterString\": [],\n" +
            "  \"Model\": {\n" +
            "    \"FACCTBOOKID\": {\"FNumber\": \"001\"},\n" +
            "    \"FCURRENCYID\": \"1\",\n" +
            "    \"FByPeriod\": \"1\",\n" +
            "    \"FSTARTYEAR\": \"2026\",\n" +
            "    \"FSTARTPERIOD\": \"1\",\n" +
            "    \"FENDYEAR\": \"2026\",\n" +
            "    \"FENDPERIOD\": \"12\"\n" +
            "  }\n" +
            "}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = SubOrderDetailResponse.class),
            @ApiResponse(code = 500, message = "服务器内部错误")
    })
    @PostMapping(value = "/cash-flow-report", consumes = "application/json", produces = "application/json")
    public Object cashFlowReport(@RequestBody String rawJson) throws Exception {
        K3CloudApi client = new K3CloudApi();

        System.err.println("===== 现金流量表原始JSON =====");
        System.err.println(rawJson);

        // 解析请求JSON获取FieldKeys
        JSONObject requestObj = JSON.parseObject(rawJson);
        String fieldKeysStr = requestObj.getString("FieldKeys");

        // 建立FieldKey到中文字段名的映射
        HashMap<String, String> fieldKeyToChinese = getCashFlowFieldKeyMapping();

        // 解析FieldKeys
        String[] fieldKeys = fieldKeysStr != null ? fieldKeysStr.split(",") : new String[0];

        String formId = "GL_Rpt_CashFlow";
        String resultJson = client.getSysReportData(formId, rawJson);
        System.err.println("===== 金蝶返回结果 =====");
        System.err.println(resultJson);

        KdResult kdResult = JSON.parseObject(resultJson, KdResult.class);
        List<List<String>> rows = (kdResult.getResult() != null && kdResult.getResult().getRows() != null)
                ? kdResult.getResult().getRows()
                : new ArrayList<>();

        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        for (List<String> r : rows) {
            HashMap<String, String> data = new HashMap<>();
            // 动态根据FieldKeys顺序映射
            for (int i = 0; i < r.size() && i < fieldKeys.length; i++) {
                String fk = fieldKeys[i].trim();
                String chineseName = fieldKeyToChinese.get(fk);
                if (chineseName != null) {
                    data.put(chineseName, getSafe(r, i));
                } else {
                    // 如果没有映射，使用原始FieldKey
                    data.put(fk, getSafe(r, i));
                }
            }
            list.add(data);
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("list", list);
        return result;
    }

    /**
     * 现金流量表FieldKey到中文字段名的映射
     */
    private HashMap<String, String> getCashFlowFieldKeyMapping() {
        HashMap<String, String> map = new HashMap<>();
        map.put("FItemTypeDesc", "项目类别");
        map.put("FCashItem", "现金流量项目");
        map.put("FRowNumber", "行次");
        map.put("FAmount", "金额");
        map.put("FPercent", "比重");
        return map;
    }

    /**
     * 应付款汇总表FieldKey到中文字段名的映射
     */
    private HashMap<String, String> getApSummaryFieldKeyMapping() {
        HashMap<String, String> map = new HashMap<>();
        // 基本信息
        map.put("FCONTACTUNITNUMBER", "往来单位编码");
        map.put("FCONTACTUNITNAME", "往来单位名称");
        map.put("FBUSINESSORGNAME", "采购组织");
        map.put("FSETTLEORGNAME", "结算组织");
        map.put("FRPORGNAME", "付款组织");
        map.put("FBUSINESSDEPTNAME", "采购部门名称");
        map.put("FBUSINESSGROUPNAME", "采购组");
        map.put("FBUSINESSNAME", "采购员");
        // 金额字段（原币）
        map.put("FCURRENCYFORNAME", "币别（原币）");
        map.put("FINITAMOUNTFOR", "期初余额（原币）");
        map.put("FAMOUNTFOR", "本期应付（原币）");
        map.put("FHADIVAMOUNTFOR", "已开票金额（原币）");
        map.put("FREALAMOUNTFOR", "本期付款（原币）");
        map.put("FOFFAMOUNTFOR", "本期冲销额（原币）");
        map.put("FLEFTAMOUNTFOR", "期末余额（原币）");
        // 金额字段（本位币）
        map.put("FCURRENCYNAME", "币别（本位币）");
        map.put("FINITAMOUNT", "期初余额（本位币）");
        map.put("FAMOUNT", "本期应付（本位币）");
        map.put("FHADIVAMOUNT", "已开票金额（本位币）");
        map.put("FREALAMOUNT", "本期付款（本位币）");
        map.put("FOFFAMOUNT", "本期冲销额（本位币）");
        map.put("FLEFTAMOUNT", "期末余额（本位币）");
        // 其他
        map.put("FYEARPERIOD", "期间");
        map.put("FSUPPLIERID", "供应商");
        map.put("FSUPPLIERGROUPNAME", "供应商分组");
        map.put("FBUSINESSDEPTID", "采购部门");
        map.put("FBUSINESSDEPTGNAME", "采购部门分组");
        map.put("FBILLTYPENAME", "单据类型");
        map.put("FCURRENCYRECNAME", "付款币别");
        map.put("FREFUNDAMOUNTFOR", "付款币别金额（原币）");
        map.put("FGROUPSUPPLIERNAME", "集团供应商");
        return map;
    }

    /**
     * 应收款汇总表FieldKey到中文字段名的映射
     */
    private HashMap<String, String> getArSummaryFieldKeyMapping() {
        HashMap<String, String> map = new HashMap<>();
        // 基本信息
        map.put("FCONTACTUNITNUMBER", "往来单位编码");
        map.put("FCONTACTUNITNAME", "往来单位名称");
        map.put("FBUSINESSORGNAME", "销售组织");
        map.put("FSETTLEORGNAME", "结算组织");
        map.put("FRPORGNAME", "收款组织");
        map.put("FBUSINESSDEPTNAME", "销售部门名称");
        map.put("FBUSINESSGROUPNAME", "销售组");
        map.put("FBUSINESSNAME", "销售员");
        // 金额字段（原币）
        map.put("FCURRENCYFORNAME", "币别（原币）");
        map.put("FINITAMOUNTFOR", "期初余额（原币）");
        map.put("FAMOUNTFOR", "本期应收（原币）");
        map.put("FHADIVAMOUNTFOR", "已开票金额（原币）");
        map.put("FREALAMOUNTFOR", "本期收款（原币）");
        map.put("FOFFAMOUNTFOR", "本期冲销额（原币）");
        map.put("FLEFTAMOUNTFOR", "期末余额（原币）");
        // 金额字段（本位币）
        map.put("FCURRENCYNAME", "币别（本位币）");
        map.put("FINITAMOUNT", "期初余额（本位币）");
        map.put("FAMOUNT", "本期应收（本位币）");
        map.put("FHADIVAMOUNT", "已开票金额（本位币）");
        map.put("FREALAMOUNT", "本期收款（本位币）");
        map.put("FOFFAMOUNT", "本期冲销额（本位币）");
        map.put("FLEFTAMOUNT", "期末余额（本位币）");
        // 其他
        map.put("FYEARPERIOD", "期间");
        map.put("FCUSTOMERID", "客户");
        map.put("FCUSTOMERGROUPNAME", "客户分组");
        map.put("FBUSINESSDEPTID", "销售部门");
        map.put("FBUSINESSDEPTGNAME", "销售部门分组");
        map.put("FBILLTYPENAME", "单据类型");
        map.put("FCURRENCYRECNAME", "收款币别");
        map.put("FREFUNDAMOUNTFOR", "收款币别金额（原币）");
        map.put("FGROUPCUSTOMERNAME", "集团客户");
        return map;
    }

    /**
     * 产品维度利润分析表FieldKey到中文字段名的映射
     */
    private HashMap<String, String> getProductProfitAnalysisFieldKeyMapping() {
        HashMap<String, String> map = new HashMap<>();
        // 基本信息
        map.put("FYearPeriod", "期间");
        map.put("FPRODUCTID", "产品编码");
        map.put("FPRODUCTNAME", "产品名称");
        map.put("FPRODUCTMODEL", "规格型号");
        map.put("FAuxPropName", "辅助属性");
        map.put("FLOTNUMBER", "批号");
        map.put("FMATERIALBASEName", "物料属性");
        map.put("FMATERIALGROUP", "物料分组");
        map.put("FBASEUNITNAME", "基本单位");
        map.put("FSETTLEORGNAME", "结算组织");
        map.put("FSALEORGNAME", "销售组织");
        map.put("FSALEDEPTID", "销售部门");
        map.put("FSALESGROUPNAME", "销售组");
        map.put("FSALERID", "销售员");
        map.put("FCUSTOMERID", "客户编码");
        map.put("FCUSTOMERNAME", "客户名称");
        map.put("FCUSTOMERTYPENAME", "客户类型");
        map.put("FCUSTOMERCOUNTORY", "客户国家");
        map.put("FCUSTOMERAREA", "客户地区");
        map.put("FCUSTOMERGROUP", "客户分组");
        // 数量和金额字段
        map.put("FSALEQTY", "销售数量");
        map.put("FSALEPRICE", "销售价格");
        map.put("FSALEAMOUNT", "销售金额");
        map.put("FCOSTQTY", "成本数量");
        map.put("FCOSTPRICE", "成本单价");
        map.put("FCOSTAMOUNT", "产品成本");
        map.put("FSALESPROFIT", "销售毛利");
        map.put("FPROFITRATE", "毛利率%");
        map.put("FTOTALCOSTPRICE", "总成本单价");
        map.put("FTOTALCOSTAMOUNT", "总成本");
        map.put("FPRIODAMOUNT", "期间费用");
        map.put("FSALENETINCOME", "销售净利");
        map.put("FNETINCOMERATE", "净利率%");
        map.put("FCONTRACTAMOUNT", "合同履约成本");
        return map;
    }

    @ApiOperation(value = "产品维度利润分析表", notes = "根据请求参数查询产品维度利润分析表并返回数据列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = SubOrderDetailResponse.class),
            @ApiResponse(code = 500, message = "服务器内部错误")
    })
    @PostMapping(value = "/product-profit-analysis-report", consumes = "application/json", produces = "application/json")
    public Object productProfitAnalysisReport(@RequestBody String rawJson) throws Exception {
        K3CloudApi client = new K3CloudApi();

        System.err.println("===== 产品维度利润分析表原始JSON =====");
        System.err.println(rawJson);

        // 解析请求JSON获取FieldKeys
        JSONObject requestObj = JSON.parseObject(rawJson);
        String fieldKeysStr = requestObj.getString("FieldKeys");

        // 如果没有提供FieldKeys，使用默认值（包含期间字段和主要财务指标）
        if (fieldKeysStr == null || fieldKeysStr.trim().isEmpty()) {
            fieldKeysStr = "FYearPeriod,FPRODUCTID,FPRODUCTNAME,FPRODUCTMODEL,FSALEQTY,FSALEPRICE,FSALEAMOUNT,FCOSTAMOUNT,FSALESPROFIT,FPROFITRATE,FSALENETINCOME,FNETINCOMERATE";
            requestObj.put("FieldKeys", fieldKeysStr);
            // 使用修改后的JSON发送给金蝶
            rawJson = requestObj.toJSONString();
            System.err.println("===== 使用默认FieldKeys =====");
            System.err.println(rawJson);
        }

        // 建立FieldKey到中文字段名的映射
        HashMap<String, String> fieldKeyToChinese = getProductProfitAnalysisFieldKeyMapping();

        // 解析FieldKeys
        String[] fieldKeys = fieldKeysStr != null ? fieldKeysStr.split(",") : new String[0];

        // 直接使用JSON发送给金蝶
        String formId = "CB_PROSALEPROFITRPT";
        String resultJson = client.getSysReportData(formId, rawJson);
        System.err.println("===== 金蝶返回结果 =====");
        System.err.println(resultJson);

        KdResult kdResult = JSON.parseObject(resultJson, KdResult.class);
        List<List<String>> rows = (kdResult.getResult() != null && kdResult.getResult().getRows() != null)
                ? kdResult.getResult().getRows()
                : new ArrayList<>();

        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        for (List<String> r : rows) {
            HashMap<String, String> data = new HashMap<>();
            // 动态根据FieldKeys顺序映射
            for (int i = 0; i < r.size() && i < fieldKeys.length; i++) {
                String fk = fieldKeys[i].trim();
                String chineseName = fieldKeyToChinese.get(fk);
                if (chineseName != null) {
                    data.put(chineseName, getSafe(r, i));
                } else {
                    // 如果没有映射，使用原始FieldKey
                    data.put(fk, getSafe(r, i));
                }
            }
            list.add(data);
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("list", list);
        return result;
    }

    @ApiOperation(value = "应收款汇总表", notes = "根据请求参数查询应收款汇总表并返回数据列表\n\n" +
            "FAMOUNTFOR: 本期应付（原币）\n" +
            "FREALAMOUNTFOR: 本期付款（原币）\n" +
            "FLEFTAMOUNTFOR: 期末余额（原币）\n\n" +
            "【示例请求】\n" +
            "{\n" +
            "  \"FieldKeys\": \"FCONTACTUNITNUMBER,FCONTACTUNITNAME,FBUSINESSORGNAME,FSETTLEORGNAME,FCURRENCYFORNAME,FINITAMOUNTFOR,FAMOUNTFOR,FREALAMOUNTFOR,FLEFTAMOUNTFOR\",\n" +
            "  \"SchemeId\": \"\",\n" +
            "  \"StartRow\": 0,\n" +
            "  \"Limit\": 2000,\n" +
            "  \"IsVerifyBaseDataField\": \"true\",\n" +
            "  \"FilterString\": [],\n" +
            "  \"Model\": {\n" +
            "    \"FSettleOrgLst\": \"101\",\n" +
            "    \"FBeginDate\": \"2026-01-01\",\n" +
            "    \"FEndDate\": \"2026-12-31\"\n" +
            "  }\n" +
            "}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = SubOrderDetailResponse.class),
            @ApiResponse(code = 500, message = "服务器内部错误")
    })
    @PostMapping(value = "/ap-summary-report", consumes = "application/json", produces = "application/json")
    public Object apSummaryReport(@RequestBody String rawJson) throws Exception {
        K3CloudApi client = new K3CloudApi();

        System.err.println("===== 应付款汇总表原始JSON =====");
        System.err.println(rawJson);

        // 解析FieldKeys
        JSONObject requestJson = JSON.parseObject(rawJson);
        String fieldKeysStr = requestJson.getString("FieldKeys");

        // 获取字段Key到中文名称的映射
        HashMap<String, String> fieldKeyToChinese = getApSummaryFieldKeyMapping();

        String formId = "AP_SumReport";
        String resultJson = client.getSysReportData(formId, rawJson);
        System.err.println("===== 金蝶返回结果 =====");
        System.err.println(resultJson);

        KdResult kdResult = JSON.parseObject(resultJson, KdResult.class);
        List<List<String>> rows = (kdResult.getResult() != null && kdResult.getResult().getRows() != null)
                ? kdResult.getResult().getRows()
                : new ArrayList<>();

        // 解析FieldKeys
        String[] fieldKeys = fieldKeysStr != null ? fieldKeysStr.split(",") : new String[0];

        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        for (List<String> r : rows) {
            HashMap<String, String> data = new HashMap<>();
            // 动态根据FieldKeys顺序映射
            for (int i = 0; i < r.size() && i < fieldKeys.length; i++) {
                String fk = fieldKeys[i].trim();
                String chineseName = fieldKeyToChinese.get(fk);
                if (chineseName != null) {
                    data.put(chineseName, getSafe(r, i));
                } else {
                    // 如果没有映射，使用原始FieldKey
                    data.put(fk, getSafe(r, i));
                }
            }
            list.add(data);
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("list", list);
        return result;
    }

    @ApiOperation(value = "应收款汇总表", notes = "根据请求参数查询应收款汇总表并返回数据列表\n\n" +
            "【请求参数说明】\n\n" +
            "FSettleOrgLst: 结算组织，示例: \"101\"\n" +
            "FBeginDate: 开始日期，示例: \"2026-01-01\"\n" +
            "FEndDate: 结束日期，示例: \"2026-12-31\"\n" +
            "FNOAUDIT: 包括未审核单据，示例: false\n\n" +
            "【响应字段说明】\n\n" +
            "FCONTACTUNITNUMBER: 往来单位编码\n" +
            "FCONTACTUNITNAME: 往来单位名称\n" +
            "FBUSINESSORGNAME: 销售组织\n" +
            "FSETTLEORGNAME: 结算组织\n" +
            "FCURRENCYFORNAME: 币别（原币）\n" +
            "FINITAMOUNTFOR: 期初余额（原币）\n" +
            "FAMOUNTFOR: 本期应收（原币）\n" +
            "FREALAMOUNTFOR: 本期收款（原币）\n" +
            "FLEFTAMOUNTFOR: 期末余额（原币）\n\n" +
            "【示例请求】\n" +
            "{\n" +
            "  \"FieldKeys\": \"FCONTACTUNITNUMBER,FCONTACTUNITNAME,FBUSINESSORGNAME,FSETTLEORGNAME,FCURRENCYFORNAME,FINITAMOUNTFOR,FAMOUNTFOR,FREALAMOUNTFOR,FLEFTAMOUNTFOR\",\n" +
            "  \"SchemeId\": \"\",\n" +
            "  \"StartRow\": 0,\n" +
            "  \"Limit\": 2000,\n" +
            "  \"IsVerifyBaseDataField\": \"true\",\n" +
            "  \"FilterString\": [],\n" +
            "  \"Model\": {\n" +
            "    \"FSettleOrgLst\": \"101\",\n" +
            "    \"FBeginDate\": \"2026-01-01\",\n" +
            "    \"FEndDate\": \"2026-12-31\"\n" +
            "  }\n" +
            "}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = SubOrderDetailResponse.class),
            @ApiResponse(code = 500, message = "服务器内部错误")
    })
    @PostMapping(value = "/ar-summary-report", consumes = "application/json", produces = "application/json")
    public Object arSummaryReport(@RequestBody String rawJson) throws Exception {
        K3CloudApi client = new K3CloudApi();

        System.err.println("===== 应收款汇总表原始JSON =====");
        System.err.println(rawJson);

        // 解析FieldKeys
        JSONObject requestJson = JSON.parseObject(rawJson);
        String fieldKeysStr = requestJson.getString("FieldKeys");

        // 获取字段Key到中文名称的映射
        HashMap<String, String> fieldKeyToChinese = getArSummaryFieldKeyMapping();

        String formId = "AR_SumReport";
        String resultJson = client.getSysReportData(formId, rawJson);
        System.err.println("===== 金蝶返回结果 =====");
        System.err.println(resultJson);

        KdResult kdResult = JSON.parseObject(resultJson, KdResult.class);
        List<List<String>> rows = (kdResult.getResult() != null && kdResult.getResult().getRows() != null)
                ? kdResult.getResult().getRows()
                : new ArrayList<>();

        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        for (List<String> r : rows) {
            HashMap<String, String> data = new HashMap<>();
            // FCONTACTUNITNUMBER, FCONTACTUNITNAME, FBUSINESSORGNAME, FSETTLEORGNAME, FRPORGNAME,
            // FBUSINESSDEPTNAME, FBUSINESSGROUPNAME, FBUSINESSERNAME, FCURRENCYFORNAME, FINITAMOUNTFOR,
            // FAMOUNTFOR, FHADIVAMOUNTFOR, FREALAMOUNTFOR, FOFFAMOUNTFOR, FLEFTAMOUNTFOR, FCURRENCYNAME,
            // FINITAMOUNT, FAMOUNT, FHADIVAMOUNT, FREALAMOUNT, FOFFAMOUNT, FLEFTAMOUNT, FYEARPERIOD
            if (r.size() > 0) data.put("FCONTACTUNITNUMBER", getSafe(r,0));
            if (r.size() > 1) data.put("FCONTACTUNITNAME", getSafe(r,1));
            if (r.size() > 2) data.put("FBUSINESSORGNAME", getSafe(r,2));
            if (r.size() > 3) data.put("FSETTLEORGNAME", getSafe(r,3));
            if (r.size() > 4) data.put("FRPORGNAME", getSafe(r,4));
            if (r.size() > 5) data.put("FBUSINESSDEPTNAME", getSafe(r,5));
            if (r.size() > 6) data.put("FBUSINESSGROUPNAME", getSafe(r,6));
            if (r.size() > 7) data.put("FBUSINESSERNAME", getSafe(r,7));
            if (r.size() > 8) data.put("FCURRENCYFORNAME", getSafe(r,8));
            if (r.size() > 9) data.put("FINITAMOUNTFOR", getSafe(r,9));
            if (r.size() > 10) data.put("FAMOUNTFOR", getSafe(r,10));
            if (r.size() > 11) data.put("FHADIVAMOUNTFOR", getSafe(r,11));
            if (r.size() > 12) data.put("FREALAMOUNTFOR", getSafe(r,12));
            if (r.size() > 13) data.put("FOFFAMOUNTFOR", getSafe(r,13));
            if (r.size() > 14) data.put("FLEFTAMOUNTFOR", getSafe(r,14));
            if (r.size() > 15) data.put("FCURRENCYNAME", getSafe(r,15));
            if (r.size() > 16) data.put("FINITAMOUNT", getSafe(r,16));
            if (r.size() > 17) data.put("FAMOUNT", getSafe(r,17));
            if (r.size() > 18) data.put("FHADIVAMOUNT", getSafe(r,18));
            if (r.size() > 19) data.put("FREALAMOUNT", getSafe(r,19));
            if (r.size() > 20) data.put("FOFFAMOUNT", getSafe(r,20));
            if (r.size() > 21) data.put("FLEFTAMOUNT", getSafe(r,21));
            if (r.size() > 22) data.put("FYEARPERIOD", getSafe(r,22));
            list.add(data);
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("list", list);
        return result;
    }

    /**
     * 存货收发存汇总表FieldKey到中文字段名的映射
     */
    private HashMap<String, String> getInventoryInOutSummaryFieldKeyMapping() {
        HashMap<String, String> map = new HashMap<>();
        // 基本信息
        map.put("FYearPeriod", "会计期间");
        map.put("FMATERIALBASEID", "物料编码");
        map.put("FMATERIALNAME", "物料名称");
        map.put("FMATERPROPERTY", "物料属性");
        map.put("FMATERTYPE", "存货类别");
        map.put("FMATERIALGROUP", "物料分组");
        map.put("FMODEL", "规格型号");
        map.put("FSTOCKSTATUSNAME", "库存状态");
        map.put("FLOTNO", "批号");
        map.put("FASSIPROPNAME", "辅助属性");
        map.put("FBOMNO", "BOM版本");
        map.put("FPLANNO", "计划跟踪号");
        map.put("FOWNERNAME", "货主");
        map.put("FSTOCKORGNAME", "库存组织");
        map.put("FSTOCKId", "仓库");
        map.put("FSTOCKPLACENAME", "仓位");
        map.put("FACCTGRANGEID", "核算范围编码");
        map.put("FACCTGRANGENAME", "核算范围名称");
        map.put("FEXPENSEID", "费用项目编码");
        map.put("FEXPENSENAME", "费用项目名称");
        map.put("FUNITNAME", "基本单位");
        // 期初结存
        map.put("FINITQty", "期初结存数量");
        map.put("FINITPrice", "期初结存单价");
        map.put("FINITAMOUNT", "期初结存金额");
        // 本期收入
        map.put("FRECEIVEQty", "本期收入数量");
        map.put("FRECEIVEPrice", "本期收入单价");
        map.put("FRECEIVEAmount", "本期收入金额");
        // 本期发出
        map.put("FSENDQty", "本期发出数量");
        map.put("FSENDPrice", "本期发出单价");
        map.put("FSENDAmount", "本期发出金额");
        // 期末结存
        map.put("FENDQty", "期末结存数量");
        map.put("FENDPrice", "期末结存单价");
        map.put("FENDAMOUNT", "期末结存金额");
        return map;
    }

    @ApiOperation(value = "存货收发存汇总表", notes = "根据请求参数查询存货收发存汇总表并返回数据列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = SubOrderDetailResponse.class),
            @ApiResponse(code = 500, message = "服务器内部错误")
    })
    @PostMapping(value = "/inventory-in-out-summary-report", consumes = "application/json", produces = "application/json")
    public Object inventoryInOutSummaryReport(@RequestBody String rawJson) throws Exception {
        K3CloudApi client = new K3CloudApi();

        System.err.println("===== 存货收发存汇总表原始JSON =====");
        System.err.println(rawJson);

        // 解析请求JSON获取FieldKeys
        JSONObject requestObj = JSON.parseObject(rawJson);
        String fieldKeysStr = requestObj.getString("FieldKeys");

        // 建立FieldKey到中文字段名的映射
        HashMap<String, String> fieldKeyToChinese = getInventoryInOutSummaryFieldKeyMapping();

        // 解析FieldKeys
        String[] fieldKeys = fieldKeysStr != null ? fieldKeysStr.split(",") : new String[0];

        // 直接使用原始JSON发送给金蝶
        String formId = "HS_INOUTSTOCKSUMMARYRPT";
        String resultJson = client.getSysReportData(formId, rawJson);
        System.err.println("===== 金蝶返回结果 =====");
        System.err.println(resultJson);

        KdResult kdResult = JSON.parseObject(resultJson, KdResult.class);
        List<List<String>> rows = (kdResult.getResult() != null && kdResult.getResult().getRows() != null)
                ? kdResult.getResult().getRows()
                : new ArrayList<>();

        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        for (List<String> r : rows) {
            HashMap<String, String> data = new HashMap<>();
            // 动态根据FieldKeys顺序映射
            for (int i = 0; i < r.size() && i < fieldKeys.length; i++) {
                String fk = fieldKeys[i].trim();
                String chineseName = fieldKeyToChinese.get(fk);
                if (chineseName != null) {
                    data.put(chineseName, getSafe(r, i));
                } else {
                    // 如果没有映射，使用原始FieldKey
                    data.put(fk, getSafe(r, i));
                }
            }
            list.add(data);
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("list", list);
        return result;
    }
}


