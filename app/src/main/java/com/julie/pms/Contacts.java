package com.julie.pms;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import com.julie.context.ApplicationContextListener;
import com.julie.pms.domain.Company;
import com.julie.pms.domain.Family;
import com.julie.pms.domain.School;
import com.julie.pms.handler.Command;
import com.julie.pms.handler.CompanyAddHandler;
import com.julie.pms.handler.CompanyDeleteHandler;
import com.julie.pms.handler.CompanyDetailHandler;
import com.julie.pms.handler.CompanyListHandler;
import com.julie.pms.handler.CompanyUpdateHandler;
import com.julie.pms.handler.FamilyAddHandler;
import com.julie.pms.handler.FamilyDeleteHandler;
import com.julie.pms.handler.FamilyDetailHandler;
import com.julie.pms.handler.FamilyListHandler;
import com.julie.pms.handler.FamilyUpdateHandler;
import com.julie.pms.handler.SchoolAddHandler;
import com.julie.pms.handler.SchoolDeleteHandler;
import com.julie.pms.handler.SchoolDetailHandler;
import com.julie.pms.handler.SchoolListHandler;
import com.julie.pms.handler.SchoolUpdateHandler;
import com.julie.pms.listener.AppListener;
import com.julie.pms.listener.FileListener;
import com.julie.util.Prompt;

public class Contacts {

  // 옵저버 객체 (ApplicationContextListener 구현체) 목록을 저장할 컬렉션 준비
  List<ApplicationContextListener> listeners = new ArrayList<>();

  // 사용자가 입력한 명령을 저장할 컬렉션 객체 준비
  ArrayDeque<String> commandStack = new ArrayDeque<>();
  LinkedList<String> commandQueue = new LinkedList<>();

  // 옵저버와 값을 공유하기 위해 사용할 공통 저장소 객체를 준비
  Map<String,Object> contactContext = new HashMap<>();

  public static void main(String[] args) {

    Contacts contacts = new Contacts();
    contacts.addApplicationContextListener(new AppListener());
    contacts.addApplicationContextListener(new FileListener());
    contacts.service();
  }

  public void addApplicationContextListener(ApplicationContextListener listener) {
    listeners.add(listener);
  }

  public void removeApplicationContextListener(ApplicationContextListener listener) {
    listeners.remove(listener);
  }

  @SuppressWarnings("unchecked")
  public void service() {

    // 애플리케이션 실행 전후에 리스너에게 보고하는 기능
    notifyOnServiceStarted();

    // FileListener 가 준비한 List 객체를 꺼낸다.
    List<Family> familyList = (List<Family>) contactContext.get("familyList");
    List<School> schoolList = (List<School>) contactContext.get("schoolList");
    List<Company> companyList = (List<Company>) contactContext.get("companyList");

    // 사용자 명령을 처리하는 객체를 맵에 보관한다.
    HashMap<String,Command> commandMap = new HashMap<>();

    commandMap.put("연락처 추가(가족)", new FamilyAddHandler(familyList));
    commandMap.put("연락처 목록(가족)", new FamilyListHandler(familyList));
    commandMap.put("연락처 상세보기(가족)", new FamilyDetailHandler(familyList));
    commandMap.put("연락처 수정(가족)", new FamilyUpdateHandler(familyList));
    commandMap.put("연락처 삭제(가족)", new FamilyDeleteHandler(familyList));

    commandMap.put("연락처 추가(친구)", new SchoolAddHandler(schoolList));
    commandMap.put("연락처 목록(친구)", new SchoolListHandler(schoolList));
    commandMap.put("연락처 상세보기(친구)", new SchoolDetailHandler(schoolList));
    commandMap.put("연락처 수정(친구)", new SchoolUpdateHandler(schoolList));
    commandMap.put("연락처 삭제(친구)", new SchoolDeleteHandler(schoolList));

    commandMap.put("연락처 추가(회사)", new CompanyAddHandler(companyList));
    commandMap.put("연락처 목록(회사)", new CompanyListHandler(companyList));
    commandMap.put("연락처 상세보기(회사)", new CompanyDetailHandler(companyList));
    commandMap.put("연락처 수정(회사)", new CompanyUpdateHandler(companyList));
    commandMap.put("연락처 삭제(회사)", new CompanyDeleteHandler(companyList));

    System.out.println("[연락처 관리 프로그램]");

    loop:
      while (true) {
        String command = Prompt.printString("--------------------원하시는 메뉴를 선택해주세요--------------------\n"
            + ">>연락처 추가(가족/친구/회사)<<\n>>연락처 목록(가족/친구/회사)<<\n>>연락처 상세보기(가족/친구/회사)<<\n"
            + ">>연락처 수정(가족/친구/회사)<<\n>>연락처 삭제(가족/친구/회사)<<\n>>검색기록조회(처음부터/마지막부터)<<\n"
            + ">>나가기<<\n>> ");

        if (command.length() == 0)
          continue;
        // 사용자가 입력한 명령을 보관해둔다.
        commandStack.push(command);
        commandQueue.offer(command);

        try { 
          Command commandHandler = commandMap.get(command);

          if (command.equals("검색기록조회(처음부터)")) {
            printCommandHistory(commandQueue.iterator());
          } else if(command.equals("검색기록조회(마지막부터)")) {
            printCommandHistory(commandStack.iterator());

          }else if (command.equals("나가기")) {
            System.out.println("안녕!");
            break loop;
          } else if (commandHandler == null) {
            System.out.println("실행할 수 없는 명령입니다.");
          }  else {
            commandHandler.service();
          }

        } catch (Exception e) {
          System.out.println("------------------------------------------");
          System.out.printf("명령어 실행 중 오류 발생: %s - %s\n", 
              e.getClass().getName(), e.getMessage());
          System.out.println("------------------------------------------");
        }
        System.out.println(); // 이전 명령의 실행을 구분하기 위해 빈 줄 출력
      }

    Prompt.close();

    // 애플리케이션 실행 전후에 리스너에게 보고하는 기능
    notifyOnServiceStopped();
  }

  private void notifyOnServiceStarted() {
    // 애플리케이션의 서비스가 시작되면 이 이벤트를 통지 받을 리스너에게 메서드를 호출하여 알린다.
    for (ApplicationContextListener listener : listeners) {
      listener.contextInitialized(contactContext);
    }
  }

  private void notifyOnServiceStopped() {
    // 애플리케이션의 서비스가 종료되면 이 이벤트를 통지 받을 리스너에게 메서드를 호출하여 알린다.
    for (ApplicationContextListener listener : listeners) {
      listener.contextDestroyed(contactContext);
    }
  }

  private void printCommandHistory(Iterator<String> iterator) {
    int count = 0;
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
      if ((++count % 5) == 0) {
        String input = Prompt.printString(": ");
        if (input.equalsIgnoreCase("q")) {
          break;
        }
      }
    }
  }
}