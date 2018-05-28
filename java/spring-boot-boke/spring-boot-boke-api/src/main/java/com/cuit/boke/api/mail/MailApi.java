package com.cuit.boke.api.mail;

import com.cuit.boke.utils.MailService;
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

@RestController
@RequestMapping(value = "/api/0/mail")
@Api(value = "邮件服务")
public class MailApi {

    @Autowired
    private MailService mailService;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ApiOperation(value = "发送邮件", notes = "上传图片")
    public ResponseVO uploadTemplate() throws BizException {
        mailService.sendMail("inori.yinjk@gmail.com", "测试", "这是测试内容");
        return ResponseFactory.ok(EApiStatus.SUCCESS);
    }
}
