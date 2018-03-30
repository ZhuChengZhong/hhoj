package com.hhoj.judge.core;

import java.util.ArrayList;
import java.util.List;

import com.hhoj.judge.entity.Problem;
import com.hhoj.judge.entity.Submit;
import com.hhoj.judge.entity.TestPoint;
import com.hhoj.judge.handler.Handler;
import com.hhoj.judge.handler.JavaHandler;

public class JavaSubmitTest {
	public static void main(String[] args) {
		JavaHandler handler=new JavaHandler("/home/zhu/datas", "/home/zhu/program");
		Submit submit=new Submit();
		String code="public class Test { public static void main(String []args) { int a=1; System.out.println(a); } }";
		submit.setCode(code);
		submit.setSid(111);
		Problem problem=new Problem();
		problem.setTimeLimit(1000);
		problem.setMemaryLimit(10000000);
		submit.setProblem(problem);
		TestPoint point=new TestPoint();
		point.setPointId(111);
		point.setInput("");
		point.setOutput("1");
		List<TestPoint>list=new ArrayList<TestPoint>();
		list.add(point);
		handler.handlerSubmit(submit, list);
		
	}
}
