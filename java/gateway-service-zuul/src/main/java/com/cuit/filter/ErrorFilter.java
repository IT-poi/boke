package com.cuit.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.yinjk.web.core.enums.EApiStatus;
import com.yinjk.web.core.factory.ResponseFactory;
import com.yinjk.web.core.util.JsonMapper;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ErrorFilter extends ZuulFilter {
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
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Throwable throwable = ctx.getThrowable();
        System.out.println("经过了error过滤器");
        if (throwable != null) {
            String message = throwable.getMessage();
            HttpServletResponse response = ctx.getResponse();


            System.out.println(ctx.get("error.status_code"));
            ctx.getResponse().setCharacterEncoding("utf-8");
            ctx.getResponse().setContentType("application/json; charset=utf-8");
            ctx.setResponseStatusCode(500);
            ctx.setResponseBody(JsonMapper.toJsonString(ResponseFactory.err("发生了错误", EApiStatus.ERR_NEED_LOGIN)));// 返回错误内容

//            System.out.println(message);
//            System.out.println("经过了error过滤器");
//            ctx.setSendZuulResponse(false);
//            ctx.setResponseStatusCode(500);
//            response.setContentType("text/html; charset=utf-8");
//            response.setCharacterEncoding("utf-8");
//            try {
//                response.getWriter().write("<div style='color:red'>抱歉，系统发生错误!请稍后再试或联系管理员!</div>");
//                ctx.setResponse(response);
//            } catch (IOException e) {
//                System.out.println("logger 获取响应字符流发生异常"+ e);
//            }
        }
        return null;
    }
}
