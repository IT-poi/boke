package com.cuit.boke.api.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel
@Data
public class ResetDTO {

    @ApiModelProperty("用户名")
    @NotNull
    private String userName;

    @ApiModelProperty("邮箱")
    @NotNull
    private String email;
}
