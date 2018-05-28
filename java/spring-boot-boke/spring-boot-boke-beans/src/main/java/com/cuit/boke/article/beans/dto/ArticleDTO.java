package com.cuit.boke.article.beans.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("创建文章")
public class ArticleDTO {

    @ApiModelProperty(value = "文章id", required = true)
    private Integer id;

    @ApiModelProperty(value = "文章类型， 详见 ArticleTypeEnum", required = true)
    private Integer type;

    @ApiModelProperty(value = "文章文章状态，详见 ArticleStatusEnum", required = true)
    private Integer status;

    @ApiModelProperty(value = "是否置顶，详见 ArticleTopEnum", required = true)
    private Integer top;

    @ApiModelProperty(value = "文章标题", required = true)
    private String title;

    @ApiModelProperty(value = "文章作者", required = true)
    private String author;

    @ApiModelProperty(value = "文章摘要", required = true)
    private String brief;

    @ApiModelProperty(value = "文章题图", required = true)
    private String picUrl;

    @ApiModelProperty(value = "文章分类id", required = true)
    private Integer categoryId;

    @ApiModelProperty(value = "文章标签名", required = true)
    private String labelNames;

    @ApiModelProperty(value = "文章html内容", required = true)
    private String htmlContent;

    @ApiModelProperty(value = "文章文字内容", required = true)
    private String stringContent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getLabelNames() {
        return labelNames;
    }

    public void setLabelNames(String labelNames) {
        this.labelNames = labelNames;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getStringContent() {
        return stringContent;
    }

    public void setStringContent(String stringContent) {
        this.stringContent = stringContent;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
