package com.hhoj.judger.handler;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.hhoj.judger.entity.JudgeResult;
import com.hhoj.judger.entity.Submit;
import com.hhoj.judger.entity.TestPoint;

/**
 * 核心处理器
 * @author Administrator
 *
 */
public interface Handler {
	
	
	public Map<String, Object> prepare(Submit submit) throws IOException;
	
	
	public boolean compare(JudgeResult js,Map<String,Object>paths,Submit submit);
	

	public boolean run(JudgeResult jr,Submit submit, Map<String,Object>paths) throws Exception;
	

	public boolean compile(JudgeResult jr,Submit submit, Map<String,Object>paths) throws Exception ;

	
	public boolean clean(Submit submit);
	

	public JudgeResult handlerSubmit(Submit submit) throws Exception;
}
