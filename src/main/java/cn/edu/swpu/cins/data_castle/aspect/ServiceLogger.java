package cn.edu.swpu.cins.data_castle.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class ServiceLogger {

	private Log logger = LogFactory.getLog(ServiceLogger.class);

	@Pointcut("execution(public * cn.edu.swpu.cins.data_castle.service.*.*(..))")
	public void service(){}

	@Before("service()")
	public void before(JoinPoint joinPoint) {
		ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request=attributes.getRequest();
		Signature signature = joinPoint.getSignature();
		String method = signature.getDeclaringTypeName() + '.' + signature.getName();
		logger.info("calling : " + method);
		Object[] args = joinPoint.getArgs();
		for (Object arg :
			args) {
			logger.info("arg : " + arg);
		}
	}


	@AfterReturning(pointcut = "service()", returning = "ret")
	public void afterReturn(Object ret) {
		logger.info("service return : " + ret);
	}

}
