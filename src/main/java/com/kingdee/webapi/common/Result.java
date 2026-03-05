package com.kingdee.webapi.common;
import java.util.List;

public class Result {

    private boolean IsSuccess;
    private int RowCount;
    private List<List<String>> Rows;
    public void setIsSuccess(boolean IsSuccess) {
         this.IsSuccess = IsSuccess;
     }
     public boolean getIsSuccess() {
         return IsSuccess;
     }

    public void setRowCount(int RowCount) {
         this.RowCount = RowCount;
     }
     public int getRowCount() {
         return RowCount;
     }

    public void setRows(List<List<String>> Rows) {
         this.Rows = Rows;
     }
     public List<List<String>> getRows() {
         return Rows;
     }

}