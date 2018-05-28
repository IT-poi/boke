package com.cuit.boke.api.article;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class TitlePicDTO {

    private String name;

    private String token;
}
