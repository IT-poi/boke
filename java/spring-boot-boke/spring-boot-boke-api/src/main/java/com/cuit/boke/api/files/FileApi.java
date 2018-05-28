package com.cuit.boke.api.files;

import com.cuit.boke.api.files.service.FileService;
import com.yinjk.web.core.enums.EApiStatus;
import com.yinjk.web.core.exception.BizException;
import com.yinjk.web.core.factory.ResponseFactory;
import com.yinjk.web.core.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/0/file")
@Api(value = "文件管理")
public class FileApi {

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/uploadPic", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "上传图片", notes = "上传图片")
    public ResponseVO uploadTemplate(HttpServletRequest request) throws BizException {
        return ResponseFactory.ok(fileService.uploadPic(request));
    }
}
