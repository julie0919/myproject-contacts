package com.julie.test.handler;

import com.julie.test.domain.Task;
import com.julie.test.util.Prompt;

public class TaskHandler {

  static final int SIZE = 4;
  Task[] tasks = new Task[SIZE];
  int count = 0;

  public MemberHandler memberStorage;
  public TaskHandler(MemberHandler memberHandler) {
    this.memberStorage = memberHandler;
  }

  public void add () {
    System.out.println("[작업 등록]");

    Task t = new Task();
    t.id = Prompt.printInt("번호> ");
    t.name = Prompt.printString("작업명> ");
    t.endDate = Prompt.printDate("마감일> ");
    t.progress= Prompt.printInt("진행 상태:\\n1.신규\\n2.진행중\\n3.완료\\n> ");

    while (true) {
      String name = Prompt.printString("담당자 (취소: 빈 문자열) > ");
      if (name.length() == 0) {
        System.out.println("작업 등록을 취소합니다.");
        return;
      } else if (this.memberStorage.exist(name)) {
        t.leader = name;
        break;
      }
      System.out.println("등록되지 않은 회원입니다.");
    }

    this.tasks[this.count++] = t;
    System.out.println();
  }

  public void list() {
    System.out.println("-------------------------------");
    System.out.println("[작업 목록]");
    for (int i = 0; i < this.count; i++) {
      Task t = this.tasks[i];
      String status = t.progress == 1 ? "신규" : t.progress == 2 ? "진행중" : "완료";
      System.out.printf("번호: %d, 작업명: %s, 마감일: %s, 진행상태: %s, 담당자: %s\n", 
          t.id, t.name, t.endDate, status, t.leader);      
    }
    System.out.println();
  }
}
