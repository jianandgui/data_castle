package cn.edu.swpu.cins.data_castle.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerAspecter {

	private final Log logger = LogFactory.getLog(ControllerAspecter.class);

	@Pointcut(value = "execution(public * cn.edu.swpu.cins.data_castle.controller.*.*(..))")
	public void controllerLog(){}

	@Before("controllerLog()")
	public void before(JoinPoint point) {
		logger.info("controller aspect beginning");
		Object[] args = point.getArgs();
		for (Object arg : args) {
			logger.info("arg : " + arg);
		}
		String method = point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName();
		logger.info("aspect finishing");
		logger.info("calling " + method);
	}

	@AfterReturning(pointcut = "controllerLog()", returning = "ret")
	public void afterReturning(Object ret) {
		logger.info("controller return " + ret);
	}

	@AfterThrowing(pointcut = "controllerLog()", throwing = "throwable")
	public void afterThrowing(Throwable throwable) {
		logger.info("controller throw " + throwable.getMessage());
	}

}
