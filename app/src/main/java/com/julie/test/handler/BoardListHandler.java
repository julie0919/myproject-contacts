package com.julie.test.handler;

import java.util.Iterator;
import java.util.List;
import com.julie.test.domain.Board;

public class BoardListHandler extends AbstractBoardHandler {

  public BoardListHandler(List<Board> boardList) {
    super(boardList);
  }

  public void service() {
    System.out.println("[게시글 목록]");

    Iterator<Board> iterator = boardList.iterator();
    while (iterator.hasNext()){
      Board b = iterator.next();

      System.out.printf("%d, %s, %s, 작성자: %s, 등록일: %s, 조회수: %d", 
          b.getId(), b.getTitle(), b.getContent(), b.getWriter(), b.getRegisteredDate(), b.getViewCount());
    }
  }

}
