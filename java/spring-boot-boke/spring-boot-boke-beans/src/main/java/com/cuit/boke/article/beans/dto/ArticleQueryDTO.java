package com.cuit.boke.article.beans.dto;

import com.cuit.boke.page.PageCommonDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "查询文章列表", parent = PageCommonDTO.class)
public class ArticleQueryDTO extends PageCommonDTO {

    @ApiModelProperty(value = "文章id")
    private Integer id;

    @ApiModelProperty(value = "文章类型")
    private Integer type;

    @ApiModelProperty(value = "文章文章状态")
    private Integer status;

    @ApiModelProperty(value = "是否置顶")
    private Integer top;

    @ApiModelProperty("文章分类")
    private Integer categoryId;

    @ApiModelProperty("分类别名")
    private String alias;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章作者")
    private String author;

    @ApiModelProperty(value = "文章摘要")
    private String brief;

    @ApiModelProperty(value = "文章标签名")
    private String labelNames;

    @ApiModelProperty("创建人")
    private Integer createBy;

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

    public String getLabelNames() {
        return labelNames;
    }

    public void setLabelNames(String labelNames) {
        this.labelNames = labelNames;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
