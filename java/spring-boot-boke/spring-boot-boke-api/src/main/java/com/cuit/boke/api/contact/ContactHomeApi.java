package com.cuit.boke.api.contact;

import com.cuit.boke.api.contact.dto.ContactDTO;
import com.cuit.boke.api.contact.dto.ContactQueryDTO;
import com.cuit.boke.api.contact.service.ContactService;
import com.cuit.boke.page.PageCommonDTO;
import com.yinjk.web.core.factory.ResponseFactory;
import com.yinjk.web.core.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/0/contact")
@Api(value = "留言管理")
public class ContactHomeApi {

    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加留言", notes = "上传图片")
    public ResponseVO add(@RequestBody @Valid ContactDTO contactDTO) {
        return ResponseFactory.ok(contactService.add(contactDTO));
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "留言列表", notes = "留言列表")
    public ResponseVO list(@RequestBody @Valid ContactQueryDTO contactQueryDTO) {
        return ResponseFactory.ok(contactService.list(contactQueryDTO, null));
    }

}
