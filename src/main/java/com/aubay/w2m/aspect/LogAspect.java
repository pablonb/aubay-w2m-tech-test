package com.aubay.w2m.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.aubay.w2m.exception.IdentificadorNoValidoException;
import com.aubay.w2m.util.W2MUtil;

@Aspect
@Component
public class LogAspect {

	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

	/**
	 * Generacion de log cuando el identificador pasado como parametro sea < 0
	 * 
	 * @param joinPoint Informacion sobre el metodo al que se aplica el aspect
	 * @throws IdentificadorNoValidoException Error convirtiendo el identificador a Long
	 */
	@Before("execution(* com.aubay.w2m.controller.NaveController.getNave(..))")
	public void getNaveBeforeLog(JoinPoint joinPoint) throws IdentificadorNoValidoException {
		final Object[] args = joinPoint.getArgs();

		if (args != null && args.length > 0) {
			final Long id = W2MUtil.validarID((String) args[0]);

			if (id < 0) {
				logger.error("{} -> {} -> Identificador no valido: {}", joinPoint.getSignature().getDeclaringTypeName(),
						joinPoint.getSignature().getName(), id);
			}
		}
	}

}
