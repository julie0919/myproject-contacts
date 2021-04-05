package com.julie.pms.listener;

import com.julie.context.ApplicationContextListener;

public class AppListener implements ApplicationContextListener {

  @Override
  public void contextInitialized() {
    System.out.println("연락처 관리 프로그램에 오신 걸 환영합니다!");
  }

  @Override
  public void contextDestroyed() {
    System.out.println("연락처 관리 프로그램을 종료합니다!");
  }


}
