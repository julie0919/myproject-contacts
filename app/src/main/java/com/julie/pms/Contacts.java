package com.julie.pms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
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

  // 사용자가 입력한 명령을 저장할 컬렉션 객체 준비
  static ArrayDeque<String> commandStack = new ArrayDeque<>();
  static LinkedList<String> commandQueue = new LinkedList<>();

  // VO 를 저장할 컬렉션 객체
  static ArrayList<Family> familyList = new ArrayList<>();
  static ArrayList<School> schoolList = new ArrayList<>();
  static ArrayList<Company> companyList = new ArrayList<>();

  // 데이터 파일 정보
  //  static File familyFile = new File("family.data");
  //  static File schoolFile = new File("school.data");
  //  static File companyFile = new File("company.data");

  public static void main(String[] args) {

    // 파일에서 데이터를 읽어온다. (데이터 로딩)
    loadFamily();
    loadSchool();
    loadCompany();
    //    familyList = loadObjects(familyFile, Family.class);
    //    schoolList = loadObjects(schoolFile, School.class);
    //    companyList = loadObjects(companyFile, Company.class);

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
    saveFamily();
    saveSchool();
    saveCompany();
    //    saveObjects(familyFile, familyList);
    //    saveObjects(schoolFile, schoolList);
    //    saveObjects(companyFile, companyList);

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

  static void loadFamily() {
    try (BufferedReader in = new BufferedReader(new FileReader("family.csv"))) {
      String csvStr = null;
      while ((csvStr = in.readLine()) != null) {
        familyList.add(Family.valueOfCsv(csvStr));            
      }
      System.out.println("연락처(가족) 데이터 로딩!");

    } catch (Exception e) {
      System.out.println("연락처(가족) 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveFamily() {
    try (BufferedWriter out = new BufferedWriter(new FileWriter("family.csv"))) {

      // family.csv 파일 포맷
      // - 번호,이름,연락처,이메일,주소,기념일(CRLF)
      for (Family f : familyList) {
        out.write(f.toCsvString() + "\n");
      }
      System.out.println("연락처(가족) 데이터 저장!");

    } catch (Exception e) {
      System.out.println("연락처(가족) 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }

  static void loadSchool() {
    try (BufferedReader in = new BufferedReader(new FileReader("school.csv"))) {
      String csvStr = null;

      while ((csvStr = in.readLine()) != null) {
        schoolList.add(School.valueOfCsv(csvStr));
      }
      System.out.println("연락처(친구) 데이터 로딩!");

    } catch (Exception e) {
      System.out.println("연락처(친구) 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveSchool() {
    try (BufferedWriter out = new BufferedWriter(new FileWriter("school.csv"))) {

      // school.csv 파일 포맷
      // - 번호,이름,연락처,이메일,학교,주소(CRLF)
      for (School s : schoolList) {
        out.write(s.toCsvString() + "\n");
      }
      System.out.println("연락처(친구) 데이터 저장!");

    } catch (Exception e) {
      System.out.println("연락처(친구) 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }
  static void loadCompany() {
    try (BufferedReader in = new BufferedReader(new FileReader("company.csv"))) {
      String csvStr = null;

      while ((csvStr = in.readLine()) != null) {
        companyList.add(Company.valueOfCsv(csvStr));
      }
      System.out.println("연락처(회사) 데이터 로딩!");

    } catch (Exception e) {
      System.out.println("연락처(회사) 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveCompany() {
    try (BufferedWriter out = new BufferedWriter(new FileWriter("company.csv"))) {

      // company.csv 파일 포맷
      // - 번호,이름,연락처,이메일,회사,주소(CRLF)
      for (Company c : companyList) {
        out.write(c.toCsvString() + "\n");
      }
      System.out.println("연락처(회사) 데이터 저장!"); 

    } catch (Exception e) {
      System.out.println("연락처(회사) 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }
  //  @SuppressWarnings("unchecked")
  //  static <T extends Serializable> List<T> loadObjects(File file, Class<T> dataType) {
  //    try (ObjectInputStream in = new ObjectInputStream(
  //        new BufferedInputStream(new FileInputStream(file)))) {
  //
  //      System.out.printf("파일 %s 로딩!\n", file.getName());
  //      return (List<T>) in.readObject();
  //
  //    } catch (Exception e) {
  //      System.out.printf("파일 %s 로딩 중 오류 발생!\n", file.getName());
  //      return new ArrayList<T>();
  //    }
  //  }
  //
  //  static <T extends Serializable> void saveObjects(File file, List<T> dataList) {
  //    try (ObjectOutputStream out = new ObjectOutputStream(
  //        new BufferedOutputStream(new FileOutputStream(file)))) {
  //
  //      out.writeObject(dataList);
  //      System.out.printf("파일 %s 저장!\n", file.getName());
  //
  //    }catch (Exception e) {
  //      System.out.printf("파일 %s 저장하는 중에 오류 발생\n", file.getName());
  //    }
  //  }
}