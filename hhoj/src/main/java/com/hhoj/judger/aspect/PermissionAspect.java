package com.hhoj.judger.aspect;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.hhoj.judger.annotation.ValidatePermission;
import com.hhoj.judger.entity.User;
import com.hhoj.judger.util.HttpObjectHolder;

//@Aspect
public class PermissionAspect {

	//@Pointcut(value="@annotation(com.hhoj.judger.annotation.ValidatePermission)")
	public void validate() {}
	
	//@Around(value="validate()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable{
		Method method=getSourceMethod(pjp);
		if(method!=null) {
			ValidatePermission vp=method.getAnnotation(ValidatePermission.class);
			int role=vp.role();
			HttpServletRequest request=HttpObjectHolder.getCurrentRequest();
			HttpServletResponse response=HttpObjectHolder.getCurrentResponse();
			User user=(User)request.getSession().getAttribute("currentUser");
			if(user==null||user.getRole()<role) {
				response.sendRedirect("/");
			}
		}
		return pjp.proceed();
	}
	
	/**
	 * 获取被拦截的方法
	 * @param jp
	 * @return
	 */
	 private Method getSourceMethod(JoinPoint jp){
	        Method proxyMethod = ((MethodSignature) jp.getSignature()).getMethod();
	        try {
	            return jp.getTarget().getClass().getMethod(proxyMethod.getName(), proxyMethod.getParameterTypes());
	        } catch (NoSuchMethodException e) {
	            e.printStackTrace();
	        } catch (SecurityException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
}
