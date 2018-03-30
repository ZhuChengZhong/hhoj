package com.hhoj.judge.core;

import java.util.ArrayList;
import java.util.List;

import com.hhoj.judge.entity.Problem;
import com.hhoj.judge.entity.Submit;
import com.hhoj.judge.entity.TestPoint;
import com.hhoj.judge.handler.CPlusHandler;
import com.hhoj.judge.handler.Handler;
import com.hhoj.judge.handler.JavaHandler;

public class CSubmitTest {
	public static void main(String[] args) {
		CPlusHandler handler=new CPlusHandler("/home/zhu/datas", "/home/zhu/program");
		Submit submit=new Submit();
		String code="#include<stdio.h> \n int main() {int a ; scanf(\"%d\",&a);printf(\"%d\\n\",a); return 0; }";
		submit.setCode(code);
		submit.setSid(111);
		Problem problem=new Problem();
		problem.setTimeLimit(1000);
		problem.setMemaryLimit(10000000);
		submit.setProblem(problem);
		TestPoint point=new TestPoint();
		point.setPointId(111);
		point.setInput("45");
		point.setOutput("45");
		List<TestPoint>list=new ArrayList<TestPoint>();
		list.add(point);
		handler.handlerSubmit(submit, list);
		
	}
}
