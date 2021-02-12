package com.julie.test.handler;

import java.sql.Date;
import com.julie.test.domain.Task;
import com.julie.test.util.Prompt;

public class TaskHandler {

  MemberList memberList;
  TaskList taskList = new TaskList();

  public TaskHandler(MemberList memberList) {
    this.memberList = memberList;
  }

  public void add () {
    System.out.println("[작업 등록]");

    Task t = new Task();
    t.id = Prompt.printInt("번호> ");
    t.name = Prompt.printString("작업명> ");
    t.endDate = Prompt.printDate("마감일> ");
    t.progress= Prompt.printInt("진행 상태:\n1.신규\n2.진행중\n3.완료\n> ");
    t.leader = inputMember("담당자 (취소: 빈 문자열) > ");
    if (t.leader == null) {
      System.out.println("작업 등록을 취소합니다.");
      return;
    } 

    taskList.add(t);
    System.out.println("작업 등록을 완료하였습니다.");
  }

  public void list() {
    System.out.println("-------------------------------");
    System.out.println("[작업 목록]");

    Task[] tasks = taskList.toArray();
    for (Task t : tasks){
      String status = t.progress == 1 ? "신규" : t.progress == 2 ? "진행중" : "완료";
      System.out.printf("번호: %d, 작업명: %s, 마감일: %s, 진행상태: %s, 담당자: %s\n", 
          t.id, t.name, t.endDate, status, t.leader);      
    }
  }

  public void detail() {
    System.out.println("[작업 상세보기]");
    int id = Prompt.printInt("번호> ");

    Task task = taskList.get(id);
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

    Task task = taskList.get(id);
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
      task.name = name;
      task.endDate = endDate;
      task.leader = leader;

      System.out.println("작업 정보를 수정하였습니다.");
    } else {
      System.out.println("작업 수정을 취소하였습니다.");
    }
  }

  public void delete() {
    System.out.println("[작업 삭제하기]");

    int id = Prompt.printInt("번호> ");
    Task task = taskList.get(id);
    if(task == null) {
      System.out.println("해당 번호의 작업이 없습니다.");    
      return;
    }

    String input = Prompt.printString("작업을 삭제하시겠습니까? (Y/N)");

    if (input.equalsIgnoreCase("Y")) {
      taskList.delete(id);
      System.out.println("작업을 삭제하였습니다.");
    } else {
      System.out.println("작업 삭제를 취소하였습니다.");
    }    
  }

  String inputMember(String promptTitle) {
    while (true) {
      String name = Prompt.printString(promptTitle);
      if (name.length() == 0) {
        return null;
      } else if (this.memberList.exist(name)) {
        return name;
      } else {
        System.out.println("등록되지 않은 회원입니다.");
      }
    }
  }

  String getStatusLabel(int progress) {
    String status = progress == 1 ? "신규" : progress == 2 ? "진행중" : "완료";
    return status;
  }
}
