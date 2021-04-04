package com.julie.pms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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
import com.julie.util.Prompt;

public class Contacts {

  static ArrayDeque<String> commandStack = new ArrayDeque<>();
  static LinkedList<String> commandQueue = new LinkedList<>();


  static List<Family> familyList;
  static List<School> schoolList;
  static List<Company> companyList;

  public static void main(String[] args) throws CloneNotSupportedException {

    // 사용자 명령을 처리하는 객체를 맵에 보관한다.
    HashMap<String,Command> commandMap = new HashMap<>();

    commandMap.put("연락처추가(가족)", new FamilyAddHandler(familyList));
    commandMap.put("연락처목록(가족)", new FamilyListHandler(familyList));
    commandMap.put("연락처상세보기(가족)", new FamilyDetailHandler(familyList));
    commandMap.put("연락처수정(가족)", new FamilyUpdateHandler(familyList));
    commandMap.put("연락처삭제(가족)", new FamilyDeleteHandler(familyList));

    commandMap.put("연락처추가(친구)", new SchoolAddHandler(schoolList));
    commandMap.put("연락처목록(친구)", new SchoolListHandler(schoolList));
    commandMap.put("연락처상세보기(친구)", new SchoolDetailHandler(schoolList));
    commandMap.put("연락처수정(친구)", new SchoolUpdateHandler(schoolList));
    commandMap.put("연락처삭제(친구)", new SchoolDeleteHandler(schoolList));

    commandMap.put("연락처추가(회사)", new CompanyAddHandler(companyList));
    commandMap.put("연락처목록(회사)", new CompanyListHandler(companyList));
    commandMap.put("연락처상세보기(회사)", new CompanyDetailHandler(companyList));
    commandMap.put("연락처수정(회사)", new CompanyUpdateHandler(companyList));
    commandMap.put("연락처삭제(회사)", new CompanyDeleteHandler(companyList));

    System.out.println("[연락처 관리 프로그램]");

    while (true) {
      String command = Prompt.printString("--------------------원하시는 메뉴를 선택해주세요--------------------\n"
          + "**연락처추가(가족/친구/회사)**\n**연락처목록(가족/친구/회사)**\n**연락처상세보기(가족/친구/회사)**\n"
          + "**연락처수정(가족/친구/회사)**\n**연락처삭제(가족/친구/회사)**\n**검색기록조회(처음부터/마지막부터)**\n**나가기**\n>> ");

      if (command.length() == 0)
        continue;
      commandStack.push(command);
      commandQueue.offer(command);
      try { 
        Command commandHandler = commandMap.get(command);
        if (commandHandler == null) {
          System.out.println("실행할 수 없는 명령입니다.");

        } else if (command.equals("검색기록조회(처음부터)")) {
          printCommandHistory(commandQueue.iterator());
        } else if(command.equals("검색기록조회(마지막부터)")) {
          printCommandHistory(commandStack.iterator());

        }else if (command.equals("나가기")) {
          System.out.println("안녕!");
          break;
        } else {
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

  @SuppressWarnings("unchecked")
  static void loadFamily() {
    try (ObjectInputStream in = new ObjectInputStream(
        new BufferedInputStream(new FileInputStream("family.data")))) {

      familyList = (List<Family>) in.readObject();

      System.out.println("연락처 정보(가족) 로딩");
    } catch (Exception e) {
      System.out.println("연락처 정보(가족) 로딩 중 오류 발생");
      familyList = new ArrayList<>();
    }
  }

  static void saveFamily() {
    try (ObjectOutputStream out = new ObjectOutputStream(
        new BufferedOutputStream(new FileOutputStream("family.data")))) {

      out.writeObject(familyList);
      System.out.println("연락처 정보(가족) 저장");
    }catch (Exception e) {
      System.out.println("연락처 정보(가족)를 파일로 저장하는 중에 오류 발생");
    }
  }

  @SuppressWarnings("unchecked")
  static void loadSchool() {
    try (ObjectInputStream in = new ObjectInputStream(
        new BufferedInputStream(new FileInputStream("school.data")))) {

      schoolList = (List<School>) in.readObject();

      System.out.println("연락처 정보(친구) 로딩");
    } catch (Exception e) {
      System.out.println("연락처 정보(친구) 로딩 중 오류 발생");
      schoolList = new ArrayList<>();
    }
  }

  static void saveSchool() {
    try (ObjectOutputStream out = new ObjectOutputStream(
        new BufferedOutputStream(new FileOutputStream("school.data")))) {

      out.writeObject(schoolList);
      System.out.println("연락처 정보(친구) 저장");
    }catch (Exception e) {
      System.out.println("연락처 정보(친구)를 파일로 저장하는 중에 오류 발생");
    }
  }

  @SuppressWarnings("unchecked")
  static void loadCompany() {
    try (ObjectInputStream in = new ObjectInputStream(
        new BufferedInputStream(new FileInputStream("company.data")))) {

      companyList = (List<Company>) in.readObject();

      System.out.println("연락처 정보(회사) 로딩");
    } catch (Exception e) {
      System.out.println("연락처 정보(회사) 로딩 중 오류 발생");
      companyList = new ArrayList<>();
    }
  }

  static void saveCompany() {
    try (ObjectOutputStream out = new ObjectOutputStream(
        new BufferedOutputStream(new FileOutputStream("company.data")))) {

      out.writeObject(companyList);
      System.out.println("연락처 정보(회사) 저장");
    }catch (Exception e) {
      System.out.println("연락처 정보(회사)를 파일로 저장하는 중에 오류 발생");
    }
  }
}