package com.julie.pms.listener;

import java.util.Map;
import com.julie.context.ApplicationContextListener;

public class AppListener implements ApplicationContextListener {

  @Override
  public void contextInitialized(Map<String,Object> context) {
    System.out.println("연락처 관리 프로그램에 오신 걸 환영합니다!");
  }

  @Override
  public void contextDestroyed(Map<String,Object> context) {
    System.out.println("연락처 관리 프로그램을 종료합니다!");
  }


}
