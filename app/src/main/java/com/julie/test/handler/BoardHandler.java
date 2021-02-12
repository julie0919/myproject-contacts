package com.julie.test.handler;

import java.sql.Date;
import com.julie.test.domain.Board;
import com.julie.test.util.Prompt;

public class BoardHandler {

  BoardList boardList = new BoardList();

  public void add() {

    System.out.println("[새 게시글]");

    Board b = new Board();

    b.id = Prompt.printInt("번호> ");
    b.title = Prompt.printString("제목> ");
    b.content = Prompt.printString("내용> ");
    b.writer = Prompt.printString("작성자> ");
    b.registeredDate = new Date(System.currentTimeMillis());

    boardList.add(b);

    System.out.println("게시글 등록을 완료했습니다.");
  }

  public void list() {
    System.out.println("[게시글 목록]");

    Board[] boards = boardList.toArray();

    for (Board b : boards){
      System.out.printf("%d, %s, %s, 작성자: %s, 등록일: %s, 조회수: %d", b.id, b.title, b.content, b.writer, b.registeredDate, b.viewCount);
    }
  }

  public void detail() {    
    System.out.println("[게시글 상세보기]");

    int id = Prompt.printInt("번호> ");

    Board board = boardList.get(id);

    if(board == null) {
      System.out.println("해당 번호의 게시글이 없습니다.");
      return;
    }  
    board.viewCount++;
    System.out.printf("제목: %s\n", board.title);
    System.out.printf("내용: %s\n", board.content);
    System.out.printf("작성자: %s\n", board.writer);
    System.out.printf("등록일: %s\n", board.registeredDate);
    System.out.printf("조회수: %s\n", board.viewCount);
  }


  public void update() {
    System.out.println("[게시글 수정하기]");

    int id = Prompt.printInt("번호> ");

    Board board = boardList.get(id);

    if(board == null) {
      System.out.println("해당 번호의 게시글이 없습니다.");   
      return;
    }  
    String title = Prompt.printString(String.format("제목 (%s)> \n", board.title));
    String content = Prompt.printString(String.format("내용 (%s)> \n", board.content));

    String input = Prompt.printString("위의 내용으로 수정하시겠습니까? (Y/N)");

    if (input.equalsIgnoreCase("Y")) {
      board.title = title;
      board.content = content;
      System.out.println("게시글을 수정하였습니다.");
    } else {
      System.out.println("게시글 수정을 취소하였습니다.");
    }

  }

  public void delete() {
    System.out.println("[게시글 삭제하기]");

    int id = Prompt.printInt("번호> ");

    Board board = boardList.get(id);
    if(board == null) {
      System.out.println("해당 번호의 게시글이 없습니다.");    
      return;
    }

    String input = Prompt.printString("게시글을 삭제하시겠습니까? (Y/N)");

    if (input.equalsIgnoreCase("Y")) {
      boardList.delete(id);
      System.out.println("게시글을 삭제하였습니다.");

    } else {
      System.out.println("게시글 삭제를 취소하였습니다.");
    }
  }
}
