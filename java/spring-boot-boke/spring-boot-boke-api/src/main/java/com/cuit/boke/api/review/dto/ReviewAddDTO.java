package com.cuit.boke.api.review.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@ApiModel("添加评论")
@Data
public class ReviewAddDTO {

    @NotBlank
    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("用户名")
    private String userName;

    @NotBlank
    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty("用户个人主页")
    private String portraitUrl;

    @ApiModelProperty("评论楼层")
    private Integer floor;

    @ApiModelProperty("评论获赞")
    private Integer praiseCount;

    @NotNull
    @ApiModelProperty("评论文章")
    private Integer articleId;

    @ApiModelProperty("评论父id")
    private Integer parentId;
}
