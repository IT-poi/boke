package com.cuit.boke.api.contact;

import com.cuit.boke.api.contact.dto.ContactQueryDTO;
import com.cuit.boke.api.contact.dto.ContactReplayDTO;
import com.cuit.boke.api.contact.service.ContactService;
import com.cuit.boke.constant.GwConstants;
import com.cuit.boke.page.PageCommonDTO;
import com.yinjk.web.core.enums.EApiStatus;
import com.yinjk.web.core.exception.BizException;
import com.yinjk.web.core.factory.ResponseFactory;
import com.yinjk.web.core.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/1/contact")
@Api(value = "留言管理")
public class ContactApi {

    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "/replay", method = RequestMethod.POST)
    @ApiOperation(value = "回复留言", notes = "回复留言")
    public ResponseVO replay(@RequestBody @Valid ContactReplayDTO contactReplayDTO,
                          @RequestHeader(GwConstants.TRANSPARENT_USERID_FIELD) Integer userId) throws BizException {
        contactService.replay(contactReplayDTO);
        return ResponseFactory.ok(EApiStatus.SUCCESS);
    }

    @RequestMapping(value = "/ignore/{cotactId}", method = RequestMethod.POST)
    @ApiOperation(value = "忽略留言", notes = "忽略留言")
    public ResponseVO ignore(@PathVariable("cotactId") Integer cotactId,
                          @RequestHeader(GwConstants.TRANSPARENT_USERID_FIELD) Integer userId) throws BizException {
        contactService.ignore(cotactId, userId);
        return ResponseFactory.ok(EApiStatus.SUCCESS);
    }

    @RequestMapping(value = "/delete/{contactId}", method = RequestMethod.POST)
    @ApiOperation(value = "删除留言")
    public ResponseVO delete(@PathVariable("contactId") Integer contactId,
                           @RequestHeader(GwConstants.TRANSPARENT_USERID_FIELD) Integer userId) {
        contactService.delete(contactId, userId);
        return ResponseFactory.ok(EApiStatus.SUCCESS);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "留言列表", notes = "留言列表")
    public ResponseVO list(@RequestBody @Valid ContactQueryDTO contactQueryDTO,
                           @RequestHeader(GwConstants.TRANSPARENT_USERID_FIELD) Integer userId) {
        return ResponseFactory.ok(contactService.list(contactQueryDTO, userId));
    }

}
