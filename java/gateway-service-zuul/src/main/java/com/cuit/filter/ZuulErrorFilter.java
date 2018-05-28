package com.cuit.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.yinjk.web.core.enums.EApiStatus;
import com.yinjk.web.core.util.JsonMapper;
import com.yinjk.web.core.vo.ResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.net.SocketTimeoutException;

public class ZuulErrorFilter extends ZuulFilter {

    private static final Logger LOG = LoggerFactory.getLogger(ZuulErrorFilter.class);

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        Throwable throwable = RequestContext.getCurrentContext().getThrowable();
        System.out.println(throwable == null);
        return RequestContext.getCurrentContext().getThrowable() != null;
    }

    @Override
    public Object run() {
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            Object e = ctx.get("error.exception");
            System.out.println("进入了ZuulErrorFilter。。。");
            if (e != null && e instanceof ZuulException) {
                ResponseVO vo = new ResponseVO();
                vo.setData("");
                vo.setStatus(EApiStatus.ERR_SYS.getStatus());
                ZuulException zuulException = (ZuulException) e;
                LOG.error("Zuul failure detected: " + zuulException.getMessage(), zuulException);

                // Remove error code to prevent further error handling in follow up filters

                // Populate context with new response values

                ctx.getResponse().setCharacterEncoding("utf8");
                //Can set any error code as excepted
                // 对已知的异常进行处理
                if (zuulException.getCause().getCause().getCause() instanceof SocketTimeoutException) {
                    vo.setMessage("网关超时");
                    ctx.getResponse().setContentType("application/json");
                    ctx.setResponseBody(JsonMapper.toJsonString(vo));
                    ctx.setResponseStatusCode(200);
                    ctx.setSendZuulResponse(false);
                } else {
                    ctx.setResponseStatusCode(500);
                    ctx.setResponseBody(zuulException.getMessage());
                }
            } else if (e != null) {
                ResponseVO vo = new ResponseVO();
                vo.setData("");
                vo.setStatus(EApiStatus.ERR_SYS.getStatus());
                vo.setMessage(e.toString());
                ctx.getResponse().setContentType("application/json");
                ctx.setResponseStatusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                ctx.setSendZuulResponse(false);
                String s = JsonMapper.toJsonString(vo);
                ctx.setResponseBody(s);
            }
        }
        catch (Exception ex) {
            LOG.error("Exception filtering in custom error filter", ex);
            ReflectionUtils.rethrowRuntimeException(ex);
        }
        return null;
    }
}
