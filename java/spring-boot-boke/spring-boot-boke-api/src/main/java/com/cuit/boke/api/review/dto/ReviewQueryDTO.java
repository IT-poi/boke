package com.cuit.boke.api.review.dto;

import com.cuit.boke.page.PageCommonDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@ApiModel("添加评论")
@Data
public class ReviewQueryDTO extends PageCommonDTO {

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty("用户个人主页")
    private String portraitUrl;

    @ApiModelProperty("评论楼层")
    private Integer floor;

    @ApiModelProperty("评论获赞")
    private Integer praiseCount;

    @ApiModelProperty("评论文章")
    private Integer articleId;

    @ApiModelProperty("评论父id")
    private Integer parentId;
}
