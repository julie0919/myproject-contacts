package com.julie.test;

import com.julie.test.handler.BoardHandler;
import com.julie.test.handler.MemberHandler;
import com.julie.test.handler.ProjectHandler;
import com.julie.test.handler.TaskHandler;
import com.julie.test.util.Prompt;

public class App {
  public static void main(String[] args) {

    BoardHandler boardStorage = new BoardHandler();
    MemberHandler memberStorage = new MemberHandler();
    ProjectHandler projectStorage = new ProjectHandler();
    TaskHandler taskStorage = new TaskHandler();

    while (true) {
      String command = Prompt.printString("명령> ");

      if (command.equals("/member/add")) {
        memberStorage.add();
      } else if (command.equals("/member/list")) {
        memberStorage.list();
      } else if (command.equals("/project/add")) {
        projectStorage.add(memberStorage);
      }else if (command.equals("/project/list")) {
        projectStorage.list();
      }else if (command.equals("/task/add")) {
        taskStorage.add(memberStorage);
      }else if (command.equals("/task/list")) {
        taskStorage.list();
      }else if (command.equals("/board/add")) {
        boardStorage.add();
      }else if (command.equals("/board/list")) {
        boardStorage.list();
      }else if (command.equalsIgnoreCase("exit")) {
        System.out.println("안녕!");
        break;
      }
    }
    Prompt.close();
  }
}
