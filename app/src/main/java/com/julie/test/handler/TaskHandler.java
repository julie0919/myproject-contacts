package com.julie.test.handler;

import java.sql.Date;
import java.util.Iterator;
import java.util.LinkedList;
import com.julie.test.domain.Task;
import com.julie.test.util.Prompt;

public class TaskHandler {

  private MemberHandler memberHandler;
  private LinkedList<Task> taskList = new LinkedList<>();

  public TaskHandler(MemberHandler memberHandler) {
    this.memberHandler = memberHandler;
  }

  public void add () {
    System.out.println("[작업 등록]");

    Task t = new Task();
    t.setId(Prompt.printInt("번호> "));
    t.setName(Prompt.printString("작업명> "));
    t.setEndDate(Prompt.printDate("마감일> "));
    t.setProgress(Prompt.printInt("진행 상태:\n1.신규\n2.진행중\n3.완료\n> "));
    t.setLeader(memberHandler.inputMember("담당자 (취소: 빈 문자열) > "));
    if (t.getLeader() == null) {
      System.out.println("작업 등록을 취소합니다.");
      return;
    } 

    taskList.add(t);
    System.out.println("작업 등록을 완료하였습니다.");
  }

  public void list() throws CloneNotSupportedException {
    System.out.println("-------------------------------");
    System.out.println("[작업 목록]");

    Iterator<Task> iterator = taskList.iterator();
    while (iterator.hasNext()){
      Task t = iterator.next();
      String status = t.getProgress() == 1 ? "신규" : t.getProgress() == 2 ? "진행중" : "완료";
      System.out.printf("번호: %d, 작업명: %s, 마감일: %s, 진행상태: %s, 담당자: %s\n", 
          t.getId(), t.getName(), t.getEndDate(), status, t.getLeader());      
    }
  }

  public void detail() {
    System.out.println("[작업 상세보기]");
    int id = Prompt.printInt("번호> ");

    Task task = findById(id);
    if (task == null) {
      System.out.println("해당 번호의 작업이 없습니다.");
      return;
    }
    System.out.printf("작업명: %s\n", task.getName());
    System.out.printf("마감일: %s\n", task.getEndDate());
    System.out.printf("진행상태: %s\n", getStatusLabel(task.getProgress()));
    System.out.printf("담당자: %s\n", task.getLeader());
  }

  public void update() {
    System.out.println("[작업 수정하기]");
    int id = Prompt.printInt("번호> ");

    Task task = findById(id);
    if (task == null) {
      System.out.println("해당 번호의 작업이 없습니다.");
      return;
    }
    String name = Prompt.printString(String.format("작업명 (%s)> \n", task.getName()));
    Date endDate = Prompt.printDate(String.format("마감일 (%s)> \n", task.getEndDate()));
    String leader = memberHandler.inputMember(String.format("조장 (%s) (취소: 빈 문자열)> ", task.getLeader()));
    if (leader == null) {
      System.out.println("작업 수정을 취소합니다.");
      return;
    }

    String input = Prompt.printString("위의 내용으로 수정하시겠습니까? (Y/N)");

    if (input.equalsIgnoreCase("Y")) {
      task.setName(name);
      task.setEndDate(endDate);
      task.setLeader(leader);

      System.out.println("작업 정보를 수정하였습니다.");
    } else {
      System.out.println("작업 수정을 취소하였습니다.");
    }
  }

  public void delete() {
    System.out.println("[작업 삭제하기]");

    int id = Prompt.printInt("번호> ");
    Task task = findById(id);
    if(task == null) {
      System.out.println("해당 번호의 작업이 없습니다.");    
      return;
    }

    String input = Prompt.printString("작업을 삭제하시겠습니까? (Y/N)");

    if (input.equalsIgnoreCase("Y")) {
      taskList.remove(task);
      System.out.println("작업을 삭제하였습니다.");
    } else {
      System.out.println("작업 삭제를 취소하였습니다.");
    }    
  }

  String getStatusLabel(int progress) {
    String status = progress == 1 ? "신규" : progress == 2 ? "진행중" : "완료";
    return status;
  }

  private Task findById(int taskId) {
    Object[] list = taskList.toArray();
    for (Object obj : list) {
      Task t = (Task) obj;
      if (t.getId() == taskId) {
        return t;
      }
    }
    return null;
  }
}
