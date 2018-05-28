package com.cuit.boke.api.category.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@ApiModel("分类信息")
public class CategoryUpdateDTO {

    @NotNull
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("分类名")
    @NotBlank
    private String name;
    @ApiModelProperty("分类别名")
    @NotBlank
    private String alias;
    @ApiModelProperty("分类描述")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
