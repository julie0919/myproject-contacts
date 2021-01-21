package com.julie.test.handler;

import java.sql.Date;
import com.julie.test.util.Prompt;

public class TaskHandler {
  static class Task {
    int id;
    String name;
    Date endDate;
    int progress;
    String leader;
  }

  static final int SIZE = 4;
  static Task[] tasks = new Task[SIZE];
  static int count = 0;

  public static void add () {
    System.out.println("[작업 등록]");

    Task t = new Task();
    t.id = Prompt.printInt("번호> ");
    t.name = Prompt.printString("작업명> ");
    t.endDate = Prompt.printDate("마감일> ");
    t.progress= Prompt.printInt("진행 상태:\\n1.신규\\n2.진행중\\n3.완료\\n> ");
    t.leader = Prompt.printString("담당자> ");

    tasks[count++] = t;
    System.out.println();
  }

  public static void list() {
    System.out.println("-------------------------------");
    System.out.println("[작업 목록]");
    for (int i = 0; i < count; i++) {
      Task t = tasks[i];
      String status = t.progress == 1 ? "신규" : t.progress == 2 ? "진행중" : "완료";
      System.out.printf("번호: %d, 작업명: %s, 마감일: %s, 진행상태: %s, 담당자: %s\n", 
          t.id, t.name, t.endDate, status, t.leader);      
    }
    System.out.println();
  }
}
