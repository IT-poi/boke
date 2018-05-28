package com.cuit.boke.page;

import com.cuit.boke.constant.PageConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
* @Description : 基础分页
* @author yinjiankang
* @date : 2018/1/2
**/
@ApiModel(value = "PageCommonDTO", description = "分页公用属性")
public class PageCommonDTO{
    @ApiModelProperty(value = "分页数目,默认为1，非必须")
    private Integer pageNum;
    @ApiModelProperty(value = "分页大小,默认为10，非必须")
    private Integer pageSize;
    @ApiModelProperty(value = "排序字段")
    private String sorter;

    /** 导出excel的默认一个sheet的数据条数 **/
    public static Integer excelPageSize = 50000;


    public Integer getPageNum() {
        return pageNum == null || pageNum ==0 ? PageConstants.PAGE_NUM : pageNum;
    }

    public void setPageNum(Integer pageNum) {
        if (pageNum == null) {
            this.pageNum = PageConstants.PAGE_NUM;
        }
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize == null ? PageConstants.PAGE_SIZE : pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null) {
            this.pageSize = PageConstants.PAGE_SIZE;
        }
        this.pageSize = pageSize;
    }

    public String getSorter() {
        return sorter;
    }

    public void setSorter(String sorter) {
        this.sorter = sorter;
    }
}