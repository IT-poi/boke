package com.cuit.boke.api.permission.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("修改用户信息")
@Data
public class UserUpdateDTO {

    @ApiModelProperty("用户ID")
    private Integer id;

    @ApiModelProperty("登录名")
    private String userName;

    @ApiModelProperty("昵称")
    private String userNickName;

    @ApiModelProperty("真名")
    private String userFullName;

    @ApiModelProperty("旧密码")
    private String userPwdOld;

    @ApiModelProperty("新密码")
    private String userPwdNew;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("签名")
    private String signature;
}
