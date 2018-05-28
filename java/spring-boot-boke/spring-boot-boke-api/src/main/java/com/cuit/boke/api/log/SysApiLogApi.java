package com.cuit.boke.api.log;

import com.cuit.boke.aop.annotation.SysLoginLog;
import com.cuit.boke.api.log.dto.ApiLogDTO;
import com.cuit.boke.api.log.service.SysApiLogService;
import com.cuit.boke.beans.entry.SysApiLog;
import com.cuit.boke.constant.GwConstants;
import com.yinjk.web.core.enums.EApiStatus;
import com.yinjk.web.core.exception.BizException;
import com.yinjk.web.core.factory.ResponseFactory;
import com.yinjk.web.core.vo.PageVO;
import com.yinjk.web.core.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api("接口日志")
@RequestMapping("/api/1/apilog")
@RestController
public class SysApiLogApi {

    @Autowired
    private SysApiLogService sysApiLogService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation("接口日志列表")
    @SysLoginLog
    public ResponseVO list(@RequestBody @Valid ApiLogDTO apiLogDTO,
                                  @RequestHeader(GwConstants.TRANSPARENT_USERID_FIELD) Integer userId) {
        return ResponseFactory.ok(sysApiLogService.list(apiLogDTO, userId));
    }

    @RequestMapping(value = "/delete/{logIds}", method = RequestMethod.POST)
    @ApiOperation("删除一条或多条")
    @SysLoginLog
    public ResponseVO delete(@PathVariable("logIds") String logIds,
                             @RequestHeader(GwConstants.TRANSPARENT_USERID_FIELD) Integer userId) throws BizException {
        return sysApiLogService.delete(logIds, userId) >= 1 ?
                ResponseFactory.ok(EApiStatus.SUCCESS) :
                ResponseFactory.err("删除失败", EApiStatus.ERR_SYS);
    }



}
