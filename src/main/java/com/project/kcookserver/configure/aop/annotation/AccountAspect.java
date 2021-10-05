package com.project.kcookserver.configure.aop.annotation;

import com.project.kcookserver.configure.security.authentication.CustomUserDetails;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AccountAspect {

    Logger logger = LoggerFactory.getLogger(AccountAspect.class);

    @Around("@annotation(com.project.kcookserver.configure.aop.annotation.AccountLog)")
    public Object AccountLogAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();

        String userName = "anonymousUser";
        String userRole = "null";

        if (!SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal().equals("anonymousUser")) {
            CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userName = user.getUsername();
            userRole = user.getAccount().getRole().toString();
        }

        logger.info(userName);
        logger.info(userRole);
        logger.info(joinPoint.getSignature().getName());

        return proceed;
    }

}
