package com.julie.test;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.LinkedList;
import com.julie.test.handler.BoardHandler;
import com.julie.test.handler.MemberHandler;
import com.julie.test.handler.ProjectHandler;
import com.julie.test.handler.TaskHandler;
import com.julie.test.util.Prompt;


public class App {

  static ArrayDeque<String> commandStack = new ArrayDeque<>();
  static LinkedList<String> commandQueue = new LinkedList<>();

  public static void main(String[] args) throws CloneNotSupportedException {

    BoardHandler boardHandler = new BoardHandler();
    MemberHandler memberHandler = new MemberHandler();
    ProjectHandler projectHandler = new ProjectHandler(memberHandler);
    TaskHandler taskHandler = new TaskHandler(memberHandler);

    while (true) {
      String command = Prompt.printString("명령> ");

      if (command.length() == 0) 
        continue;

      commandStack.push(command);
      commandQueue.offer(command);
      try {  
        if (command.equals("/member/add")) {
          memberHandler.add();
        } else if (command.equals("/member/list")) {
          memberHandler.list();
        } else if (command.equals("/member/detail")) {
          memberHandler.detail();
        }else if (command.equals("/member/update")) {
          memberHandler.update();
        }else if (command.equals("/member/delete")) {
          memberHandler.delete();
        }else if (command.equals("/project/add")) {
          projectHandler.add();
        }else if (command.equals("/project/list")) {
          projectHandler.list();
        }else if (command.equals("/project/detail")) {
          projectHandler.detail();
        }else if (command.equals("/project/update")) {
          projectHandler.update();
        }else if (command.equals("/project/delete")) {
          projectHandler.delete();
        }else if (command.equals("/task/add")) {
          taskHandler.add();
        }else if (command.equals("/task/list")) {
          taskHandler.list();
        }else if (command.equals("/task/detail")) {
          taskHandler.detail();
        }else if (command.equals("/task/update")) {
          taskHandler.update();
        }else if (command.equals("/task/delete")) {
          taskHandler.delete();
        }else if (command.equals("/board/add")) {
          boardHandler.add();
        }else if (command.equals("/board/list")) {
          boardHandler.list();
        }else if (command.equals("/board/detail")) {
          boardHandler.detail();
        }else if (command.equals("/board/update")) {
          boardHandler.update();
        }else if (command.equals("/board/delete")) {
          boardHandler.delete();
        }else if (command.equals("history")) {
          printCommandHistory(commandStack.iterator());
        }else if (command.equals("history2")) {
          printCommandHistory(commandQueue.iterator());
        }else if (command.equalsIgnoreCase("exit")) {
          System.out.println("안녕!");
          break;
        }else {
          System.out.println("실행할 수 없는 명령입니다.");
        }
      } catch (Exception e) {
        System.out.println("-----------------------------------------");
        System.out.printf("명령어 실행 중 오류 발생: %s - %s\n", 
            e.getClass().getName(), e.getMessage());
        System.out.println("-----------------------------------------");
      }
      System.out.println();
    } 
    Prompt.close();
  }

  static void printCommandHistory(Iterator<String> iterator) {

    int size = 0;
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
      if ((++size % 5) == 0) {
        String input = Prompt.printString(": ");
        if(input.equalsIgnoreCase("q")) {
          break;
        }
      }
    }
  }
}
