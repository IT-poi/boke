package com.cuit.boke.article.beans.dto;

import com.cuit.boke.page.PageCommonDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "全文搜索文章列表", parent = PageCommonDTO.class)
public class EsArticleQueryDTO extends PageCommonDTO {

    @ApiModelProperty(value = "搜索关键字")
    private String keywords;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
