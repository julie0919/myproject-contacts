package com.julie.test.handler;

import java.sql.Date;
import java.util.List;
import com.julie.test.domain.Board;
import com.julie.test.util.Prompt;

public class BoardAddHandler extends AbstractBoardHandler {

  public BoardAddHandler(List<Board> boardList) {
    super(boardList);
  }

  @Override
  public void service() {
    System.out.println("[새 게시글]");

    Board b = new Board();

    b.setNo(Prompt.printInt("번호> "));
    b.setTitle(Prompt.printString("제목> "));
    b.setContent(Prompt.printString("내용> "));
    b.setWriter(Prompt.printString("작성자> "));
    b.setRegisteredDate(new Date(System.currentTimeMillis()));

    boardList.add(b);

    System.out.println("게시글 등록을 완료했습니다.");
  }
}
