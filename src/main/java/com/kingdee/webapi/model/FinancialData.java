package com.kingdee.webapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 科目余额表数据模型
 *
 * @author Kingdee
 * @version 1.0.0
 */
@Data
@ApiModel(description = "科目余额表数据项")
public class FinancialData {
    
    @JsonProperty("科目编码")
    private String fBalanceId;
    
    @JsonProperty("科目名称")
    private String fBalanceName;
    
    @JsonProperty("科目类别")
    private String fAcctType;
    
    @JsonProperty("会计要素")
    private String fAcctGroup;
    
    @JsonProperty("核算维度编码")
    private String fDetailNumber;
    
    @JsonProperty("核算维度名称")
    private String fDetailName;
    
    @JsonProperty("币别")
    private String fCyName;
    
    @JsonProperty("年初余额-原币（借）")
    private String fBeginYearDebit;
    
    @JsonProperty("年初余额-本位币（借）")
    private String fBeginYearDebitLocal;
    
    @JsonProperty("年初余额-原币（贷）")
    private String fBeginYearCredit;
    
    @JsonProperty("年初余额-本位币（贷）")
    private String fBeginYearCreditLocal;
    
    @JsonProperty("期初余额-原币（借）")
    private String fBeginDebit;
    
    @JsonProperty("期初余额-本位币（借）")
    private String fBeginDebitLocal;
    
    @JsonProperty("期初余额-原币（贷）")
    private String fBeginCredit;
    
    @JsonProperty("期初余额-本位币（贷）")
    private String fBeginCreditLocal;
    
    @JsonProperty("本期发生-原币（借）")
    private String fDebit;
    
    @JsonProperty("本期发生-本位币（借）")
    private String fDebitLocal;
    
    @JsonProperty("本期发生-原币（贷）")
    private String fCredit;
    
    @JsonProperty("本期发生-本位币（贷）")
    private String fCreditLocal;
    
    @JsonProperty("本年累计-原币（借）")
    private String fYtdDebit;
    
    @JsonProperty("本年累计-本位币（借）")
    private String fYtdDebitLocal;
    
    @JsonProperty("本年累计-原币（贷）")
    private String fYtdCredit;
    
    @JsonProperty("本年累计-本位币（贷）")
    private String fYtdCreditLocal;
    
    @JsonProperty("期末余额-原币（借）")
    private String fEndDebit;
    
    @JsonProperty("期末余额-本位币（借）")
    private String fEndDebitLocal;
    
    @JsonProperty("期末余额-原币（贷）")
    private String fEndCredit;
    
    @JsonProperty("期末余额-本位币（贷）")
    private String fEndCreditLocal;
    
    @JsonProperty("方向")
    private String fAcctDc;
    
    @JsonProperty("本期实际损益发生额-原币")
    private String fProfit;
    
    @JsonProperty("本期实际损益发生额-本位币")
    private String fProfitLocal;
    
    @JsonProperty("本年累计实际损益发生额-原币")
    private String fYtdProfit;
    
    @JsonProperty("本年累计实际损益发生额-本位币")
    private String fYtdProfitLocal;
    
    @JsonProperty("事业部")
    private String syb;
    
    @JsonProperty("部门")
    private String bm;
    
    @JsonProperty("客户")
    private String kh;
    
    @JsonProperty("事业部编码")
    private String sybNumber;
    
    @JsonProperty("部门编码")
    private String bmNumber;
    
    @JsonProperty("客户编码")
    private String khNumber;
    
    @JsonProperty("专项事件")
    private String cp;
    
    @JsonProperty("专项事件编码")
    private String cpNumber;
    
    @JsonProperty("月份")
    private String month;
    
    @JsonProperty("年份")
    private String year;
    
    @JsonProperty("费用项目")
    private String fyxm;
    
    @JsonProperty("费用项目编码")
    private String fyxmNumber;

    @JsonProperty("组织名字")
    private String orgName;

} 