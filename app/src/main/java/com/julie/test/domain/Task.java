package com.julie.test.domain;

import java.sql.Date;

public class Task {
  private int id;
  private String name;
  private Date endDate;
  private int progress;
  private String leader;
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Date getEndDate() {
    return endDate;
  }
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  public int getProgress() {
    return progress;
  }
  public void setProgress(int progress) {
    this.progress = progress;
  }
  public String getLeader() {
    return leader;
  }
  public void setLeader(String leader) {
    this.leader = leader;
  }


}
