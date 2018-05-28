package com.cuit.boke.api.contact.dto;

import com.cuit.boke.page.PageCommonDTO;
import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@ApiModel("回复留言")
public class ContactQueryDTO extends PageCommonDTO{

    @ApiModelProperty("留言id")
    private Integer replay;

    @ApiModelProperty("回复内容")
    private Integer ignore;

    public Integer getReplay() {
        return replay;
    }

    public void setReplay(Integer replay) {
        this.replay = replay;
    }

    public Integer getIgnore() {
        return ignore;
    }

    public void setIgnore(Integer ignore) {
        this.ignore = ignore;
    }
}
