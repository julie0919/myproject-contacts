package com.julie.test.handler;

import java.util.List;
import com.julie.test.domain.Board;

public abstract class AbstractBoardHandler implements Command {

  protected List<Board> boardList;

  public AbstractBoardHandler(List<Board> boardList) {
    this.boardList = boardList;
  }

  protected Board findById(int boardId) {
    Board[] list = boardList.toArray(new Board[boardList.size()]);
    for (Board b : list) {
      if (b.getId() == boardId) {
        return b;
      }
    }
    return null;
  }
}
