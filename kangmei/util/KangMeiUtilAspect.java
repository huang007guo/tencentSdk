package com.wjj.application.facade.kangmei.util;

import com.wjj.application.facade.kangmei.KangMeiConfig;
import com.wjj.application.facade.kangmei.annotation.RetryProcess;
import com.wjj.application.util.EmailUtilsAsync;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 康美请求工具类切面
 * 这里所有公共方法都应该是对位的接口,公共方法会经过切面:
 * 失败重试二次,重试后仍然失败发邮件,KangmeiBaseException异常直接邮件
 * @author hank
 */
@Slf4j
@Aspect
@Component
public class KangMeiUtilAspect {

    @Autowired
    private KangMeiConfig kangMeiConfig;

    @Autowired
    private EmailUtilsAsync emailUtils;

    @Pointcut("execution(* com.wjj.application.facade.kangmei.util.KangMeiUtil.*(..))")
    public void utilPointcut() {
    }

    @Around("utilPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("康美接口,开始调用,入参:{}", Arrays.toString(joinPoint.getArgs()));
        try {
            Object ret = joinPoint.proceed();
            log.info("康美接口,结束调用,出参:{}", String.valueOf(ret));
            return ret;
        } catch (Throwable ex) {
            log.error("康美接口,调用异常",  ex);
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            RetryProcess retryProcess = methodSignature.getMethod().getAnnotation(RetryProcess.class);
            if(retryProcess != null){
                return runThrowing(joinPoint, ex, 1, retryProcess);
            }
            throw ex;
        }
    }

//    private Object runThrowing(JoinPoint joinPoint, Throwable ex, Integer nowCount) throws Throwable {
//        log.error("KangMeiUtil exception.", ex);
//        if (ex instanceof KangmeiBaseException || nowCount >= kangMeiConfig.getTryCount()) {
//            // 发邮件
//            throw ex;
//        }else{
//            //日志记录功能
//            Class<?> clazz = joinPoint.getTarget().getClass();
//            Class<?>[] par = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
//            Method method = clazz.getMethod(joinPoint.getSignature().getName(), par);
//            try {
//                return method.invoke(joinPoint.getSignature(), joinPoint.getArgs());
//            }catch (Throwable e){
//                return runThrowing(e, joinPoint, ++nowCount);
//            }
//        }
//    }


    public Object runThrowing(JoinPoint point, Throwable ex, Integer nowCount, RetryProcess retryProcess) throws Throwable {
        if (isInstanceof(ex, retryProcess.notRetryThrowable()) || nowCount > retryProcess.value()) {
            if (retryProcess.isRetryFailSendEmail()){
                // 发邮件
                emailUtils.sendText(
                        String.format("康美%s接口调用失败", retryProcess.apiName()),
                        kangMeiConfig.getEmails(),
                        String.format("请求参数:\n\r%s;\n\r异常信息:\n\r%s", Arrays.toString(point.getArgs()), ex.getMessage()));
            }
            throw ex;
        }else{
            log.info("康美请求 开始重试第"+nowCount);
            try {
                MethodInvocationProceedingJoinPoint methodPoint = ((MethodInvocationProceedingJoinPoint) point);
                return methodPoint.proceed();
            } catch (Throwable throwable) {
                log.error("康美请求 重试失败", throwable);
                return runThrowing(point, ex, ++nowCount, retryProcess);
            }
        }
    }

    /**
     * 指定sourceObj是否在inClasses中
     * @param sourceObj
     * @param inClasses
     * @return
     */
    private boolean isInstanceof(Object sourceObj, Class<?>[] inClasses){
        for (Class<?> inObj : inClasses) {
            if(inObj.isInstance(sourceObj))
                return true;
        }
        return false;
    }

}
