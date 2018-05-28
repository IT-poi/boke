package com.cuit.filter;

import com.cuit.GwConstants;
import com.cuit.redis.RedisTools;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.yinjk.web.core.enums.EApiStatus;
import com.yinjk.web.core.factory.ResponseFactory;
import com.yinjk.web.core.util.JsonMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class AccessFilter extends ZuulFilter {

    private static final JwtParser jwtParser = Jwts.parser();

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String reqUrl = request.getRequestURL().toString();
        System.out.println(request.getRequestURL());
        System.out.println(request.getRequestURI());
        //不拦截登录注册页面
        if (reqUrl.matches("\\S+/login") || reqUrl.matches("\\S+/register")
                || reqUrl.matches("\\S+/api/0\\S+")) {
            String accessToken = request.getHeader(GwConstants.REQ_AUTH_HEAD_FIELD);
            if (StringUtils.isBlank(accessToken)) {
                ctx.addZuulRequestHeader(GwConstants.TRANSPARENT_USERID_FIELD, "-1");
                ctx.addZuulRequestHeader(GwConstants.TRANSPARENT_TOKEN_FIELD, "-1");
            }
            return false;
        }
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        try{
            String accessToken = request.getHeader(GwConstants.REQ_AUTH_HEAD_FIELD);
            if (RedisTools.siscontains(GwConstants.REDIS_USER_TOKEN_LOGOUT_KEY, accessToken)) {
                this.returnError(ctx, "您已注销，请重新登陆!");
                return null;
            }
            if (StringUtils.isNotBlank(accessToken) && jwtParser.isSigned(accessToken)) {
                Map jwtBody =getJwtBody(accessToken);
                if (jwtBody != null) {
                    Object userId = jwtBody.get(GwConstants.JWT_USER_ID_FIELD);
                    if (userId != null) {
                        ctx.addZuulRequestHeader(GwConstants.TRANSPARENT_USERID_FIELD, String.valueOf(userId));
                        ctx.addZuulRequestHeader(GwConstants.TRANSPARENT_TOKEN_FIELD, accessToken);
                        return null;
                    } else {
                        this.returnError(ctx, "token parser error");
                        return null;
                    }
                } else {
                    this.returnError(ctx, "token parser error");
                    return null;
                }
            }
        }catch (ExpiredJwtException ex) {
            this.returnError(ctx, "JWT expired ");
            return null;
        }catch (SignatureException ex){
            this.returnError(ctx, "JWT 签名错误 ");
            return null;
        } catch (Exception ex){
            // nothing
        }
        this.returnError(ctx, "token认证失败");
        return null;
    }

    private void returnError(RequestContext ctx, String errorMsg) {
        ctx.setSendZuulResponse(false);
        ctx.getResponse().setCharacterEncoding("utf-8");
        ctx.getResponse().setContentType("application/json; charset=utf-8");
        ctx.setResponseStatusCode(401);
        ctx.setResponseBody(JsonMapper.toJsonString(ResponseFactory.err(errorMsg, EApiStatus.ERR_NEED_LOGIN)));// 返回错误内容
        ctx.set("isSuccess", false);
    }

    public static Map getJwtBody(String token) {
        if (token == null) {
            return null;
        } else {
            JwtParser jwtParser = Jwts.parser().setSigningKey("zA/*xp?$[/5*z");
            if (jwtParser.isSigned(token)) {
                Object obj = jwtParser.parse(token).getBody();
                return obj != null ? (Map)obj : null;
            } else {
                return null;
            }
        }
    }

}
