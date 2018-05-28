package com.cuit.boke.api.system;

import com.cuit.boke.aop.annotation.SysControllerLog;
import com.cuit.boke.service.SystemService;
import com.cuit.boke.utils.TimeUtil;
import com.yinjk.web.core.factory.ResponseFactory;
import com.yinjk.web.core.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/1/system")
@Api("系统信息")
public class SystemApi {

    @Autowired
    private SystemService service;

    @RequestMapping(value = "/time", method = {RequestMethod.GET})
    @ApiOperation(value = "系统时间", notes = "系统时间")
    @SysControllerLog(description = "系统时间")
    public ResponseVO sysTime(){
        LocalDateTime now = service.sysTime();
        return ResponseFactory.ok(now.format(TimeUtil.FORMATTER_DEF));
    }

    @RequestMapping(value = "/info", method = {RequestMethod.GET})
    @ApiOperation(value = "系统信息", notes = "系统时间")
    @SysControllerLog(description = "系统信息")
    public ResponseVO info(HttpServletRequest request){
        return ResponseFactory.ok(service.sysInfo(request));
    }


}
