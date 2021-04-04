package com.julie.test.handler;

import java.sql.Date;
import java.util.List;
import com.julie.test.domain.Task;
import com.julie.test.util.Prompt;

public class TaskUpdateHandler extends AbstractTaskHandler {

  private MemberValidatorHandler memberValidatorHandler;

  public TaskUpdateHandler(List<Task> taskList, MemberValidatorHandler memberValidatorHandler) {
    super(taskList);
    this.memberValidatorHandler = memberValidatorHandler;
  }

  @Override
  public void service() {
    System.out.println("[작업 수정하기]");
    int no = Prompt.printInt("번호> ");

    Task task = findByNo(no);
    if (task == null) {
      System.out.println("해당 번호의 작업이 없습니다.");
      return;
    }
    String name = Prompt.printString(String.format("작업명 (%s)> \n", task.getName()));
    Date endDate = Prompt.printDate(String.format("마감일 (%s)> \n", task.getEndDate()));
    String leader = memberValidatorHandler.inputMember(String.format("조장 (%s) (취소: 빈 문자열)> ", task.getLeader()));
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
}
