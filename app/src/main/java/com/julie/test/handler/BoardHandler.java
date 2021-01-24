package com.julie.test.handler;

import java.sql.Date;
import com.julie.test.domain.Board;
import com.julie.test.util.Prompt;

public class BoardHandler {

  static final int SIZE = 100;
  static Board[] boards = new Board[SIZE];
  static int count = 0;

  public static void add() {

    Board b = new Board();

    System.out.println("[새 게시글]");
    b.id = Prompt.printInt("번호> ");
    b.title = Prompt.printString("제목> ");
    b.content = Prompt.printString("내용> ");
    b.writer = Prompt.printString("작성자> ");
    b.registeredDate = new Date(System.currentTimeMillis());
    b.viewCount = Prompt.printInt("조회수> "); 
    boards[count++] = b;

    System.out.println("게시글 등록을 완료했습니다.");
  }

  public static void list() {
    System.out.println("[게시글 목록]");

    for (int i = 0; i < count; i++) {
      Board b = boards[i];
      System.out.printf("%d, %s, %s, 작성자: %s, 등록일: %s, 조회수: %d", b.id, b.title, b.content, b.writer, b.registeredDate, b.viewCount);
    }
  }

}
