package com.julie.test.handler;

import java.util.List;
import com.julie.test.domain.Task;

public abstract class AbstractTaskHandler implements Command {

  protected List<Task> taskList;

  public AbstractTaskHandler(List<Task> taskList) {
    this.taskList = taskList;
  }

  protected String getStatusLabel(int progress) {
    String status = progress == 1 ? "신규" : progress == 2 ? "진행중" : "완료";
    return status;
  }

  protected Task findByNo(int taskNo) {
    Task[] list = taskList.toArray(new Task[taskList.size()]);
    for (Task t : list) {
      if (t.getNo() == taskNo) {
        return t;
      }
    }
    return null;
  }
}
