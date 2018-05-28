package com.cuit.boke.api.log.dto;

import com.cuit.boke.page.PageCommonDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@ApiModel("访问日志查询DTO")
@Data
public class ApiLogDTO extends PageCommonDTO {

    @ApiModelProperty("http请求方法")
    private String httpMethod;

    @ApiModelProperty("请求者ip")
    private String ip;

    @ApiModelProperty("请求的url")
    private String url;

    @ApiModelProperty("请求的类")
    private String clazz;

    @ApiModelProperty("请求的方法")
    private String method;

    @ApiModelProperty("请求者")
    private String userId;

}
