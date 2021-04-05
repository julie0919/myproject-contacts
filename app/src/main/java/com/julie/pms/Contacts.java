package com.julie.pms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import com.julie.util.CsvObject;
import com.julie.util.Prompt;

public class Contacts {

  // 옵저버 객체 (ApplicationContextListener 구현체) 목록을 저장할 컬렉션 준비
  List<ApplicationContextListener> listeners = new ArrayList<>();

  // 사용자가 입력한 명령을 저장할 컬렉션 객체 준비
  ArrayDeque<String> commandStack = new ArrayDeque<>();
  LinkedList<String> commandQueue = new LinkedList<>();

  // VO 를 저장할 컬렉션 객체
  ArrayList<Family> familyList = new ArrayList<>();
  ArrayList<School> schoolList = new ArrayList<>();
  ArrayList<Company> companyList = new ArrayList<>();

  // 데이터 파일 정보
  File familyFile = new File("family.json");
  File schoolFile = new File("school.json");
  File companyFile = new File("company.json");

  public static void main(String[] args) {

    Contacts contacts = new Contacts();
    contacts.addApplicationContextListener(new AppListener());
    contacts.service();
  }

  public void addApplicationContextListener(ApplicationContextListener listener) {
    listeners.add(listener);
  }

  public void removeApplicationContextListener(ApplicationContextListener listener) {
    listeners.remove(listener);
  }

  public void service() {

    // 애플리케이션 실행 전후에 리스너에게 보고하는 기능
    notifyOnServiceStarted();

    // 파일에서 데이터를 읽어온다. (데이터 로딩)
    loadObjects(familyFile, familyList, Family.class);
    loadObjects(schoolFile, schoolList, School.class);
    loadObjects(companyFile, companyList, Company.class);

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

    // 게시글 데이터를 파일로 출력한다.
    saveObjects(familyFile, familyList);
    saveObjects(schoolFile, schoolList);
    saveObjects(companyFile, companyList);

    Prompt.close();

    // 애플리케이션 실행 전후에 리스너에게 보고하는 기능
    notifyOnServiceStopped();
  }

  private void notifyOnServiceStarted() {
    // 애플리케이션의 서비스가 시작되면 이 이벤트를 통지 받을 리스너에게 메서드를 호출하여 알린다.
    for (ApplicationContextListener listener : listeners) {
      listener.contextInitialized();
    }
  }

  private void notifyOnServiceStopped() {
    // 애플리케이션의 서비스가 종료되면 이 이벤트를 통지 받을 리스너에게 메서드를 호출하여 알린다.
    for (ApplicationContextListener listener : listeners) {
      listener.contextDestroyed();
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

  private <T> void loadObjects(File file, List<T> list, Class<T> elementType) {
    try (BufferedReader in = new BufferedReader(new FileReader(file))) {

      // 1) 파일의 모든 데이터를 읽어서 StringBuilder 객체에 보관한다.
      StringBuilder strBuilder = new StringBuilder();
      String str = null;

      while ((str = in.readLine()) != null) {
        strBuilder.append(str);
      }

      // 2) StringBuilder 객체에 보관된 값을 꺼내 자바 객체로 만든다.
      Gson gson = new Gson();

      // JSON 문자열 ==> 컬렉션 객체
      Type collectionType = TypeToken.getParameterized(Collection.class, elementType).getType();

      // JSON 문자열을 컬렉션 객체로 변환
      Collection<T> collection = gson.fromJson(strBuilder.toString(), collectionType);

      // JSON 문자열을 읽어 만든 객체 목록을 해당 컬렉션으로 옮긴다.
      list.addAll(collection);

      System.out.printf("%s 파일 데이터 로딩!\n", file.getName());

    } catch (Exception e) {
      System.out.printf("%s 파일 데이터 로딩 중 오류 발생!\n", file.getName());
    }
  }


  private <T extends CsvObject> void saveObjects(File file, List<T> list) {
    try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {

      out.write(new Gson().toJson(list));
      System.out.printf("파일 %s 데이터 저장!\n", file.getName());

    } catch (Exception e) {
      System.out.printf("파일 %s에 데이터를 저장하는 중에 오류 발생!\n", file.getName());
    }
  }
}