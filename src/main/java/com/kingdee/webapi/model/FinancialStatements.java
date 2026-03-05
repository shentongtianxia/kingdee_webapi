package com.kingdee.webapi.model;

import java.util.List;

/**
 * 财务报表包装类
 * 
 * @author Kingdee
 * @version 1.0.0
 */
public class FinancialStatements {
    
    private List<BalanceReport> balanceReportList;
    private List<IncomeStatementReport> incomeStatementReport;
    private List<IncomeStatementReport> incomeStatementReport1;
    
    public FinancialStatements() {
    }
    
    public List<BalanceReport> getBalanceReportList() {
        return balanceReportList;
    }
    
    public void setBalanceReportList(List<BalanceReport> balanceReportList) {
        this.balanceReportList = balanceReportList;
    }
    
    public List<IncomeStatementReport> getIncomeStatementReport() {
        return incomeStatementReport;
    }
    
    public void setIncomeStatementReport(List<IncomeStatementReport> incomeStatementReport) {
        this.incomeStatementReport = incomeStatementReport;
    }
    
    public List<IncomeStatementReport> getIncomeStatementReport1() {
        return incomeStatementReport1;
    }
    
    public void setIncomeStatementReport1(List<IncomeStatementReport> incomeStatementReport1) {
        this.incomeStatementReport1 = incomeStatementReport1;
    }
}
