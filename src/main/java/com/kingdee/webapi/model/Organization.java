package com.kingdee.webapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 组织信息模型类
 *
 * @author Kingdee
 * @version 1.0.0
 */
@ApiModel(description = "组织信息")
public class Organization {

    @ApiModelProperty(value = "组织编码", example = "100")
    @JsonProperty("FNumber")  // 前端发送的是 "FNumber"
    private String fnumber;

    @ApiModelProperty(value = "组织名称", example = "总公司")
    @JsonProperty("FName")    // 前端发送的是 "FName"
    private String fname;
    
    public Organization() {
    }
    
    public Organization(String fnumber, String fname) {
        this.fnumber = fnumber;
        this.fname = fname;
    }
    
    public String getFnumber() {
        return fnumber;
    }
    
    public void setFnumber(String fnumber) {
        this.fnumber = fnumber;
    }
    
    public String getFname() {
        return fname;
    }
    
    public void setFname(String fname) {
        this.fname = fname;
    }
    
    @Override
    public String toString() {
        return "Organization{" +
                "fnumber='" + fnumber + '\'' +
                ", fname='" + fname + '\'' +
                '}';
    }
} 