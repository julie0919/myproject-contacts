package com.julie.test.handler;

import java.util.List;
import com.julie.test.domain.Task;
import com.julie.test.util.Prompt;

public class TaskDetailHandler  extends AbstractTaskHandler {

  public TaskDetailHandler(List<Task> taskList) {
    super(taskList);
  }

  @Override
  public void service() {
    System.out.println("[작업 상세보기]");
    int no = Prompt.printInt("번호> ");

    Task task = findByNo(no);
    if (task == null) {
      System.out.println("해당 번호의 작업이 없습니다.");
      return;
    }
    System.out.printf("작업명: %s\n", task.getName());
    System.out.printf("마감일: %s\n", task.getEndDate());
    System.out.printf("진행상태: %s\n", getStatusLabel(task.getProgress()));
    System.out.printf("담당자: %s\n", task.getLeader());
  }
}
