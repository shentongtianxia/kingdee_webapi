package com.kingdee.webapi.model;

import lombok.Data;

@Data
public class ReportCell {
    private int row;
    private int col;
    private String project;
    private String value;
    private String formula;
    public ReportCell(int row, int col, String project, String value, String formula) {
        this.row = row;
        this.col = col;
        this.project = project;
        this.value = value;
        this.formula = formula;
    }
}
