/**
  * Copyright 2023 bejson.com 
  */
package com.kingdee.webapi.common;

import java.util.List;

/**
 * 金蝶API返回结果包装类
 * 
 * @author Kingdee
 * @version 1.0.0
 */
public class KdResult {
    
    private Result result;
    
    public Result getResult() {
        return result;
    }
    
    public void setResult(Result result) {
        this.result = result;
    }
    
    public static class Result {
        private List<List<String>> rows;
        
        public List<List<String>> getRows() {
            return rows;
        }
        
        public void setRows(List<List<String>> rows) {
            this.rows = rows;
        }
    }
}