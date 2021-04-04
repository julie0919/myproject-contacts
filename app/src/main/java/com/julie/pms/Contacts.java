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
import com.julie.domain.Company;
import com.julie.domain.Family;
import com.julie.domain.School;
import com.julie.handler.Command;
import com.julie.handler.CompanyAddHandler;
import com.julie.handler.CompanyDeleteHandler;
import com.julie.handler.CompanyDetailHandler;
import com.julie.handler.CompanyListHandler;
import com.julie.handler.CompanyUpdateHandler;
import com.julie.handler.FamilyAddHandler;
import com.julie.handler.FamilyDeleteHandler;
import com.julie.handler.FamilyDetailHandler;
import com.julie.handler.FamilyListHandler;
import com.julie.handler.FamilyUpdateHandler;
import com.julie.handler.SchoolAddHandler;
import com.julie.handler.SchoolDeleteHandler;
import com.julie.handler.SchoolDetailHandler;
import com.julie.handler.SchoolListHandler;
import com.julie.handler.SchoolUpdateHandler;
import com.julie.util.CsvObject;
import com.julie.util.Prompt;

public class Contacts {

  // 사용자가 입력한 명령을 저장할 컬렉션 객체 준비
  static ArrayDeque<String> commandStack = new ArrayDeque<>();
  static LinkedList<String> commandQueue = new LinkedList<>();

  // VO 를 저장할 컬렉션 객체
  static ArrayList<Family> familyList = new ArrayList<>();
  static ArrayList<School> schoolList = new ArrayList<>();
  static ArrayList<Company> companyList = new ArrayList<>();

  // 데이터 파일 정보
  static File familyFile = new File("family.json");
  static File schoolFile = new File("school.json");
  static File companyFile = new File("company.json");

  public static void main(String[] args) {

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
  }

  static void printCommandHistory(Iterator<String> iterator) {
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

  static <T> void loadObjects(File file, List<T> list, Class<T> elementType) {
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


  static <T extends CsvObject> void saveObjects(File file, List<T> list) {
    try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {

      out.write(new Gson().toJson(list));
      System.out.printf("파일 %s 데이터 저장!\n", file.getName());

    } catch (Exception e) {
      System.out.printf("파일 %s에 데이터를 저장하는 중에 오류 발생!\n", file.getName());
    }
  }
}