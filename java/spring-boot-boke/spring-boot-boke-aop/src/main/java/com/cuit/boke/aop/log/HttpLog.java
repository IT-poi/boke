package com.cuit.boke.aop.log;

import com.cuit.boke.aop.annotation.SysControllerLog;
import com.cuit.boke.beans.entry.LoginRecord;
import com.cuit.boke.beans.entry.SysApiLog;
import com.cuit.boke.constant.GwConstants;
import com.cuit.boke.dao.LoginRecordMapper;
import com.cuit.boke.dao.SysApiLogMapper;
import com.cuit.boke.utils.JsonMapper;
import com.yinjk.web.core.enums.EApiStatus;
import com.yinjk.web.core.exception.BizException;
import com.yinjk.web.core.jwt.util.JwtUtil;
import com.yinjk.web.core.vo.ResponseVO;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import static com.cuit.boke.utils.TransformUtils.castTo;

@Aspect
@Component
public class HttpLog {

    @Autowired
    private LoginRecordMapper loginRecordMapper;

    @Autowired
    private SysApiLogMapper sysApiLogMapper;



    private static Logger logger = LoggerFactory.getLogger(HttpLog.class);

//    @Pointcut("execution(public * com.cuit.boke.api..*.*(..))")
//    public void webLog(){}

//    @Pointcut("@annotation(com.cuit.boke.aop.annotation.SysLoginLog)")
//    public void loginLog(){}
//
//    @Pointcut("@annotation(com.cuit.boke.aop.annotation.SysControllerLog)")
//    public void controllerAspect(){}

    @Pointcut("@annotation(io.swagger.annotations.ApiOperation)")
    public void apiAspect(){}

//    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws IOException {
        //获取request
        Date startDate = new Date();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Logger logger = getLogger(joinPoint.getTarget());
        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + getIp(request));
//        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + JsonMapper.toJsonString(joinPoint.getArgs()));
        Date endDate = new Date();
        System.out.println(endDate.getTime() - startDate.getTime());
    }

//    @AfterThrowing(value = "controllerAspect()", throwing = "e")
    public void doAfter(JoinPoint joinPoint, Throwable e) throws IOException {
        //获取request
        Date startDate = new Date();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Object targetObj = joinPoint.getTarget();
        Logger logger = getLogger(targetObj);
        String description = getDescription(joinPoint);

        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
//        logger.info("IP : " + getIpAddress(request));
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("DESC : " + description);
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        logger.info("ERROR: " + e.getMessage());
        Date endDate = new Date();
        System.out.println(endDate.getTime() - startDate.getTime());
    }

    @Around(value = "apiAspect()")
    public Object apiLog(ProceedingJoinPoint jp) throws Throwable {
        //获得request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //记录接口日志

        SysApiLog sysApiLog = new SysApiLog();
        Object[] args = jp.getArgs();

        String url = request.getRequestURL().toString();
        if (url.contains("/api/1/apilog/")) { //自己不记录，不然会很乱
            return jp.proceed(args);
        }
        String error;
        sysApiLog.setArgs(JsonMapper.toJsonString(args));
        String className = jp.getSignature().getDeclaringTypeName();
        String methodName = jp.getSignature().getName();
        sysApiLog.setClazz(className);
        sysApiLog.setMethod(methodName);
        sysApiLog.setUrl(request.getRequestURL().toString());
        sysApiLog.setHttpMethod(request.getMethod());
        sysApiLog.setIp(getIp(request));
        String userId = request.getHeader(GwConstants.TRANSPARENT_USERID_FIELD);
        sysApiLog.setUserId(castTo(userId, Integer.class));
        sysApiLog.setDesc(getValue(jp));
        sysApiLog.setCreateTime(new Date());
        Object rtv;
        try {
            rtv = jp.proceed(args);
        } catch (Throwable throwable) {
            error = throwable.getMessage();
            sysApiLog.setError(error);
            sysApiLogMapper.insert(sysApiLog);
            throw throwable;
        }
        sysApiLogMapper.insert(sysApiLog);
        return rtv;
    }

    /**
     * 登陆记录
     * @param joinPoint joinPoint
     * @param rtv 登陆返回值
     * @throws BizException 自定义异常
     */
//    @AfterReturning(value = "loginLog()", returning="rtv")
    public void loginRecord(JoinPoint joinPoint, Object rtv) throws BizException {
        System.out.println("方法后处理器。。。");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        ResponseVO responseVO = (ResponseVO) rtv;
        if (EApiStatus.SUCCESS.getStatus().equals(responseVO.getStatus())) { //登陆成功
            LoginRecord loginRecord = new LoginRecord();
            loginRecord.setLoginIp(getIp(request));
            loginRecord.setLoginAddress("成都");
            loginRecord.setLoginTime(new Date());
            String token = (String) responseVO.getData();
            Map jwtBody = JwtUtil.getJwtBody(token);
            Integer userId = castTo(jwtBody.get(GwConstants.JWT_USER_ID_FIELD), Integer.class);
            String userName = castTo(jwtBody.get("userName"), String.class);
            loginRecord.setLoginUserId(userId);
            loginRecord.setLoginUserName(userName);
            loginRecordMapper.insert(loginRecord);
        }
    }

    public static Logger getLogger(Object targetObj) {
        Logger logger = null;
        Field filed = null;
        try {
            filed = targetObj.getClass().getDeclaredField("logger");
            filed.setAccessible(true);
            logger = (Logger) filed.get(targetObj);
            logger = logger == null ? HttpLog.logger : logger;
        } catch (NoSuchFieldException | IllegalAccessException e1) {
            logger = HttpLog.logger;
        }
        return logger;
    }

    public static String getDescription(JoinPoint joinPoint){
        String description = "";
        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
        if (method.isAnnotationPresent(SysControllerLog.class)){
            SysControllerLog annotation = method.getAnnotation(SysControllerLog.class);
            description = annotation.description();
        }
        return description;
    }

    public static String getValue(JoinPoint joinPoint){
        String value = "";
        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
        if (method.isAnnotationPresent(ApiOperation.class)){
            ApiOperation annotation = method.getAnnotation(ApiOperation.class);
            value = annotation.value();
        }
        return value;
    }

    public static String getIp(HttpServletRequest request) {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址

        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }
}