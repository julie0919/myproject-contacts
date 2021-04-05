package com.julie.pms.listener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.julie.context.ApplicationContextListener;
import com.julie.pms.domain.Company;
import com.julie.pms.domain.Family;
import com.julie.pms.domain.School;
import com.julie.util.CsvObject;

public class FileListener implements ApplicationContextListener {

  // 데이터 파일 정보
  File familyFile = new File("family.json");
  File schoolFile = new File("school.json");
  File companyFile = new File("company.json");

  // VO 를 저장할 컬렉션 객체
  List<Family> familyList;
  List<School> schoolList;
  List<Company> companyList;

  @Override
  public void contextInitialized(Map<String,Object> context) {

    // 파일에서 데이터를 읽어온다. (데이터 로딩)
    familyList = loadObjects(familyFile, Family.class);
    schoolList = loadObjects(schoolFile, School.class);
    companyList = loadObjects(companyFile, Company.class);

    // Contacts 클래스에서 사용할 수 있도록 컬렉션 객체를 맵 객체에 담는다.
    context.put("familyList", familyList);
    context.put("schoolList", schoolList);
    context.put("companyList", companyList);
  }

  @Override
  public void contextDestroyed(Map<String,Object> context) {

    // 게시글 데이터를 파일로 출력한다.
    saveObjects(familyFile, familyList);
    saveObjects(schoolFile, schoolList);
    saveObjects(companyFile, companyList);
  }


  private <T> List<T> loadObjects(File file, Class<T> elementType) {
    try (BufferedReader in = new BufferedReader(new FileReader(file))) {

      // 1) 파일의 모든 데이터를 읽어서 StringBuilder 객체에 보관한다.
      StringBuilder strBuilder = new StringBuilder();
      String str = null;

      while ((str = in.readLine()) != null) {
        strBuilder.append(str);
      }

      // 2) StringBuilder 객체에 보관된 값을 꺼내 자바 객체로 만든다.
      Gson gson = new Gson();

      // JSON 문자열 ==> 리스트 객체
      Type listType = TypeToken.getParameterized(ArrayList.class, elementType).getType();

      // JSON 문자열을 리스트 객체로 변환
      List<T> list = gson.fromJson(strBuilder.toString(), listType);

      System.out.printf("%s 파일 데이터 로딩!\n", file.getName());

      return list;

    } catch (Exception e) {
      System.out.printf("%s 파일 데이터 로딩 중 오류 발생!\n", file.getName());
      return new ArrayList<T>();
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
