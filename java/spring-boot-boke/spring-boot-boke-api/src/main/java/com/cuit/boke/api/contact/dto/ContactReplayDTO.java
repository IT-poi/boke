package com.cuit.boke.api.contact.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@ApiModel("回复留言")
public class ContactReplayDTO {

    @ApiModelProperty("留言id")
    @NotNull
    private Integer contactId;

    @ApiModelProperty("回复内容")
    @NotBlank
    private String replayContext;

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public String getReplayContext() {
        return replayContext;
    }

    public void setReplayContext(String replayContext) {
        this.replayContext = replayContext;
    }
}
