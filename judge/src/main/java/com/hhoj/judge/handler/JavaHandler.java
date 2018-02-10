package com.hhoj.judge.handler;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hhoj.judge.entity.Submit;
import com.hhoj.judge.util.FileUtil;

/**
 * 针对Java语言的处理类
 * @author Administrator
 *
 */
public class JavaHandler extends AbstractHandler{

	@Override
	public boolean createCodeFile(Submit submit, String codeDir,Map<String,Object>map) {
	    String className=findClassName(submit.getCode());
	    if(className==null)
		   return false;
	    String newFilePath=codeDir+FileUtil.separator+className+".java";
		FileUtil.createFile(newFilePath, submit.getCode());
		map.put("className", className);
		return true;
	}
	
	/**
	 * 找出Java代码中的类名
	 * @param code
	 * @return
	 */
	public String findClassName(String code){
		String className="";
		Pattern pattern=Pattern.compile("(public)?\\s+\\S*\\s+class\\s+(\\S+)");
		Matcher matcher=pattern.matcher(code);
		if(matcher.find()){
			className=matcher.group(2);
		}
		return className;
	}

	public boolean compile(Submit submit, String codePath, String codeName, Map<String, Object> map) {
		// TODO Auto-generated method stub
		return false;
	}

	public void run(Submit submit, String programPath, String programName, List<String> inList, List<String> outList) {
		// TODO Auto-generated method stub
		
	}

}
