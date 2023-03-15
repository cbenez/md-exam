package com.exercise.heros.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * 
 * @author CRISTIAN
 */

@Aspect
@Component
public class AuditTimeAspect {

    private static Logger logger = LoggerFactory.getLogger(AuditTimeAspect.class);

    @Around("@annotation(com.exercise.heros.aspect.annotation.AuditTime)")
    public Object measureTime(ProceedingJoinPoint point) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object objectRuntime = point.proceed();
        stopWatch.stop();
        logger.info("Demora de request " + point.getSignature().getName() + "() es "
                + stopWatch.getTotalTimeMillis() + " ms");
        return objectRuntime;
    }
}
