package com.julie.test.handler;

import java.sql.Date;
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

  public void detail() {
    System.out.println("[작업 상세보기]");
    int id = Prompt.printInt("번호> ");

    Task task = findById(id);
    if (task == null) {
      System.out.println("해당 번호의 작업이 없습니다.");
      return;
    }
    System.out.printf("작업명: %s\n", task.name);
    System.out.printf("마감일: %s\n", task.endDate);
    System.out.printf("진행상태: %s\n", getStatusLabel(task.progress));
    System.out.printf("담당자: %s\n", task.leader);
  }

  public void update() {
    System.out.println("[작업 수정하기]");
    int id = Prompt.printInt("번호> ");

    Task task = findById(id);
    if (task == null) {
      System.out.println("해당 번호의 작업이 없습니다.");
      return;
    }
    String name = Prompt.printString(String.format("작업명 (%s)> \n", task.name));
    Date endDate = Prompt.printDate(String.format("마감일 (%s)> \n", task.endDate));
    String leader = inputMember(String.format("조장 (%s) (취소: 빈 문자열)> ", task.leader));
    if (leader == null) {
      System.out.println("작업 수정을 취소합니다.");
      return;
    }

    String input = Prompt.printString("위의 내용으로 수정하시겠습니까? (Y/N)");

    if (input.equalsIgnoreCase("Y")) {
      findById(id).name = name;
      findById(id).endDate = endDate;
      findById(id).leader = leader;

      System.out.println("작업 정보를 수정하였습니다.");
    } else {
      System.out.println("작업 수정을 취소하였습니다.");
    }
  }

  public void delete() {
    System.out.println("[작업 삭제하기]");

    int id = Prompt.printInt("번호> ");
    if(indexOf(id) == -1) {
      System.out.println("해당 번호의 작업이 없습니다.");    
      return;
    }

    String input = Prompt.printString("작업을 삭제하시겠습니까? (Y/N)");

    if (input.equalsIgnoreCase("Y")) {
      for (int x = indexOf(id) + 1; x < this.count; x++) {
        this.tasks[x-1] = this.tasks[x];
      }
      tasks[--this.count] = null;
      System.out.println("작업을 삭제하였습니다.");
    } else {
      System.out.println("작업 삭제를 취소하였습니다.");
    }    
  }

  int indexOf(int taskId) {
    for (int i = 0; i < this.count; i++) {
      Task task = this.tasks[i];
      if (task != null && taskId == task.id) {
        return i;
      }
    }
    return -1;
  }

  Task findById(int taskId) {
    if (indexOf(taskId) == -1) {
      return null;
    }
    return this.tasks[indexOf(taskId)];
  }

  String inputMember(String promptTitle) {
    while (true) {
      String name = Prompt.printString(promptTitle);
      if (name.length() == 0) {
        return null;
      } else if (this.memberStorage.exist(name)) {
        return name;
      }
      System.out.println("등록되지 않은 회원입니다.");
    }
  }

  String getStatusLabel(int progress) {
    String status = progress == 1 ? "신규" : progress == 2 ? "진행중" : "완료";
    return status;
  }
}
