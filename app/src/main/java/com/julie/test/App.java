package com.julie.test;

import com.julie.test.handler.MemberHandler;
import com.julie.test.handler.ProjectHandler;
import com.julie.test.handler.TaskHandler;
import com.julie.test.util.Prompt;

public class App {
  public static void main(String[] args) {
    while (true) {
      String command = Prompt.printString("명령> ");

      if (command.equals("/member/add")) {
        MemberHandler.add();
      } else if (command.equals("/member/list")) {
        MemberHandler.list();
      } else if (command.equals("/project/add")) {
        ProjectHandler.add();
      }else if (command.equals("/project/list")) {
        ProjectHandler.list();
      }else if (command.equals("/task/add")) {
        TaskHandler.add();
      }else if (command.equals("/task/list")) {
        TaskHandler.list();
      }else if (command.equalsIgnoreCase("exit")) {
        System.out.println("안녕!");
        break;
      }
    }
    Prompt.close();
  }
}
