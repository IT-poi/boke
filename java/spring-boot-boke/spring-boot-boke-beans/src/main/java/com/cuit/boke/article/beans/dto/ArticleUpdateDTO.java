package com.cuit.boke.article.beans.dto;

import com.cuit.boke.enums.article.ArticleStatusEnum;
import com.cuit.boke.enums.article.ArticleTopEnum;
import com.cuit.boke.enums.article.ArticleTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel("修改文章")
public class ArticleUpdateDTO {

    @NotNull
    @ApiModelProperty(value = "文章id")
    private Integer id;

    @ApiModelProperty(value = "文章类别，0表示原创，1表示转载，2表示翻译")
    private Integer type;

    @ApiModelProperty(value = "文章状态，0表示草稿，1表示已发布，2表示私密文章，-1表示删除进入回收站")
    private Integer status;

    @ApiModelProperty(value = "是否置顶，0表示取消置顶，1表示置顶")
    private Integer top;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章作者")
    private String author;

    @ApiModelProperty(value = "文章摘要")
    private String brief;

    @ApiModelProperty(value = "文章分类id", required = true)
    private Integer categoryId;

    @ApiModelProperty(value = "文章题图", required = true)
    private String picUrl;

    @ApiModelProperty(value = "文章标签名, 多个以逗号隔开", required = true)
    private String labelNames;

    @ApiModelProperty(value = "文章html内容", required = true)
    private String htmlContent;

    @ApiModelProperty(value = "文章文字内容", required = true)
    private String stringContent;

    @ApiModelProperty("修改人")
    private Integer updateBy;

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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getLabelNames() {
        return labelNames;
    }

    public void setLabelNames(String labelNames) {
        this.labelNames = labelNames;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
