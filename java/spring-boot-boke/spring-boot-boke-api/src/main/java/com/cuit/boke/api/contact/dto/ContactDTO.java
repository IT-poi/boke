package com.cuit.boke.api.contact.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@ApiModel("留言")
public class ContactDTO {

    @ApiModelProperty("留言主题")
    @NotBlank
    private String subject;

    @ApiModelProperty("留言内容")
    @NotBlank
    private String content;

    @ApiModelProperty("留言用户名")
    @NotBlank
    private String name;

    @ApiModelProperty("关联用户")
    private Integer userId;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("个人主页")
    private String portraitUrl;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
