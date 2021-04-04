package com.julie.test.handler;

import java.util.Iterator;
import java.util.List;
import com.julie.test.domain.Task;

public class TaskListHandler extends AbstractTaskHandler {

  public TaskListHandler(List<Task> taskList) {
    super(taskList);
  }

  @Override
  public void service() {
    System.out.println("-------------------------------");
    System.out.println("[작업 목록]");

    Iterator<Task> iterator = taskList.iterator();
    while (iterator.hasNext()){
      Task t = iterator.next();
      String status = t.getProgress() == 1 ? "신규" : t.getProgress() == 2 ? "진행중" : "완료";
      System.out.printf("번호: %d, 작업명: %s, 마감일: %s, 진행상태: %s, 담당자: %s\n", 
          t.getNo(), t.getName(), t.getEndDate(), status, t.getLeader());      
    }
  }
}
