package com.julie.test.handler;

import java.sql.Date;
import com.julie.test.domain.Board;
import com.julie.test.util.Prompt;

public class BoardHandler {

  static final int SIZE = 100;
  Board[] boards = new Board[SIZE];
  int count = 0;

  public void add() {

    Board b = new Board();

    System.out.println("[새 게시글]");
    b.id = Prompt.printInt("번호> ");
    b.title = Prompt.printString("제목> ");
    b.content = Prompt.printString("내용> ");
    b.writer = Prompt.printString("작성자> ");
    b.registeredDate = new Date(System.currentTimeMillis());
    b.viewCount = Prompt.printInt("조회수> "); 
    this.boards[this.count++] = b;

    System.out.println("게시글 등록을 완료했습니다.");
  }

  public void list() {
    System.out.println("[게시글 목록]");

    for (int i = 0; i < this.count; i++) {
      Board b = this.boards[i];

      if (b == null)
        continue;

      System.out.printf("%d, %s, %s, 작성자: %s, 등록일: %s, 조회수: %d", b.id, b.title, b.content, b.writer, b.registeredDate, b.viewCount);
    }
  }

  public void detail() {    
    System.out.println("[게시글 상세보기]");

    int id = Prompt.printInt("번호> ");

    Board board = findByNo(id);

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

    if(findByNo(id) == null) {
      System.out.println("해당 번호의 게시글이 없습니다.");   
      return;
    }  
    String title = Prompt.printString(String.format("제목 (%s)> \n", findByNo(id).title));
    String content = Prompt.printString(String.format("내용 (%s)> \n", findByNo(id).content));

    String input = Prompt.printString("위의 내용으로 수정하시겠습니까? (Y/N)");

    if (input.equalsIgnoreCase("Y")) {
      findByNo(id).title = title;
      findByNo(id).content = content;
      System.out.println("게시글을 수정하였습니다.");
    } else {
      System.out.println("게시글 수정을 취소하였습니다.");
    }

  }

  public void delete() {
    System.out.println("[게시글 삭제하기]");

    int id = Prompt.printInt("번호> ");
    if(indexOf(id) == -1) {
      System.out.println("해당 번호의 게시글이 없습니다.");    
      return;
    }

    String input = Prompt.printString("게시글을 삭제하시겠습니까? (Y/N)");

    if (input.equalsIgnoreCase("Y")) {
      for (int x = indexOf(id) + 1; x < this.count; x++) {
        this.boards[x-1] = this.boards[x];
      }
      boards[--this.count] = null;
      System.out.println("게시글을 삭제하였습니다.");
    } else {
      System.out.println("게시글 삭제를 취소하였습니다.");
    }
  }

  int indexOf(int boardNo) {
    for(int i = 0; i < this.count; i++) {
      Board board = this.boards[i];
      if(board != null && board.id == boardNo) {
        return i;
      } 
    }   
    return -1;
  }

  Board findByNo(int boardNo) {
    if(indexOf(boardNo) == -1) {
      return null;
    }
    return this.boards[indexOf(boardNo)];
  }




}
