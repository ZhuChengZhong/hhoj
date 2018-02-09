package com.hhoj.judge.core;

import com.hhoj.judge.entity.Submit;

public interface Runner {
	
  public boolean compile(Submit submit,String codeDirPath);
  
  public void run(Submit submit,String codeDirPath,String testDataDirPath);
}
